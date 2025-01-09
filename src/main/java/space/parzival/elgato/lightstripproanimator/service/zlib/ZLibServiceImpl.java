package space.parzival.elgato.lightstripproanimator.service.zlib;

import org.springframework.stereotype.Service;
import space.parzival.elgato.lightstripproanimator.service.ZLibService;

import java.beans.Encoder;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class ZLibServiceImpl implements ZLibService {
    @Override
    public byte[] compress(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        while (!deflater.finished()) {
            int compressedDataLength = deflater.deflate(buffer);
            outputStream.write(buffer, 0, compressedDataLength);
        }

        return outputStream.toByteArray();
    }

    @Override
    public byte[] decompress(byte[] data) throws DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        while (!inflater.finished()) {
            int decompressedDataLength = inflater.inflate(buffer);
            outputStream.write(buffer, 0, decompressedDataLength);
        }

        return outputStream.toByteArray();
    }

    @Override
    public byte[] compress(String data) {
        byte[] stringBytes = data.getBytes(StandardCharsets.UTF_8);
        return compress(stringBytes);
    }

    @Override
    public String decompressToString(byte[] data) throws DataFormatException {
        byte [] decompressedData = decompress(data);
        return new String(decompressedData, StandardCharsets.UTF_8);
    }
}
