package com.talan.AdminModule.service.impl;


import com.talan.AdminModule.dto.ChangePassword;
import com.talan.AdminModule.dto.RegisterDto;
import com.talan.AdminModule.dto.UserDto;
import com.talan.AdminModule.entity.User;
import com.talan.AdminModule.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserService {
    @Autowired
    private  ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
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
            Path uploadDir = Paths.get(currentDir,"..","Front", "src","assets","demo","images", "user-profiles");
            Path storeDir = Paths.get("assets","demo","images","user-profiles",fileName);
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
    public UserDto addUser(RegisterDto dto, MultipartFile file) throws IOException {
        String profileImagePath = null;
        if (file != null && !file.isEmpty()) {
            profileImagePath = storeProfileImage(file);
        }
        User user = User.builder()
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .email(dto.getEmail())
                .company(dto.getCompany())
                .phone(dto.getPhone())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRole())
                .profileImagePath(profileImagePath)
                .build();

        userRepository.save(user);
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
    public UserDto update(int id, RegisterDto dto, MultipartFile file) throws IOException {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }

        if (dto.getFirstname() != null) {
            user.setFirstname(dto.getFirstname());
        }
        if (dto.getLastname() != null) {
            user.setLastname(dto.getLastname());
        }
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getPhone() != null) {
            user.setPhone(dto.getPhone());
        }
        if (dto.getRole() != null) {
            user.setRole(dto.getRole());
        }
        if (file != null && !file.isEmpty()) {
            String profileImagePath = storeProfileImage(file);
            user.setProfileImagePath(profileImagePath);
        }

        userRepository.save(user);
        return mapUserToDto(user);
    }

    public User findbyemail(String email)
    {
        return userRepository.findByEmail(email).orElse(null);
    }


}