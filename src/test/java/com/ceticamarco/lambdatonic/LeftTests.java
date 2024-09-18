package com.ceticamarco.lambdatonic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class LeftTests {
    private Either<Integer, String> resEither;
    private Either<Error, Integer> numEither;

    @BeforeEach
    public void tearUp() {
        this.resEither = new Left<>(19);
        this.numEither = new Left<>(new Error("Undefined variable"));
    }

    @Test
    public void testMatchLeft() {
        var actual = this.resEither.match(
            errorCode  -> "Error code: " + errorCode.toString(),
            successMsg -> successMsg
        );

        var expected = "Error code: 19";

        assertEquals(expected, actual);
    }

    @Test
    public void testIsLeft() {
        assertTrue(this.resEither.isLeft());
    }

    @Test
    public void testIsRight() {
        assertFalse(this.resEither.isRight());
    }

    @Test
    public void testFunctorMapLeft() {
        Either<Error, Integer> res = this.numEither.map(x -> x * x);

        assertEquals(res.match(
            Throwable::getMessage,
            _ -> "Undefined variable"
        ), "Undefined variable");
    }

    @Test
    public void testFunctorBiMapLeft() {
        Function<Integer, Integer> f = x -> x + 1;
        Function<String, String> g = String::toUpperCase;

        Either<Integer, String> actual = this.resEither.bimap(f, g);

        assertEquals(actual.fromLeft(-1), 20);
    }

    @Test
    public void testLeftFunctorIdentityMorphism() {
        // Applying the map function with the identity function,
        // should not change data type structure
        Either<Error, Integer> result = this.numEither.map(Function.identity());

        assertEquals(this.numEither, result);
    }

    @Test
    public void testLeftFunctorCompositionMorphism() {
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
    public void testFromRightOnLeft() {
        assertEquals(this.numEither.fromRight(-1), -1);
    }

    @Test
    public void testFromLeftOnLeft() {
        assertEquals(this.resEither.fromLeft(-1), 19);
    }

    @Test
    public void testToOptionalFromLeft() {
        assertEquals(
            this.numEither.toOptional(),
            Optional.empty()
        );
    }

    @Test
    public void testSwapFromLeft() {
        assertEquals(
                this.resEither.swap(),
                new Right<>(19)
        );
    }
}
