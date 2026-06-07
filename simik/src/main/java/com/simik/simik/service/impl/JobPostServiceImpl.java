package com.simik.simik.service.impl;

import com.simik.simik.dto.JobPostRequest;
import com.simik.simik.entity.EmployerSubscription;
import com.simik.simik.entity.JobPost;
import com.simik.simik.entity.User;
import com.simik.simik.repository.EmployerSubscriptionRepository;
import com.simik.simik.repository.JobPostRepository;
import com.simik.simik.repository.UserRepository;
import com.simik.simik.service.JobPostService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class JobPostServiceImpl implements JobPostService {

    private final JobPostRepository jobPostRepository;
    private final UserRepository userRepository;
    private final EmployerSubscriptionRepository employerSubscriptionRepository;

    public JobPostServiceImpl(
            JobPostRepository jobPostRepository,
            UserRepository userRepository,
            EmployerSubscriptionRepository employerSubscriptionRepository
    ) {
        this.jobPostRepository = jobPostRepository;
        this.userRepository = userRepository;
        this.employerSubscriptionRepository = employerSubscriptionRepository;
    }

    @Override
    public void createJobPost(JobPostRequest request) {
        User employer = userRepository.findByEmail(request.getEmployerEmail())
                .orElseThrow(() -> new RuntimeException("Punëdhënësi nuk u gjet."));

        if (!employer.getRole().getName().equals("PUNEDHENES")) {
            throw new RuntimeException("Vetëm punëdhënësi mund të postojë njoftime.");
        }

        EmployerSubscription subscription = employerSubscriptionRepository
                .findTopByEmployerAndActiveTrueAndStatusOrderByEndDateDesc(
                        employer,
                        "APPROVED"
                )
                .orElseThrow(() -> new RuntimeException(
                        "Duhet të keni një paketë të aprovuar nga admini për të postuar njoftime."
                ));

        if (subscription.getEndDate().isBefore(LocalDate.now())) {
            subscription.setActive(false);
            subscription.setStatus("EXPIRED");
            employerSubscriptionRepository.save(subscription);

            throw new RuntimeException("Paketa ka skaduar.");
        }

        int maxPosts = subscription.getSubscriptionPackage().getMaxPosts();

        if (subscription.getPostsUsed() >= maxPosts) {
            throw new RuntimeException("Keni arritur limitin e postimeve për paketën tuaj.");
        }

        JobPost jobPost = new JobPost();
        jobPost.setTitle(request.getTitle());
        jobPost.setDescription(request.getDescription());
        jobPost.setCriteria(request.getCriteria());
        jobPost.setBenefits(request.getBenefits());
        jobPost.setEmployer(employer);
        jobPost.setActive(true);

        jobPostRepository.save(jobPost);

        subscription.setPostsUsed(subscription.getPostsUsed() + 1);
        employerSubscriptionRepository.save(subscription);
    }

    @Override
    public List<JobPost> getAllActiveJobPosts() {
        return jobPostRepository.findByActiveTrue();
    }
}