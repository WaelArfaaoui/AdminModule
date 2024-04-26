package com.talan.AdminModule.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talan.AdminModule.dto.ChangePassword;
import com.talan.AdminModule.dto.RegisterDto;
import com.talan.AdminModule.dto.UserDto;
import com.talan.AdminModule.entity.User;
import com.talan.AdminModule.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
@Tag(name = "User")
public class UserController {
    @Autowired
    private UserService userservice;
    @Autowired
    private ModelMapper modelMapper;


    @GetMapping()
    public List<UserDto> getusers() {
        return userservice.getAll();
    }

    @PostMapping()
    public ResponseEntity<UserDto> addUser(@ModelAttribute RegisterDto dto,
                                           @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        UserDto userDto = this.userservice.addUser(dto, file);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable int id,
                                              @RequestParam(value = "file", required = false) MultipartFile file,
                                              @RequestParam("dto") String dtoJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RegisterDto dto = objectMapper.readValue(dtoJson, RegisterDto.class);

            UserDto updatedUser = this.userservice.update(id, dto, file);
            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.notFound().build(); // User with given ID not found
            }
        } catch (IOException e) {
            // Handle IO exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




    @PatchMapping("/changepassword")
    public ResponseEntity<ChangePassword> changePassword(
            @RequestBody ChangePassword request,
            Principal connectedUser
    ) {
        ChangePassword changePassword=   userservice.changePassword(request, connectedUser);
        return new ResponseEntity<>(changePassword, HttpStatus.OK);
    }



    @GetMapping("/{email}")
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
