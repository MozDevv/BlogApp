package app.moz.blogapp.payloads;

import app.moz.blogapp.Entity.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;



@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CategoryDto {

    private  int categoryId;

    @NotBlank
    @Size(min = 5, message = "minimum letters should be 5")
    private String title;

    private String description;


    private List<PostDTO> posts = new ArrayList<>();


}
