package org.flow.assignment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExtensionWithStateDto {
    private String extension;
    private boolean isActivate;

    @Builder
    public ExtensionWithStateDto(String ext, boolean isActivate) {
        this.extension = ext;
        this.isActivate = isActivate;
    }
}