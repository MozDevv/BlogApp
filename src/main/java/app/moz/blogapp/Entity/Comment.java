package app.moz.blogapp.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(generator = "comment_comment_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "comment_comment_id_seq", initialValue = 1, sequenceName = "comment_comment_id_seq", allocationSize = 1)
    private int commentId;

    @Column(name = "content")
    private String content;


    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;
}
