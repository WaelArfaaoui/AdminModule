package com.talan.adminmodule.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sun.security.auth.UserPrincipal;
import com.talan.adminmodule.dto.ChangePassword;
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
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {UserService.class, PasswordEncoder.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UserServiceDiffblueTest {
    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * Method under test: {@link UserService#mapUserToDto(User)}
     */
    @Test
    void testMapUserToDto() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        ModelMapper modelMapper = new ModelMapper();
        UserService userService = new UserService(modelMapper, new BCryptPasswordEncoder(), mock(UserRepository.class));

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

        // Act
        UserDto actualMapUserToDtoResult = userService.mapUserToDto(user);

        // Assert
        assertEquals("6625550144", actualMapUserToDtoResult.getPhone());
        assertEquals("Company", actualMapUserToDtoResult.getCompany());
        assertEquals("Doe", actualMapUserToDtoResult.getLastname());
        assertEquals("Jane", actualMapUserToDtoResult.getFirstname());
        assertEquals("Profile Image Path", actualMapUserToDtoResult.getProfileImagePath());
        assertEquals("jane.doe@example.org", actualMapUserToDtoResult.getEmail());
        assertEquals(1L, actualMapUserToDtoResult.getId().longValue());
        assertEquals(Role.BUSINESSEXPERT, actualMapUserToDtoResult.getRole());
        assertTrue(actualMapUserToDtoResult.isActive());
        assertTrue(actualMapUserToDtoResult.isNonExpired());
    }

    /**
     * Method under test: {@link UserService#mapUserToDto(User)}
     */
    @Test
    void testMapUserToDto2() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        ModelMapper modelMapper = mock(ModelMapper.class);
        UserDto buildResult = UserDto.builder()
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
                .build();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<UserDto>>any())).thenReturn(buildResult);
        UserService userService = new UserService(modelMapper, new BCryptPasswordEncoder(), mock(UserRepository.class));

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

        // Act
        userService.mapUserToDto(user);

        // Assert
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
    }

    /**
     * Method under test: {@link UserService#mapUserToDto(User)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testMapUserToDto3() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@25c28eae testClass = com.talan.adminmodule.service.impl.DiffblueFakeClass673, locations = [], classes = [com.talan.adminmodule.service.impl.UserService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@3c57f52d, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@61abe1f3, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@471b66fe, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@25b52b55], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //       at java.base/java.util.Optional.map(Optional.java:260)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange
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

        // Act
        userService.mapUserToDto(user);
    }

    /**
     * Method under test:
     * {@link UserService#changePassword(ChangePassword, Principal)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testChangePassword() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@5e1f01cf testClass = com.talan.adminmodule.service.impl.DiffblueFakeClass244, locations = [], classes = [com.talan.adminmodule.service.impl.UserService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@3c57f52d, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@61abe1f3, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@471b66fe, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@25b52b55], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //       at java.base/java.util.Optional.map(Optional.java:260)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange and Act
        userService.changePassword(null, new UserPrincipal("connectedUser"));
    }

    /**
     * Method under test: {@link UserService#storeProfileImage(MultipartFile)}
     */
    @Test
    void testStoreProfileImage() throws IOException {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        ModelMapper modelMapper = new ModelMapper();

        // Act and Assert
        assertEquals("", (new UserService(modelMapper, new BCryptPasswordEncoder(), mock(UserRepository.class)))
                .storeProfileImage(null));
    }

    /**
     * Method under test: {@link UserService#storeProfileImage(MultipartFile)}
     */
    @Test
    void testStoreProfileImage2() throws IOException {
        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
            //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

            // Arrange
            mockFiles.when(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class))).thenReturn(true);
            mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
                    .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
            mockFiles.when(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), isA(CopyOption[].class)))
                    .thenReturn(1L);
            ModelMapper modelMapper = new ModelMapper();
            UserService userService = new UserService(modelMapper, new BCryptPasswordEncoder(), mock(UserRepository.class));

            // Act
            String actualStoreProfileImageResult = userService
                    .storeProfileImage(new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

            // Assert
            mockFiles.verify(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), isA(CopyOption[].class)));
            mockFiles.verify(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)));
            assertEquals("assets\\demo\\images\\user-profiles", actualStoreProfileImageResult);
        }
    }

    /**
     * Method under test: {@link UserService#storeProfileImage(MultipartFile)}
     */
    @Test
    void testStoreProfileImage3() throws IOException {
        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
            //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

            // Arrange
            mockFiles.when(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class))).thenReturn(false);
            mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
                    .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
            mockFiles.when(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), isA(CopyOption[].class)))
                    .thenReturn(1L);
            ModelMapper modelMapper = new ModelMapper();
            UserService userService = new UserService(modelMapper, new BCryptPasswordEncoder(), mock(UserRepository.class));

            // Act
            String actualStoreProfileImageResult = userService
                    .storeProfileImage(new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

            // Assert
            mockFiles.verify(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), isA(CopyOption[].class)));
            mockFiles.verify(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)));
            mockFiles.verify(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)));
            assertEquals("assets\\demo\\images\\user-profiles", actualStoreProfileImageResult);
        }
    }

    /**
     * Method under test: {@link UserService#storeProfileImage(MultipartFile)}
     */
    @Test
    void testStoreProfileImage4() throws IOException {
        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
            //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

            // Arrange
            mockFiles.when(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class))).thenReturn(true);
            mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
                    .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
            mockFiles.when(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), isA(CopyOption[].class)))
                    .thenReturn(1L);
            ModelMapper modelMapper = new ModelMapper();
            UserService userService = new UserService(modelMapper, new BCryptPasswordEncoder(), mock(UserRepository.class));

            // Act and Assert
            assertEquals("",
                    userService.storeProfileImage(new MockMultipartFile("Name", new ByteArrayInputStream(new byte[]{}))));
        }
    }

    /**
     * Method under test: {@link UserService#storeProfileImage(MultipartFile)}
     */
    @Test
    void testStoreProfileImage5() throws IOException {
        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
            //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

            // Arrange
            mockFiles.when(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class))).thenReturn(true);
            mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
                    .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
            mockFiles.when(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), isA(CopyOption[].class)))
                    .thenReturn(1L);
            ModelMapper modelMapper = new ModelMapper();
            UserService userService = new UserService(modelMapper, new BCryptPasswordEncoder(), mock(UserRepository.class));

            // Act
            String actualStoreProfileImageResult = userService.storeProfileImage(new MockMultipartFile("user.dir", "foo.txt",
                    "text/plain", new ByteArrayInputStream(new byte[]{'A', 7, 'A', 7, 'A', 7, 'A', 7})));

            // Assert
            mockFiles.verify(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), isA(CopyOption[].class)));
            mockFiles.verify(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)));
            assertEquals("assets\\demo\\images\\user-profiles\\foo.txt", actualStoreProfileImageResult);
        }
    }

    /**
     * Method under test: {@link UserService#storeProfileImage(MultipartFile)}
     */
    @Test
    void testStoreProfileImage6() throws IOException {
        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
            //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

            // Arrange
            mockFiles.when(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class))).thenReturn(true);
            mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
                    .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
            mockFiles.when(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), isA(CopyOption[].class)))
                    .thenReturn(1L);
            ModelMapper modelMapper = new ModelMapper();
            UserService userService = new UserService(modelMapper, new BCryptPasswordEncoder(), mock(UserRepository.class));
            MultipartFile profileImage = mock(MultipartFile.class);
            when(profileImage.getInputStream()).thenReturn(null);
            when(profileImage.isEmpty()).thenReturn(false);
            when(profileImage.getOriginalFilename()).thenReturn("foo.txt");

            // Act
            String actualStoreProfileImageResult = userService.storeProfileImage(profileImage);

            // Assert
            mockFiles.verify(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), isA(CopyOption[].class)));
            mockFiles.verify(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)));
            verify(profileImage).getInputStream();
            verify(profileImage).getOriginalFilename();
            verify(profileImage).isEmpty();
            assertEquals("assets\\demo\\images\\user-profiles\\foo.txt", actualStoreProfileImageResult);
        }
    }

    /**
     * Method under test: {@link UserService#storeProfileImage(MultipartFile)}
     */
    @Test
    void testStoreProfileImage7() throws IOException {
        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
            //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

            // Arrange
            mockFiles.when(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class))).thenReturn(true);
            mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
                    .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
            mockFiles.when(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), isA(CopyOption[].class)))
                    .thenReturn(1L);
            ModelMapper modelMapper = new ModelMapper();
            UserService userService = new UserService(modelMapper, new BCryptPasswordEncoder(), mock(UserRepository.class));
            MultipartFile profileImage = mock(MultipartFile.class);
            when(profileImage.getInputStream()).thenReturn(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));
            when(profileImage.isEmpty()).thenReturn(false);
            when(profileImage.getOriginalFilename()).thenReturn("foo");

            // Act
            String actualStoreProfileImageResult = userService.storeProfileImage(profileImage);

            // Assert
            mockFiles.verify(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), isA(CopyOption[].class)));
            mockFiles.verify(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)));
            verify(profileImage).getInputStream();
            verify(profileImage).getOriginalFilename();
            verify(profileImage).isEmpty();
            assertEquals("assets\\demo\\images\\user-profiles\\foo", actualStoreProfileImageResult);
        }
    }

    /**
     * Method under test: {@link UserService#storeProfileImage(MultipartFile)}
     */
    @Test
    void testStoreProfileImage8() throws IOException {
        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
            //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

            // Arrange
            mockFiles.when(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class))).thenReturn(true);
            mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
                    .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
            mockFiles.when(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), isA(CopyOption[].class)))
                    .thenReturn(1L);
            ModelMapper modelMapper = new ModelMapper();
            UserService userService = new UserService(modelMapper, new BCryptPasswordEncoder(), mock(UserRepository.class));
            MultipartFile profileImage = mock(MultipartFile.class);
            when(profileImage.getInputStream()).thenThrow(new IOException("user.dir"));
            when(profileImage.isEmpty()).thenReturn(false);
            when(profileImage.getOriginalFilename()).thenReturn("foo.txt");

            // Act and Assert
            assertThrows(IOException.class, () -> userService.storeProfileImage(profileImage));
            mockFiles.verify(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)));
            verify(profileImage).getInputStream();
            verify(profileImage).getOriginalFilename();
            verify(profileImage).isEmpty();
        }
    }

    /**
     * Method under test: {@link UserService#storeProfileImage(MultipartFile)}
     */
    @Test
    void testStoreProfileImage9() throws IOException {
        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
            //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

            // Arrange
            mockFiles.when(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class))).thenReturn(true);
            mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
                    .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
            mockFiles.when(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), isA(CopyOption[].class)))
                    .thenReturn(1L);
            ModelMapper modelMapper = new ModelMapper();
            UserService userService = new UserService(modelMapper, new BCryptPasswordEncoder(), mock(UserRepository.class));
            MultipartFile profileImage = mock(MultipartFile.class);
            when(profileImage.getInputStream()).thenThrow(new IOException("user.dir"));
            when(profileImage.isEmpty()).thenReturn(false);
            when(profileImage.getOriginalFilename()).thenReturn("..");

            // Act and Assert
            assertThrows(IOException.class, () -> userService.storeProfileImage(profileImage));
            mockFiles.verify(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)));
            verify(profileImage).getInputStream();
            verify(profileImage).getOriginalFilename();
            verify(profileImage).isEmpty();
        }
    }

    /**
     * Method under test: {@link UserService#storeProfileImage(MultipartFile)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testStoreProfileImage10() throws IOException {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@42a81f92 testClass = com.talan.adminmodule.service.impl.DiffblueFakeClass973, locations = [], classes = [com.talan.adminmodule.service.impl.UserService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@3c57f52d, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@61abe1f3, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@471b66fe, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@25b52b55], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //       at java.base/java.util.Optional.map(Optional.java:260)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange and Act
        userService
                .storeProfileImage(new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));
    }

    /**
     * Method under test: {@link UserService#addUser(RegisterDto, MultipartFile)}
     */
    @Test
    void testAddUser() throws IOException {
        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
            // Arrange
            mockFiles.when(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class))).thenReturn(true);
            mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
                    .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
            mockFiles.when(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), isA(CopyOption[].class)))
                    .thenReturn(1L);
            when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");
            UserDto buildResult = UserDto.builder()
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
                    .build();
            when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<UserDto>>any())).thenReturn(buildResult);

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
            RegisterDto dto = new RegisterDto("Jane", "Doe", "iloveyou", "jane.doe@example.org", "Company", "6625550144",
                    Role.BUSINESSEXPERT);

            // Act
            userService.addUser(dto, new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

            // Assert
            mockFiles.verify(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), isA(CopyOption[].class)));
            mockFiles.verify(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)));
            verify(modelMapper).map(isA(Object.class), isA(Class.class));
            verify(userRepository).save(isA(User.class));
            verify(passwordEncoder).encode(isA(CharSequence.class));
        }
    }

    /**
     * Method under test: {@link UserService#getAll()}
     */
    @Test
    void testGetAll() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        ModelMapper modelMapper = new ModelMapper();

        // Act
        List<UserDto> actualAll = (new UserService(modelMapper, new BCryptPasswordEncoder(), userRepository)).getAll();

        // Assert
        verify(userRepository).findAll();
        assertTrue(actualAll.isEmpty());
    }

    /**
     * Method under test: {@link UserService#getAll()}
     */
    @Test
    void testGetAll2() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
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

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(userList);
        ModelMapper modelMapper = new ModelMapper();

        // Act
        List<UserDto> actualAll = (new UserService(modelMapper, new BCryptPasswordEncoder(), userRepository)).getAll();

        // Assert
        verify(userRepository).findAll();
        assertEquals(1, actualAll.size());
    }

    /**
     * Method under test: {@link UserService#getAll()}
     */
    @Test
    void testGetAll3() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
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

        User user2 = new User();
        user2.setActive(false);
        user2.setCompany("com.talan.adminmodule.entity.User");
        user2.setEmail("john.smith@example.org");
        user2.setFirstname("John");
        user2.setId(2);
        user2.setLastname("Smith");
        user2.setNonExpired(false);
        user2.setPassword("Password");
        user2.setPhone("8605550118");
        user2.setProfileImagePath("com.talan.adminmodule.entity.User");
        user2.setRole(Role.ADMIN);

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user2);
        userList.add(user);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(userList);
        ModelMapper modelMapper = new ModelMapper();

        // Act
        List<UserDto> actualAll = (new UserService(modelMapper, new BCryptPasswordEncoder(), userRepository)).getAll();

        // Assert
        verify(userRepository).findAll();
        assertEquals(2, actualAll.size());
    }

    /**
     * Method under test: {@link UserService#getAll()}
     */
    @Test
    void testGetAll4() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        ModelMapper modelMapper = mock(ModelMapper.class);
        UserDto buildResult = UserDto.builder()
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
                .build();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<UserDto>>any())).thenReturn(buildResult);

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

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(userList);

        // Act
        List<UserDto> actualAll = (new UserService(modelMapper, new BCryptPasswordEncoder(), userRepository)).getAll();

        // Assert
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(userRepository).findAll();
        assertEquals(1, actualAll.size());
    }

    /**
     * Method under test: {@link UserService#getAll()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAll5() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@43b3f91e testClass = com.talan.adminmodule.service.impl.DiffblueFakeClass671, locations = [], classes = [com.talan.adminmodule.service.impl.UserService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@3c57f52d, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@61abe1f3, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@471b66fe, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@25b52b55], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //       at java.base/java.util.Optional.map(Optional.java:260)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange and Act
        userService.getAll();
    }

    /**
     * Method under test:
     * {@link UserService#update(int, RegisterDto, MultipartFile)}
     */
    @Test
    void testUpdate() throws IOException {
        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
            //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

            // Arrange
            mockFiles.when(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class))).thenReturn(true);
            mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
                    .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
            mockFiles.when(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), isA(CopyOption[].class)))
                    .thenReturn(1L);

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
            Optional<User> ofResult = Optional.of(user);

            User user2 = new User();
            user2.setActive(true);
            user2.setCompany("Company");
            user2.setEmail("jane.doe@example.org");
            user2.setFirstname("Jane");
            user2.setId(1);
            user2.setLastname("Doe");
            user2.setNonExpired(true);
            user2.setPassword("iloveyou");
            user2.setPhone("6625550144");
            user2.setProfileImagePath("Profile Image Path");
            user2.setRole(Role.BUSINESSEXPERT);
            UserRepository userRepository = mock(UserRepository.class);
            when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
            when(userRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
            ModelMapper modelMapper = new ModelMapper();
            UserService userService = new UserService(modelMapper, new BCryptPasswordEncoder(), userRepository);
            RegisterDto dto = new RegisterDto("Jane", "Doe", "iloveyou", "jane.doe@example.org", "Company", "6625550144",
                    Role.BUSINESSEXPERT);

            // Act
            UserDto actualUpdateResult = userService.update(1, dto,
                    new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

            // Assert
            mockFiles.verify(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), isA(CopyOption[].class)));
            mockFiles.verify(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)));
            verify(userRepository).findById(eq(1));
            verify(userRepository).save(isA(User.class));
            assertEquals("6625550144", actualUpdateResult.getPhone());
            assertEquals("Company", actualUpdateResult.getCompany());
            assertEquals("Doe", actualUpdateResult.getLastname());
            assertEquals("Jane", actualUpdateResult.getFirstname());
            assertEquals("assets\\demo\\images\\user-profiles", actualUpdateResult.getProfileImagePath());
            assertEquals("jane.doe@example.org", actualUpdateResult.getEmail());
            assertEquals(1L, actualUpdateResult.getId().longValue());
            assertEquals(Role.BUSINESSEXPERT, actualUpdateResult.getRole());
            assertTrue(actualUpdateResult.isActive());
            assertTrue(actualUpdateResult.isNonExpired());
        }
    }

    /**
     * Method under test:
     * {@link UserService#update(int, RegisterDto, MultipartFile)}
     */
    @Test
    void testUpdate2() throws IOException {
        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
            //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

            // Arrange
            mockFiles.when(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class))).thenReturn(false);
            mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
                    .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
            mockFiles.when(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), isA(CopyOption[].class)))
                    .thenReturn(1L);

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
            Optional<User> ofResult = Optional.of(user);

            User user2 = new User();
            user2.setActive(true);
            user2.setCompany("Company");
            user2.setEmail("jane.doe@example.org");
            user2.setFirstname("Jane");
            user2.setId(1);
            user2.setLastname("Doe");
            user2.setNonExpired(true);
            user2.setPassword("iloveyou");
            user2.setPhone("6625550144");
            user2.setProfileImagePath("Profile Image Path");
            user2.setRole(Role.BUSINESSEXPERT);
            UserRepository userRepository = mock(UserRepository.class);
            when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
            when(userRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
            ModelMapper modelMapper = new ModelMapper();
            UserService userService = new UserService(modelMapper, new BCryptPasswordEncoder(), userRepository);
            RegisterDto dto = new RegisterDto("Jane", "Doe", "iloveyou", "jane.doe@example.org", "Company", "6625550144",
                    Role.BUSINESSEXPERT);

            // Act
            UserDto actualUpdateResult = userService.update(1, dto,
                    new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

            // Assert
            mockFiles.verify(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), isA(CopyOption[].class)));
            mockFiles.verify(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)));
            mockFiles.verify(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)));
            verify(userRepository).findById(eq(1));
            verify(userRepository).save(isA(User.class));
            assertEquals("6625550144", actualUpdateResult.getPhone());
            assertEquals("Company", actualUpdateResult.getCompany());
            assertEquals("Doe", actualUpdateResult.getLastname());
            assertEquals("Jane", actualUpdateResult.getFirstname());
            assertEquals("assets\\demo\\images\\user-profiles", actualUpdateResult.getProfileImagePath());
            assertEquals("jane.doe@example.org", actualUpdateResult.getEmail());
            assertEquals(1L, actualUpdateResult.getId().longValue());
            assertEquals(Role.BUSINESSEXPERT, actualUpdateResult.getRole());
            assertTrue(actualUpdateResult.isActive());
            assertTrue(actualUpdateResult.isNonExpired());
        }
    }

    /**
     * Method under test:
     * {@link UserService#update(int, RegisterDto, MultipartFile)}
     */
    @Test
    void testUpdate3() throws IOException {
        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
            //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

            // Arrange
            mockFiles.when(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class))).thenReturn(true);
            mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
                    .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
            mockFiles.when(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), isA(CopyOption[].class)))
                    .thenReturn(1L);
            ModelMapper modelMapper = mock(ModelMapper.class);
            UserDto buildResult = UserDto.builder()
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
                    .build();
            when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<UserDto>>any())).thenReturn(buildResult);

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
            Optional<User> ofResult = Optional.of(user);

            User user2 = new User();
            user2.setActive(true);
            user2.setCompany("Company");
            user2.setEmail("jane.doe@example.org");
            user2.setFirstname("Jane");
            user2.setId(1);
            user2.setLastname("Doe");
            user2.setNonExpired(true);
            user2.setPassword("iloveyou");
            user2.setPhone("6625550144");
            user2.setProfileImagePath("Profile Image Path");
            user2.setRole(Role.BUSINESSEXPERT);
            UserRepository userRepository = mock(UserRepository.class);
            when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
            when(userRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
            UserService userService = new UserService(modelMapper, new BCryptPasswordEncoder(), userRepository);
            RegisterDto dto = new RegisterDto("Jane", "Doe", "iloveyou", "jane.doe@example.org", "Company", "6625550144",
                    Role.BUSINESSEXPERT);

            // Act
            userService.update(1, dto, new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

            // Assert
            mockFiles.verify(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), isA(CopyOption[].class)));
            mockFiles.verify(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)));
            verify(modelMapper).map(isA(Object.class), isA(Class.class));
            verify(userRepository).findById(eq(1));
            verify(userRepository).save(isA(User.class));
        }
    }

    /**
     * Method under test:
     * {@link UserService#update(int, RegisterDto, MultipartFile)}
     */
    @Test
    void testUpdate4() throws IOException {
        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
            //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

            // Arrange
            mockFiles.when(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class))).thenReturn(true);
            mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
                    .thenThrow(new IOException("foo"));
            mockFiles.when(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), isA(CopyOption[].class)))
                    .thenThrow(new IOException("foo"));

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
            Optional<User> ofResult = Optional.of(user);
            UserRepository userRepository = mock(UserRepository.class);
            when(userRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
            ModelMapper modelMapper = new ModelMapper();
            UserService userService = new UserService(modelMapper, new BCryptPasswordEncoder(), userRepository);
            RegisterDto dto = RegisterDto.builder()
                    .company("Company")
                    .email("jane.doe@example.org")
                    .firstname("Jane")
                    .lastname("Doe")
                    .password("iloveyou")
                    .phone("6625550144")
                    .role(Role.BUSINESSEXPERT)
                    .build();

            // Act and Assert
            assertThrows(IOException.class, () -> userService.update(1, dto,
                    new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")))));
            mockFiles.verify(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), isA(CopyOption[].class)));
            mockFiles.verify(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)));
            verify(userRepository).findById(eq(1));
        }
    }

    /**
     * Method under test:
     * {@link UserService#update(int, RegisterDto, MultipartFile)}
     */
    @Test
    void testUpdate5() throws IOException {
        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
            //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

            // Arrange
            mockFiles.when(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class))).thenReturn(false);
            mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
                    .thenThrow(new IOException("foo"));
            mockFiles.when(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), isA(CopyOption[].class)))
                    .thenThrow(new IOException("foo"));

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
            Optional<User> ofResult = Optional.of(user);
            UserRepository userRepository = mock(UserRepository.class);
            when(userRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
            ModelMapper modelMapper = new ModelMapper();
            UserService userService = new UserService(modelMapper, new BCryptPasswordEncoder(), userRepository);
            RegisterDto dto = RegisterDto.builder()
                    .company("Company")
                    .email("jane.doe@example.org")
                    .firstname("Jane")
                    .lastname("Doe")
                    .password("iloveyou")
                    .phone("6625550144")
                    .role(Role.BUSINESSEXPERT)
                    .build();

            // Act and Assert
            assertThrows(IOException.class, () -> userService.update(1, dto,
                    new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")))));
            mockFiles.verify(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)));
            mockFiles.verify(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)));
            verify(userRepository).findById(eq(1));
        }
    }

    /**
     * Method under test:
     * {@link UserService#update(int, RegisterDto, MultipartFile)}
     */
    @Test
    void testUpdate6() throws IOException {
        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
            //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

            // Arrange
            mockFiles.when(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class))).thenReturn(true);
            mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
                    .thenThrow(new IOException("foo"));
            mockFiles.when(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), isA(CopyOption[].class)))
                    .thenThrow(new IOException("foo"));
            User user = mock(User.class);
            doNothing().when(user).setActive(anyBoolean());
            doNothing().when(user).setCompany(Mockito.<String>any());
            doNothing().when(user).setEmail(Mockito.<String>any());
            doNothing().when(user).setFirstname(Mockito.<String>any());
            doNothing().when(user).setId(Mockito.<Integer>any());
            doNothing().when(user).setLastname(Mockito.<String>any());
            doNothing().when(user).setNonExpired(anyBoolean());
            doNothing().when(user).setPassword(Mockito.<String>any());
            doNothing().when(user).setPhone(Mockito.<String>any());
            doNothing().when(user).setProfileImagePath(Mockito.<String>any());
            doNothing().when(user).setRole(Mockito.<Role>any());
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
            Optional<User> ofResult = Optional.of(user);
            UserRepository userRepository = mock(UserRepository.class);
            when(userRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
            ModelMapper modelMapper = new ModelMapper();
            UserService userService = new UserService(modelMapper, new BCryptPasswordEncoder(), userRepository);
            RegisterDto dto = RegisterDto.builder()
                    .company("Company")
                    .email("jane.doe@example.org")
                    .firstname("Jane")
                    .lastname("Doe")
                    .password("iloveyou")
                    .phone("6625550144")
                    .role(Role.BUSINESSEXPERT)
                    .build();

            // Act and Assert
            assertThrows(IOException.class, () -> userService.update(1, dto,
                    new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")))));
            verify(user).setActive(eq(true));
            verify(user).setCompany(eq("Company"));
            verify(user, atLeast(1)).setEmail(eq("jane.doe@example.org"));
            verify(user, atLeast(1)).setFirstname(eq("Jane"));
            verify(user).setId(eq(1));
            verify(user, atLeast(1)).setLastname(eq("Doe"));
            verify(user).setNonExpired(eq(true));
            verify(user, atLeast(1)).setPassword(Mockito.<String>any());
            verify(user, atLeast(1)).setPhone(eq("6625550144"));
            verify(user).setProfileImagePath(eq("Profile Image Path"));
            verify(user, atLeast(1)).setRole(eq(Role.BUSINESSEXPERT));
            mockFiles.verify(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), isA(CopyOption[].class)));
            mockFiles.verify(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)));
            verify(userRepository).findById(eq(1));
        }
    }

    /**
     * Method under test:
     * {@link UserService#update(int, RegisterDto, MultipartFile)}
     */
    @Test
    void testUpdate7() throws IOException {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
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
        Optional<User> ofResult = Optional.of(user);

        User user2 = new User();
        user2.setActive(true);
        user2.setCompany("Company");
        user2.setEmail("jane.doe@example.org");
        user2.setFirstname("Jane");
        user2.setId(1);
        user2.setLastname("Doe");
        user2.setNonExpired(true);
        user2.setPassword("iloveyou");
        user2.setPhone("6625550144");
        user2.setProfileImagePath("Profile Image Path");
        user2.setRole(Role.BUSINESSEXPERT);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        ModelMapper modelMapper = new ModelMapper();
        UserService userService = new UserService(modelMapper, new BCryptPasswordEncoder(), userRepository);
        RegisterDto dto = RegisterDto.builder()
                .company("Company")
                .email("jane.doe@example.org")
                .firstname("Jane")
                .lastname("Doe")
                .password("iloveyou")
                .phone("6625550144")
                .role(Role.BUSINESSEXPERT)
                .build();
        dto.setFirstname(null);
        dto.setPassword(null);
        dto.setRole(null);
        dto.setPhone(null);
        dto.setEmail(null);
        dto.setLastname(null);

        // Act
        UserDto actualUpdateResult = userService.update(1, dto, null);

        // Assert
        verify(userRepository).findById(eq(1));
        verify(userRepository).save(isA(User.class));
        assertEquals("6625550144", actualUpdateResult.getPhone());
        assertEquals("Company", actualUpdateResult.getCompany());
        assertEquals("Doe", actualUpdateResult.getLastname());
        assertEquals("Jane", actualUpdateResult.getFirstname());
        assertEquals("Profile Image Path", actualUpdateResult.getProfileImagePath());
        assertEquals("jane.doe@example.org", actualUpdateResult.getEmail());
        assertEquals(1L, actualUpdateResult.getId().longValue());
        assertEquals(Role.BUSINESSEXPERT, actualUpdateResult.getRole());
        assertTrue(actualUpdateResult.isActive());
        assertTrue(actualUpdateResult.isNonExpired());
    }

    /**
     * Method under test:
     * {@link UserService#update(int, RegisterDto, MultipartFile)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdate8() throws IOException {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@4017b90f testClass = com.talan.adminmodule.service.impl.DiffblueFakeClass1070, locations = [], classes = [com.talan.adminmodule.service.impl.UserService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@3c57f52d, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@61abe1f3, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@471b66fe, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@25b52b55], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //       at java.base/java.util.Optional.map(Optional.java:260)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange
        RegisterDto dto = new RegisterDto("Jane", "Doe", "iloveyou", "jane.doe@example.org", "Company", "6625550144",
                Role.BUSINESSEXPERT);

        // Act
        userService.update(1, dto, new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));
    }

    /**
     * Method under test: {@link UserService#findbyemail(String)}
     */
    @Test
    void testFindbyemail() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
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
        Optional<User> ofResult = Optional.of(user);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
        ModelMapper modelMapper = new ModelMapper();

        // Act
        User actualFindbyemailResult = (new UserService(modelMapper, new BCryptPasswordEncoder(), userRepository))
                .findbyemail("jane.doe@example.org");

        // Assert
        verify(userRepository).findByEmail(eq("jane.doe@example.org"));
        assertSame(user, actualFindbyemailResult);
    }

    /**
     * Method under test: {@link UserService#findbyemail(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testFindbyemail2() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@9449d5f testClass = com.talan.adminmodule.service.impl.DiffblueFakeClass514, locations = [], classes = [com.talan.adminmodule.service.impl.UserService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@3c57f52d, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@61abe1f3, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@471b66fe, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@25b52b55], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //       at java.base/java.util.Optional.map(Optional.java:260)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange and Act
        userService.findbyemail("jane.doe@example.org");
    }

    /**
     * Method under test: {@link UserService#delete(Integer)}
     */
    @Test
    void testDelete() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
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
        Optional<User> ofResult = Optional.of(user);

        User user2 = new User();
        user2.setActive(true);
        user2.setCompany("Company");
        user2.setEmail("jane.doe@example.org");
        user2.setFirstname("Jane");
        user2.setId(1);
        user2.setLastname("Doe");
        user2.setNonExpired(true);
        user2.setPassword("iloveyou");
        user2.setPhone("6625550144");
        user2.setProfileImagePath("Profile Image Path");
        user2.setRole(Role.BUSINESSEXPERT);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        ModelMapper modelMapper = new ModelMapper();

        // Act
        (new UserService(modelMapper, new BCryptPasswordEncoder(), userRepository)).delete(1);

        // Assert
        verify(userRepository).findById(eq(1));
        verify(userRepository).save(isA(User.class));
    }

    /**
     * Method under test: {@link UserService#delete(Integer)}
     */
    @Test
    void testDelete2() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        User user = mock(User.class);
        doNothing().when(user).setActive(anyBoolean());
        doNothing().when(user).setCompany(Mockito.<String>any());
        doNothing().when(user).setEmail(Mockito.<String>any());
        doNothing().when(user).setFirstname(Mockito.<String>any());
        doNothing().when(user).setId(Mockito.<Integer>any());
        doNothing().when(user).setLastname(Mockito.<String>any());
        doNothing().when(user).setNonExpired(anyBoolean());
        doNothing().when(user).setPassword(Mockito.<String>any());
        doNothing().when(user).setPhone(Mockito.<String>any());
        doNothing().when(user).setProfileImagePath(Mockito.<String>any());
        doNothing().when(user).setRole(Mockito.<Role>any());
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
        Optional<User> ofResult = Optional.of(user);

        User user2 = new User();
        user2.setActive(true);
        user2.setCompany("Company");
        user2.setEmail("jane.doe@example.org");
        user2.setFirstname("Jane");
        user2.setId(1);
        user2.setLastname("Doe");
        user2.setNonExpired(true);
        user2.setPassword("iloveyou");
        user2.setPhone("6625550144");
        user2.setProfileImagePath("Profile Image Path");
        user2.setRole(Role.BUSINESSEXPERT);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        ModelMapper modelMapper = new ModelMapper();

        // Act
        (new UserService(modelMapper, new BCryptPasswordEncoder(), userRepository)).delete(1);

        // Assert
        verify(user, atLeast(1)).setActive(anyBoolean());
        verify(user).setCompany(eq("Company"));
        verify(user).setEmail(eq("jane.doe@example.org"));
        verify(user).setFirstname(eq("Jane"));
        verify(user).setId(eq(1));
        verify(user).setLastname(eq("Doe"));
        verify(user).setNonExpired(eq(true));
        verify(user).setPassword(eq("iloveyou"));
        verify(user).setPhone(eq("6625550144"));
        verify(user).setProfileImagePath(eq("Profile Image Path"));
        verify(user).setRole(eq(Role.BUSINESSEXPERT));
        verify(userRepository).findById(eq(1));
        verify(userRepository).save(isA(User.class));
    }

    /**
     * Method under test: {@link UserService#delete(Integer)}
     */
    @Test
    void testDelete3() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
        ModelMapper modelMapper = new ModelMapper();

        // Act
        (new UserService(modelMapper, new BCryptPasswordEncoder(), userRepository)).delete(1);

        // Assert
        verify(userRepository).findById(eq(1));
    }

    /**
     * Method under test: {@link UserService#delete(Integer)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDelete4() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@72671f98 testClass = com.talan.adminmodule.service.impl.DiffblueFakeClass484, locations = [], classes = [com.talan.adminmodule.service.impl.UserService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@3c57f52d, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@61abe1f3, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@471b66fe, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@25b52b55], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //       at java.base/java.util.Optional.map(Optional.java:260)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange and Act
        userService.delete(1);
    }

    /**
     * Method under test: {@link UserService#expireUser(Integer)}
     */
    @Test
    void testExpireUser() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
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
        Optional<User> ofResult = Optional.of(user);

        User user2 = new User();
        user2.setActive(true);
        user2.setCompany("Company");
        user2.setEmail("jane.doe@example.org");
        user2.setFirstname("Jane");
        user2.setId(1);
        user2.setLastname("Doe");
        user2.setNonExpired(true);
        user2.setPassword("iloveyou");
        user2.setPhone("6625550144");
        user2.setProfileImagePath("Profile Image Path");
        user2.setRole(Role.BUSINESSEXPERT);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        ModelMapper modelMapper = new ModelMapper();

        // Act
        (new UserService(modelMapper, new BCryptPasswordEncoder(), userRepository)).expireUser(1);

        // Assert
        verify(userRepository).findById(eq(1));
        verify(userRepository).save(isA(User.class));
    }

    /**
     * Method under test: {@link UserService#expireUser(Integer)}
     */
    @Test
    void testExpireUser2() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        User user = mock(User.class);
        doNothing().when(user).setActive(anyBoolean());
        doNothing().when(user).setCompany(Mockito.<String>any());
        doNothing().when(user).setEmail(Mockito.<String>any());
        doNothing().when(user).setFirstname(Mockito.<String>any());
        doNothing().when(user).setId(Mockito.<Integer>any());
        doNothing().when(user).setLastname(Mockito.<String>any());
        doNothing().when(user).setNonExpired(anyBoolean());
        doNothing().when(user).setPassword(Mockito.<String>any());
        doNothing().when(user).setPhone(Mockito.<String>any());
        doNothing().when(user).setProfileImagePath(Mockito.<String>any());
        doNothing().when(user).setRole(Mockito.<Role>any());
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
        Optional<User> ofResult = Optional.of(user);

        User user2 = new User();
        user2.setActive(true);
        user2.setCompany("Company");
        user2.setEmail("jane.doe@example.org");
        user2.setFirstname("Jane");
        user2.setId(1);
        user2.setLastname("Doe");
        user2.setNonExpired(true);
        user2.setPassword("iloveyou");
        user2.setPhone("6625550144");
        user2.setProfileImagePath("Profile Image Path");
        user2.setRole(Role.BUSINESSEXPERT);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        ModelMapper modelMapper = new ModelMapper();

        // Act
        (new UserService(modelMapper, new BCryptPasswordEncoder(), userRepository)).expireUser(1);

        // Assert
        verify(user).setActive(eq(true));
        verify(user).setCompany(eq("Company"));
        verify(user).setEmail(eq("jane.doe@example.org"));
        verify(user).setFirstname(eq("Jane"));
        verify(user).setId(eq(1));
        verify(user).setLastname(eq("Doe"));
        verify(user, atLeast(1)).setNonExpired(anyBoolean());
        verify(user).setPassword(eq("iloveyou"));
        verify(user).setPhone(eq("6625550144"));
        verify(user).setProfileImagePath(eq("Profile Image Path"));
        verify(user).setRole(eq(Role.BUSINESSEXPERT));
        verify(userRepository).findById(eq(1));
        verify(userRepository).save(isA(User.class));
    }

    /**
     * Method under test: {@link UserService#expireUser(Integer)}
     */
    @Test
    void testExpireUser3() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
        ModelMapper modelMapper = new ModelMapper();

        // Act
        (new UserService(modelMapper, new BCryptPasswordEncoder(), userRepository)).expireUser(1);

        // Assert
        verify(userRepository).findById(eq(1));
    }

    /**
     * Method under test: {@link UserService#expireUser(Integer)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testExpireUser4() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@6052be2b testClass = com.talan.adminmodule.service.impl.DiffblueFakeClass499, locations = [], classes = [com.talan.adminmodule.service.impl.UserService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@3c57f52d, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@61abe1f3, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@471b66fe, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@25b52b55], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //       at java.base/java.util.Optional.map(Optional.java:260)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange and Act
        userService.expireUser(1);
    }

    /**
     * Method under test: {@link UserService#unexpireUser(Integer)}
     */
    @Test
    void testUnexpireUser() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
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
        Optional<User> ofResult = Optional.of(user);

        User user2 = new User();
        user2.setActive(true);
        user2.setCompany("Company");
        user2.setEmail("jane.doe@example.org");
        user2.setFirstname("Jane");
        user2.setId(1);
        user2.setLastname("Doe");
        user2.setNonExpired(true);
        user2.setPassword("iloveyou");
        user2.setPhone("6625550144");
        user2.setProfileImagePath("Profile Image Path");
        user2.setRole(Role.BUSINESSEXPERT);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        ModelMapper modelMapper = new ModelMapper();

        // Act
        (new UserService(modelMapper, new BCryptPasswordEncoder(), userRepository)).unexpireUser(1);

        // Assert
        verify(userRepository).findById(eq(1));
        verify(userRepository).save(isA(User.class));
    }

    /**
     * Method under test: {@link UserService#unexpireUser(Integer)}
     */
    @Test
    void testUnexpireUser2() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        User user = mock(User.class);
        doNothing().when(user).setActive(anyBoolean());
        doNothing().when(user).setCompany(Mockito.<String>any());
        doNothing().when(user).setEmail(Mockito.<String>any());
        doNothing().when(user).setFirstname(Mockito.<String>any());
        doNothing().when(user).setId(Mockito.<Integer>any());
        doNothing().when(user).setLastname(Mockito.<String>any());
        doNothing().when(user).setNonExpired(anyBoolean());
        doNothing().when(user).setPassword(Mockito.<String>any());
        doNothing().when(user).setPhone(Mockito.<String>any());
        doNothing().when(user).setProfileImagePath(Mockito.<String>any());
        doNothing().when(user).setRole(Mockito.<Role>any());
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
        Optional<User> ofResult = Optional.of(user);

        User user2 = new User();
        user2.setActive(true);
        user2.setCompany("Company");
        user2.setEmail("jane.doe@example.org");
        user2.setFirstname("Jane");
        user2.setId(1);
        user2.setLastname("Doe");
        user2.setNonExpired(true);
        user2.setPassword("iloveyou");
        user2.setPhone("6625550144");
        user2.setProfileImagePath("Profile Image Path");
        user2.setRole(Role.BUSINESSEXPERT);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        ModelMapper modelMapper = new ModelMapper();

        // Act
        (new UserService(modelMapper, new BCryptPasswordEncoder(), userRepository)).unexpireUser(1);

        // Assert
        verify(user).setActive(eq(true));
        verify(user).setCompany(eq("Company"));
        verify(user).setEmail(eq("jane.doe@example.org"));
        verify(user).setFirstname(eq("Jane"));
        verify(user).setId(eq(1));
        verify(user).setLastname(eq("Doe"));
        verify(user, atLeast(1)).setNonExpired(eq(true));
        verify(user).setPassword(eq("iloveyou"));
        verify(user).setPhone(eq("6625550144"));
        verify(user).setProfileImagePath(eq("Profile Image Path"));
        verify(user).setRole(eq(Role.BUSINESSEXPERT));
        verify(userRepository).findById(eq(1));
        verify(userRepository).save(isA(User.class));
    }

    /**
     * Method under test: {@link UserService#unexpireUser(Integer)}
     */
    @Test
    void testUnexpireUser3() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
        ModelMapper modelMapper = new ModelMapper();

        // Act
        (new UserService(modelMapper, new BCryptPasswordEncoder(), userRepository)).unexpireUser(1);

        // Assert
        verify(userRepository).findById(eq(1));
    }

    /**
     * Method under test: {@link UserService#unexpireUser(Integer)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUnexpireUser4() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@1173cd76 testClass = com.talan.adminmodule.service.impl.DiffblueFakeClass1054, locations = [], classes = [com.talan.adminmodule.service.impl.UserService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@3c57f52d, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@61abe1f3, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@471b66fe, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@25b52b55], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //       at java.base/java.util.Optional.map(Optional.java:260)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange and Act
        userService.unexpireUser(1);
    }
}
