package org.flow.assignment.repository;

import org.flow.assignment.entity.ExtensionFilter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ExtensionFilterRepositoryTest {

    @Autowired
    private ExtensionFilterRepository filterRepository;
    
    @AfterEach
    public void tearDown() { filterRepository.deleteAll(); }

    @Test
    public void ExtensionFilter가_저장된다() {

        //given
        Boolean isFixed = true;
        String fixedExt = ".fixed";

        Boolean isNotFixed = false;
        String customExt = ".custom";
        LocalDateTime genTime = LocalDateTime.now();

        filterRepository.save(ExtensionFilter.builder()
                .isFixed(isFixed)
                .extension(fixedExt)
                .build());

        filterRepository.save(ExtensionFilter.builder()
                .isFixed(isNotFixed)
                .extension(customExt)
                .build());

        // when
        List<ExtensionFilter> saveResults = filterRepository.findAll();

        // then
        ExtensionFilter fixed = saveResults.get(0);
        assertThat(fixed.getIsFixedExtension()).isEqualTo(isFixed);
        assertThat(fixed.getExtension()).isEqualTo(fixedExt);
        assertThat(fixed.getCreatedAt()).isAfter(genTime);

        ExtensionFilter custom = saveResults.get(1);
        assertThat(custom.getIsFixedExtension()).isEqualTo(isNotFixed);
        assertThat(custom.getExtension()).isEqualTo(customExt);
        assertThat(custom.getCreatedAt()).isAfter(genTime);

    }

    
}