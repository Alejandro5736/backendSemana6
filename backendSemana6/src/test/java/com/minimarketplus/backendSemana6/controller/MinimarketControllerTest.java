package com.minimarketplus.backendSemana6.controller;

import com.minimarketplus.backendSemana6.model.Usuario;
import com.minimarketplus.backendSemana6.service.MinimarketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MinimarketController.class)
public class MinimarketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MinimarketService minimarketService;

    @Test
    public void testEndpointAutenticarRetornaOk() throws Exception {
        Usuario usuario = new Usuario("juan123", "clave123", "CAJERO");
        when(minimarketService.autenticar(usuario, "clave123")).thenReturn(true);

        String jsonPayload = "{\"username\":\"juan123\",\"password\":\"clave123\",\"rol\":\"CAJERO\"}";

        mockMvc.perform(post("/api/minimarket/autenticar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload)
                .param("passwordIngresada", "clave123"))
                .andExpect(status().isOk());
    }
}