package app.moz.blogapp.service;

import app.moz.blogapp.Entity.Role;
import app.moz.blogapp.Entity.User;
import app.moz.blogapp.dao.UserRepository;
import app.moz.blogapp.payloads.UserDto;
import app.moz.blogapp.Exceptions.ResourceNotFound;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;

   private final PasswordEncoder passwordEncoder;
   
    private ModelMapper modelMapper;

    private UserServiceImpl(UserRepository theUserRepository, PasswordEncoder passwordEncoder, ModelMapper theModelMapper) {
        userRepository = theUserRepository;
        this.passwordEncoder = passwordEncoder;
        modelMapper = theModelMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        String email = userDto.getEmail();

        // Check if an existing user with the same email exists
        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
           throw new RuntimeException("Email Already Exists");
        }

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());

        userDto.setPassword(encodedPassword);

        userDto.setRole(Role.USER);

        User user = modelMapper.map(userDto, User.class);



        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserDto.class);
    }


    @Override
    public List<UserDto> getAllUsers() {

        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto userDto, int id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new ResourceNotFound("User with Id " + id + "Not found");
        }

        User existingUser = user.get();

        existingUser.setName(userDto.getName());

        existingUser.setEmail(userDto.getEmail());

        existingUser.setAbout(userDto.getAbout());

        existingUser.setPassword(userDto.getPassword());

        User user1 = userRepository.save(existingUser);

        return modelMapper.map(user1, UserDto.class);
    }

    @Override
    public UserDto getUserById(int id) {

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new ResourceNotFound("User with Id " + id + "Not found");
        }

        return modelMapper.map(user.get(), UserDto.class);
    }


    @Override
    public void deleteUser(int id) {

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new ResourceNotFound("User with id " + id + "Not found");

        }

        userRepository.delete(user.get());

    }

    @Override
    public Optional<User> getUserByEmail(String email) {

      Optional<User> userOptional = userRepository.findByEmail(email);



        return userOptional;
    }

    @Override
    public Integer getUserIdbyEmail(String email) {

        return userRepository.findIdByEmail(email);


    }




}
