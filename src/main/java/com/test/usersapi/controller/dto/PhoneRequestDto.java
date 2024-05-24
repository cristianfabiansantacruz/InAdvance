package com.test.usersapi.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;

public record PhoneRequestDto(
        @NotBlank(message = "El campo es obligatorio") String number,
        @NotBlank(message = "El campo es obligatorio") @JsonProperty("citycode") String cityCode,
        @NotBlank(message = "El campo es obligatorio") @JsonProperty("contrycode") String countryCode
) {}

