package com.talan.adminmodule.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.*;

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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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

class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void authenticate_ValidCredentials_ReturnsAuthenticationResponse() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest("test@example.com", "password");
        when(userRepository.findByEmail(request.getEmail())).thenReturn(java.util.Optional.ofNullable(null));
        when(jwtService.generateToken(any())).thenReturn("mockedAccessToken");
        when(jwtService.generateRefreshToken(any())).thenReturn("mockedRefreshToken");

        // Act
        AuthenticationResponse response = authenticationService.authenticate(request);

        // Assert
        assertEquals("mockedAccessToken", response.getAccessToken());
        assertEquals("mockedRefreshToken", response.getRefreshToken());
    }

    @Test
    void refreshToken_ValidToken_GeneratesNewAccessToken() throws Exception {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer mockedRefreshToken");
        when(jwtService.extractUsername("mockedRefreshToken")).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(java.util.Optional.ofNullable(null));
        when(jwtService.isTokenValid("mockedRefreshToken", null)).thenReturn(true);
        when(jwtService.generateToken(any())).thenReturn("mockedAccessToken");

        // Act
        authenticationService.refreshToken(request, response);

        // Assert
        verify(response).getOutputStream();
    }

    @Test
    void refreshToken_InvalidToken_NoActionTaken() throws Exception {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader("Authorization")).thenReturn(null);

        // Act
        authenticationService.refreshToken(request, response);

        // Assert
        verify(response, never()).getOutputStream();
    }

}