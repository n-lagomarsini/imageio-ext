package it.geosolutions.imageio.tiff;

import static org.junit.Assert.*;
import junit.framework.Assert;
import it.geosolutions.imageioimpl.plugins.tiff.TIFFCIELabColorConverter;

import org.junit.Test;

public class TIFFCIELabColorConverterTest {

/*
 * This test-class the functionality of TIFFCIELabColorConverter.class. This
 * class converts RGB data in CIELab format and viceversa. For achieving this
 * purpose different test has been made in order to have a complete coverage of
 * the class.
 */

@Test
public void testFromRGB() {
    // Tolerance value for the comparison
    float delta = 0.1f;
    // Test 1
    // The first array is the source, the second the result from the test
    // and the last the expected result
    float[] testRGB_1 = new float[] { 1, 1, 1 };
    float[] result_1 = new float[3];
    float[] result_right = new float[3];
    // Creation of an instance of the converter
    TIFFCIELabColorConverter RGB_converter_1 = new TIFFCIELabColorConverter();
    // Conversion of the RGB data
    RGB_converter_1.fromRGB(testRGB_1[0], testRGB_1[1], testRGB_1[2], result_1);
    // Same operation for the expected data
    fromRGB_test(testRGB_1[0], testRGB_1[1], testRGB_1[2], result_right);
    // Comparison
    Assert.assertEquals("Different values", result_right[0], result_1[0], delta);
    Assert.assertEquals("Different values", result_right[1], result_1[1], delta);
    Assert.assertEquals("Different values", result_right[2], result_1[2], delta);

    // Test 2 other values for the "if clauses" (if ZZn XXn YYn..)
    float[] testRGB_2 = new float[] { 0.5f, 0.5f, 0.5f };
    float[] result_2 = new float[3];
    float[] result_right2 = new float[3];

    RGB_converter_1.fromRGB(testRGB_2[0], testRGB_2[1], testRGB_2[2], result_2);
    fromRGB_test(testRGB_2[0], testRGB_2[1], testRGB_2[2], result_right2);

    Assert.assertEquals("Different values", result_right2[0], result_2[0],
            delta);
    Assert.assertEquals("Different values", result_right2[1], result_2[1],
            delta);
    Assert.assertEquals("Different values", result_right2[2], result_2[2],
            delta);

    // Test 3 other values for the "clamp2" if clauses (x<0.0f & bStar <0.0f)
    float[] testRGB_3 = new float[] { -1.0f, -1.0f, -1.0f };
    float[] result_3 = new float[3];
    float[] result_right3 = new float[3];

    RGB_converter_1.fromRGB(testRGB_3[0], testRGB_3[1], testRGB_3[2], result_3);
    fromRGB_test(testRGB_3[0], testRGB_3[1], testRGB_3[2], result_right3);

    Assert.assertEquals("Different values", result_right3[0], result_3[0],
            delta);
    Assert.assertEquals("Different values", result_right3[1], result_3[1],
            delta);
    Assert.assertEquals("Different values", result_right3[2], result_3[2],
            delta);

}

@Test
public void testToRGB() {
    // Tolerance value for the comparison
    float delta = 0.1f;
    // Test 1
    // The first array is the source, the second the result from the test
    // and the last the expected result
    float[] test_to_RGB_1 = new float[] { 1, 1, 1 };
    float[] result_to_1 = new float[3];
    float[] result_right1 = new float[3];
    // Creation of an instance of the converter
    TIFFCIELabColorConverter RGB_converter_1 = new TIFFCIELabColorConverter();
    // Conversion of the RGB data
    RGB_converter_1.toRGB(test_to_RGB_1[0], test_to_RGB_1[1], test_to_RGB_1[2],
            result_to_1);
    // Same operation for the expected data
    toRGB_test(test_to_RGB_1[0], test_to_RGB_1[1], test_to_RGB_1[2],
            result_right1);
    // Comparison
    Assert.assertEquals("Different values", result_right1[0], result_to_1[0],
            delta);
    Assert.assertEquals("Different values", result_right1[1], result_to_1[1],
            delta);
    Assert.assertEquals("Different values", result_right1[2], result_to_1[2],
            delta);

    // Test 2 for the clauses float aStar & float bStar and the other features
    // not covered by the first test
    float[] test_to_RGB_2 = new float[] { 129f, 129f, 129f };
    float[] result_to_2 = new float[3];
    float[] result_right2 = new float[3];

    RGB_converter_1.toRGB(test_to_RGB_2[0], test_to_RGB_2[1], test_to_RGB_2[2],
            result_to_2);
    toRGB_test(test_to_RGB_2[0], test_to_RGB_2[1], test_to_RGB_2[2],
            result_right2);

    Assert.assertEquals("Different values", result_right2[0], result_to_2[0],
            delta);
    Assert.assertEquals("Different values", result_right2[1], result_to_2[1],
            delta);
    Assert.assertEquals("Different values", result_right2[2], result_to_2[2],
            delta);
}

/*
 * IT DOESN'T GIVE THE SAME RGB!!! NEEDS MORE ATTENTION!!!
 * @Test public void testFromToRGB() { float delta = 0.1f; // Test inserts RGB
 * and converts to CIELab then comes back to RGB float[] testRGB_fromto = new
 * float[] { 1, 1, 1 }; float[] result_from = new float[3]; float[] result_to =
 * new float[3]; TIFFCIELabColorConverter RGB_converter_tofrom = new
 * TIFFCIELabColorConverter(); RGB_converter_tofrom.fromRGB(testRGB_fromto[0],
 * testRGB_fromto[1], testRGB_fromto[2], result_from);
 * RGB_converter_tofrom.toRGB(result_from[0], result_from[1], result_from[2],
 * result_to); Assert.assertEquals("Different values", testRGB_fromto[0],
 * result_to[0], delta); Assert.assertEquals("Different values",
 * testRGB_fromto[1], result_to[1], delta);
 * Assert.assertEquals("Different values", testRGB_fromto[2], result_to[2],
 * delta); }
 */

// Method for the calculation of the CIELab data from RGB data
public void fromRGB_test(float r, float g, float b, float[] result) {

    // CIE XYZ tristimulus values of the reference white point
    float Xn = 95.047f;
    float Yn = 100.0f;
    float Zn = 108.883f;
    // RGB conversion to XYZ
    float X = 0.412453f * r + 0.357580f * g + 0.180423f * b;
    float Y = 0.212671f * r + 0.715160f * g + 0.072169f * b;
    float Z = 0.019334f * r + 0.119193f * g + 0.950227f * b;
    // Initialization of the CIELab components
    float Lstar;
    float Astar;
    float Bstar;
    // Normalization of XYZ data to the reference
    float YYn = Y / Yn;
    float XXn = X / Xn;
    float ZZn = Z / Zn;

    // XYZ conversion to CIELab
    if (YYn <= 0.008856f) {
        YYn = 7.787f * YYn + 16.0f / 116.0f;
    } else {
        YYn = (float) Math.pow(YYn, 1.0 / 3.0);

    }

    if (XXn <= 0.008856f) {
        XXn = 7.787f * XXn + 16.0f / 116.0f;
    } else {
        XXn = (float) Math.pow(XXn, 1.0 / 3.0);
    }

    if (ZZn <= 0.008856f) {
        ZZn = 7.787f * ZZn + 16.0f / 116.0f;
    } else {
        ZZn = (float) Math.pow(ZZn, 1.0 / 3.0);
    }

    Lstar = 116.0f * YYn - 16.0f;
    Astar = 500.0f * (XXn - YYn);
    Bstar = 200.0f * (YYn - ZZn);

    // Scaling data
    Lstar *= 255.0f / 100.0f;

    if (Astar < 0.0f) {
        Astar += 256.0f;
    }
    if (Bstar < 0.0f) {
        Bstar += 256.0f;
    }

    result[0] = clamp2(Lstar);
    result[1] = clamp2(Astar);
    result[2] = clamp2(Bstar);

}

// Method for the calculation of the RGB data from CIELab data
public void toRGB_test(float x0, float x1, float x2, float[] rgb) {

    // CIE XYZ tristimulus values of the reference white point
    float Xn = 95.047f;
    float Yn = 100.0f;
    float Zn = 108.883f;
    // Threshold value for the conversion
    float THRESHOLD = (float) Math.pow(0.008856, 1.0 / 3.0);
    // Scaling data
    float LStar = x0 * 100.0f / 255.0f;
    float aStar = (x1 > 128.0f) ? (x1 - 256.0f) : x1;
    float bStar = (x2 > 128.0f) ? (x2 - 256.0f) : x2;

    float YYn;
    float fY; // 'F' value for Y
    // CIELab conversion to XYZ
    if (LStar < 8.0f) {
        YYn = LStar / 903.3f;
        fY = 7.787f * YYn + 16.0f / 116.0f;
    } else {
        float YYn_cubeRoot = (LStar + 16.0f) / 116.0f;
        YYn = YYn_cubeRoot * YYn_cubeRoot * YYn_cubeRoot;
        fY = (float) Math.pow(YYn, 1.0 / 3.0);
    }
    float Y = YYn * Yn;

    float fX = fY + (aStar / 500.0f);
    float X;
    if (fX <= THRESHOLD) {
        X = Xn * (fX - 16.0f / 116.0f) / 7.787f;
    } else {
        X = Xn * fX * fX * fX;
    }

    float fZ = fY - bStar / 200.0f;
    float Z;
    if (fZ <= THRESHOLD) {
        Z = Zn * (fZ - 16.0f / 116.0f) / 7.787f;
    } else {
        Z = Zn * fZ * fZ * fZ;
    }
    // XYZ conversion to RGB
    float R = 3.240479f * X - 1.537150f * Y - 0.498535f * Z;
    float G = -0.969256f * X + 1.875992f * Y + 0.041556f * Z;
    float B = 0.055648f * X - 0.204043f * Y + 1.057311f * Z;
    // Scaling data
    rgb[0] = clamp(R);
    rgb[1] = clamp(G);
    rgb[2] = clamp(B);
}

// Simple method for scaling data
private float clamp2(float x) {
    if (x < 0.0f) {
        return 0.0f;
    } else if (x > 255.0f) {
        return 255.0f;
    } else {
        return x;
    }
}

private float clamp(float x) {
    if (x < 0.0f) {
        return 0.0f;
    } else if (x > 100.0f) {
        return 255.0f;
    } else {
        return x * (255.0f / 100.0f);
    }
}

}
