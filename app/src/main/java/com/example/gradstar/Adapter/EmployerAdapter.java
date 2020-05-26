package com.example.gradstar.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gradstar.Model.Employers;
import com.example.gradstar.ProfileFragment;
import com.example.gradstar.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmployerAdapter extends RecyclerView.Adapter<EmployerAdapter.ViewHolder>{

    private Context sContext;
    private List<Employers> sEmployers;

    FirebaseUser firebaseUser;

    public EmployerAdapter(Context sContext, List<Employers> sEmployers) {
        this.sContext = sContext;
        this.sEmployers = sEmployers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(sContext).inflate(R.layout.employer_item,parent, false);

        return new EmployerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final Employers employers = sEmployers.get(position);
        holder.btn_subscribe.setVisibility(View.VISIBLE);
        holder.employer.setText(employers.getName());
        Glide.with(sContext).load(employers.getImageurl()).into(holder.image_profile);

        isSubscribed(employers.getId(), holder.btn_subscribe);

        if(employers.getId().equals(firebaseUser.getUid()))
        {
            holder.btn_subscribe.setVisibility(View.GONE);

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                editor.putString("profile id", employers.getId());
                editor.apply();

                ((FragmentActivity)sContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_view_tag, new ProfileFragment()).commit();
            }
        });

        holder.btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.btn_subscribe.getText().toString().equals("Subscribe"))
                {
                    FirebaseDatabase.getInstance().getReference().child("Subscribe").child(firebaseUser.getUid())
                            .child("Subscriptions").child(employers.getId()).setValue(true);

                    FirebaseDatabase.getInstance().getReference().child("Subscribe").child(employers.getId())
                            .child("Subscribers").child(firebaseUser.getUid()).setValue(true);
                }
                else
                {
                    FirebaseDatabase.getInstance().getReference().child("Subscribe").child(firebaseUser.getUid())
                            .child("Subscriptions").child(employers.getId()).removeValue();

                    FirebaseDatabase.getInstance().getReference().child("Subscribe").child(employers.getId())
                            .child("Subscribers").child(firebaseUser.getUid()).removeValue();

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sEmployers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView employer;
        public CircleImageView image_profile;
        public Button btn_subscribe;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            employer = itemView.findViewById(R.id.employer_name);
            image_profile = itemView.findViewById(R.id.image_profile);
            btn_subscribe = itemView.findViewById(R.id.btn_subscribe);
        }
    }
    public void isSubscribed (final String employerid, final Button button){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Subscribe")
                .child(firebaseUser.getUid()).child("Subscriptions");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(employerid).exists())
                {
                    button.setText("Subscriptions");
                }
                else
                {
                    button.setText("Subscribe");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
