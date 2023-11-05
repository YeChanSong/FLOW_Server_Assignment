package org.flow.assignment.configuration;

import lombok.RequiredArgsConstructor;
import org.flow.assignment.entity.ExtensionFilter;
import org.flow.assignment.repository.ExtensionFilterRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class InitDataSetting implements ApplicationRunner {
    private final ExtensionFilterRepository filterRepository;

    @Override
    public void run(ApplicationArguments args) {
        List<String> fixedExtensions = new ArrayList<>(Arrays.asList("bat", "cmd", "com", "cpl", "exe", "scr", "js"));
        ExtensionFilter existFixedExt = filterRepository.findByExtension("bat");
        if (existFixedExt == null) {
            List<ExtensionFilter> fixedExtFilters = new ArrayList<>();
            fixedExtensions.forEach( ext -> fixedExtFilters.add(new ExtensionFilter(true, false, ext)));
            filterRepository.saveAll(fixedExtFilters);
        }
    }

}
