package com.ceticamarco.lambdatonic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RightTests {
    @Test
    public void testMatchRight() {
        Either<Integer, String> resEither = new Right<>("Query executed successfully");

        var actual = resEither.match(
                errorCode  -> "Error code: " + errorCode.toString(),
                successMsg -> successMsg
        );

        var expected = "Query executed successfully";

        assertEquals(expected, actual);
    }
}
