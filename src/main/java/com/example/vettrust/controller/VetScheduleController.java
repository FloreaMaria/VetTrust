package com.example.vettrust.controller;

import com.example.vettrust.dto.VetScheduleDto;
import com.example.vettrust.model.VetSchedule;
import com.example.vettrust.service.VetScheduleService;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
public class VetScheduleController {
    private final VetScheduleService vetScheduleService;

    @Autowired
    public VetScheduleController(VetScheduleService vetScheduleService) {
        this.vetScheduleService = vetScheduleService;
    }

    @PostMapping("save")
    public ResponseEntity<VetScheduleDto> save(@RequestBody VetScheduleDto vetScheduleDto){
        return ResponseEntity.of(Optional.of(vetScheduleService.save(vetScheduleDto)));
    }

    @GetMapping("/all-for-vet/{id}")
    public ResponseEntity<List<VetScheduleDto>> getSchedulesForVetId(@PathVariable("id") Long id){
        return new ResponseEntity<>(vetScheduleService.findAllByVetUserId(id), HttpStatus.OK);
    }

    @GetMapping("/all-for-vet-and-location")
    public ResponseEntity<List<VetSchedule>> getSchedulesForVetAndLocation(@RequestBody Map<String, Long> payload){
        Long vetId = payload.get("vet_id");
        Long locationId = payload.get("location_id");
        return new ResponseEntity<>(vetScheduleService.findAllByVetUserIdAndClinicLocationId(vetId, locationId), HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<VetScheduleDto> updateAvailableHours(@PathVariable("id") Long id, @RequestBody VetScheduleDto vetScheduleDto){
        return new ResponseEntity<>(vetScheduleService.updateHours(id, vetScheduleDto), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteScheduleById(@PathVariable("id") Long id){
        boolean result = vetScheduleService.deleteSchedule(id);
        if(result){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
