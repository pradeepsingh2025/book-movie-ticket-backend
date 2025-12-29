package me.riazulislam.infinitecineplexbackend.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateGenreDTO {
    @NotNull(message = "Genre name is required")
    private String name;
}
