package com.tamingthymeleaf.application.user.web;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.tamingthymeleaf.application.infrastructure.security.StubUserDetailsService;
import com.tamingthymeleaf.application.infrastructure.security.WebSecurityConfiguration;
import com.tamingthymeleaf.application.user.UserName;
import com.tamingthymeleaf.application.user.UserService;
import com.tamingthymeleaf.application.user.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.List;

import static com.tamingthymeleaf.application.infrastructure.security.StubUserDetailsService.USERNAME_ADMIN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
public class UserControllerHtmlUnitTest {

    @Autowired
    private WebClient webClient;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setup() {
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
    }

    @Test
    @WithUserDetails(USERNAME_ADMIN)
    void testGetUsersAsAdmin() throws Exception {
        when(userService.getUsers(any(Pageable.class))).thenReturn(
                new PageImpl<>(
                        List.of(
                                Users.createUser(new UserName("Kaden", "Whyte")),
                                Users.createUser(new UserName("Charlton", "Faulkner")),
                                Users.createUser(new UserName("Yuvaan", "Mcpherson"))
                        )
                )
        );

        HtmlPage htmlPage = webClient.getPage("/users");
        DomNodeList<DomElement> h1headers = htmlPage.getElementsByTagName("h1");
        assertThat(h1headers).hasSize(1)
                .element(0)
                .extracting(DomElement::asNormalizedText)
                .isEqualTo("Users");

        HtmlTable usersTable = htmlPage.getHtmlElementById("users-table");
        List<HtmlTableRow> rows = usersTable.getRows();

        HtmlTableRow headerRow = rows.get(0);
        assertThat(headerRow.getCell(0).asNormalizedText()).isEqualTo("Name");
        assertThat(headerRow.getCell(1).asNormalizedText()).isEqualTo("Gender");
        assertThat(headerRow.getCell(2).asNormalizedText()).isEqualTo("Birthday");
        assertThat(headerRow.getCell(3).asNormalizedText()).isEqualTo("Email");

        HtmlTableRow row1 = rows.get(0);
        assertThat(row1.getCell(0).asNormalizedText()).isEqualTo("Kaden Whyte");
        assertThat(row1.getCell(1).asNormalizedText()).isEqualTo("Female");
        assertThat(row1.getCell(2).asNormalizedText()).isEqualTo("2001-06-19");
        assertThat(row1.getCell(3).asNormalizedText()).isEqualTo("kaden.whyte@gmail.com");
    }

    @TestConfiguration
    @Import(WebSecurityConfiguration.class)
    static class TestConfig {
        @Bean
        public PasswordEncoder passwordEncoder() {
            return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }

        @Bean
        public ITemplateResolver svgTemplateResolver() {
            SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
            resolver.setPrefix("classpath:/templates/svg/");
            resolver.setSuffix(".svg");
            resolver.setTemplateMode("XML");

            return resolver;
        }

        @Bean
        public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
            return new StubUserDetailsService(passwordEncoder);
        }
    }
}
