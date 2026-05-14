package com.simik.simik.service;

import com.simik.simik.dto.BuyPackageRequest;
import com.simik.simik.entity.EmployerSubscription;
import com.simik.simik.entity.SubscriptionPackage;

import java.util.List;

public interface PackageService {

    void buyPackage(BuyPackageRequest request);

    List<SubscriptionPackage> getAllPackages();

    List<EmployerSubscription> getSubscriptionsByEmployerEmail(String email);

    List<EmployerSubscription> getPendingSubscriptions();

    void approveSubscription(Long subscriptionId);

    void rejectSubscription(Long subscriptionId);
}