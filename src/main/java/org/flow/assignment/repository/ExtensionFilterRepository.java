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

    List<ExtensionFilter> findAllExtensionsByIsFixedExtensionAndIsActivateOrderById(Boolean isFixedExtension, Boolean isActivate);

    List<ExtensionFilter> findAllByIsFixedExtension(Boolean isFixedExtension);

    List<ExtensionFilter> findAllByExtensionOrderById(String extension);
    ExtensionFilter findByExtension(String extension);
}
