package com.noahcharlton.wgpuj.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

public class ImageDataTests {

    private static ImageData testImage;

    @BeforeAll
    static void setUp() throws IOException {
        testImage = ImageData.fromFile("/test_texture.png");
    }

    @Test
    void testImageWidthTest() {
        Assertions.assertEquals(2, testImage.getWidth());
    }

    @Test
    void testImageHeightTest() {
        Assertions.assertEquals(2, testImage.getHeight());
    }

    @ParameterizedTest
    @MethodSource("getImageDataInputs")
    public void imageDataTest(int x, int y, String expected) {
        int actual = testImage.getPixel(x, y);

        Assertions.assertEquals(expected, Integer.toUnsignedString(actual, 16));
    }

    static Stream<Arguments> getImageDataInputs() {
        return Stream.of(
                Arguments.of(0, 0, "ff3264ea"),
                Arguments.of(1, 0, "ff2b7b2f"),
                Arguments.of(0, 1, "ff9b4b3c"),
                Arguments.of(1, 1, "8cffffff")
        );
    }

    @ParameterizedTest
    @MethodSource("getArgbToRgbaInputs")
    public void argbToRgba(String hexInput, String hexOutput) {
        int input = Integer.parseUnsignedInt(hexInput, 16);

        int actual = ImageData.argbToRgba(input);
        Assertions.assertEquals(hexOutput, Integer.toUnsignedString(actual, 16));
    }

    static Stream<Arguments> getArgbToRgbaInputs() {
        return Stream.of(
                Arguments.of("11223344", "11443322"),
                Arguments.of("1a2a3a4a", "1a4a3a2a")
        );
    }
}
