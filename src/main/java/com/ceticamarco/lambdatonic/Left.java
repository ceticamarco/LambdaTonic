package com.ceticamarco.lambdatonic;

import java.util.function.Function;

/**
 * <p>
 *      Subtype of the <i>Either</i> protocol.
 *      This class sets the left value of the Either type
 * </p>
 * @param value The left value of the <i>Either</i> type
 * @param <L> The left type, representing the error value
 * @param <R> The right type, representing the actual value
 */
public record Left<L, R>(L value) implements Either<L, R> {
    @Override
    public <T> T match(Function<L, T> onLeft, Function<R, T> onRight) {
        return onLeft.apply(this.value);
    }

    @Override
    public boolean isLeft() {
        return true;
    }

    @Override
    public boolean isRight() {
        return false;
    }
}