package com.simik.simik.repository;

import com.simik.simik.entity.SubscriptionPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionPackageRepository
        extends JpaRepository<SubscriptionPackage, Long> {

    Optional<SubscriptionPackage> findByName(String name);
}