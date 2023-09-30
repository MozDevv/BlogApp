package app.moz.blogapp.payloads;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@NoArgsConstructor
@Getter
@Setter
public class UpdatePostDto {
    private String title;

    private String content;

    private String image;

    private Date dateCreated;
}
