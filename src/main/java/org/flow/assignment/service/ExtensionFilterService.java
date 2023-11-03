package org.flow.assignment.service;

import lombok.RequiredArgsConstructor;
import org.flow.assignment.entity.ExtensionFilter;
import org.flow.assignment.repository.ExtensionFilterRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ExtensionFilterService {

    private final ExtensionFilterRepository filterRepository;

    @Transactional
    public void addExtensionFilterSuccess(String extension) {
        filterRepository.addExtFilterByisFixedExtensionAndExtensionAndCreatedAt(true, extension, LocalDateTime.now());
    }

    @Transactional
    public List<String> getFixedExtensions() {
        return filterRepository.findAllExtensionsByIsFixedExtensionOrderById(true).stream().map(ExtensionFilter::getExtension).collect(Collectors.toList());
    }

    @Transactional
    public List<String> getCustomExtensions() {
        return filterRepository.findAllExtensionsByIsFixedExtensionOrderById(false).stream().map(ExtensionFilter::getExtension).collect(Collectors.toList());
    }

}
