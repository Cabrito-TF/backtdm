package com.backtdm.controllers;

import com.backtdm.models.AuthRequest;
import com.backtdm.models.AuthResponse;
import com.backtdm.models.User;
import com.backtdm.repository.UserRepository;
import com.backtdm.services.MyUserDetailsService;
import com.backtdm.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    UserRepository userRepository;

    private List<User> users = new ArrayList<>();

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @PostMapping("/Register")
    public User setcliente(@RequestBody User user){
        users.add(user);
        return userRepository.save(user);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthRequest authRequest) throws Exception{
        System.out.println(authRequest.getUsername() + authRequest.getPassword());
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        }catch (BadCredentialsException e){
            throw new Exception("email ou senha inválidos", e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @GetMapping("/{id}")
    public User cliente(@PathVariable("id") Long id) {
        System.out.println("O id é " + id);

        Optional<User> userFind = users.stream().filter(user -> user.getId() == id).findFirst();

        if (userFind.isPresent()) {
            return userFind.get();
        }

        return null;
    }

    @GetMapping("/list")
    public List<User> list() {
        return users;
    }
}
