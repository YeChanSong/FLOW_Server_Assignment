package org.flow.assignment.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class TestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testResponseDto가_리턴된다() throws Exception{
        // given
        String firstParam = "first";
        String secondParam = "second";

        //when then
        mvc.perform(MockMvcRequestBuilders.get("/test")
                .param("firstParam", firstParam)
                .param("secondParam", secondParam))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstParam", is(firstParam)))
                .andExpect(jsonPath("$.secondParam", is(secondParam)));
    }
}