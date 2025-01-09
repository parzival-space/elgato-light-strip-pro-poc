package space.parzival.elgato.lightstripproanimator.output.elgatostrip.resources;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LightStripUpdateResource {
    private int numberOfLights = 1; // always 1 for some reason?
    private List<LightStripLightResource> lights;
}
