package com.example.springboottaskmanager.service;

import com.example.springboottaskmanager.exception.Body.UserExitsException;
import com.example.springboottaskmanager.mapper.ToUserMapper;
import com.example.springboottaskmanager.mapper.UserMapper;
import com.example.springboottaskmanager.model.User;
import com.example.springboottaskmanager.repository.UserRepo;
import com.example.springboottaskmanager.security.authConfig.JwtService;
import com.example.springboottaskmanager.security.securityModels.LoginRequest;
import com.example.springboottaskmanager.security.securityModels.LoginResponse;
import com.example.springboottaskmanager.security.atoken.Token;
import com.example.springboottaskmanager.security.atoken.TokenRepo;
import com.example.springboottaskmanager.TokenType;
import com.example.springboottaskmanager.security.securityModels.RegisterRequest;
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
    private final ToUserMapper toUserMapper;

    public LoginResponse Register(RegisterRequest registerRequest, UserRepo userRepo) {
        if(userRepo.findByUsername(registerRequest.getUsername()).isEmpty()) {
            User user = UserMapper.INSTANCE.toUser(registerRequest, toUserMapper, 2L);
            userRepo.save(user);

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    registerRequest.getUsername(),
                    registerRequest.getPassword()));
            String jwtToken = jwtService.GenerateToken(user);
            revokeUserToken(user);
            saveUserToken(user, jwtToken);
            return new LoginResponse(jwtToken);
        }
        else
            throw new UserExitsException("Пользователь с данным ником уже существует");
    }

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
