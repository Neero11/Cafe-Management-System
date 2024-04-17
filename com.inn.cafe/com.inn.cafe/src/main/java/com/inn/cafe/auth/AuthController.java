package com.inn.cafe.auth;



import com.inn.cafe.auth.DTO.LoginDTO;
import com.inn.cafe.auth.DTO.LoginResponse;
import com.inn.cafe.auth.DTO.SigninDTO;
import com.inn.cafe.security.JwtService;
import com.inn.cafe.user.UserModel;
import com.inn.cafe.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody LoginDTO authRequest) {
        System.out.println("LOGIN CALLED");

        Authentication authentication;
   try {
       authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.userName(), authRequest.password()));
       UserModel user = userService.getByUserName(authRequest.userName());
       SigninDTO sing =new SigninDTO(user.getId(), user.getUsername(),user.isAccountNonLocked() ,user.getRole());
       if (authentication.isAuthenticated()) {
           return  ResponseEntity.ok(new LoginResponse(jwtService.generateToken(authRequest.userName(), null)));
       } else {
           throw new UsernameNotFoundException("invalid user request !");
       }
   }
   catch (Exception ex){
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid user request");

   }



    }




}
