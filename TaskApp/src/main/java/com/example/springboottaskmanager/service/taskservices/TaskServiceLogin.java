package com.example.springboottaskmanager.service.taskservices;

import com.example.springboottaskmanager.exception.body.UserExitsException;
import com.example.springboottaskmanager.mapper.ToUserMapper;
import com.example.springboottaskmanager.mapper.UserMapper;
import com.example.springboottaskmanager.model.User;
import com.example.springboottaskmanager.repository.UserRepo;
import com.example.springboottaskmanager.security.authconfig.JwtService;
import com.example.springboottaskmanager.security.securitymodels.LoginRequest;
import com.example.springboottaskmanager.security.securitymodels.LoginResponse;
import com.example.springboottaskmanager.security.atoken.Token;
import com.example.springboottaskmanager.security.atoken.TokenRepo;
import com.example.springboottaskmanager.TokenType;
import com.example.springboottaskmanager.security.securitymodels.RegisterRequest;
import com.example.springboottaskmanager.service.departmentservices.DepartmentServicePost;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceLogin {
    /**
     * Переменная окружения, определяющая способ обращения к сервису подразделений.
     */
    @Value("${department_client}")
    private String departmentClient;

    /**
     * Сервис для обработки Post-запросов к сервису подразделений {@link DepartmentServicePost}.
     */
    private final DepartmentServicePost departmentServicePost;
    /**
     * Репозиторий токенов {@link TokenRepo}.
     */
    private final TokenRepo tokenRepo;
    /**
     * Сервис для создания JWT-токенов {@link JwtService}.
     */
    private final JwtService jwtService;
    /**
     * Менеджер аутентификации {@link AuthenticationManager}.
     */
    private final AuthenticationManager authenticationManager;
    /**
     * Вспомогательный класс для маппера пользователей {@link ToUserMapper}.
     */
    private final ToUserMapper toUserMapper;

    /**
     * Метод для регистрации нового пользователя.
     * @param registerRequest Объект, хранящий в себе информацию о новом пользователе
     * @param userRepo        База данных пользователей
     * @return JWT-токен
     */
    @Transactional
    public LoginResponse register(
            final RegisterRequest registerRequest,
            final UserRepo userRepo) {
        if (userRepo.findByUsername(registerRequest.getUsername()).isEmpty()) {
            User user = UserMapper.INSTANCE
                    .toUser(registerRequest, toUserMapper, 2L);
            userRepo.save(user);

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    registerRequest.getUsername(),
                    registerRequest.getPassword()));
            String jwtToken = jwtService.generateToken(user);
            revokeUserToken(user);
            saveUserToken(user, jwtToken);
            /*addUserInfoToDepartments(user.getId().toString(), user.getDepartmentId().toString());*/
            return new LoginResponse(jwtToken);
        } else {
            throw new UserExitsException("Пользователь с данным ником уже существует");
        }
    }

    /**
     * Метод для авторизации пользователя.
     * @param loginRequest Объект, хранящий в себе пароль и логин пользователя
     * @param userRepo     База данных пользователей
     * @return JWT-токен
     */
    @Transactional
    public LoginResponse login(final LoginRequest loginRequest, final UserRepo userRepo) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()));
        User user = userRepo.findByUsername(loginRequest.getUsername()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        revokeUserToken(user);
        saveUserToken(user, jwtToken);
        return new LoginResponse(jwtToken);
    }

    /**
     * Метод, сохранящий токен пользователя.
     * @param user     Пользователь-владелец токена
     * @param jwtToken Токен пользователя
     */
    private void saveUserToken(final User user, final String jwtToken) {
        Token token = new Token();
        token.setUser(user);
        token.setTokenCode(jwtToken);
        token.setTokenType(TokenType.BEARER);
        token.setExpired(false);
        token.setRevoked(false);
        tokenRepo.save(token);
    }

    /**
     * Метод, деактивирующий все прошлые токены пользователя.
     * @param user Пользователь, старые токены которого нужно деактивировать
     */
    private void revokeUserToken(final User user) {
        List<Token> validUserTokens = tokenRepo.findValidUserToken(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepo.saveAll(validUserTokens);
    }

/*    private String addUserInfoToDepartments(final String userId, final String depId) {
        return switch (departmentClient) {
            case "FeignClient" -> departmentServicePost.postUserDepartmentFeign(userId, depId);
            case "WebClient" -> departmentServicePost.postUserDepartmentWebClient(userId, depId);
            case "RestTemplate" -> departmentServicePost.postUserDepartmentRestTemplate(userId, depId);
            default -> "Переменная department_client может быть: RestTemplate, FeignClient или WebClient";
        };
    }*/
}
