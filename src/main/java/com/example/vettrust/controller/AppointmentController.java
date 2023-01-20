package com.example.vettrust.controller;

import com.example.vettrust.dto.AppointmentDto;
import com.example.vettrust.dto.VetReviewDto;
import com.example.vettrust.service.AppointmentService;
import com.example.vettrust.service.VetScheduleService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;


    @Autowired
    public AppointmentController(AppointmentService appointmentService1){
        this.appointmentService = appointmentService1;

    }

    @PostMapping("/save")
    public ResponseEntity<AppointmentDto> saveAppointment(@NotNull @RequestBody AppointmentDto appointmentDto){
        AppointmentDto result =  new AppointmentDto();
        try{
            result = appointmentService.saveAppointment(appointmentDto);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/update-by-id/{id}")
    public ResponseEntity<AppointmentDto> updateAppointment(@PathVariable("id") Long id, @RequestBody AppointmentDto appointmentDto){
        AppointmentDto result = appointmentService.updateAppointment(id, appointmentDto);
        if(result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("all-appointments-for-pet/{id}")
    public ResponseEntity<List<AppointmentDto>> getAllAppointmentsForPet(@PathVariable("id") Long id){
        List<AppointmentDto> appointments = appointmentService.getAllAppointmentsForPet(id);
        if(appointments == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("all-appointments-for-vet/{id}")
    public ResponseEntity<List<AppointmentDto>> getAllAppointmentsForVet(@PathVariable("id") Long id){
        List<AppointmentDto> appointments = appointmentService.getAllAppointmentsForVet(id);
        if(appointments == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }


}
