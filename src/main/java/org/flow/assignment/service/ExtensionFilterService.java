package org.flow.assignment.service;

import lombok.RequiredArgsConstructor;
import org.flow.assignment.dto.ExtensionWithStateDto;
import org.flow.assignment.entity.ExtensionFilter;
import org.flow.assignment.repository.ExtensionFilterRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ExtensionFilterService {

    private final ExtensionFilterRepository filterRepository;

    @Transactional
    public void addExtensionFilter(String extension) {
        filterRepository.save(ExtensionFilter.builder().isFixed(false).isActivate(true).extension(extension).build());
    }

    @Transactional
    public List<ExtensionWithStateDto> getFixedExtensions() {

        return filterRepository.findAllExtensionsByIsFixedExtensionAndIsActivateOrderById(true, true).stream().map(ExtensionFilter::toExtensionWithState).collect(Collectors.toList());
    }

    @Transactional
    public List<String> getCustomExtensions() {
        return filterRepository.findAllExtensionsByIsFixedExtensionAndIsActivateOrderById(false, true).stream().map(ExtensionFilter::getExtension).collect(Collectors.toList());
    }

}
