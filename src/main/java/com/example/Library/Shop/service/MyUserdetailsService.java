package com.example.Library.Shop.service;
import com.example.Library.Shop.model.Users;
import com.example.Library.Shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserdetailsService implements UserDetailsService {

private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("User not found" + username));
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new MyUser(user);
    }
}
