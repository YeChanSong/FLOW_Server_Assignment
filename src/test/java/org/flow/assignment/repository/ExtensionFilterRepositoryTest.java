package org.flow.assignment.repository;

import org.flow.assignment.configuration.InitDataSetting;
import org.flow.assignment.entity.ExtensionFilter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ExtensionFilterRepositoryTest {

    @Autowired
    private ExtensionFilterRepository filterRepository;
    
    @AfterEach
    public void tearDown() { filterRepository.deleteAll(); }

    @MockBean
    private InitDataSetting initSetting;

    @Test
    public void ExtensionFilter가_저장된다() {
        // applicationRunner block
        doNothing().when(initSetting).run(any());

        //given
        Boolean isFixed = true;
        String fixedExt = ".fixed";

        Boolean isNotFixed = false;
        String customExt = ".custom";
        LocalDateTime genTime = LocalDateTime.now();

        filterRepository.save(ExtensionFilter.builder()
                .isFixed(isFixed)
                .isActivate(true)
                .extension(fixedExt)
                .build());

        filterRepository.save(ExtensionFilter.builder()
                .isFixed(isNotFixed)
                .extension(customExt)
                .isActivate(true)
                .build());

        // when
        List<ExtensionFilter> saveResults = filterRepository.findAll();

        // then
        ExtensionFilter fixed = saveResults.get(0);
        assertThat(fixed.getIsFixedExtension()).isEqualTo(isFixed);
        assertThat(fixed.getIsActivate()).isEqualTo(true);
        assertThat(fixed.getExtension()).isEqualTo(fixedExt);
        assertThat(fixed.getCreatedAt()).isAfter(genTime);

        ExtensionFilter custom = saveResults.get(1);
        assertThat(custom.getIsFixedExtension()).isEqualTo(isNotFixed);
        assertThat(custom.getIsActivate()).isEqualTo(true);
        assertThat(custom.getExtension()).isEqualTo(customExt);
        assertThat(custom.getCreatedAt()).isAfter(genTime);

    }

    @Test
    public void Extension_중복_저장_방지된다() {

        // given
        String duplicateFixedExt = "bat";
        String duplicateCustomExt = "extension";

        filterRepository.save(ExtensionFilter.builder().isFixed(true).extension(duplicateFixedExt).isActivate(true).build());
        filterRepository.save(ExtensionFilter.builder().isFixed(false).extension(duplicateCustomExt).isActivate(true).build());

        try {
            // when
            filterRepository.save(ExtensionFilter.builder().isFixed(true).extension(duplicateFixedExt).isActivate(true).build());
        } catch (Exception e) {
            // then
            assertThat(e.getClass().getName()).isEqualTo("org.springframework.dao.DataIntegrityViolationException");
        }

        try {
            // when
            filterRepository.save(ExtensionFilter.builder().isFixed(false).extension(duplicateCustomExt).isActivate(true).build());
        } catch (Exception e) {
            // then
            assertThat(e.getClass().getName()).isEqualTo("org.springframework.dao.DataIntegrityViolationException");
        }

        // when
        List<Long> fixedExts = filterRepository.findAllByExtensionOrderById(duplicateFixedExt).stream().map(ExtensionFilter::getId).collect(Collectors.toList());
        List<Long> customExts = filterRepository.findAllByExtensionOrderById(duplicateCustomExt).stream().map(ExtensionFilter::getId).collect(Collectors.toList());

        // then
        assertThat(fixedExts.size()).isEqualTo(1);
        assertThat(customExts.size()).isEqualTo(1);

    }
    
}