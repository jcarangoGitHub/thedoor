package com.jca.thedoor.security.config;

import com.jca.thedoor.constants.ApiPaths;
import com.jca.thedoor.constants.enums.RolesEnum;
import com.jca.thedoor.security.jwt.JwtAuthEntryPoint;
import com.jca.thedoor.security.jwt.JwtRequestFilter;
import com.jca.thedoor.security.service.UserDetailsServiceImpl;
import com.jca.thedoor.util.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.*;

/**
 * Clase para la configuración de seguridad Spring Security
 */
@Configuration
@EnableWebSecurity // permite a Spring aplicar esta configuracion a la configuraicon de seguridad global
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(ApplicationProperties.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final ClearSiteDataHeaderWriter.Directive[] SOURCE =
            {CACHE, COOKIES, STORAGE, EXECUTION_CONTEXTS};

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    // ================ CREACIÓN DE BEANS ======================
    @Bean
    public JwtRequestFilter authenticationJwtTokenFilter() {
        return new JwtRequestFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configuracion global de CORS para toda la aplicacion
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // configuration.setAllowedOrigins(List.of("http://localhost:3000", "https://angular-springboot-*.vercel.app"));
        configuration.setAllowedOriginPatterns(List.of("http://localhost:3000", "https://angular-springboot1-beta.vercel.app"));
        configuration.setAllowedMethods(List.of("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
        configuration.setAllowedHeaders(List.of("Access-Control-Allow-Origin", "X-Requested-With",
                "Origin", "Content-Type", "Accept", "Authorization", "application/json"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // ========================= OVERRIDE: SOBREESCRIBIR FUNCIONALIDAD SECURITY POR DEFECTO ======
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Cross-Site Request Forgery CSRF
        // CORS (Cross-origin resource sharing)
        //TODO separar los links a otro archivo

        for (String path : ApiPaths.SECURED_PATHS) {
            http.requiresChannel().antMatchers(path).requiresSecure();
        }

        http
            .cors().and()
            .csrf().disable()//no se tienen cookies, por eso se deshabilita
            .authorizeRequests()
                .antMatchers(ApiPaths.LOGIN, ApiPaths.LOGOUT, ApiPaths.LOGIN_PAGE, ApiPaths.REGISTER_ADMIN).permitAll()
                .antMatchers(ApiPaths.REGISTER_USER,
                        ApiPaths.EXCHANGER_RATES_CURRENT,
                        ApiPaths.EXCHANGER_RATES_LAST_DAYS,
                        ApiPaths.NOTEBOOK)
                .hasRole(RolesEnum.SUPER.name())
                .antMatchers(ApiPaths.USER_FIND_BY_USERNAME,
                        ApiPaths.USER_UPDATE,
                        ApiPaths.NOTEBOOK_FIND_ALL_BY_USER,
                        ApiPaths.NOTEBOOK_FIND_BY_USER_AND_NAME,
                        ApiPaths.COWORKER,
                        ApiPaths.COWORKER_FIND_BY_ID,
                        ApiPaths.COWORKER_FIND_ALL,
                        ApiPaths.COWORKER_FIND_REVIEWERS,
                        ApiPaths.HASHTAG,
                        ApiPaths.HASHTAG_FIND_ALL,
                        ApiPaths.THOUGHT,
                        ApiPaths.THOUGHT_FIND_BY_NOTEBOOK)
                .hasAnyRole(RolesEnum.SUPER.name(), RolesEnum.USER.name())
                .antMatchers(ApiPaths.SWAGGER_WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
            .accessDeniedHandler(accessDeniedHandler)
            .and()
            .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")));

        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public JwtRequestFilter authenticationTokenFilterBean() throws Exception {
        return new JwtRequestFilter();
    }

}
