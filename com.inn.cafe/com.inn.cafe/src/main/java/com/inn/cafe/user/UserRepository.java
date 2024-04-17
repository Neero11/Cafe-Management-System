package com.inn.cafe.user;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserModel, String> {

 public Optional<UserModel> findByuserName(String username);

}
