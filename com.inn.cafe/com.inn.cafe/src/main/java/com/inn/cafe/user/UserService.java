package com.inn.cafe.user;




import com.inn.cafe.common.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseServiceImpl<UserModel, String> implements UserDetailsService {
    @Autowired
  private final UserRepository userRepository;

    public UserService(UserRepository repository)
    {         super(repository);
        this.userRepository = repository;
    }

    public static UserDTO toUserDTO(UserModel userModel) {
        if (userModel==null )return null;
        return new UserDTO(userModel.getId(),userModel.getUsername());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByuserName(username).orElseThrow(()->new UsernameNotFoundException("User is Not Found "));
    }
    public UserModel getByUserName(String userName){
        return userRepository.findByuserName(userName).orElseThrow(()->new UsernameNotFoundException("User is Not Found "));
    }




}
