package org.flow.assignment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExtensionFilterRequestDto {

    private String extension;

    @Builder
    public ExtensionFilterRequestDto(String extension) {
        this.extension = extension;
    }

}
