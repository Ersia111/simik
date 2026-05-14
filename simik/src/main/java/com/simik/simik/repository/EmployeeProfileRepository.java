package com.simik.simik.repository;

import com.simik.simik.entity.EmployeeProfile;
import com.simik.simik.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfile, Long> {

    Optional<EmployeeProfile> findByEmployee(User employee);

    List<EmployeeProfile> findByProfessionContainingIgnoreCase(String profession);
}