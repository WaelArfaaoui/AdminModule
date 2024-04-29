package com.talan.adminmodule.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.talan.adminmodule.config.JwtService;
import com.talan.adminmodule.dto.AuthenticationRequest;
import com.talan.adminmodule.dto.AuthenticationResponse;
import com.talan.adminmodule.entity.Role;
import com.talan.adminmodule.entity.User;
import com.talan.adminmodule.repository.UserRepository;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Optional;

import org.apache.catalina.connector.CoyoteOutputStream;
import org.apache.catalina.connector.Response;
import org.apache.catalina.connector.ResponseFacade;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.springframework.mock.web.MockHttpServletMapping;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthenticationService.class, AuthenticationManager.class})
@ExtendWith(SpringExtension.class)
class AuthenticationServiceTest {
    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationService authenticationService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private Logger logger;

    @MockBean
    private UserRepository userRepository;

    /**
     * Method under test: {@link AuthenticationService#authenticate(AuthenticationRequest)}
     */
    @Test
    void testAuthenticate() throws AuthenticationException {
        when(jwtService.generateRefreshToken(Mockito.<UserDetails>any())).thenReturn("ABC123");
        when(jwtService.generateToken(Mockito.<UserDetails>any())).thenReturn("ABC123");

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
        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
        when(authenticationManager.authenticate(Mockito.<Authentication>any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        AuthenticationResponse actualAuthenticateResult = authenticationService
                .authenticate(new AuthenticationRequest("jane.doe@example.org", "iloveyou"));
        assertEquals("ABC123", actualAuthenticateResult.getAccessToken());
        assertEquals("ABC123", actualAuthenticateResult.getRefreshToken());
        assertNull(actualAuthenticateResult.getError());
        verify(jwtService).generateRefreshToken(Mockito.<UserDetails>any());
        verify(jwtService).generateToken(Mockito.<UserDetails>any());
        verify(userRepository).findByEmail(Mockito.<String>any());
        verify(authenticationManager).authenticate(Mockito.<Authentication>any());
    }

    /**
     * Method under test: {@link AuthenticationService#refreshToken(HttpServletRequest, HttpServletResponse)}
     */
    @Test
    void testRefreshToken() throws IOException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R026 Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@5c5f20e9 testClass = com.talan.adminmodule.service.impl.DiffblueFakeClass261, locations = [], classes = [com.talan.adminmodule.service.impl.AuthenticationService, org.springframework.security.authentication.AuthenticationManager], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@7c5efd02, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@1af1f84d, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@c723d171, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@488484e4], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //       at java.util.Optional.map(Optional.java:260)
        //   See https://diff.blue/R026 to resolve this issue.

        AuthenticationService authenticationService = new AuthenticationService();
        MockHttpServletRequest request = new MockHttpServletRequest();
        Response response = new Response();
        authenticationService.refreshToken(request, response);
        assertTrue(request.isRequestedSessionIdValid());
        assertFalse(request.isRequestedSessionIdFromURL());
        assertTrue(request.isRequestedSessionIdFromCookie());
        assertFalse(request.isAsyncSupported());
        assertFalse(request.isAsyncStarted());
        assertTrue(request.isActive());
        assertTrue(request.getSession() instanceof MockHttpSession);
        assertEquals("", request.getServletPath());
        assertEquals("localhost", request.getLocalName());
        assertEquals(80, request.getLocalPort());
        assertEquals("", request.getRequestURI());
        assertEquals("", request.getContextPath());
        assertEquals("http", request.getScheme());
        assertEquals("localhost", request.getServerName());
        assertEquals(80, request.getServerPort());
        assertEquals("", request.getMethod());
        assertEquals(DispatcherType.REQUEST, request.getDispatcherType());
        assertTrue(request.getHttpServletMapping() instanceof MockHttpServletMapping);
        assertEquals("HTTP/1.1", request.getProtocol());
        assertTrue(request.getInputStream() instanceof DelegatingServletInputStream);
        assertEquals("localhost", request.getRemoteHost());
        assertEquals(80, request.getRemotePort());
        HttpServletResponse response2 = response.getResponse();
        assertTrue(response2 instanceof ResponseFacade);
        assertSame(response.getOutputStream(), response2.getOutputStream());
    }
}

