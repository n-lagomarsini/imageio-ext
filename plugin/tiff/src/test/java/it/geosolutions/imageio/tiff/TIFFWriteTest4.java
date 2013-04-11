package it.geosolutions.imageio.tiff;

import static org.junit.Assert.*;
import it.geosolutions.imageio.plugins.tiff.BaselineTIFFTagSet;
import it.geosolutions.imageio.plugins.tiff.TIFFField;
import it.geosolutions.imageio.plugins.tiff.TIFFImageReadParam;
import it.geosolutions.imageio.plugins.tiff.TIFFImageWriteParam;
import it.geosolutions.imageioimpl.plugins.tiff.TIFFImageMetadata;
import it.geosolutions.imageioimpl.plugins.tiff.TIFFImageReader;
import it.geosolutions.imageioimpl.plugins.tiff.TIFFImageReaderSpi;
import it.geosolutions.imageioimpl.plugins.tiff.TIFFImageWriter;
import it.geosolutions.imageioimpl.plugins.tiff.TIFFImageWriterSpi;
import it.geosolutions.resources.TestData;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.imageio.IIOImage;
import javax.imageio.ImageWriteParam;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;

import junit.framework.Assert;

import org.junit.Test;

public class TIFFWriteTest4 {
/*
 * This test-class is used for testing if the compression tag is set to "JPEG"
 * in the Metadata associated to the LZW-compressed file.
 */

@Test
public void testwriteImage_and_watch_flag() throws IOException {
    // Creation of the input file to read and the output file to write
    final File inputFile = TestData.file(this, "test.tif");
    final File outputFile = TestData.temp(this, "testw.tif", true);
    // Instantiation of the read-params
    final TIFFImageReadParam param = new TIFFImageReadParam();
    // Instantiation of the file-reader
    TIFFImageReader reader = (TIFFImageReader) new TIFFImageReaderSpi()
            .createReaderInstance();
    // Instantiation of the file-writer
    final TIFFImageWriter writer = (TIFFImageWriter) new TIFFImageWriterSpi()
            .createWriterInstance();
    // Instantiation of the write-params
    final ImageWriteParam writeParam = new TIFFImageWriteParam(
            Locale.getDefault());
    // Selection of the compression mode and type
    writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
    writeParam.setCompressionType("JPEG");
    // Choice of the image index
    int img_index = 0;
    // Instantiation of the imageinputstream and imageoutputstrem
    FileImageInputStream stream_in = new FileImageInputStream(inputFile);
    FileImageOutputStream stream_out = new FileImageOutputStream(outputFile);
    FileImageInputStream stream_in2 = new FileImageInputStream(outputFile);

    try {
        // Setting the inputstream to the reader
        reader.setInput(stream_in);
        // Creation of a buffered image to store the image
        BufferedImage image = reader.read(0, param);
        // Setting the outputstream to the writer
        writer.setOutput(stream_out);
        // Writing
        writer.write(null, new IIOImage(image, null, null), writeParam);
        // Disposing of the writer
        writer.dispose();
        // Restoring the reader to the initial state
        reader.reset();
        // Selecting the tag related to the field "compression"
        int tag_num = 259;
        /*
         * Setting the new inputstream to the reader for reading the written
         * file
         */
        reader.setInput(stream_in2);
        // Getting the Metadata associated to the image
        TIFFImageMetadata metatest = (TIFFImageMetadata) reader
                .getImageMetadata(img_index);
        // Getting the value of the selected TAG
        TIFFField field1 = metatest.getTIFFField(tag_num);
        /*
         * Comparison for evaluating if the type of compression is equal to the
         * expected one. Used 7 instead of 6 because 6 means
         * COMPRESSION_OLD_JPEG NOT COMPRESSION_JPEG
         */
        int value_compression = field1.getAsInt(0);
        Assert.assertEquals("Not Equal", 7, value_compression);
    } finally {
        /*
         * All the readers, writers, and stream are closed even if the program
         * throws an exception
         */
        try {
            if (stream_in != null)
                stream_in.flush();
        } catch (Throwable t) {
            //
        }

        try {
            if (stream_out != null)
                stream_in.flush();
        } catch (Throwable t) {
            //
        }

        try {
            if (stream_in2 != null)
                stream_in2.flush();
        } catch (Throwable t) {
            //
        }
        try {
            if (reader != null)
                reader.dispose();
        } catch (Throwable t) {
            //
        }

        try {
            if (writer != null)
                writer.dispose();
        } catch (Throwable t) {
            //
        }

    }

}

}
