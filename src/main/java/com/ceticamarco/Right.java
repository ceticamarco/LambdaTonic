package com.ceticamarco;

import java.util.function.Function;

public record Right<L, R>(R value) implements Either<L, R> {
    @Override
    public <T> T match(Function<L, T> onLeft, Function<R, T> onRight) {
        return onRight.apply(this.value);
    }
}