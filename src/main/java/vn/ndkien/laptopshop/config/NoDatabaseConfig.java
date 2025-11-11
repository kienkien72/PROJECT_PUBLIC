package vn.ndkien.laptopshop.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class NoDatabaseConfig {

    @Bean
    @ConditionalOnMissingBean
    public UserDetailsService userDetailsService() {
        // Create in-memory admin user when no database is available
        UserDetails admin = User.builder()
                .username("admin@admin.com")
                .password("$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi") // 123456
                .authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}