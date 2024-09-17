package com.ceticamarco.lambdatonic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RightTests {
    private Either<Integer, String> resEither;

    @BeforeEach
    public void tearUp() {
        this.resEither = new Right<>("Query executed successfully");
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
}
