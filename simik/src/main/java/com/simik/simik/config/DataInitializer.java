package com.simik.simik.config;

import com.simik.simik.entity.Role;
import com.simik.simik.entity.SubscriptionPackage;
import com.simik.simik.repository.RoleRepository;
import com.simik.simik.repository.SubscriptionPackageRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final SubscriptionPackageRepository packageRepository;

    public DataInitializer(RoleRepository roleRepository,
                           SubscriptionPackageRepository packageRepository) {
        this.roleRepository = roleRepository;
        this.packageRepository = packageRepository;
    }

    @Override
    public void run(String... args) {
        createRoleIfNotExists("PUNONJES");
        createRoleIfNotExists("PUNEDHENES");
        createRoleIfNotExists("ADMIN");

        createPackageIfNotExists("PAKETA_7_DITORE", 7, 1, 50);
        createPackageIfNotExists("PAKETA_MUJORE", 30, 10, 150);
        createPackageIfNotExists("PAKETA_3_MUJORE", 90, 9999, 300);
    }

    private void createRoleIfNotExists(String roleName) {
        if (roleRepository.findByName(roleName).isEmpty()) {
            roleRepository.save(new Role(roleName));
        }
    }

    private void createPackageIfNotExists(String name, int durationDays, int maxPosts, double price) {
        if (packageRepository.findByName(name).isEmpty()) {
            SubscriptionPackage subscriptionPackage = new SubscriptionPackage();
            subscriptionPackage.setName(name);
            subscriptionPackage.setDurationDays(durationDays);
            subscriptionPackage.setMaxPosts(maxPosts);
            subscriptionPackage.setPrice(price);

            packageRepository.save(subscriptionPackage);
        }
    }
}