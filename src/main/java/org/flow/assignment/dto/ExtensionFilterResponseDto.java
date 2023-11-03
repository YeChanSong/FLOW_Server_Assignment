package org.flow.assignment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Getter
@NoArgsConstructor
public class ExtensionFilterResponseDto {
    private List<ExtensionWithStateDto> fixedExtensions;
    private List<String> customExtensions;
    private String message;
    @Builder
    public ExtensionFilterResponseDto(List<ExtensionWithStateDto> fixed, List<String> custom, String message) {
        this.fixedExtensions = fixed;
        this.customExtensions = custom;
        this.message = message;
    }

}

