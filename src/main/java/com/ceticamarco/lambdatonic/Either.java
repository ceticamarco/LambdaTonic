package com.ceticamarco.lambdatonic;

import java.util.Optional;
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
     *     Applies <i>onLeft</i> function to the <i>Left</i> subtype or the
     *     <i>onRight</i> function to the <i>Right</i> subtype.
     * </p>
     * @param onLeft The function to apply to the <i>Left</i> subtype
     * @param onRight The function to apply to the <i>Right</i> subtype
     * @return An <i>Either</i> functor
     * @param <T> The return type of the <i>onLeft</i> function
     * @param <K> The return type of the <i>onRight</i> function
     */
    <T, K> Either<T, K> bimap(Function<L, T> onLeft, Function<R, K> onRight);

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

    /**
     * <p>
     *     Converts an <i>Either</i> data type to a <i>java.util.Optional</i>.
     *     The <i>Right</i> becomes a non-null <i>Optional</i> and the <i>Left</i>
     *     becomes a null <i>Optional</i>
     * </p>
     * @return An <i>Optional</i> data type
     */
    Optional<R> toOptional();

    /**
     * <p>
     *     Flips the <i>Left</i> and the <i>Right</i> data types.
     * </p>
     * @return An <i>Either</i> monad with <i>Left</i> and <i>Right</i> flipped
     */
    Either<R, L> swap();
}