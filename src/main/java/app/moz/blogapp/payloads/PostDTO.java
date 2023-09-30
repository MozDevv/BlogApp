package app.moz.blogapp.payloads;

import java.util.Date;

public class PostDTO {
    private int postId;

    private String title;

    private String content;

    private String image;

    private Date dateCreated;



    public PostDTO(){

    }

    public PostDTO(int postId, String title, String content, String image, Date dateCreated) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.image = image;
        this.dateCreated = dateCreated;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
