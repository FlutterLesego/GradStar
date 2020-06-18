package com.example.gradstar;

public class Posts
{
    public String name, description, postid, postimage, profileimage;

    public Posts()
    {

    }

    public Posts(String name, String description, String postid, String postimage, String profileimage) {
        this.name = name;
        this.description = description;
        this.postid = postid;
        this.postimage = postimage;
        this.profileimage = profileimage;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }
}
