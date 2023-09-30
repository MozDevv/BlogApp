package app.moz.blogapp.payloads;

import app.moz.blogapp.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {


    private int id;



    @NotEmpty
    @Size(min = 6, message = "Username should have a minimum of 6 Characters!")
    private String name;
    @Email(message = "Email address is not valid")
    private String email;

    @NotEmpty
    @Size(min = 6, message = "Password should be min of 6")
    private String password;

    @NotEmpty
    private String about;

    private Role role;


}
