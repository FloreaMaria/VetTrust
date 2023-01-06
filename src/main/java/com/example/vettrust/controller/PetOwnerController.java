package com.example.vettrust.controller;

import com.example.vettrust.dto.user.PetOwnerDto;
import com.example.vettrust.model.PetOwner;
import com.example.vettrust.service.PetOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/owner")
public class PetOwnerController {

    private final PetOwnerService petOwnerService;

    @Autowired
    public PetOwnerController(PetOwnerService petOwnerService) {
        this.petOwnerService = petOwnerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetOwnerDto> findPetOwnerById(@PathVariable("id") Long id){
       PetOwnerDto petOwnerDto = petOwnerService.findPetOwnerDto(id);
       return new ResponseEntity<>(petOwnerDto, HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<PetOwnerDto>> findAll(){
        return new ResponseEntity<>(petOwnerService.getAllPetOwners(), HttpStatus.OK);
    }

    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        petOwnerService.deletePetOwner(id);
        return ResponseEntity.ok().body("User with id " + id + " was successfully deleted");
    }

}
