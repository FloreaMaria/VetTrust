package com.example.vettrust.controller;

import com.example.vettrust.dto.VetReviewDto;
import com.example.vettrust.service.VetUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/vet")
public class VetUserController {
    private final VetUserService vetUserService;

    @Autowired
    public VetUserController(VetUserService vetUserService) {
        this.vetUserService = vetUserService;
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<List<VetReviewDto>> getAllReviews(@PathVariable("id") Long id){
        return new ResponseEntity<>(vetUserService.getReviewsForVet(id), HttpStatus.OK);
    }

}
