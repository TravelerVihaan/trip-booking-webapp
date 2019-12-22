package com.github.vihaan.adinxwebsite.users;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RoleDTO {

    @NotEmpty
    private String role;
}
