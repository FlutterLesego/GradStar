package com.example.gradstar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class PostActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{
    private Toolbar aToolbar;
    private ImageButton SelectPostImage;
    private ImageButton UpdatePostButton;
    private EditText PostTitle, PostDescription;

    private static final int Gallery_Pick = 1;
    private Uri ImageUri;
    private String Description;
    private String JobTitle;
    private String postRandomKey, downloadImageURL;

    private StorageReference PostImagesReference;
    private DatabaseReference JobRef;
    private ProgressDialog loadingBar;

    private String saveCurrentDate, saveCurrentTime, postRandomName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Button dateButton = (Button) findViewById(R.id.date_button);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Closing Date");

            }
        });

        PostImagesReference = FirebaseStorage.getInstance().getReference().child("JobsImages");
        JobRef = FirebaseDatabase.getInstance().getReference().child("Jobs");

        SelectPostImage = (ImageButton) findViewById(R.id.add_imageButton);
        UpdatePostButton = (ImageButton) findViewById(R.id.post_imageButton);
        PostTitle = (EditText) findViewById(R.id.post_title);
        PostDescription = (EditText) findViewById(R.id.post_description);
        loadingBar = new ProgressDialog(this);

        aToolbar = (Toolbar) findViewById(R.id.admin_post_update_toolbar);
        setSupportActionBar(aToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Create a post");

        SelectPostImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                OpenGallery();
            }
        });

        UpdatePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ValidatePostInfo();

            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView closingDate = (TextView) findViewById(R.id.closing_date);
        closingDate.setText(currentDateString);

    }

    private void ValidatePostInfo()
    {
        JobTitle = PostTitle.getText().toString();
        Description = PostDescription.getText().toString();

        if (TextUtils.isEmpty(JobTitle))
        {
            Toast.makeText(this, "Please write a job title", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Description))
        {
            Toast.makeText(this, "Please write a job description", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoringImageToFirebaseStorage();
        }
    }

    private void StoringImageToFirebaseStorage()
    {

        loadingBar.setTitle("Adding Job");
        loadingBar.setMessage("please wait while we are adding the new job");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-YYYY");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calendar.getTime());

        postRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = PostImagesReference.child(ImageUri.getLastPathSegment() + postRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) 
            {
                String message = e.toString();
                Toast.makeText(PostActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(PostActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw  task.getException();
                        }

                        downloadImageURL = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }

                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) 
                    {
                        if (task.isSuccessful())
                        {
                            downloadImageURL = task.getResult().toString();

                            Toast.makeText(PostActivity.this, "Image saved to database successfully", Toast.LENGTH_SHORT).show();

                            SaveInfoToDatabase();
                        }
                        
                    }
                });
            }
        });

    }

    private void SaveInfoToDatabase()
    {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", postRandomName);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("title", JobTitle);
        productMap.put("description", Description);

        JobRef.child(postRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent PostIntent = new Intent(PostActivity.this, AdminProfessionalActivity.class);
                            startActivity(PostIntent);

                            loadingBar.dismiss();
                            Toast.makeText(PostActivity.this, "Job posted successfully!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(PostActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/+");
        startActivityForResult(galleryIntent,Gallery_Pick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==Gallery_Pick && resultCode==RESULT_OK && data!=null)
        {
            ImageUri = data.getData();
            SelectPostImage.setImageURI(ImageUri);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();

        if (id==android.R.id.home)
        {
            SendUserToAdminPanelActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    private void SendUserToAdminPanelActivity()
    {
        Intent adminIntent = new Intent(PostActivity.this, AdminPanelActivity.class);
        startActivity(adminIntent);
    }
}
