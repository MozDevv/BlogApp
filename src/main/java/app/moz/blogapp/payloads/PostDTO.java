package app.moz.blogapp.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor

public class PostDTO {
    private int postId;

    private String title;

    private String content;


    private String image;


    private Date dateCreated;

    @Getter
    private List<CommentDto> comments;

    @Getter
    private int categoryId;


    private  int userId;
    public PostDTO(){

    }

    public PostDTO(int postId, String title, String content, String image, Date dateCreated, int categoryId) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.image = image;
        this.dateCreated = dateCreated;
        this.categoryId = categoryId;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }
    public void setPostId(int postId) {
        this.postId = postId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
