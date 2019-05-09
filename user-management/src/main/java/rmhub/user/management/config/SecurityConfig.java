package rmhub.user.management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Bean
  public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
    jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
    return jwtAuthenticationTokenFilter;
  }
  
  @Bean
  @Autowired
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }
  
  @Bean
  public RestAuthenticationEntryPoint restServiceEntryPoint() {
    return new RestAuthenticationEntryPoint();
  }
  
  @Bean
  public CustomAccessDeniedHander customAccessDeniedHander() {
    return new CustomAccessDeniedHander();
  }
  
  protected void configure(HttpSecurity http) throws Exception{
    // Chỉ cho phép user đã đăng nhập mới được truy cập đường dẫn /admin/**
    http.authorizeRequests().antMatchers("/admin/**").authenticated();
    
    // Cấu hình remember me, thời gian là 1296000 giây
    http.rememberMe().key("uniqueAndSecret").tokenValiditySeconds(1296000);
    http.logout()
    .invalidateHttpSession(true)
    .clearAuthentication(true)
    .logoutSuccessUrl("/api/v1/login") .logoutUrl("/api/v1/logout") .permitAll();
    http.csrf().ignoringAntMatchers("/api/v1/**");
    http.authorizeRequests().antMatchers("/api/v1/login/auth", "/rmhub/test").permitAll();
    http.antMatcher("/api/v1/**").httpBasic().authenticationEntryPoint(restServiceEntryPoint())
    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    .and().authorizeRequests()
    .antMatchers(HttpMethod.GET, "/api/v1/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    .antMatchers(HttpMethod.POST, "/api/v1/**").access("hasRole('ROLE_ADMIN')")
    .antMatchers(HttpMethod.PUT, "/api/v1/**").access("hasRole('ROLE_ADMIN')")
    .antMatchers(HttpMethod.DELETE, "/api/v1/**").access("hasRole('ROLE_ADMIN')")
    .and()
    .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class).exceptionHandling()
    .accessDeniedHandler(customAccessDeniedHander());
  }
}
