package com.example.vettrust.service;

import com.example.vettrust.dto.VetReviewDto;
import com.example.vettrust.dto.VetScheduleDto;
import com.example.vettrust.model.VetReview;
import com.example.vettrust.model.VetSchedule;
import com.example.vettrust.repository.VetReviewRepository;
import com.example.vettrust.repository.VetUserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class VetUserService {
    private final VetUserRepository vetUserRepository;
    private final VetReviewRepository vetReviewRepository;

    @Autowired
    public VetUserService(VetUserRepository vetUserRepository, VetReviewRepository vetReviewRepository) {
        this.vetUserRepository = vetUserRepository;
        this.vetReviewRepository = vetReviewRepository;
    }

    public List<VetReviewDto>getReviewsForVet(@NotNull Long vetId){
        List<VetReview> vetReviews = vetReviewRepository.findAllByVetUserId(vetId);
        return vetReviews.stream().map(VetReviewDto::entityToDto).collect(toList());
    }
}
