package net.codingdemon.vectorization;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class JavaDocExampleTest {
    @Test
    void vectorMultiply() {
        JavaDocExample jde = new JavaDocExample();
        jde.size = 256;
        jde.init();

        jde.simpleMultiply();
        float[] tempResult = Arrays.copyOf(jde.c, jde.c.length);
        Arrays.fill(jde.c, 0f);

        jde.simpleMultiplyUnrolled();
        assertArrayEquals(tempResult, jde.c, 0.001f);
        tempResult = Arrays.copyOf(jde.c, jde.c.length);
        Arrays.fill(jde.c, 0f);

        jde.vectorMultiply();
        assertArrayEquals(tempResult, jde.c, 0.001f);
    }
}