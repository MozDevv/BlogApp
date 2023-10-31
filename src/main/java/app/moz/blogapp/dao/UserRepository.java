package app.moz.blogapp.dao;

import app.moz.blogapp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {


   Optional <User> findByEmail(String email);

   @Query("SELECT u.id FROM User u WHERE u.email = ?1")
   Integer findIdByEmail(String email);




}
