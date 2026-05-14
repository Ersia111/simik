package com.simik.simik.repository;

import com.simik.simik.entity.EmployerSubscription;
import com.simik.simik.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployerSubscriptionRepository
        extends JpaRepository<EmployerSubscription, Long> {

    Optional<EmployerSubscription> findByEmployerAndActiveTrue(User employer);

    Optional<EmployerSubscription>
    findByEmployerAndActiveTrueAndStatus(
            User employer,
            String status
    );

    List<EmployerSubscription> findByStatus(String status);

    List<EmployerSubscription> findByEmployer(User employer);
}