package com.simik.simik.service.impl;

import com.simik.simik.dto.BuyPackageRequest;
import com.simik.simik.entity.EmployerSubscription;
import com.simik.simik.entity.SubscriptionPackage;
import com.simik.simik.entity.User;
import com.simik.simik.repository.EmployerSubscriptionRepository;
import com.simik.simik.repository.SubscriptionPackageRepository;
import com.simik.simik.repository.UserRepository;
import com.simik.simik.service.PackageService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PackageServiceImpl implements PackageService {

    private final UserRepository userRepository;
    private final SubscriptionPackageRepository subscriptionPackageRepository;
    private final EmployerSubscriptionRepository employerSubscriptionRepository;

    public PackageServiceImpl(
            UserRepository userRepository,
            SubscriptionPackageRepository subscriptionPackageRepository,
            EmployerSubscriptionRepository employerSubscriptionRepository
    ) {
        this.userRepository = userRepository;
        this.subscriptionPackageRepository = subscriptionPackageRepository;
        this.employerSubscriptionRepository = employerSubscriptionRepository;
    }

    @Override
    public void buyPackage(BuyPackageRequest request) {

        User employer = userRepository.findByEmail(request.getEmployerEmail())
                .orElseThrow(() -> new RuntimeException("Punedhenesi nuk u gjet."));

        if (!employer.getRole().getName().equals("PUNEDHENES")) {
            throw new RuntimeException("Vetem punedhenesi mund te bleje pakete.");
        }

        SubscriptionPackage selectedPackage =
                subscriptionPackageRepository.findByName(request.getPackageName())
                        .orElseThrow(() -> new RuntimeException("Paketa nuk u gjet."));

        EmployerSubscription subscription = new EmployerSubscription();

        subscription.setEmployer(employer);
        subscription.setSubscriptionPackage(selectedPackage);

        subscription.setStartDate(LocalDate.now());

        subscription.setEndDate(
                LocalDate.now().plusDays(selectedPackage.getDurationDays())
        );

        subscription.setPostsUsed(0);

        subscription.setActive(false);

        subscription.setStatus("PENDING");

        employerSubscriptionRepository.save(subscription);
    }

    @Override
    public List<SubscriptionPackage> getAllPackages() {
        return subscriptionPackageRepository.findAll();
    }

    @Override
    public List<EmployerSubscription> getPendingSubscriptions() {
        return employerSubscriptionRepository.findByStatus("PENDING");
    }

    @Override
    public void approveSubscription(Long subscriptionId) {

        EmployerSubscription subscription =
                employerSubscriptionRepository.findById(subscriptionId)
                        .orElseThrow(() ->
                                new RuntimeException("Kerkesa nuk u gjet.")
                        );

        User employer = subscription.getEmployer();

        List<EmployerSubscription> activeSubscriptions =
                employerSubscriptionRepository.findByEmployerAndActiveTrueAndStatus(
                        employer,
                        "APPROVED"
                );

        for (EmployerSubscription activeSub : activeSubscriptions) {
            activeSub.setActive(false);
            employerSubscriptionRepository.save(activeSub);
        }

        subscription.setStatus("APPROVED");
        subscription.setActive(true);

        employerSubscriptionRepository.save(subscription);
    }
    @Override
    public void rejectSubscription(Long subscriptionId) {

        EmployerSubscription subscription =
                employerSubscriptionRepository.findById(subscriptionId)
                        .orElseThrow(() ->
                                new RuntimeException("Kerkesa nuk u gjet.")
                        );

        subscription.setStatus("REJECTED");
        subscription.setActive(false);

        employerSubscriptionRepository.save(subscription);
    }

    @Override
    public List<EmployerSubscription> getSubscriptionsByEmployerEmail(String email) {
        User employer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Punedhenesi nuk u gjet."));

        return employerSubscriptionRepository.findByEmployer(employer);
    }
}