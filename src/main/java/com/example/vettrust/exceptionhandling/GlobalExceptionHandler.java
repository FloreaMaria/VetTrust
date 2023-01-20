package com.example.vettrust.exceptionhandling;

import com.example.vettrust.exception.*;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({InvalidAppointmentStatusException.class,InvalidAvailableHoursException.class, InvalidWorkingDayException.class,
            InvalidWorkingHoursException.class, NoAppointmentFoundException.class, NoAppointmentTypeFoundException.class,
            NoAvailableWorkingHoursFoundException.class, NoChosenDateFoundException.class, NoChosenHourFoundException.class,
            NoClinicLocationFoundException.class, NoDiagnosisConclusionFoundException.class, NoPetFoundException.class,
            NoMatchingBetweenWorkingDayAndChosenDateException.class, NoPetOwnerFoundException.class, NoScheduleForVetIdAndLocationIdFoundException.class,
            NoVetScheduleFoundException.class, NoVetFoundException.class, NoWorkingHoursFoundException.class, ReviewAlreadyAddedException.class})
    public ResponseEntity<?> handleNotFoundException(RuntimeException e) {
        Map<String, String> responseParameters = new HashMap<>();
        responseParameters.put("Reason: ", e.getMessage());
        responseParameters.put("DateTime: ", LocalDateTime.now().toString());
        return ResponseEntity.badRequest().body(responseParameters);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleHibernateObjectValidation(MethodArgumentNotValidException exception) {
        Map<String, Object> responseParameters = new HashMap<>();

        List<String> errors = exception.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        responseParameters.put("Reason: ", errors);
        responseParameters.put("DateTime: ", LocalDateTime.now().toString());

        return ResponseEntity.badRequest().body(responseParameters);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleHibernateParametersValidation(ConstraintViolationException exception) {
        Map<String, Object> responseParameters = new HashMap<>();

        List<String> errors = Collections.singletonList(exception.getMessage());

        responseParameters.put("Reason: ", errors);
        responseParameters.put("DateTime: ", LocalDateTime.now().toString());

        return ResponseEntity.badRequest().body(responseParameters);
    }
}
