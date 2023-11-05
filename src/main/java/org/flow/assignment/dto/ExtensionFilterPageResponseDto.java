package org.flow.assignment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.*;

@Getter
@NoArgsConstructor
public class ExtensionFilterPageResponseDto {
    private List<ExtensionWithStateDto> fixedExtensions;
    private List<String> customExtensions;
    private Integer customExtsLength;
    private String message;
    @Builder
    public ExtensionFilterPageResponseDto(List<ExtensionWithStateDto> fixed, List<String> custom, String message) {
        this.fixedExtensions = fixed;
        this.customExtensions = custom;
        this.customExtsLength = custom.size();
        this.message = message;
    }

}

