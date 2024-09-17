package com.ceticamarco.lambdatonic;

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
    /**
     * <p>
     *      Executing an anonymous function by discriminating the Either data type value
     * </p>
     *
     * @param onLeft The function to execute on the Left case
     * @param onRight The function to execute on the Right case
     * @return The return value of the onLeft/onRight methods
     * @param <T> The type of the return value of the onLeft/onRight methods
     */
    <T> T match(Function<L, T> onLeft, Function<R, T> onRight);

    /**
     * <p>
     *     Returns true if the Either type is instantiated with the Left subtype,
     *     false otherwise
     * </p>
     * @return Boolean value
     */
    boolean isLeft();

    /**
     * <p>
     *     Returns true if the Either type is instantiated with the Right subtype,
     *     false otherwise
     * </p>
     * @return Boolean value
     */
    boolean isRight();
}