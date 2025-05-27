package com.diatoz.assesment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRegister() throws Exception {
        String requestBody = objectMapper.writeValueAsString(new RegisterRequest(
                "testuser", "password123"
        ));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginUnauthorized() throws Exception {
        String requestBody = objectMapper.writeValueAsString(new LoginRequest(
                "invaliduser", "wrongpassword"
        ));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    static class RegisterRequest {
        public String username;
        public String password;

        public RegisterRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    static class LoginRequest {
        public String username;
        public String password;

        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }
}
