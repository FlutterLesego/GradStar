package com.example.gradstar.Model;

public class Employers
{
    private String id;
    private String name;
    private String imageurl;
    private String bio;


    public Employers(String id, String name, String imageurl, String bio) {
        this.id = id;
        this.name = name;
        this.imageurl = imageurl;
        this.bio = bio;
    }

    public Employers() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
