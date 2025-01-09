package space.parzival.elgato.lightstripproanimator.output.elgatostrip.resources;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LightStripLightResource {
    private String id;
    private String name;
    private int brightness;
    private boolean on;

    private List<LightStripSceneResource> sceneSet;
    private LightStripMetadataResource metadata;
}
