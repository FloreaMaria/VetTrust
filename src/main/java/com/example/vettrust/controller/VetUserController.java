package com.example.vettrust.controller;

import com.example.vettrust.dto.AppointmentDto;
import com.example.vettrust.dto.VetReviewDto;
import com.example.vettrust.dto.user.VetUserDto;
import com.example.vettrust.exception.NoVetFoundException;
import com.example.vettrust.service.VetUserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vet")
public class VetUserController {
    private final VetUserService vetUserService;

    @Autowired
    public VetUserController(VetUserService vetUserService) {
        this.vetUserService = vetUserService;
    }


    @PostMapping("/save")
    public ResponseEntity<VetUserDto> save(@NotNull @RequestBody @Valid VetUserDto vetUserDto){
        VetUserDto result =  new VetUserDto();
        try{
            result = vetUserService.saveVet(vetUserDto);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<?> findById(@NotNull @PathVariable("id") Long id){
        return ResponseEntity.ok().body(vetUserService.findByIdDto(id));

    }
    @PostMapping("/find-by-email")
    public ResponseEntity<?> findByEmail(@NotNull @RequestBody Map<String, String> payload){
        return ResponseEntity.ok().body(vetUserService.findByEmail(payload.get("email")));

    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<?> getAllReviews(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(vetUserService.getReviewsForVet(id));

    }
    @PutMapping("update-vet/{id}")
    public ResponseEntity<?> update(@NotNull @PathVariable("id") Long id, @RequestBody VetUserDto vetUserDto){
        return ResponseEntity.ok().body(vetUserService.updateVet(id, vetUserDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@NotNull @PathVariable("id") Long id){
        return ResponseEntity.ok().body(vetUserService.deleteVet(id));
    }

}
