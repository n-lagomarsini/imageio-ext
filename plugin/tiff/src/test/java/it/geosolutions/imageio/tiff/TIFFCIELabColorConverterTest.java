package it.geosolutions.imageio.tiff;

import static org.junit.Assert.*;
import junit.framework.Assert;
import it.geosolutions.imageioimpl.plugins.tiff.TIFFCIELabColorConverter;

import org.junit.Test;

public class TIFFCIELabColorConverterTest {

@Test
public void testFromRGB() {
    //Test 1
    float[] testRGB_1= new float[]{1,1,1};
    float[] result_1= new float[3];
    
    TIFFCIELabColorConverter RGB_converter_1 = new TIFFCIELabColorConverter();    
    RGB_converter_1.fromRGB(testRGB_1[0],testRGB_1[1],testRGB_1[2],result_1);
    Assert.assertNotNull("It's 0", result_1[0]);
    Assert.assertNotNull("It's 0", result_1[1]);
    Assert.assertNotNull("It's 0", result_1[2]);
    
    //Test 2 other values for if clauses if ZZn XXn YYn
    float[] testRGB_2= new float[]{0.5f,0.5f,0.5f};
    float[] result_2= new float[3];
       
    RGB_converter_1.fromRGB(testRGB_2[0],testRGB_2[1],testRGB_2[2],result_2);
    Assert.assertNotNull("It's 0", result_2[0]);
    Assert.assertNotNull("It's 0", result_2[1]);
    Assert.assertNotNull("It's 0", result_2[2]);

    //Test 3 other values for clamp2 if clauses x<0.0f & bStar <0.0f
    float[] testRGB_3= new float[]{-1.0f,-1.0f,-1.0f};
    float[] result_3= new float[3];
     
    RGB_converter_1.fromRGB(testRGB_3[0],testRGB_3[1],testRGB_3[2],result_3);
    Assert.assertNotNull("It's 0", result_3[0]);
    Assert.assertNotNull("It's 0", result_3[1]);
    Assert.assertNotNull("It's 0", result_3[2]);   
}




@Test
public void testToRGB() {
    //Test 1
    float[] test_to_RGB_1= new float[]{1,1,1};
    float[] result_to_1= new float[3];
    
    TIFFCIELabColorConverter RGB_converter_1 = new TIFFCIELabColorConverter();    
    RGB_converter_1.toRGB(test_to_RGB_1[0],test_to_RGB_1[1],test_to_RGB_1[2],result_to_1);
    Assert.assertNotNull("It's 0", result_to_1[0]);
    Assert.assertNotNull("It's 0", result_to_1[1]);
    Assert.assertNotNull("It's 0", result_to_1[2]);
    
    //Test 2  for the clauses float aStar & float bStar and the other features not covered by
    //the first test
    float[] test_to_RGB_2= new float[]{129f,129f,129f};
    float[] result_to_2= new float[3];
       
    RGB_converter_1.toRGB(test_to_RGB_2[0],test_to_RGB_2[1],test_to_RGB_2[2],result_to_2);
    Assert.assertNotNull("It's 0", result_to_2[0]);
    Assert.assertNotNull("It's 0", result_to_2[1]);
    Assert.assertNotNull("It's 0", result_to_2[2]);
    
}


}
