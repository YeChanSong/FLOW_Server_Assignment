package org.flow.assignment.api;

import org.flow.assignment.dto.TestResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@RestController
public class TestController {

    @GetMapping("/test")
    public TestResponseDto controllerTestApi(
            @RequestParam("firstParam")
            String firstParam,
            @RequestParam("secondParam")
            String secondParam
    ) {
        return new TestResponseDto(firstParam, secondParam);
    }
}
