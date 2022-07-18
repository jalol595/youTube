package com.company.youtube.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    private static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources",
            "/swagger-resources/**"
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Authentication
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
       /* auth.inMemoryAuthentication()
                .withUser("Ali").password("{bcrypt}$2a$10$V93CWoH3NxAPC7VzPd9ouuU8PWvZWYdoo94H3HOZ8kFSkBAvYssEe").roles("ADMIN")
                .and()
                .withUser("Vali").password("{noop}valish123").roles("PROFILE");*/
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Authorization
        http.authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers("/register", "/register/*").permitAll()
                .antMatchers("/register", "/register/email/verification/**").permitAll()
                .antMatchers("/auth/*").permitAll()
                .antMatchers("/playlist_video/**").permitAll()
                .antMatchers("/channel/*").permitAll()
                .antMatchers("/video/increaseViewCountbyKey/*").permitAll()
                .antMatchers("/video/getVideoPaginationbyCategoryId/*").permitAll()
                .antMatchers("/video/videoByTagIdWithPagination/*").permitAll()
                .antMatchers("/video/videoByTitle/*").permitAll()
                .antMatchers("/playlist/*").permitAll()
                .antMatchers("/playlist/playListByChannelKey/*").permitAll()
                .antMatchers("/playlist/fullInfo/*").permitAll()
                .antMatchers("/attache", "/attache/**").permitAll()
                .antMatchers("/category/list").permitAll()
                .antMatchers("/tag/list").permitAll()
                .antMatchers("/tag/create").permitAll()
                .antMatchers("/attache/adm", "/attache/adm/*").hasRole("ADMIN")
                .antMatchers("/category", "/category/**").hasRole("ADMIN")
                .antMatchers("/tag", "/tag/**").hasRole("ADMIN")
                .antMatchers("/profile/adm/*", "/profile/adm/*").hasRole( "ADMIN")
                .antMatchers("/channel/adm/*", "/channel/adm/*").hasRole( "ADMIN")
                .antMatchers("/playlist/adm/*", "/playlist/adm/*").hasRole( "ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors().disable().csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        /*return NoOpPasswordEncoder.getInstance();*/
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
