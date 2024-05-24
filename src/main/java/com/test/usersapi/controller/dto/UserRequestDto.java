package com.test.usersapi.controller.dto;

        import javax.validation.Valid;
        import javax.validation.constraints.NotBlank;
        import javax.validation.constraints.Pattern;
        import java.util.List;

public record UserRequestDto(
        @NotBlank(message = "El campo es obligatorio") String name,
        @NotBlank(message = "El campo es obligatorio")
        @Pattern(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message = "Formato incorrecto para email. ejemplo: 'xxxxx@domain.xxx' ")
        String email,
        @NotBlank(message = "El campo es obligatorio")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$", message = "La contraseña debe tener al menos 8 caracteres y debe contener:, 1 simbolo '!@#&()–[{}]:;',?/*~$^+=<>', 1  número, 1 letra minuscula y 1 letra mayuscula ")
        String password,
        @Valid List<PhoneRequestDto> phones
) {}

