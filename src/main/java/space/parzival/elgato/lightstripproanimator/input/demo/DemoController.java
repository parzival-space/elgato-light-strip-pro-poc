package space.parzival.elgato.lightstripproanimator.input.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import space.parzival.elgato.lightstripproanimator.output.elgatostrip.ElgatoStripClient;
import space.parzival.elgato.lightstripproanimator.output.elgatostrip.resources.LightStripLightResource;
import space.parzival.elgato.lightstripproanimator.output.elgatostrip.resources.LightStripMetadataResource;
import space.parzival.elgato.lightstripproanimator.output.elgatostrip.resources.LightStripSceneResource;
import space.parzival.elgato.lightstripproanimator.output.elgatostrip.resources.LightStripUpdateResource;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/demo")
@AllArgsConstructor
public class DemoController {
    private final ElgatoStripClient elgatoStripClient;

    @GetMapping("/upload")
    public LightStripUpdateResource demoEndpoint(
            @RequestParam String host,
            @RequestParam(required = false, defaultValue = "5") int brightness,
            @RequestParam(required = false, defaultValue = "true") boolean active,
            @RequestParam(required = false, defaultValue = "120") int ledCount,
            @RequestParam(required = false, defaultValue = "255") int r,
            @RequestParam(required = false, defaultValue = "255") int g,
            @RequestParam(required = false, defaultValue = "255") int b
    ) throws JsonProcessingException, InterruptedException {
        log.debug("Received request to upload demo scene to host: {}", host);

        LightStripUpdateResource config = LightStripUpdateResource.builder()
                .numberOfLights(1)
                .lights(
                        List.of(
                                LightStripLightResource.builder()
                                        .name("Elgato Mod Scene")
                                        .id("space.parzival.elgato")
                                        .brightness(brightness)
                                        .on(active)
                                        .sceneSet(
                                                List.of(
                                                        LightStripSceneResource.builder()
                                                                .duration(1000)
                                                                .pushLed(r, g, b, ledCount)
                                                                .build()
                                                )
                                        )
                                        .metadata(
                                                LightStripMetadataResource.builder()
                                                        .scene("space.parzival.websocket")
                                                        .build()
                                        ).build()
                        )
                )
                .build();

        elgatoStripClient.uploadLightStripConfiguration(host, config);
        return config;
    }
}
