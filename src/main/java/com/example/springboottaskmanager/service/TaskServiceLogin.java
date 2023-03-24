package com.example.springboottaskmanager.service;

import com.example.springboottaskmanager.model.User;
import com.example.springboottaskmanager.repository.UserRepo;
import com.example.springboottaskmanager.security.authConfig.JwtService;
import com.example.springboottaskmanager.security.securityModels.LoginRequest;
import com.example.springboottaskmanager.security.securityModels.LoginResponse;
import com.example.springboottaskmanager.security.atoken.Token;
import com.example.springboottaskmanager.security.atoken.TokenRepo;
import com.example.springboottaskmanager.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceLogin {
    private final TokenRepo tokenRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginResponse Login(LoginRequest loginRequest, UserRepo userRepo) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()));
        User user = userRepo.findByUsername(loginRequest.getUsername()).orElseThrow();
        String jwtToken = jwtService.GenerateToken(user);
        revokeUserToken(user);
        saveUserToken(user, jwtToken);
        return new LoginResponse(jwtToken);
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = new Token();
        token.setUser(user);
        token.setToken(jwtToken);
        token.setTokenType(TokenType.BEARER);
        token.setExpired(false);
        token.setRevoked(false);
        tokenRepo.save(token);
    }

    private void revokeUserToken(User user) {
        List<Token> validUserTokens = tokenRepo.findValidUserToken(user.getId());
        if(validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);});
        tokenRepo.saveAll(validUserTokens);
    }
}
