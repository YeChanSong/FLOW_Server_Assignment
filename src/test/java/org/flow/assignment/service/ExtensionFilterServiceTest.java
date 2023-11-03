package org.flow.assignment.service;

import org.flow.assignment.entity.ExtensionFilter;
import org.flow.assignment.repository.ExtensionFilterRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.UnexpectedRollbackException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ExtensionFilterServiceTest {

    @Autowired
    ExtensionFilterService filterService;

    @Autowired
    ExtensionFilterRepository filterRepository;

    @AfterEach
    public void tearDown() {
        filterRepository.deleteAll();
    }

    @Test
    public void 확장자_추가된다() {

        // given
        String ext = "extension";

        // when
        filterService.addExtensionFilterSuccess(ext);

        // then
        List<ExtensionFilter> searchedFilters = filterRepository.findAllByExtensionOrderById(ext);
        assertThat(searchedFilters.get(0).getExtension()).isEqualTo(ext);
    }

    @Test
    public void 확장자_중복_방지된다() {

        // given
        String ext = "extension";

        //when
        filterService.addExtensionFilterSuccess(ext);

        try {
            filterService.addExtensionFilterSuccess(ext);
        } catch (DataIntegrityViolationException | UnexpectedRollbackException e) {
            // do nothing
            System.out.println(">>>>>>>> "+e.getClass().getName());
        }

        // then
        List<ExtensionFilter> searchedFilters = filterRepository.findAllByExtensionOrderById(ext);
        assertThat(searchedFilters.size()).isEqualTo(1);
    }

    @Test
    public void 확장자_조회된다() {

        // given
        List<String> fixedExtensions = new ArrayList<>(Arrays.asList("bat", "cmd", "com", "cpl", "exe", "scr", "js"));
        String ext1 = "extension1", ext2 = "extension2";

        // when
        filterService.addExtensionFilterSuccess(ext1);
        filterService.addExtensionFilterSuccess(ext2);

        //then
        List<String> fixedExts = filterService.getFixedExtensions();
        List<String> customExts = filterService.getCustomExtensions();

        assertThat(fixedExts.size()).isEqualTo(fixedExtensions.size());
        assertThat(customExts.size()).isEqualTo(2);
    }



}