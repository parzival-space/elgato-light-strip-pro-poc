package space.parzival.elgato.lightstripproanimator.service;

import org.springframework.stereotype.Service;

import java.util.zip.DataFormatException;

public interface ZLibService {
    byte[] compress(byte[] data);
    byte[] decompress(byte[] data) throws DataFormatException;

    byte[] compress(String data);
    String decompressToString(byte[] data) throws DataFormatException;
}
