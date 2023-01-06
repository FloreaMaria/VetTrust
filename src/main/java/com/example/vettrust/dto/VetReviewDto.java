package com.example.vettrust.dto;

import com.example.vettrust.model.VetReview;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class VetReviewDto {

    private int stars;
    private String comment;
    private Long petOwnerId;
    private Long vetId;

    public static @NotNull VetReviewDto entityToDto(@NotNull VetReview vetReview) {
        VetReviewDto dto = new VetReviewDto();
        dto.setComment(vetReview.getComment());
        dto.setStars(vetReview.getStars());
        dto.setVetId(vetReview.getVetUser().getId());
        dto.setPetOwnerId(vetReview.getPetOwner().getId());
        return dto;
    }
}
