package com.ceticamarco.lambdatonic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LeftTests {
    @Test
    public void testMatchLeft() {
        Either<Integer, String> resEither = new Left<>(19);

        var actual = resEither.match(
            errorCode  -> "Error code: " + errorCode.toString(),
            successMsg -> successMsg
        );

        var expected = "Error code: 19";

        assertEquals(expected, actual);
    }
}
