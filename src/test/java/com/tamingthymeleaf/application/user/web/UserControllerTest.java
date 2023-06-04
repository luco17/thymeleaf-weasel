package com.tamingthymeleaf.application.user.web;

import com.tamingthymeleaf.application.infrastructure.security.WebSecurityConfiguration;
import com.tamingthymeleaf.application.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    void testGetUsersRedirectToLoginWhenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

//    @Test
//    @WithUserDetails(USERNAME_USER)
//    void testGetUsersAsUser() throws Exception {
//        when(userService.getUsers(any(Pageable.class)))
//                .thenReturn(Page.empty());
//
//        mockMvc.perform(get("/users"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }

    @TestConfiguration
    @Import(WebSecurityConfiguration.class)
    @MockBean(UserDetailsService.class)
    static class TestConfig {
        @Bean
        public PasswordEncoder passwordEncoder() {
            return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }

//        @Bean
//        public ITemplateResolver svgTemplateResolver() {
//            SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
//            resolver.setPrefix("classpath:/templates/svg/");
//            resolver.setSuffix(".svg");
//            resolver.setTemplateMode("XML");
//
//            return resolver;
//        }

//        @Bean
//        public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//            return new StubUserDetailsService(passwordEncoder);
//        }
    }
}
