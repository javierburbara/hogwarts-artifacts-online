package edu.tcu.cs.hogwartsartifactsonline.user.dto;

import jakarta.validation.constraints.NotEmpty;

public record UserDto(
        Integer id,

        @NotEmpty
        String username,

        boolean enabled,

        @NotEmpty
        String roles
) {}