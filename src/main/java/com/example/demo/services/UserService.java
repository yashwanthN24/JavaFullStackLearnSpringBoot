package com.example.demo.services;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.SignUpDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entities.User;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()-> new BadCredentialsException(" User with email" + username + " not found " ));
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(" User with id " + id  + " not found " ));
    }

    public UserDTO signup(SignUpDTO signUpDTO) {
       Optional<User> user = userRepository.findByEmail(signUpDTO.getEmail());
       if(user.isPresent()){
           throw new BadCredentialsException(" user with email already exists " + signUpDTO.getEmail());
       }

       User toBeCreatedUser = modelMapper.map(signUpDTO , User.class);
        toBeCreatedUser.setPassword(passwordEncoder.encode(toBeCreatedUser.getPassword()));
//       save the user in db to add resgistration onto our platform
        User savedUser = userRepository.save(toBeCreatedUser);

        return modelMapper.map(savedUser , UserDTO.class);

    }


}
