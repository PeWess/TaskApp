package com.example.springboottaskmanager.appconfig;

import com.example.springboottaskmanager.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {
    /**
     * Репозиторий пользователей.
     */
    private final UserRepo userRepo;

    /**
     * Бин, получающий записи из репозитория по лоигну пользователя.
     * @return Пользователя, если такой есть в репозитории.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepo.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));
    }

    /**
     * @return Результат проверки.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthProv = new DaoAuthenticationProvider();
        daoAuthProv.setUserDetailsService(userDetailsService());
        daoAuthProv.setPasswordEncoder(passwordEncoder());
        return daoAuthProv;
    }

    /**
     * @param config Настройки аутентификации.
     * @return Результат настроки менеджера аутентификации.
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(
            final AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * @return Кодировщик паролей
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
