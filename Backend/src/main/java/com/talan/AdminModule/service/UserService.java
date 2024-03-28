package com.talan.AdminModule.service;


import com.talan.AdminModule.config.ApplicationConfig;
import com.talan.AdminModule.dto.ChangePassword;
import com.talan.AdminModule.dto.UserDto;
import com.talan.AdminModule.entities.Role;
import com.talan.AdminModule.entities.User;
import com.talan.AdminModule.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    public UserDto mapUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
    public ChangePassword changePassword(ChangePassword request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            return ChangePassword.builder()
                    .message("Wrong password").build();
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            return ChangePassword.builder()
                    .message("Passwords are not the same").build();
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        userRepository.save(user);
        return ChangePassword.builder()
                .message("Password Changed").build();
    }
    public String storeProfileImage(MultipartFile profileImage) throws IOException {
        String imagePath = null;
        String path ="";
        if (profileImage != null && !profileImage.isEmpty()) {
            String fileName = StringUtils.cleanPath(profileImage.getOriginalFilename());
            String currentDir = System.getProperty("user.dir");
            Path uploadDir = Paths.get(currentDir,"Front", "src","assets","demo","images", "user-profiles");
            Path storeDir = Paths.get("images","user-profiles",fileName);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            try (InputStream inputStream = profileImage.getInputStream()) {
                Path filePath = uploadDir.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                imagePath = filePath.toAbsolutePath().toString();
                path= storeDir.toString();
            } catch (IOException ex) {
                throw new IOException("Could not store file " + fileName + ". Please try again!", ex);
            }
        }
       return path;
    }
public UserDto addUser(String firstname, String lastname, String email, String password, String phone, Role role, MultipartFile file) throws IOException {

        User user = new User();

        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setPhone(phone);
        user.setRole(role);
        if (file != null) {
            String path = storeProfileImage(file);
            user.setProfileImagePath(path);
        }
    return mapUserToDto(user);

    }
public List<UserDto> getAll(){
        List<User>  users= userRepository.findAll();
    return users.stream()
            .map(this::mapUserToDto)
            .collect(Collectors.toList());
}
public void delete (int id){
      User user =  userRepository.findById(id).orElse(null);
    userRepository.delete(user);
}
public UserDto update(int id,String firstname,String lastname, String email, String password, String phone, Role role, MultipartFile file) throws IOException {
        User user = userRepository.findById(id).orElse(null);
        if (firstname!=null){
            user.setFirstname(firstname);
        }
    if (lastname!=null){
        user.setLastname(lastname);
    }
        if (email!=null){
            user.setEmail(email);
        }
        if (password!=null){
            user.setPassword(passwordEncoder.encode(password));
        }
        if (phone!=null){
            user.setPhone(phone);
        }
        if (role!=null){
            user.setRole(role);
        }
    if(file!=null){
        String path = storeProfileImage(file);
        user.setProfileImagePath(path);
    }
    userRepository.save(user);
    return   mapUserToDto(user);
}
public User findbyemail(String email)
{
    return userRepository.findByEmail(email).orElse(null);
}


}
