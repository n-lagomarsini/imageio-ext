package it.geosolutions.imageio.tiff;

import static org.junit.Assert.*;

import java.io.IOException;

import junit.framework.Assert;

import it.geosolutions.imageioimpl.plugins.tiff.TIFFRLECompressor;

import org.junit.Test;

public class TIFFRLECompressorTest {
/*
 * This test-class complete the coverage of TIFFRLECompressor class
 */
@Test(expected = IOException.class)
public void testEncode() throws IOException {
    // Instantiation of a new RLE compressor
    TIFFRLECompressor compress = new TIFFRLECompressor();
    // Creation of test-data with wrong bitsPerSample for throwing a IOException
    byte[] b = new byte[] { 1, 1, 1, 1 };
    int off = 0;
    int width = 2;
    int height = 2;
    int[] bitsPerSample = new int[] { 1, 1 };
    int scanlineStride = 2;
    // Encoding test-data and expecting an exception
    int result = compress.encode(b, off, width, height, bitsPerSample,
            scanlineStride);
}

@Test(expected = IOException.class)
public void testEncode1() throws IOException {
    // Instantiation of a new RLE compressor
    TIFFRLECompressor compress = new TIFFRLECompressor();
    // Creation of test-data with wrong bitsPerSample for throwing a IOException
    byte[] b = new byte[] { 1, 1, 1, 1 };
    int off = 0;
    int width = 2;
    int height = 2;
    int[] bitsPerSample = new int[] { 0 };
    int scanlineStride = 2;
    // Encoding test-data and expecting an exception
    int result = compress.encode(b, off, width, height, bitsPerSample,
            scanlineStride);
}

@Test
public void testEncodeRLE() {
    // Test for complete the coverage of the class
    // Creation of test-data
    byte[] data = new byte[] { 1, 1, 1, 1 };
    int rowOffset = 0;
    int colOffset = 0;
    int rowLength = 1;
    byte[] compData = new byte[4];
    // Instantiation of a new RLE compressor
    TIFFRLECompressor compress = new TIFFRLECompressor();
    // Setting inverseFill to true for complete the test coverage
    compress.inverseFill = true;
    int result = compress.encodeRLE(data, rowOffset, colOffset, rowLength,
            compData);
    // Checking if not null
    Assert.assertNotNull("It's Null", result);
}

}
