package com.example.vettrust.repository;

import com.example.vettrust.model.VetReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VetReviewRepository extends JpaRepository<VetReview, Long> {
    List<VetReview> findAllByVetUserId(Long vetId);
}
