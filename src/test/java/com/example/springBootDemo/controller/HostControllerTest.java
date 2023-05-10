package com.example.springBootDemo.controller;

import com.example.springBootDemo.service.HostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(HostController.class)
class HostControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private HostService hostService;

    @Test
    void getAllEmployees() throws Exception {
        // given
        when(hostService.getHostData()).thenReturn("Hostname=legion host address=127.0.0.1");

        // when
        ResultActions resultActions = mockMvc
                .perform(get("/")).andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(containsString("Hostname=")));
    }
}