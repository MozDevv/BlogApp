package app.moz.blogapp.service;

import app.moz.blogapp.Entity.User;
import app.moz.blogapp.payloads.PostDTO;
import app.moz.blogapp.payloads.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {


    UserDto createUser(UserDto userDto);

    List<UserDto> getAllUsers ();

    UserDto updateUser(UserDto userDto, int id);

    UserDto getUserById( int id);

    void deleteUser(int id);

    Optional<User> getUserByEmail(String email);

    Integer getUserIdbyEmail(String email);



}
