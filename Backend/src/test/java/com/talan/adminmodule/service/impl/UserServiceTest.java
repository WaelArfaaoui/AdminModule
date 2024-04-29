package com.talan.adminmodule.service.impl;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.talan.adminmodule.dto.RegisterDto;
import com.talan.adminmodule.dto.UserDto;
import com.talan.adminmodule.entity.Role;
import com.talan.adminmodule.entity.User;
import com.talan.adminmodule.repository.UserRepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {UserService.class, PasswordEncoder.class})
@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * Method under test: {@link UserService#addUser(RegisterDto, MultipartFile)}
     */
    @Test
    void testAddUser() throws IOException {
        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
            mockFiles.when(() -> Files.exists(Mockito.<Path>any(), (LinkOption[]) any())).thenReturn(true);
            mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), (FileAttribute[]) any()))
                    .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
            mockFiles.when(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), (CopyOption[]) any()))
                    .thenReturn(1L);
            when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<UserDto>>any())).thenReturn(UserDto.builder()
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

            User user = new User();
            user.setActive(true);
            user.setCompany("Company");
            user.setEmail("jane.doe@example.org");
            user.setFirstname("Jane");
            user.setId(1);
            user.setLastname("Doe");
            user.setNonExpired(true);
            user.setPassword("iloveyou");
            user.setPhone("6625550144");
            user.setProfileImagePath("Profile Image Path");
            user.setRole(Role.BUSINESSEXPERT);
            when(userRepository.save(Mockito.<User>any())).thenReturn(user);
            when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");
            RegisterDto dto = new RegisterDto("Jane", "Doe", "iloveyou", "jane.doe@example.org", "Company", "6625550144",
                    Role.BUSINESSEXPERT);

            userService.addUser(dto, new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));
            mockFiles.verify(() -> Files.exists(Mockito.<Path>any(), (LinkOption[]) any()));
            mockFiles.verify(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), (CopyOption[]) any()));
            verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<UserDto>>any());
            verify(userRepository).save(Mockito.<User>any());
            verify(passwordEncoder).encode(Mockito.<CharSequence>any());
        }
    }
}

