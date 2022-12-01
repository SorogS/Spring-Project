package com.sorog.carrent;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PageTest {
    @Autowired
    private MockMvc mockMvc;

    private static final Logger logger = Logger.getLogger(PageTest.class);

    @Test
    public void loadMainPage() throws Exception {
        logger.info("running main page test");
        mockMvc.perform(get("/")).andDo(print()).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void LoadUser() throws Exception {
        logger.info("running get /users api");
        mockMvc.perform(post("/users")).andDo(print()).andExpect(status().is2xxSuccessful());
    }

}