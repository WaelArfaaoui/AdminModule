package com.talan.AdminModule.controllers;

import com.talan.AdminModule.dto.ChangePassword;
import com.talan.AdminModule.dto.RegisterDto;
import com.talan.AdminModule.dto.UserDto;
import com.talan.AdminModule.entities.Role;
import com.talan.AdminModule.entities.User;
import com.talan.AdminModule.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.NonUniqueObjectException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userservice;
    private final ModelMapper modelMapper;


    @GetMapping()
    public List<UserDto> getusers() {
        return userservice.getAll();
    }

    @PostMapping("/add")
    public ResponseEntity<UserDto> add(@RequestBody RegisterDto dto) throws IOException {
        if (userservice.findbyemail(dto.getEmail())!=null){
            throw new RuntimeException("Email already exists");
        }

        return ResponseEntity.ok(userservice.addUser(dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable int id ,
                                       @RequestParam(value="firstname", required = false) String firstname,
                                       @RequestParam(value="lastname", required = false) String lastname,
                                       @RequestParam(value= "email", required = false) String email,
                                       @RequestParam(value="password", required = false) String password,
                                       @RequestParam(value= "phone", required = false) String phone,
                                       @RequestParam(value= "role", required = false) Role role,
                                       @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        if (userservice.findbyemail(email)!=null){
            UserDto userDto=UserDto.builder()
                    .error("Email already exists").build();
            return new ResponseEntity<>(userDto, HttpStatus.BAD_REQUEST);
        }
        UserDto userDto = userservice.update(id,firstname,lastname, email, password, phone, role, file);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }


    @PatchMapping("/changepassword")
    public ResponseEntity<ChangePassword> changePassword(
          @RequestBody ChangePassword request,
          Principal connectedUser
    ) {
       ChangePassword changePassword=   userservice.changePassword(request, connectedUser);
        return new ResponseEntity<>(changePassword, HttpStatus.OK);
    }



    @GetMapping("/get/{email}")
    public ResponseEntity<UserDto> getUser(@PathVariable String email){
        User user = this.userservice.findbyemail(email);
        UserDto userDto = modelMapper.map(user, UserDto.class) ;
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        userservice.delete(id);
    }


}
