package org.amsidh.mvc.controller;

import org.amsidh.mvc.ShopSpringApplication;
import org.amsidh.mvc.service.impl.WelcomeServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {WelcomeController.class})
@ContextConfiguration(classes = {ShopSpringApplication.class})
public class WelcomeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WelcomeServiceImpl welcomeService;

    @Test
    public void testWelcome() throws Exception {

        final String welcomeMessage = "This is test welcome message";
        when(welcomeService.getWelcomeMessage()).thenReturn(welcomeMessage);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String actualMessage = result.getResponse().getContentAsString();
        assertEquals(welcomeMessage, actualMessage);

    }
}
