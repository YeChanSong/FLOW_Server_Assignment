package org.flow.assignment.api;

import org.flow.assignment.dto.ExtensionFilterRequestDto;
import org.flow.assignment.dto.ExtensionFilterResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExtensionFilterControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void extensionFilter_등록된다() {

        // given
        List<String> initExtensions = new ArrayList<>(Arrays.asList("bat", "cmd", "com", "cpl", "exe", "scr", "js"));
        List<String> addedExtension = new ArrayList<>(Arrays.asList("extension"));
        ExtensionFilterRequestDto requesDto = ExtensionFilterRequestDto
                .builder()
                .extension("extension")
                .build();

        String url = "http://localhost:"+port+"/api/assignment/filter/extension";

        // when
        ResponseEntity<ExtensionFilterResponseDto> responseDto = restTemplate.postForEntity(url, requesDto, ExtensionFilterResponseDto.class);

        // then
        assertThat(responseDto.getBody().getFixedExtensions()).isEqualTo(initExtensions);
        assertThat(responseDto.getBody().getCustomExtensions()).isEqualTo(addedExtension);
        assertThat(responseDto.getBody().getMessage()).isEqualTo("정상 처리되었습니다.");
    }

}