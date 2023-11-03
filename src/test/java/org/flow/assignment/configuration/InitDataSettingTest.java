package org.flow.assignment.configuration;

import org.flow.assignment.entity.ExtensionFilter;
import org.flow.assignment.repository.ExtensionFilterRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
public class InitDataSettingTest {

    @Autowired
    ExtensionFilterRepository filterRepository;

    @AfterEach
    public void tearDown() {
        filterRepository.deleteAll();
    }

    @Test
    public void 초기_설정으로_고정_확장자가_등록된다(){
        // given
        List<String> fixedExtensions = new ArrayList<>(Arrays.asList("bat", "cmd", "com", "cpl", "exe", "scr", "js"));

        // when
        List<String> insertedExtensions = filterRepository.findAll().stream().map(ExtensionFilter::getExtension).collect(Collectors.toList());

        //then
        for(int i=0;i<fixedExtensions.size();i++) {
            String givenExt = fixedExtensions.get(i);
            String insertedExt = insertedExtensions.get(i);
            assertThat(givenExt).isEqualTo(insertedExt);
        }

    }

}