package com.example.vettrust.controller;

import com.example.vettrust.dto.AppointmentDto;
import com.example.vettrust.dto.VetReviewDto;
import com.example.vettrust.enums.AppointmentValueType;
import com.example.vettrust.service.AppointmentService;
import com.example.vettrust.service.VetScheduleService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

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

    @PostMapping("filter-by-type")
    public ResponseEntity<List<AppointmentDto>> getAllSpecificTypeAppointmentsForVet(@RequestBody Map<String, String> payload){
        Long vetId = Long.valueOf(payload.get("vet_id"));
        AppointmentValueType appointmentValueType = AppointmentValueType.valueOf(payload.get("appointment_value_type"));
        List<AppointmentDto> appointments = appointmentService.getAllSpecificTypeAppointmentsForVet(vetId, appointmentValueType);
        if(appointments == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @PostMapping("filter-by-date")
    public ResponseEntity<List<AppointmentDto>> getAllSpecificDateAppointmentsForVet(@RequestBody Map<String, String> payload){
        Long vetId = Long.valueOf(payload.get("vet_id"));
        String date = payload.get("date");
        List<AppointmentDto> appointments = appointmentService.getAllSpecificDateAppointmentsForVet(vetId, date);
        if(appointments == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }


    }
