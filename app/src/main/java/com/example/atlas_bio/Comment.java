package com.example.atlas_bio;

import java.io.Serializable;

public class Comment  {

    private String content ;

    private String author;

    public Comment(){

    }

    public Comment( String content, String author){
        this.author = author;
        this.content =content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
