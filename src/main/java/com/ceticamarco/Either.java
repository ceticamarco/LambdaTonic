package com.ceticamarco;

import java.util.function.Function;

/**
 * <p>
 *     This protocol defines a new sum type and provides the methods
 *     to operate on the associated data.
 *     The <i>Either</i> type can be implemented only by the <i>Left</i>
 *     and the <i>Right</i> classes.
 * </p>
 * @param <L> The left type, representing the error value
 * @param <R> The right type, representing the actual value
 */
public sealed interface Either<L, R> permits Left, Right {
    <T> T match(Function<L, T> onLeft, Function<R, T> onRight);
}