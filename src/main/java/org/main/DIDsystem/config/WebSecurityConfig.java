//package org.main.DIDsystem.config;

/**
 * 当前版本暂不启用springboot security，已使用Java web Token
 */
//import org.main.DIDsystem.raw.CustomAuthenticationFilter;
//import org.main.DIDsystem.raw.JwtRequestFilter;
//import org.main.DIDsystem.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception { //显式加载bean
//        return super.authenticationManagerBean();
//    }
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private JwtRequestFilter jwtRequestFilter;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.addFilterBefore(new CustomAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)//提前处理请求
//                .formLogin()
//                .loginPage("/login")//请求为login，用security中自带的过滤器去处理
//                .successForwardUrl("/welcome")
//                .loginProcessingUrl("/user/login")
//                //.successHandler(new MyAuthenticationSuccessHandler())
//                .and()
//                .authorizeRequests() //请求授权
//                .antMatchers("static/**","templates/img/**","/","/favicon.ico","login").permitAll()
//                .anyRequest().authenticated()  //所有请求都拦截，进行认证
//                .and()
//                .headers().frameOptions().disable()
//                .and()
//                .csrf().disable(); // 禁用 CSRF 保护;
//        http.rememberMe().rememberMeParameter("remember");
//    }
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {//这里对密钥进行加密
//        // 指定认证服务
//        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring()
//                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "templates/img/**");
//    }
//
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        // 使用BCrypt加密密码
//        return new BCryptPasswordEncoder();
//    }
//}
//
