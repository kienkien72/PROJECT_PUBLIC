package vn.ndkien.laptopshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

import jakarta.servlet.DispatcherType;
import vn.ndkien.laptopshop.service.CustomUserDetailsService;
import vn.ndkien.laptopshop.service.UserService;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

        // Ghi đè PasswordEncoder -> BCryptPasswordEncoder
        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        // Ghi đè UserDetailsService -> CustomUserDetailsService
        @Bean
        public UserDetailsService userDetailsService(UserService userService) {
                return new CustomUserDetailsService(userService);
        }

        @Bean
        public DaoAuthenticationProvider authProvider(
                        PasswordEncoder passwordEncoder,
                        UserDetailsService userDetailsService) {
                DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
                authProvider.setUserDetailsService(userDetailsService);
                authProvider.setPasswordEncoder(passwordEncoder);
                // authProvider.setHideUserNotFoundExceptions(false);
                return authProvider;
        }

        @Bean
        public AuthenticationSuccessHandler customHandlerRole() {
                return new CustomHandlerRole();
        }

        @Bean
        public SpringSessionRememberMeServices rememberMeServices() {
                SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
                // optionally customize
                rememberMeServices.setAlwaysRemember(true);
                return rememberMeServices;
        }

        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                // v6. lamda
                http
                                .authorizeHttpRequests(authorize -> authorize
                                                .dispatcherTypeMatchers(DispatcherType.FORWARD,
                                                                DispatcherType.INCLUDE)
                                                .permitAll()

                                                .requestMatchers("/", "/product/**", "/login", "/client/**", "/css/**",
                                                                "/js/**",
                                                                "/images/**")
                                                .permitAll()
                                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                                .anyRequest().authenticated())

                                .sessionManagement((sessionManagement) -> sessionManagement
                                                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                                                .invalidSessionUrl("/logout?expired")
                                                .maximumSessions(1)
                                                .maxSessionsPreventsLogin(true))

                                .logout(logout -> logout.deleteCookies("JSESSIONID").invalidateHttpSession(true))

                                .rememberMe(r -> r.rememberMeServices(rememberMeServices()))

                                .formLogin(formLogin -> formLogin
                                                .loginPage("/login")
                                                .successHandler(customHandlerRole())
                                                .failureUrl("/login?error")
                                                .permitAll())
                                // Định nghĩa 403 khi bị lỗi
                                .exceptionHandling(ex -> ex.accessDeniedPage("/access-deny"));

                return http.build();
        }

}
