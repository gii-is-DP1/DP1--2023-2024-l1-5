package org.springframework.samples.petclinic.game;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class GameRequest {
    @NotNull
    private GameMode gameMode;

}
