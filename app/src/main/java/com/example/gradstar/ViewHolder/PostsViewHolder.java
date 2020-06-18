package com.example.gradstar.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gradstar.Interface.ItemClickListener;
import com.example.gradstar.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtUser, txtDescription, txtComments;
    public CircleImageView userProfileImage;
    public ImageView postImage, likeImage, commentImage, saveImage;
    public ItemClickListener listener;

    public PostsViewHolder(@NonNull View itemView)
    {
        super(itemView);

        userProfileImage = (CircleImageView) itemView.findViewById(R.id.profile_image);
        postImage = (ImageView) itemView.findViewById(R.id.post_image);
        likeImage = (ImageView) itemView.findViewById(R.id.like);
        commentImage = (ImageView) itemView.findViewById(R.id.comment);
        saveImage = (ImageView) itemView.findViewById(R.id.save);

        txtUser = (TextView) itemView.findViewById(R.id.user);
        txtDescription = (TextView) itemView.findViewById(R.id.description);
        txtComments = (TextView) itemView.findViewById(R.id.comments);
    }

    public void setItemClickLIstener(ItemClickListener lIstener)
    {
        this.listener = listener;
    }

    @Override
    public void onClick(View v)
    {
        listener.onClick(v, getAdapterPosition(), false);
    }
}
