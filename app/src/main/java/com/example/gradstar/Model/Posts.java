package com.example.gradstar.Model;

public class Posts
{
    private String postDescription, postImage, profileImage, name;

    public Posts( String postDescription, String postImage, String profileImage, String name) {
        this.postDescription = postDescription;
        this.postImage = postImage;
        this.profileImage = profileImage;
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }


    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public Posts()
    {

    }
}
