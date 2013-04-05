/*
 *    ImageI/O-Ext - OpenSource Java Image translation Library
 *    http://www.geo-solutions.it/
 *    (C) 2007 - 2009, GeoSolutions
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    either version 3 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package it.geosolutions.imageio.tiff;

import it.geosolutions.imageio.plugins.tiff.TIFFImageWriteParam;
import it.geosolutions.imageio.utilities.ImageIOUtilities;
import it.geosolutions.imageioimpl.plugins.tiff.TIFFImageReader;
import it.geosolutions.imageioimpl.plugins.tiff.TIFFImageReaderSpi;
import it.geosolutions.imageioimpl.plugins.tiff.TIFFImageWriter;
import it.geosolutions.imageioimpl.plugins.tiff.TIFFImageWriterSpi;
import it.geosolutions.resources.TestData;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Logger;

import javax.imageio.IIOImage;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.event.IIOWriteProgressListener;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import javax.media.jai.PlanarImage;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.sun.media.jai.operator.ImageReadDescriptor;


/**
 * Testing reading capabilities for {@link JP2KKakaduImageReader} leveraging on JAI.
 * 
 * @author Simone Giannecchini, GeoSolutions.
 * @author Daniele Romagnoli, GeoSolutions.
 */
public class TIFFWriteTest2 extends Assert {
    
    private final static Logger LOGGER = Logger.getLogger(TIFFWriteTest2.class.toString());

    @Test
    public void readFromFileJAI() throws IOException {
        final File file = TestData.file(this, "test.tif");
        final File file1 = TestData.file(this, "test.tif");
        // double sum = 0;
        // final long num = 10000l;

        // for (long i = 0; i < num; i++) {
        // final double time = System.nanoTime();

        // IMAGE 0
        RenderedImage image = ImageReadDescriptor.create(new FileImageInputStream(file),
                Integer.valueOf(0), false, false, false, null, null, null,
                new TIFFImageReaderSpi().createReaderInstance(), null);
        
        RenderedImage image1 = ImageReadDescriptor.create(new FileImageInputStream(file1),
                Integer.valueOf(0), false, false, false, null, null, null,
                new TIFFImageReaderSpi().createReaderInstance(), null);
        
        if (TestData.isInteractiveTest())
            ImageIOUtilities.visualize(image, "testManualRead");
        else
            Assert.assertNotNull(PlanarImage.wrapRenderedImage(image).getTiles());
        // sum += System.nanoTime() - time;
   //     Assert.assertEquals(30, image.getWidth());
   //     Assert.assertEquals(26, image.getHeight());

        Assert.assertEquals(image1.getWidth(), image.getWidth());
        Assert.assertEquals(image1.getHeight(), image.getHeight());
        
        Assert.assertNotNull("null", image1.getHeight()-image.getHeight());
        
        PlanarImage.wrapRenderedImage(image).dispose();
        image = null;
        // }

        

    }


}

