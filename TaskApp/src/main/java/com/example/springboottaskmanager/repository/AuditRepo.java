package com.example.springboottaskmanager.repository;

import com.example.springboottaskmanager.model.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepo extends JpaRepository<Audit, Long> {
}
