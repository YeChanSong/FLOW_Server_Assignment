package org.flow.assignment.repository;

import org.flow.assignment.entity.ExtensionFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtensionFilterRepository extends JpaRepository<ExtensionFilter, Long> {

}
