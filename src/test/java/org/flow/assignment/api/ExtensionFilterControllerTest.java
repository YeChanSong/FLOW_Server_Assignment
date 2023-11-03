package org.flow.assignment.api;

import org.flow.assignment.configuration.InitDataSetting;
import org.flow.assignment.dto.ExtensionFilterRequestDto;
import org.flow.assignment.dto.ExtensionFilterResponseDto;
import org.flow.assignment.entity.ExtensionFilter;
import org.flow.assignment.repository.ExtensionFilterRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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

    @Autowired
    ExtensionFilterRepository filterRepository;

    @MockBean
    InitDataSetting initSetting;

    @AfterEach
    public void tearDown() { filterRepository.deleteAll(); }

    @Test
    public void extensionFilter_등록된다() {

        // given
        List<String> addedExtension = new ArrayList<>(Arrays.asList("extension"));
        ExtensionFilterRequestDto requesDto = ExtensionFilterRequestDto
                .builder()
                .extension("extension")
                .build();

        String url = "http://localhost:"+port+"/api/assignment/filter/extension";

        // when
        ResponseEntity<ExtensionFilterResponseDto> responseDto = restTemplate.postForEntity(url, requesDto, ExtensionFilterResponseDto.class);

        // then
        assertThat(responseDto.getBody().getCustomExtensions()).isEqualTo(addedExtension);
        assertThat(responseDto.getBody().getMessage()).isEqualTo("정상 처리되었습니다.");
    }

    @Test
    public void extensionFilter_수정된다() {

        // given
        String fixedExt = "bat", customExt = "extension";
        filterRepository.save(ExtensionFilter.builder().isFixed(true).isActivate(false).extension(fixedExt).build());
        filterRepository.save(ExtensionFilter.builder().isFixed(false).isActivate(true).extension(customExt).build());

        ExtensionFilterRequestDto fixedRequestDto = ExtensionFilterRequestDto
                .builder()
                .extension(fixedExt)
                .build();

        ExtensionFilterRequestDto customRequesDto = ExtensionFilterRequestDto
                .builder()
                .extension(customExt)
                .build();

        String url = "http://localhost:"+port+"/api/assignment/filter/extension";
        HttpEntity<ExtensionFilterRequestDto> fixedRequestEntity = new HttpEntity<>(fixedRequestDto);
        HttpEntity<ExtensionFilterRequestDto> customRequestEntity = new HttpEntity<>(customRequesDto);

        // when
        ResponseEntity<ExtensionFilterResponseDto> fixedResponse = restTemplate.exchange(url, HttpMethod.PUT, fixedRequestEntity, ExtensionFilterResponseDto.class);
        ResponseEntity<ExtensionFilterResponseDto> customResponse = restTemplate.exchange(url, HttpMethod.PUT, customRequestEntity, ExtensionFilterResponseDto.class);

        // then
        assertThat(fixedResponse.getBody().getFixedExtensions().size()).isEqualTo(1);
        assertThat(customResponse.getBody().getCustomExtensions().size()).isEqualTo(0);

    }

}