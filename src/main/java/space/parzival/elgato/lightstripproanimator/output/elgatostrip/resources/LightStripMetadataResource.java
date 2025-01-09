package space.parzival.elgato.lightstripproanimator.output.elgatostrip.resources;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LightStripMetadataResource {
    // just the scene id, I am not even sure if this is necessary as the whole scene object seems stupid and useless
    private String scene;
}
