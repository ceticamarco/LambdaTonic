package com.ceticamarco.lambdatonic;

import java.util.function.Function;

/**
 * <p>
 *     Subtype of the <i>Either</i> protocol.
 *     This class sets the right value of the either type
 * </p>
 * @param value The right value of the <i>Either</i> type
 * @param <L> The left type, representing the error value
 * @param <R> The right type, representing the actual value
 */
public record Right<L, R>(R value) implements Either<L, R> {
    @Override
    public <T> T match(Function<L, T> onLeft, Function<R, T> onRight) {
        return onRight.apply(this.value);
    }

    @Override
    public boolean isLeft() {
        return false;
    }

    @Override
    public boolean isRight() {
        return true;
    }
}