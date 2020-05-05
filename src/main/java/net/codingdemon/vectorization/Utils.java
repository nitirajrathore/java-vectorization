package net.codingdemon.vectorization;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorSpecies;

import java.util.concurrent.ThreadLocalRandom;

import static jdk.incubator.vector.VectorShape.S_256_BIT;

public class Utils {
    public static final VectorSpecies<Float> F256 = VectorSpecies.of(float.class, S_256_BIT);
    public static final VectorSpecies<Float> PREFERRED = FloatVector.SPECIES_PREFERRED;

    public static float[] newFloatVector(int size) {
        float[] vector = new float[size];
        for (int i = 0; i < vector.length; ++i) {
            vector[i] = ThreadLocalRandom.current().nextFloat();
        }
        return vector;
    }
}
