package space.parzival.elgato.lightstripproanimator.output.elgatostrip.resources;

import lombok.Data;
import space.parzival.elgato.lightstripproanimator.utils.Base64Utils;

import java.util.ArrayList;
import java.util.List;

@Data
public class LightStripSceneResource {
    private String rgbRaw;
    private int duration;

    public static class Builder {
        private List<Byte> rgbRaw = new ArrayList<>();
        private int duration = 10;

        public Builder pushLed(int r, int g, int b) {
            rgbRaw.add((byte) r);
            rgbRaw.add((byte) g);
            rgbRaw.add((byte) b);
            return this;
        }
        public Builder pushLed(int r, int g, int b, int amount) {
            for (int i = 0; i < amount; i++) {
                rgbRaw.add((byte) r);
                rgbRaw.add((byte) g);
                rgbRaw.add((byte) b);
            }
            return this;
        }

        public Builder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public LightStripSceneResource build() {
            LightStripSceneResource resource = new LightStripSceneResource();
            resource.setDuration(this.duration);
            resource.setRgbRaw(Base64Utils.fromByteArray(this.rgbRaw));
            return resource;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
