package com.ceticamarco;

import java.util.function.Function;

/**
 * <p>
 *
 * </p>
 * @param value
 * @param <L>
 * @param <R>
 */
public record Left<L, R>(L value) implements Either<L, R> {
    @Override
    public <T> T match(Function<L, T> onLeft, Function<R, T> onRight) {
        return onLeft.apply(this.value);
    }
}