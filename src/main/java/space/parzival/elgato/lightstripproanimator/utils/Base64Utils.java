package space.parzival.elgato.lightstripproanimator.utils;

import java.util.List;

public class Base64Utils {
    private static final char[] lookup = new char[64];
    private static final char[] code = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

    static {
        for (int i = 0; i < code.length; ++i) {
            lookup[i] = code[i];
        }
    }

    private static String tripletToBase64(int num) {
        return "" + lookup[num >> 18 & 0x3F] +
                lookup[num >> 12 & 0x3F] +
                lookup[num >> 6 & 0x3F] +
                lookup[num & 0x3F];
    }

    private static String encodeChunk(List<Byte> uint8, int start, int end) {
        StringBuilder output = new StringBuilder();
        for (int i = start; i < end; i += 3) {
            int tmp = ((uint8.get(i) << 16) & 0xFF0000) +
                    ((uint8.get(i + 1) << 8) & 0xFF00) +
                    (uint8.get(i + 2) & 0xFF);
            output.append(tripletToBase64(tmp));
        }
        return output.toString();
    }

    public static String fromByteArray(List<Byte> uint8) {
        int len = uint8.size();
        int extraBytes = len % 3;
        StringBuilder parts = new StringBuilder();
        int maxChunkLength = 16383;

        for (int i = 0; i < len - extraBytes; i += maxChunkLength) {
            parts.append(encodeChunk(uint8, i, Math.min(i + maxChunkLength, len - extraBytes)));
        }

        if (extraBytes == 1) {
            int tmp = uint8.get(len - 1);
            parts.append(lookup[tmp >> 2])
                    .append(lookup[(tmp << 4) & 0x3F])
                    .append("==");
        } else if (extraBytes == 2) {
            int tmp = (uint8.get(len - 2) << 8) + uint8.get(len - 1);
            parts.append(lookup[tmp >> 10])
                    .append(lookup[(tmp >> 4) & 0x3F])
                    .append(lookup[(tmp << 2) & 0x3F])
                    .append('=');
        }

        return parts.toString();
    }
}
