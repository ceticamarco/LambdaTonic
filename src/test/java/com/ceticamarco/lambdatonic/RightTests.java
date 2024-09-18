package com.ceticamarco.lambdatonic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RightTests {
    private Either<Integer, String> resEither;
    private Either<Error, Integer> numEither;

    @BeforeEach
    public void tearUp() {
        this.resEither = new Right<>("Query executed successfully");
        this.numEither = new Right<>(4);
    }

    @Test
    public void testMatchRight() {
        var actual = this.resEither.match(
            errorCode  -> "Error code: " + errorCode.toString(),
            successMsg -> successMsg
        );

        var expected = "Query executed successfully";

        assertEquals(expected, actual);
    }

    @Test
    public void testIsLeft() {
        assertFalse(this.resEither.isLeft());
    }

    @Test
    public void testIsRight() {
        assertTrue(this.resEither.isRight());
    }

    @Test
    public void testFunctorMapRight() {
        Either<Error, Integer> res = this.numEither.map(x -> x * x);

        assertEquals((int)res.match(
            _ -> 0,
            x -> x
        ), 16);
    }

    @Test
    public void testFunctorBiMapRight() {
        Function<Integer, Integer> f = x -> x + 1;
        Function<String, String> g = String::toUpperCase;

        Either<Integer, String> actual = this.resEither.bimap(f, g);

        assertEquals(actual.fromRight("-1"), "QUERY EXECUTED SUCCESSFULLY");
    }

    @Test
    public void testRightFunctorIdentityMorphism() {
        // Applying the map function with the identity function,
        // should not change data type structure
        Either<Error, Integer> result = this.numEither.map(Function.identity());

        assertEquals(this.numEither, result);
    }

    @Test
    public void testRightFunctorCompositionMorphism() {
        // map ( f . g ) == map f . map g
        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;

        // Evaluate map x . map g
        Either<Error, Integer> gMapped = this.numEither.map(g);
        Either<Error, Integer> FGMapped = gMapped.map(f);
        // Evaluate map ( f . g )
        Either<Error, Integer> composition = this.numEither.map(f.compose(g));

        assertEquals(composition, FGMapped);
    }

    @Test
    public void testFromRightOnRight() {
        assertEquals(this.numEither.fromRight(-1), 4);
    }

    @Test
    public void testFromLeftOnRight() {
        assertEquals(this.resEither.fromLeft(-1), -1);
    }
}
