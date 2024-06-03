package com.talan.adminmodule.config;
import com.talan.adminmodule.entity.Role;
import com.talan.adminmodule.entity.User;
import com.talan.adminmodule.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserInitializer {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        if (userRepository.findByEmail("wael.arfaoui@talan.com").isEmpty()) {
            User user = new User();
            user.setEmail("admin@talan.com");
            user.setPassword(passwordEncoder.encode("123"));
            user.setFirstname("Admin");
            user.setLastname(" ");
            user.setRole(Role.ADMIN);
            user.setPhone("58623120");
            userRepository.save(user);
        }
    }
}
