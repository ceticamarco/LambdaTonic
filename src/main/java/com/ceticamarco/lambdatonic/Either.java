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
     *      Executing an anonymous function by discriminating against
     *      the <i>Either</i> data type value
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
     *     Returns true if the <i>Either</i> type is instantiated with the Left subtype,
     *     false otherwise
     * </p>
     * @return Boolean value
     */
    boolean isLeft();

    /**
     * <p>
     *     Returns true if the Either type is instantiated with the <i>Right</i> subtype,
     *     false otherwise
     * </p>
     * @return Boolean value
     */
    boolean isRight();

    /**
     * <p>
     *     Defines a functor. That is, a data type that supports
     *     a mapping operation defined by the map method.
     *     <br /><br />
     *     This method
     *     applies a function(<i>fn</i>) to the values inside the data type,
     *     returning a new data type(i.e., a new functor) if and only if the the Either
     *     type is instantiated with the <i>Right</i> subtype. Otherwise it leaves the functor
     *     unchanged.
     *     <br /><br />
     *     The type of the resulting functor is the return type specified on the <i>fn</i>
     *     function
     * </p>
     * @param fn The function to applies to the Either data type
     * @return An <i>Either</i> functor
     * @param <T> The return type of the <i>fn</i> function
     */
    <T> Either<L, T> map(Function<R, T> fn);

    /**
     * <p>
     *     Returns the content of <i>Right</i> or a default value
     *     <br /><br />
     *     The default value must be of the same type of the <i>Right</i> value
     * </p>
     * @param defaultValue The default value to return if <i>Either</i> is <i>Left</i>
     * @return The right value of <i>Either</i> or the default value
     */
    R fromRight(R defaultValue);

    /**
     * <p>
     *     Returns the content of <i>Left</i> or a default value
     *     <br /><br />
     *     The default value must be of the same type of the <i>Left</i> value
     * </p>
     * @param defaultValue The default value to return if <i>Either</i> is <i>Right</i>
     * @return The left value of <i>Either</i> or the default value
     */
    L fromLeft(L defaultValue);
}