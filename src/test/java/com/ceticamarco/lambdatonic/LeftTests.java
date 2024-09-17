package com.ceticamarco.lambdatonic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

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
}
