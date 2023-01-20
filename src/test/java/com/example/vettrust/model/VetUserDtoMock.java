package com.example.vettrust.model;

import com.example.vettrust.dto.user.VetUserDto;
import org.jetbrains.annotations.NotNull;

public class VetUserDtoMock {
    public static @NotNull VetUserDto vetUserDto() {
        VetUserDto vetUserDto = new VetUserDto();
        vetUserDto.setEmail("vet@gmail.com");
        vetUserDto.setFirstName("vet");
        vetUserDto.setLastName("vet");
        vetUserDto.setPhoneNumber("0738838944");
        vetUserDto.setPassword("password");
        return vetUserDto;
    }
}
