package org.springframework.samples.petclinic.round;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class RoundRequest {
    @NotNull
    private RoundMode roundMode;

}
