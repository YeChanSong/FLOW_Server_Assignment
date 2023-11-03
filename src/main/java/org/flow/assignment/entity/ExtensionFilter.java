package org.flow.assignment.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.flow.assignment.dto.ExtensionWithStateDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class ExtensionFilter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean isFixedExtension;

    @Column(nullable = false)
    private Boolean isActivate;

    @Column(length = 20, nullable = false, unique = true)
    private String extension;

    @Column(nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Builder
    public ExtensionFilter(Boolean isFixed, Boolean isActivate, String extension) {
        this.isFixedExtension = isFixed;
        this.isActivate = isActivate;
        this.extension = extension;
        this.createdAt = LocalDateTime.now();
    }

    public ExtensionWithStateDto toExtensionWithState() {
        return ExtensionWithStateDto.builder().ext(this.extension).isActivate(this.isActivate).build();
    }

    public void update(Boolean isActivate) {
        this.isActivate = isActivate;
    }
}
