package it.geosolutions.imageio.tiff;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

import it.geosolutions.imageio.plugins.tiff.BaselineTIFFTagSet;
import it.geosolutions.imageio.plugins.tiff.TIFFField;
import it.geosolutions.imageio.plugins.tiff.TIFFImageReadParam;
import it.geosolutions.imageio.plugins.tiff.TIFFImageWriteParam;
import it.geosolutions.imageioimpl.plugins.tiff.TIFFIFD;
import it.geosolutions.imageioimpl.plugins.tiff.TIFFImageMetadata;
import it.geosolutions.imageioimpl.plugins.tiff.TIFFImageReader;
import it.geosolutions.imageioimpl.plugins.tiff.TIFFImageReaderSpi;
import it.geosolutions.imageioimpl.plugins.tiff.TIFFImageWriter;
import it.geosolutions.imageioimpl.plugins.tiff.TIFFImageWriterSpi;
import it.geosolutions.imageioimpl.plugins.tiff.TIFFJPEGCompressor;
import it.geosolutions.resources.TestData;

import javax.imageio.IIOImage;
import javax.imageio.ImageWriteParam;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;

import junit.framework.Assert;

import org.junit.Test;

public class TIFFJPEGCompressorTest {
/*
 * This test-class performs a partial coverage of the class TIFFJPEGCompressor.
 * The coverage is not complete because some parts need a JPEGtable already
 * present in the image to compress.
 */

@Test
public void testSetMetadata() throws IOException {
    // Opening an input file in test-data
    final File inputFile = TestData.file(this, "test.tif");
    // Instantiation of a reader for the image opened
    TIFFImageReader reader = (TIFFImageReader) new TIFFImageReaderSpi()
            .createReaderInstance();
    // Instantiation of the write-params
    final ImageWriteParam writeParam = new TIFFImageWriteParam(
            Locale.getDefault());
    // Instantiation of the imageinputstream
    FileImageInputStream stream_in = new FileImageInputStream(inputFile);

    try {
        // Setting the inputstream to the reader
        reader.setInput(stream_in);
        // Getting the Metadata associated to the image
        TIFFImageMetadata metatest = (TIFFImageMetadata) reader
                .getImageMetadata(0);
        // Instantiation of a new compressor associated to the write-params
        // above
        TIFFJPEGCompressor test = new TIFFJPEGCompressor(writeParam);
        // Setting the Metadata associated to the image to the write-params
        test.setMetadata(metatest);
        // Getting the Metadata associated to the compressor
        IIOMetadata proof = test.getMetadata();
        // Comparison
        Assert.assertEquals("Not Equal", metatest, proof);

    } finally {
        /*
         * The reader and the stream are closed even if the program throws an
         * exception
         */
        try {
            if (stream_in != null)
                stream_in.flush();
        } catch (Throwable t) {
            //
        }

        try {
            if (reader != null)
                reader.dispose();
        } catch (Throwable t) {
            //
        }
    }
}

@Test
// Test for the coverage of the false If clause
// "if (metadata instanceof TIFFImageMetadata)"
public void testSetMetadata1() {
    // Instantiation of a new compressor associated to nothing
    TIFFJPEGCompressor test = new TIFFJPEGCompressor(null);
    // Setting null(Only for coverage)
    test.setMetadata(null);
    // Getting the Metadata associated to the compressor
    IIOMetadata proof = test.getMetadata();
    // Comparison
    Assert.assertNull("There is something", proof);

}

@Test
// Test for the coverage of the If clause "if(numBands == 1)" with numBands =3
public void testSetMetadata2() throws IOException {
    // Opening an input file in test-data
    final File inputFile = TestData.file(this, "test.tif");
    // Instantiation a reader for the image opened
    TIFFImageReader reader = (TIFFImageReader) new TIFFImageReaderSpi()
            .createReaderInstance();
    // Instantiation of the write-params
    final ImageWriteParam writeParam = new TIFFImageWriteParam(
            Locale.getDefault());
    // Instantiation of the imageinputstream
    FileImageInputStream stream_in = new FileImageInputStream(inputFile);

    try {
        // Setting the inputstream to the reader
        reader.setInput(stream_in);
        // Getting the Metadata associated to the image
        TIFFImageMetadata tim = (TIFFImageMetadata) reader.getImageMetadata(0);
        /*
         * Removal of the field "Sample per pixel" and replacing it with the
         * same field but with a different value.
         */
        tim.removeTIFFField(BaselineTIFFTagSet.TAG_SAMPLES_PER_PIXEL);
        tim.addShortOrLongField(BaselineTIFFTagSet.TAG_SAMPLES_PER_PIXEL, 3);
        // Instantiation of a new compressor associated to the write-params
        // above
        TIFFJPEGCompressor test = new TIFFJPEGCompressor(writeParam);
        // Setting the Metadata associated to the image to the write-params
        test.setMetadata(tim);
        // Getting the Metadata associated to the compressor
        IIOMetadata proof = test.getMetadata();
        // Comparison
        Assert.assertEquals("Not Equal", tim, proof);

    } finally {
        /*
         * The reader and the stream are closed even if the program throws an
         * exception
         */
        try {
            if (stream_in != null)
                stream_in.flush();
        } catch (Throwable t) {
            //
        }

        try {
            if (reader != null)
                reader.dispose();
        } catch (Throwable t) {
            //
        }

    }
}

@Test
// TEST FOR READING IF JPEG TABLES ARE PRESENT
public void testReadIfJPEGTables() throws IOException {
    // Opening an input file in test-data with JPEGTables
    final File inputFile = TestData.file(this, "world.tif");
    // Instantiation a reader for the image opened
    TIFFImageReader reader = (TIFFImageReader) new TIFFImageReaderSpi()
            .createReaderInstance();
    // Instantiation of the write-params
    final ImageWriteParam writeParam = new TIFFImageWriteParam(
            Locale.getDefault());
    // Selection of the compression mode and type
    writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
    writeParam.setCompressionType("JPEG");
    // Instantiation of the imageinputstream
    FileImageInputStream stream_in = new FileImageInputStream(inputFile);

    try {
        // Setting the inputstream to the reader
        reader.setInput(stream_in);
        // Getting the Metadata associated to the image
        TIFFImageMetadata metatest0 = (TIFFImageMetadata) reader
                .getImageMetadata(0);
        // Selecting the tag related to the field "JPEGTables"
        int tag_num = 347;
        // Getting the value of the selected TAG
        TIFFField field0 = metatest0.getTIFFField(tag_num);
        // Control if the JPEGTables are present
        Assert.assertNotNull("It's null!!!", field0);

    } finally {
        /*
         * The reader and the stream are closed even if the program throws an
         * exception
         */
        try {
            if (stream_in != null)
                stream_in.flush();
        } catch (Throwable t) {
            //
        }
        try {
            if (reader != null)
                reader.dispose();
        } catch (Throwable t) {
            //
        }

    }

}

/*
 * NOT FINISHED!!! THIS PART NEED JPEG TABLE FOR THE COMPLETE COVERAGE OF THE
 * "setMetadata" METHOD
 */

}
