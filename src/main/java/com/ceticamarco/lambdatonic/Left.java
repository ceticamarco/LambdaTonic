package com.ceticamarco.lambdatonic;

import java.util.Optional;
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
    public boolean isLeft() {
        return true;
    }

    @Override
    public boolean isRight() {
        return false;
    }

    @Override
    public <T> Either<L, T> map(Function<R, T> fn) {
        return new Left<>(this.value);
    }

    @Override
    public <T, K> Either<T, K> bimap(Function<L, T> onLeft, Function<R, K> onRight) {
        return new Left<>(onLeft.apply(this.value));
    }

    @Override
    public R fromRight(R defaultValue) {
        return defaultValue;
    }

    @Override
    public L fromLeft(L defaultValue) {
        return this.value;
    }

    @Override
    public Optional<R> toOptional() {
        return Optional.empty();
    }

    @Override
    public Either<R, L> swap() {
        return new Right<>(this.value);
    }
}