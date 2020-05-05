package net.codingdemon.vectorization;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FloatVector256DotProductTest {

    @Test
    void vector() {
        FloatVector256DotProduct dp = new FloatVector256DotProduct();
        dp.size = 256;
        dp.init();

        float vectorfma = dp.vectorfma();
        float vectorfmaUnrolled = dp.vectorfmaUnrolled();
        float vector = dp.vector();
        float vectorUnrolled = dp.vectorUnrolled();
        float unrolled = dp.unrolled();
        float vanilla = dp.vanilla();

        assertEquals(vectorfma, vectorfmaUnrolled, 0.01f);
        assertEquals(vectorfma, vector, 0.01f);
        assertEquals(vectorfma, vectorUnrolled, 0.01f);
        assertEquals(vectorfma, unrolled, 0.01f);
        assertEquals(vectorfma, vanilla, 0.01f);
    }
}