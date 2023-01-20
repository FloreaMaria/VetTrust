package com.example.vettrust.controller;

import com.example.vettrust.dto.PetDto;
import com.example.vettrust.dto.user.PetOwnerDto;
import com.example.vettrust.model.PetOwner;
import com.example.vettrust.service.PetService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<PetDto> getPetById(@PathVariable("id") Long id){
        PetDto petDto = petService.findByIdDao(id);
        return new ResponseEntity<>(petDto, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<PetDto> savePet(@RequestBody PetDto petDto){
        return ResponseEntity.of(Optional.of(petService.savePet(petDto)));
    }

    @GetMapping("/all")
    public  ResponseEntity<List<PetDto>> findAll(){
        return new ResponseEntity<>(petService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/get-by-owner-email")
    public  ResponseEntity<List<PetDto>> getByOwnerId(@RequestBody Map<String, String> payload){
        return new ResponseEntity<>(petService.findPetsByOwnerEmail(payload.get("email")), HttpStatus.OK);
    }
    @PutMapping("/update-by-id/{id}")
    public ResponseEntity<PetDto> update(@PathVariable("id") Long id, @RequestBody PetDto petDto){
        PetDto result =  petService.updatePet(id, petDto);
        if(result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        petService.deletePet(id);
        return ResponseEntity.ok().body("Pet with id " + id + " was successfully deleted");
    }


}
