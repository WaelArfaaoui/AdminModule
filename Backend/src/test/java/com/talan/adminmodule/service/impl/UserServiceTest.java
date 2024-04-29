package com.talan.adminmodule.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
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

import org.junit.jupiter.api.Disabled;

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
     * Method under test: {@link UserService#mapUserToDto(User)}
     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testMapUserToDto() {
//        // TODO: Complete this test.
//        //   Reason: R026 Failed to create Spring context.
//        //   Attempt to initialize test context failed with
//        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@5fdc57dd testClass = com.talan.adminmodule.service.impl.DiffblueFakeClass674, locations = [], classes = [com.talan.adminmodule.service.impl.UserService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@52d05208, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@7646b68d, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@8247fd8a, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@6e761596], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
//        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
//        //       at java.util.Optional.map(Optional.java:260)
//        //   See https://diff.blue/R026 to resolve this issue.
//
//        User user = new User();
//        user.setActive(true);
//        user.setCompany("Company");
//        user.setEmail("jane.doe@example.org");
//        user.setFirstname("Jane");
//        user.setId(1);
//        user.setLastname("Doe");
//        user.setNonExpired(true);
//        user.setPassword("iloveyou");
//        user.setPhone("6625550144");
//        user.setProfileImagePath("Profile Image Path");
//        user.setRole(Role.BUSINESSEXPERT);
//        userService.mapUserToDto(user);
//    }
//
//    /**
//     * Method under test: {@link UserService#changePassword(ChangePassword, Principal)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testChangePassword() {
//        // TODO: Complete this test.
//        //   Reason: R026 Failed to create Spring context.
//        //   Attempt to initialize test context failed with
//        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@109b893f testClass = com.talan.adminmodule.service.impl.DiffblueFakeClass240, locations = [], classes = [com.talan.adminmodule.service.impl.UserService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@52d05208, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@7646b68d, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@8247fd8a, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@6e761596], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
//        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
//        //       at java.util.Optional.map(Optional.java:260)
//        //   See https://diff.blue/R026 to resolve this issue.
//
//        userService.changePassword(mock(ChangePassword.class), new UserPrincipal("connectedUser"));
//    }
//
//    /**
//     * Method under test: {@link UserService#storeProfileImage(MultipartFile)}
//     */
//    @Test
//    void testStoreProfileImage() throws IOException {
//        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
//            //   Diffblue Cover was unable to write a Spring test,
//            //   so wrote a non-Spring test instead.
//            //   Reason: R026 Failed to create Spring context.
//            //   Attempt to initialize test context failed with
//            //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@4d3b47fa testClass = com.talan.adminmodule.service.impl.DiffblueFakeClass975, locations = [], classes = [com.talan.adminmodule.service.impl.UserService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@52d05208, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@7646b68d, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@8247fd8a, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@6e761596], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
//            //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
//            //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
//            //       at java.util.Optional.map(Optional.java:260)
//            //   See https://diff.blue/R026 to resolve this issue.
//
//            mockFiles.when(() -> Files.exists(Mockito.<Path>any(), (LinkOption[]) any())).thenReturn(true);
//            mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), (FileAttribute[]) any()))
//                    .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
//            mockFiles.when(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), (CopyOption[]) any()))
//                    .thenReturn(1L);
//            UserService userService = new UserService();
//            assertEquals("assets\\demo\\images\\user-profiles", userService
//                    .storeProfileImage(new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")))));
//            mockFiles.verify(() -> Files.exists(Mockito.<Path>any(), (LinkOption[]) any()));
//            mockFiles.verify(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), (CopyOption[]) any()));
//        }
//    }
//
//    /**
//     * Method under test: {@link UserService#storeProfileImage(MultipartFile)}
//     */
//    @Test
//    void testStoreProfileImage2() throws IOException {
//        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
//            //   Diffblue Cover was unable to write a Spring test,
//            //   so wrote a non-Spring test instead.
//            //   Reason: R026 Failed to create Spring context.
//            //   Attempt to initialize test context failed with
//            //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@4d3b47fa testClass = com.talan.adminmodule.service.impl.DiffblueFakeClass975, locations = [], classes = [com.talan.adminmodule.service.impl.UserService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@52d05208, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@7646b68d, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@8247fd8a, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@6e761596], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
//            //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
//            //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
//            //       at java.util.Optional.map(Optional.java:260)
//            //   See https://diff.blue/R026 to resolve this issue.
//
//            mockFiles.when(() -> Files.exists(Mockito.<Path>any(), (LinkOption[]) any())).thenReturn(false);
//            mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), (FileAttribute[]) any()))
//                    .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
//            mockFiles.when(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), (CopyOption[]) any()))
//                    .thenReturn(1L);
//            UserService userService = new UserService();
//            assertEquals("assets\\demo\\images\\user-profiles", userService
//                    .storeProfileImage(new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")))));
//            mockFiles.verify(() -> Files.exists(Mockito.<Path>any(), (LinkOption[]) any()));
//            mockFiles.verify(() -> Files.createDirectories(Mockito.<Path>any(), (FileAttribute[]) any()));
//            mockFiles.verify(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), (CopyOption[]) any()));
//        }
//    }
//
//    /**
//     * Method under test: {@link UserService#storeProfileImage(MultipartFile)}
//     */
//    @Test
//    void testStoreProfileImage3() throws IOException {
//        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
//            //   Diffblue Cover was unable to write a Spring test,
//            //   so wrote a non-Spring test instead.
//            //   Reason: R026 Failed to create Spring context.
//            //   Attempt to initialize test context failed with
//            //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@4d3b47fa testClass = com.talan.adminmodule.service.impl.DiffblueFakeClass975, locations = [], classes = [com.talan.adminmodule.service.impl.UserService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@52d05208, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@7646b68d, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@8247fd8a, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@6e761596], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
//            //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
//            //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
//            //       at java.util.Optional.map(Optional.java:260)
//            //   See https://diff.blue/R026 to resolve this issue.
//
//            mockFiles.when(() -> Files.exists(Mockito.<Path>any(), (LinkOption[]) any())).thenReturn(true);
//            mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), (FileAttribute[]) any()))
//                    .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
//            mockFiles.when(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), (CopyOption[]) any()))
//                    .thenReturn(1L);
//            UserService userService = new UserService();
//            assertEquals("",
//                    userService.storeProfileImage(new MockMultipartFile("Name", new ByteArrayInputStream(new byte[]{}))));
//        }
//    }
//
//    /**
//     * Method under test: {@link UserService#storeProfileImage(MultipartFile)}
//     */
//    @Test
//    void testStoreProfileImage4() throws IOException {
//        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
//            //   Diffblue Cover was unable to write a Spring test,
//            //   so wrote a non-Spring test instead.
//            //   Reason: R026 Failed to create Spring context.
//            //   Attempt to initialize test context failed with
//            //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@4d3b47fa testClass = com.talan.adminmodule.service.impl.DiffblueFakeClass975, locations = [], classes = [com.talan.adminmodule.service.impl.UserService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@52d05208, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@7646b68d, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@8247fd8a, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@6e761596], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
//            //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
//            //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
//            //       at java.util.Optional.map(Optional.java:260)
//            //   See https://diff.blue/R026 to resolve this issue.
//
//            mockFiles.when(() -> Files.exists(Mockito.<Path>any(), (LinkOption[]) any())).thenReturn(true);
//            mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), (FileAttribute[]) any()))
//                    .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
//            mockFiles.when(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), (CopyOption[]) any()))
//                    .thenReturn(1L);
//            assertEquals("", (new UserService()).storeProfileImage(null));
//        }
//    }
//
//    /**
//     * Method under test: {@link UserService#storeProfileImage(MultipartFile)}
//     */
//    @Test
//    void testStoreProfileImage5() throws IOException {
//        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
//            //   Diffblue Cover was unable to write a Spring test,
//            //   so wrote a non-Spring test instead.
//            //   Reason: R026 Failed to create Spring context.
//            //   Attempt to initialize test context failed with
//            //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@4d3b47fa testClass = com.talan.adminmodule.service.impl.DiffblueFakeClass975, locations = [], classes = [com.talan.adminmodule.service.impl.UserService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@52d05208, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@7646b68d, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@8247fd8a, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@6e761596], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
//            //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
//            //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
//            //       at java.util.Optional.map(Optional.java:260)
//            //   See https://diff.blue/R026 to resolve this issue.
//
//            mockFiles.when(() -> Files.exists(Mockito.<Path>any(), (LinkOption[]) any())).thenReturn(true);
//            mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), (FileAttribute[]) any()))
//                    .thenThrow(new IOException("user.dir"));
//            mockFiles.when(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), (CopyOption[]) any()))
//                    .thenThrow(new IOException("user.dir"));
//            UserService userService = new UserService();
//            assertThrows(IOException.class, () -> userService
//                    .storeProfileImage(new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")))));
//            mockFiles.verify(() -> Files.exists(Mockito.<Path>any(), (LinkOption[]) any()));
//            mockFiles.verify(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), (CopyOption[]) any()));
//        }
//    }
//
//    /**
//     * Method under test: {@link UserService#storeProfileImage(MultipartFile)}
//     */
//    @Test
//    void testStoreProfileImage6() throws IOException {
//        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
//            //   Diffblue Cover was unable to write a Spring test,
//            //   so wrote a non-Spring test instead.
//            //   Reason: R026 Failed to create Spring context.
//            //   Attempt to initialize test context failed with
//            //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@4d3b47fa testClass = com.talan.adminmodule.service.impl.DiffblueFakeClass975, locations = [], classes = [com.talan.adminmodule.service.impl.UserService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@52d05208, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@7646b68d, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@8247fd8a, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@6e761596], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
//            //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
//            //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
//            //       at java.util.Optional.map(Optional.java:260)
//            //   See https://diff.blue/R026 to resolve this issue.
//
//            mockFiles.when(() -> Files.exists(Mockito.<Path>any(), (LinkOption[]) any())).thenReturn(false);
//            mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), (FileAttribute[]) any()))
//                    .thenThrow(new IOException("user.dir"));
//            mockFiles.when(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), (CopyOption[]) any()))
//                    .thenThrow(new IOException("user.dir"));
//            UserService userService = new UserService();
//            assertThrows(IOException.class, () -> userService
//                    .storeProfileImage(new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")))));
//            mockFiles.verify(() -> Files.exists(Mockito.<Path>any(), (LinkOption[]) any()));
//            mockFiles.verify(() -> Files.createDirectories(Mockito.<Path>any(), (FileAttribute[]) any()));
//        }
//    }
//
//    /**
//     * Method under test: {@link UserService#addUser(RegisterDto, MultipartFile)}
//     */
//    @Test
//    void testAddUser() throws IOException {
//        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
//            mockFiles.when(() -> Files.exists(Mockito.<Path>any(), (LinkOption[]) any())).thenReturn(true);
//            mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), (FileAttribute[]) any()))
//                    .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
//            mockFiles.when(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), (CopyOption[]) any()))
//                    .thenReturn(1L);
//            when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<UserDto>>any())).thenReturn(UserDto.builder()
//                    .active(true)
//                    .company("Company")
//                    .email("jane.doe@example.org")
//                    .error("An error occurred")
//                    .firstname("Jane")
//                    .id(1L)
//                    .lastname("Doe")
//                    .nonExpired(true)
//                    .phone("6625550144")
//                    .profileImagePath("Profile Image Path")
//                    .role(Role.BUSINESSEXPERT)
//                    .build());
//
//            User user = new User();
//            user.setActive(true);
//            user.setCompany("Company");
//            user.setEmail("jane.doe@example.org");
//            user.setFirstname("Jane");
//            user.setId(1);
//            user.setLastname("Doe");
//            user.setNonExpired(true);
//            user.setPassword("iloveyou");
//            user.setPhone("6625550144");
//            user.setProfileImagePath("Profile Image Path");
//            user.setRole(Role.BUSINESSEXPERT);
//            when(userRepository.save(Mockito.<User>any())).thenReturn(user);
//            when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");
//            RegisterDto dto = new RegisterDto("Jane", "Doe", "iloveyou", "jane.doe@example.org", "Company", "6625550144",
//                    Role.BUSINESSEXPERT);
//
//            userService.addUser(dto, new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));
//            mockFiles.verify(() -> Files.exists(Mockito.<Path>any(), (LinkOption[]) any()));
//            mockFiles.verify(() -> Files.copy(Mockito.<InputStream>any(), Mockito.<Path>any(), (CopyOption[]) any()));
//            verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<UserDto>>any());
//            verify(userRepository).save(Mockito.<User>any());
//            verify(passwordEncoder).encode(Mockito.<CharSequence>any());
//        }
//    }
//
//    /**
//     * Method under test: {@link UserService#getAll()}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testGetAll() {
//        // TODO: Complete this test.
//        //   Reason: R026 Failed to create Spring context.
//        //   Attempt to initialize test context failed with
//        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@ac3805c testClass = com.talan.adminmodule.service.impl.DiffblueFakeClass671, locations = [], classes = [com.talan.adminmodule.service.impl.UserService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@52d05208, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@7646b68d, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@8247fd8a, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@6e761596], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
//        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
//        //       at java.util.Optional.map(Optional.java:260)
//        //   See https://diff.blue/R026 to resolve this issue.
//
//        userService.getAll();
//    }
//
//    /**
//     * Method under test: {@link UserService#update(int, RegisterDto, MultipartFile)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testUpdate() throws IOException {
//        // TODO: Complete this test.
//        //   Reason: R026 Failed to create Spring context.
//        //   Attempt to initialize test context failed with
//        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@6ab48365 testClass = com.talan.adminmodule.service.impl.DiffblueFakeClass1071, locations = [], classes = [com.talan.adminmodule.service.impl.UserService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@52d05208, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@7646b68d, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@8247fd8a, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@6e761596], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
//        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
//        //       at java.util.Optional.map(Optional.java:260)
//        //   See https://diff.blue/R026 to resolve this issue.
//
//        RegisterDto dto = new RegisterDto("Jane", "Doe", "iloveyou", "jane.doe@example.org", "Company", "6625550144",
//                Role.BUSINESSEXPERT);
//
//        userService.update(1, dto, new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));
//    }
//
//    /**
//     * Method under test: {@link UserService#findbyemail(String)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testFindbyemail() {
//        // TODO: Complete this test.
//        //   Reason: R026 Failed to create Spring context.
//        //   Attempt to initialize test context failed with
//        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@e4cab4 testClass = com.talan.adminmodule.service.impl.DiffblueFakeClass513, locations = [], classes = [com.talan.adminmodule.service.impl.UserService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@52d05208, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@7646b68d, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@8247fd8a, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@6e761596], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
//        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
//        //       at java.util.Optional.map(Optional.java:260)
//        //   See https://diff.blue/R026 to resolve this issue.
//
//        userService.findbyemail("jane.doe@example.org");
//    }
//
//    /**
//     * Method under test: {@link UserService#delete(Integer)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testDelete() {
//        // TODO: Complete this test.
//        //   Reason: R026 Failed to create Spring context.
//        //   Attempt to initialize test context failed with
//        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@b0ed87c testClass = com.talan.adminmodule.service.impl.DiffblueFakeClass481, locations = [], classes = [com.talan.adminmodule.service.impl.UserService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@52d05208, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@7646b68d, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@8247fd8a, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@6e761596], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
//        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
//        //       at java.util.Optional.map(Optional.java:260)
//        //   See https://diff.blue/R026 to resolve this issue.
//
//        userService.delete(1);
//    }

    /**
     * Method under test: {@link UserService#expireUser(Integer)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testExpireUser() {
        // TODO: Complete this test.
        //   Reason: R026 Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@5bcea927 testClass = com.talan.adminmodule.service.impl.DiffblueFakeClass497, locations = [], classes = [com.talan.adminmodule.service.impl.UserService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@52d05208, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@7646b68d, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@8247fd8a, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@6e761596], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //       at java.util.Optional.map(Optional.java:260)
        //   See https://diff.blue/R026 to resolve this issue.

        userService.expireUser(1);
    }

    /**
     * Method under test: {@link UserService#unexpireUser(Integer)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUnexpireUser() {
        // TODO: Complete this test.
        //   Reason: R026 Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@48465a40 testClass = com.talan.adminmodule.service.impl.DiffblueFakeClass1054, locations = [], classes = [com.talan.adminmodule.service.impl.UserService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@52d05208, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@7646b68d, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@8247fd8a, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@6e761596], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //       at java.util.Optional.map(Optional.java:260)
        //   See https://diff.blue/R026 to resolve this issue.

        userService.unexpireUser(1);
    }
}

