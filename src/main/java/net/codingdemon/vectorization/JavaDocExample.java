package net.codingdemon.vectorization;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorSpecies;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static net.codingdemon.vectorization.Utils.newFloatVector;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
@Fork(value = 1, jvmArgsPrepend = {
        "--add-modules=jdk.incubator.vector",

        "-Djdk.incubator.vector.VECTOR_ACCESS_OOB_CHECK=0"
})
public class JavaDocExample {

    @Param({"1048576"})
    int size;

    float[] a;
    float[] b;
    float[] c;

    @Setup(Level.Trial)
    public void init() {
        this.a = newFloatVector(size);
        this.b = newFloatVector(size);
        this.c = new float[size];
    }


    static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_PREFERRED;

    @Benchmark
    public void simpleMultiplyUnrolled() {
        for (int i = 0; i < size; i += 8) {
            c[i] = a[i] * b[i];
            c[i + 1] = a[i + 1] * b[i + 1];
            c[i + 2] = a[i + 2] * b[i + 2];
            c[i + 3] = a[i + 3] * b[i + 3];
            c[i + 4] = a[i + 4] * b[i + 4];
            c[i + 5] = a[i + 5] * b[i + 5];
            c[i + 6] = a[i + 6] * b[i + 6];
            c[i + 7] = a[i + 7] * b[i + 7];
        }
    }

    @Benchmark
    public void simpleMultiply() {
        for (int i = 0; i < size; i++) {
            c[i] = a[i] * b[i];
        }
    }

    @Benchmark
    public void vectorMultiply() {
        int i = 0;
        // It is assumed array arguments are of the same size
        for (; i < SPECIES.loopBound(a.length); i += SPECIES.length()) {
            FloatVector va = FloatVector.fromArray(SPECIES, a, i);
            FloatVector vb = FloatVector.fromArray(SPECIES, b, i);
            FloatVector vc = va.mul(vb);
            vc.intoArray(c, i);
        }

        for (; i < a.length; i++) {
            c[i] = a[i] * b[i];
        }
    }

    public static void main(String[] args) {
        System.out.println("SPECIES_PREFERRED : " + SPECIES);
        // test validity.
        JavaDocExample jde = new JavaDocExample();
        jde.size = 256;
        jde.init();

        jde.simpleMultiply();
        System.out.println("c = " + Arrays.toString(jde.c));
        Arrays.fill(jde.c, 0f);
        System.out.println("c = " + Arrays.toString(jde.c));

        jde.simpleMultiplyUnrolled();
        System.out.println("c = " + Arrays.toString(jde.c));
        Arrays.fill(jde.c, 0f);
        System.out.println("c = " + Arrays.toString(jde.c));

        jde.vectorMultiply();
        System.out.println("c = " + Arrays.toString(jde.c));
        Arrays.fill(jde.c, 0f);
        System.out.println("c = " + Arrays.toString(jde.c));
    }

}
