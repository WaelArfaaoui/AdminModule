package com.talan.adminmodule.controller;

import static org.mockito.Mockito.when;

import com.talan.adminmodule.dto.RegisterDto;
import com.talan.adminmodule.dto.UserDto;
import com.talan.adminmodule.entity.Role;
import com.talan.adminmodule.service.impl.UserService;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link UserController#addUser(RegisterDto, MultipartFile)}
     */
    @Test
    void testAddUser() throws Exception {
        when(userService.getAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/users");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("file", String.valueOf((Object) null));
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link UserController#addUser(RegisterDto, MultipartFile)}
     */
    @Test
    void testAddUser2() throws Exception {
        ArrayList<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(UserDto.builder()
                .active(true)
                .company("Company")
                .email("jane.doe@example.org")
                .error("An error occurred")
                .firstname("Jane")
                .id(1L)
                .lastname("Doe")
                .nonExpired(true)
                .phone("6625550144")
                .profileImagePath("Profile Image Path")
                .role(Role.BUSINESSEXPERT)
                .build());
        when(userService.getAll()).thenReturn(userDtoList);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/users");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("file", String.valueOf((Object) null));
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":1,\"firstname\":\"Jane\",\"lastname\":\"Doe\",\"email\":\"jane.doe@example.org\",\"active\":true,\"nonExpired"
                                        + "\":true,\"profileImagePath\":\"Profile Image Path\",\"phone\":\"6625550144\",\"company\":\"Company\",\"role\":"
                                        + "\"BUSINESSEXPERT\",\"error\":\"An error occurred\"}]"));
    }

    /**
     * Method under test: {@link UserController#addUser(RegisterDto, MultipartFile)}
     */
    @Test
    void testAddUser3() throws Exception {
        ArrayList<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(UserDto.builder()
                .active(true)
                .company("Company")
                .email("jane.doe@example.org")
                .error("An error occurred")
                .firstname("Jane")
                .id(1L)
                .lastname("Doe")
                .nonExpired(true)
                .phone("6625550144")
                .profileImagePath("Profile Image Path")
                .role(Role.BUSINESSEXPERT)
                .build());
        userDtoList.add(UserDto.builder()
                .active(true)
                .company("Company")
                .email("jane.doe@example.org")
                .error("An error occurred")
                .firstname("Jane")
                .id(1L)
                .lastname("Doe")
                .nonExpired(true)
                .phone("6625550144")
                .profileImagePath("Profile Image Path")
                .role(Role.BUSINESSEXPERT)
                .build());
        when(userService.getAll()).thenReturn(userDtoList);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/users");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("file", String.valueOf((Object) null));
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":1,\"firstname\":\"Jane\",\"lastname\":\"Doe\",\"email\":\"jane.doe@example.org\",\"active\":true,\"nonExpired"
                                        + "\":true,\"profileImagePath\":\"Profile Image Path\",\"phone\":\"6625550144\",\"company\":\"Company\",\"role\":"
                                        + "\"BUSINESSEXPERT\",\"error\":\"An error occurred\"},{\"id\":1,\"firstname\":\"Jane\",\"lastname\":\"Doe\",\"email\":"
                                        + "\"jane.doe@example.org\",\"active\":true,\"nonExpired\":true,\"profileImagePath\":\"Profile Image Path\",\"phone"
                                        + "\":\"6625550144\",\"company\":\"Company\",\"role\":\"BUSINESSEXPERT\",\"error\":\"An error occurred\"}]"));
    }
}

