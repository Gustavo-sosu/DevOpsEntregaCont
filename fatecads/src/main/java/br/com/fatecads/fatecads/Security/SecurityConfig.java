package br.com.fatecads.fatecads.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(
                                                                "/",
                                                                "/index",
                                                                "/login",
                                                                "/cadastro",
                                                                "/cadastro/salvar",
                                                                "/acesso-negado",
                                                                "/css/**",
                                                                "/images/**")
                                                .permitAll()
                                                .requestMatchers("/produto/listar")
                                                .authenticated()
                                                .requestMatchers("/produto/detalhes/**", "/carrinho/**")
                                                .hasRole("USER")
                                                .requestMatchers("/home", "/fatecads", "/alunos/**", "/curso/**",
                                                                "/professor/**", "/disciplina/**", "/usuario/**",
                                                                "/produto/**", "/pedido/**")
                                                .hasRole("ADMIN")
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .successHandler(authenticationSuccessHandler())
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutSuccessUrl("/login?logout")
                                                .permitAll())
                                .exceptionHandling(exception -> exception.accessDeniedPage("/acesso-negado"));

                return http.build();
        }

        @Bean
        public AuthenticationSuccessHandler authenticationSuccessHandler() {
                return (request, response, authentication) -> {
                        boolean isAdmin = authentication.getAuthorities().stream()
                                        .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
                        response.sendRedirect(isAdmin ? "/home" : "/produto/listar");
                };
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
                        throws Exception {
                return config.getAuthenticationManager();
        }

}
