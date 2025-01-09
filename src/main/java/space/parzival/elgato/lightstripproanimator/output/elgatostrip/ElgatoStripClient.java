package space.parzival.elgato.lightstripproanimator.output.elgatostrip;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import space.parzival.elgato.lightstripproanimator.output.elgatostrip.resources.LightStripUpdateResource;
import space.parzival.elgato.lightstripproanimator.service.ZLibService;

@Slf4j
@Service
@AllArgsConstructor
public class ElgatoStripClient {

    private final ObjectMapper objectMapper;
    private final ZLibService zLibService;
    private final RestClient restClient = RestClient.create();

    public void uploadLightStripConfiguration(String host, LightStripUpdateResource config) throws JsonProcessingException {

        String configJson = objectMapper.writeValueAsString(config);
        byte[] configBytes = zLibService.compress(configJson);

        log.debug("Sending configuration to Elgato Strip: " + configJson);
        log.debug("Compressed configuration size: " + configBytes.length);

        try {
            String resp = restClient.put()
                    .uri("http://" + host + "/elgato/lights")
                    .accept(MediaType.APPLICATION_JSON)
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "en-US,en;q=0.9")
                    .header("Connection", "keep-alive")
                    .header("Content-Encoding", "deflate")
                    .contentLength(configBytes.length)
                    .body(configBytes, ParameterizedTypeReference.forType(Byte.class))
                    .retrieve()
                    .body(String.class);


            log.info("Upload result: " + resp);
        } catch (HttpClientErrorException e) {
            log.error("Failed to upload configuration to Elgato Strip", e);
        }
    }
}
