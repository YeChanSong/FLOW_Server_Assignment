package org.flow.assignment.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(length = 20, nullable = false)
    private String extension;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public ExtensionFilter(Boolean isFixed, String extension) {
        this.isFixedExtension = isFixed;
        this.extension = extension;
        this.createdAt = LocalDateTime.now();
    }
}
