package org.flow.assignment.repository;

import org.flow.assignment.entity.ExtensionFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExtensionFilterRepository extends JpaRepository<ExtensionFilter, Long> {

    List<ExtensionFilter> findAllExtensionsByIsFixedExtensionOrderById(Boolean isFixedExtension);

    List<ExtensionFilter> findAllByExtensionOrderById(String extension);

    @Modifying
    @Query(value = "INSERT INTO extension_filter (is_fixed_extension, is_activate, extension, created_at) VALUES (false, :isActivate, :extension, :createTime)", nativeQuery = true)
    void addExtFilterByisFixedExtensionAndExtensionAndCreatedAt(Boolean isActivate, String extension, LocalDateTime createTime);

}
