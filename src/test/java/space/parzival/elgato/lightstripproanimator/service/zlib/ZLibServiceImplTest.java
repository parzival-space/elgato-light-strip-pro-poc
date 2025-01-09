package space.parzival.elgato.lightstripproanimator.service.zlib;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import space.parzival.elgato.lightstripproanimator.service.ZLibService;

import java.util.zip.DataFormatException;

import static org.junit.jupiter.api.Assertions.*;

class ZLibServiceImplTest {

    private ZLibService zlibService;

    @BeforeEach
    void setUp() {
        zlibService = new ZLibServiceImpl();
    }

    @Test
    void compress() {
        String input = "Hello, World!";
        byte[] compressed = zlibService.compress(input.getBytes());
        assertNotNull(compressed);
        assertTrue(compressed.length > 0);
    }

    @Test
    void decompress() throws DataFormatException {
        String input = "Hello, World!";
        byte[] compressed = zlibService.compress(input.getBytes());
        byte[] decompressed = zlibService.decompress(compressed);
        assertNotNull(decompressed);
        assertEquals(input, new String(decompressed));
    }

    @Test
    void testCompress() {
        String input = "Hello, World!";
        byte[] compressed = zlibService.compress(input.getBytes());
        assertNotNull(compressed);
        assertTrue(compressed.length > 0);
    }

    @Test
    void decompressToString() throws DataFormatException {
        String input = "Hello, World!";
        byte[] compressed = zlibService.compress(input.getBytes());
        String decompressed = zlibService.decompressToString(compressed);
        assertNotNull(decompressed);
        assertEquals(input, decompressed);
    }
}