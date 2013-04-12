package it.geosolutions.imageio.tiff;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.spi.ImageOutputStreamSpi;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import junit.framework.Assert;

import it.geosolutions.imageio.plugins.tiff.TIFFCompressor;
import it.geosolutions.imageio.plugins.tiff.TIFFImageWriteParam;
import it.geosolutions.imageioimpl.plugins.tiff.TIFFImageMetadata;
import it.geosolutions.imageioimpl.plugins.tiff.TIFFImageWriter;
import it.geosolutions.imageioimpl.plugins.tiff.TIFFImageWriterSpi;
import it.geosolutions.imageioimpl.plugins.tiff.TIFFJPEGCompressor;
import it.geosolutions.imageioimpl.plugins.tiff.TIFFRLECompressor;
import it.geosolutions.resources.TestData;

import org.junit.Test;

public class TIFFCompressorTest {
/*
 * This test-class improves the coverage of the class TIFFCompressor.java. Only
 * the exception are not covered because the TIFFCompressor class cannot be
 * directly accessed.
 */

@Test
public void testGetCompressionType() {
    // Instantiation of a simple run-length compressor
    TIFFRLECompressor test = new TIFFRLECompressor();
    // Getting the compression type
    String testo = test.getCompressionType();
    // Comparison with the expected value
    Assert.assertEquals("Different", "CCITT RLE", testo);
}

@Test
public void testGetCompressionTagValue() {
    // Instantiation of a simple run-length compressor
    TIFFRLECompressor test = new TIFFRLECompressor();
    // Getting the compression tag value
    int testo = test.getCompressionTagValue();
    // Comparison with the expected value
    Assert.assertEquals("Different", 2, testo);
}

@Test
public void testIsCompressionLossless() {
    // Instantiation of a simple run-length compressor
    TIFFRLECompressor test = new TIFFRLECompressor();
    // Checking if the compression is lossless or not
    boolean compression_state = test.isCompressionLossless();
    // Comparison with the expected value
    Assert.assertEquals("Different", true, compression_state);

}

@Test
public void testStream_and_Writer() throws IOException {
    // Instantiation of a simple run-length compressor
    TIFFRLECompressor test = new TIFFRLECompressor();
    // Creation of the output file to write
    final File outputFile = TestData.temp(this, "testw.tif", true);
    // Instantiation of the imageoutputstream
    ImageOutputStream stream_out = new FileImageOutputStream(outputFile);
    // Instantiation of the iterator of the image-writer
    Iterator imageWriters = ImageIO.getImageWritersByFormatName("TIFF");
    // Selection of the image-writer
    ImageWriter imw = (ImageWriter) imageWriters.next();

    try {
        // Setting the inputstream to the compressor
        test.setStream(stream_out);
        // Comparison with the expected value
        Assert.assertEquals("It's Null", stream_out, test.getStream());
        // Setting the image-writer to the compressor
        test.setWriter(imw);
        // Comparison with the expected value
        Assert.assertEquals("Different", imw, test.getWriter());

    } finally {
        /*
         * The stream and the writer are closed even if the program throws an
         * exception
         */
        try {
            if (stream_out != null)
                stream_out.flush();
        } catch (Throwable t) {
            //
        }

        try {
            if (imw != null)
                imw.dispose();
        } catch (Throwable t) {
            //
        }

    }

}

}
