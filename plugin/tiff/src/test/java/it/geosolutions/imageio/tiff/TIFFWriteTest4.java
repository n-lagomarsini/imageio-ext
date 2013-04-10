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

	@Test
	public void testwriteImage_and_watch_flag() throws IOException {

	    final File inputFile = TestData.file(this, "test.tif");
	    final File outputFile = TestData.temp(this, "testw.tif", true);

	    final TIFFImageReadParam param = new TIFFImageReadParam();
	    TIFFImageReader reader = (TIFFImageReader) new TIFFImageReaderSpi()
	            .createReaderInstance();

	    final TIFFImageWriter writer = (TIFFImageWriter) new TIFFImageWriterSpi()
	            .createWriterInstance();
	    final ImageWriteParam writeParam = new TIFFImageWriteParam(
	            Locale.getDefault());
	    writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	    writeParam.setCompressionType("JPEG");

	    int img_index = 0;

	    try {
	        reader.setInput(new FileImageInputStream(inputFile));

	        BufferedImage image = reader.read(0, param);
	        writer.setOutput(new FileImageOutputStream(outputFile));

	        writer.write(null, new IIOImage(image, null, null), writeParam);

	        writer.dispose();

	    } finally {
	        reader.reset();
	        writer.reset();
	    }

	    try {
	        int tag_num = 259;
	        reader.setInput(new FileImageInputStream(outputFile));
	        TIFFImageMetadata metatest = (TIFFImageMetadata) reader
	                .getImageMetadata(img_index);
	        TIFFField field1 = metatest.getTIFFField(tag_num);
	        int value_compression = field1.getAsInt(0);
	        //used 7 instead of 6 because 6 means COMPRESSION_OLD_JPEG NOT JPEG
	        Assert.assertEquals("Not Equal", 7, value_compression);
	    } finally {
	        reader.reset();
	    }

	}

}
