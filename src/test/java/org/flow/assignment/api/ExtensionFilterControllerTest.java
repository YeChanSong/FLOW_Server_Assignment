package org.flow.assignment.api;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.filter;

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

    @Test
    public void extension의_수는_200을_넘지_않는다() {

        // given
        List<ExtensionFilter> randomExtFilters = new ArrayList<>();
        filterRepository.save(ExtensionFilter.builder().isFixed(true).isActivate(true).extension("bat").build());
        for(int i=0;i<200;i++) {
            String randStr = RandomString.make(10);
            randomExtFilters.add(ExtensionFilter.builder().isFixed(false).isActivate(true).extension(randStr).build());
        }
        filterRepository.saveAll(randomExtFilters);

        ExtensionFilterRequestDto customRequesDto = ExtensionFilterRequestDto
                .builder()
                .extension("exts")
                .build();

        String url = "http://localhost:"+port+"/api/assignment/filter/extension";

        // when
        ResponseEntity<ExtensionFilterResponseDto> customResponse = restTemplate.postForEntity(url, customRequesDto, ExtensionFilterResponseDto.class);

        // then
        assertThat(customResponse.getBody().getCustomExtensions().size()).isEqualTo(200);
        assertThat(customResponse.getBody().getMessage()).isEqualTo("커스텀 확장자의 수가 200을 넘었습니다.");
    }

    @Test
    public void extension의_길이는_20을_넘지_않는다() {

        // given
        String ext20 = "asdfasdfasdfasdfasdf";
        String ext21 = "asdfasdfasdfasdfasdfa";

        ExtensionFilterRequestDto ext20RequesDto = ExtensionFilterRequestDto
                .builder()
                .extension(ext20)
                .build();

        ExtensionFilterRequestDto ext21RequesDto = ExtensionFilterRequestDto
                .builder()
                .extension(ext21)
                .build();

        String url = "http://localhost:"+port+"/api/assignment/filter/extension";

        // when
        ResponseEntity<ExtensionFilterResponseDto> ext20Response = restTemplate.postForEntity(url, ext20RequesDto, ExtensionFilterResponseDto.class);

        // then
        assertThat(ext20Response.getBody().getMessage()).isEqualTo("정상 처리되었습니다.");

        ExtensionFilter extension = filterRepository.findByExtension(ext20);
        assertThat(extension.getExtension()).isEqualTo(ext20);

        // when
        ResponseEntity<ExtensionFilterResponseDto> ext21Response = restTemplate.postForEntity(url, ext21RequesDto, ExtensionFilterResponseDto.class);

        // then
        assertThat(ext21Response.getBody().getMessage()).isEqualTo("확장자의 길이는 20자 이하만 가능합니다.");
        List<ExtensionFilter> extensions = filterRepository.findAllExtensionsByIsFixedExtensionAndIsActivateOrderById(false, true);
        assertThat(extensions.size()).isEqualTo(1);
    }

}