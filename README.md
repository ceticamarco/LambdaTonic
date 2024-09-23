# λTonic 🥃 ![](https://github.com/ceticamarco/lambdatonic/actions/workflows/LambdaTonic.yml/badge.svg)

**λTonic**(_LambdaTonic_) is functional library designed for modern Java(+21).  

This library introduces a new algebraic data type called `Either<L, R>`; 
that is, an immutable  sum type that _discriminates_ between two values, `Left<L>` and `Right<R>`, 
representing the failure and the success values, respectively.

The `Either<L, R>` data type is implemented using a **sealed interface**, while the `Left<L>`
and the `Right<R>` are **record classes** that adopt the `Either<L, R>` protocol. Both the `Left<L>`
and the `Right<R>` data types can be used inside a `switch` statement using Java pattern matching. 

## Overview
The `Either<L, R>` algebraic data type can be used orthogonally over exceptions to propagate
an error from a function. Consider the following scenario:
```java
public class Main {
    public static Either<Error, Double> division(double dividend, double divisor) {
        // Return an error whether the divisor is zero
        if(divisor == 0) {
            return new Left<>(new Error("Cannot divide by zero"));
        }

        // Otherwise return the result of the division
        return new Right<>(dividend / divisor);
    }

    public static void main(String[] args) {
        // Try to divide 15 by 3
        Either<Error, Double> divResult = division(15, 3);
        switch (divResult) {
            case Left<Error, Double> err -> System.err.println(err.value().getMessage());
            case Right<Error, Double> val -> System.out.printf("15 / 3 = %f\n", val.value());
        }

        // Try to divide 2 by 0
        var div2Result = division(2, 0);
        switch (div2Result) {
            case Left<Error, Double> err -> System.err.println(err.value().getMessage());
            case Right<Error, Double> val -> System.out.printf("2 / 0 = %f\n", val.value());
        }
    }
}
```

In this example we have defined a `division` method that takes two arguments and perform a division
on them. To handle the case when the `divisor` parameter is equal to zero, we return a new `Left<L>`
instance of the `Either<L, R>` type, while in any other case, we return a new `Right<R>` instance of the
`Either<L, R>` type. In the caller method(i.e., the `main`) we can then execute a custom statement using 
Java's builtin pattern matching.


## Installation
**λTonic** is available on [Maven Central](https://central.sonatype.com/artifact/io.github.ceticamarco/LambdaTonic),
you can install it either by using Maven:
```xml
<dependency>
    <groupId>io.github.ceticamarco</groupId>
    <artifactId>LambdaTonic</artifactId>
    <version>0.0.3</version>
</dependency>
```

or by using Gradle:
```kotlin
implementation 'io.github.ceticamarco:LambdaTonic:0.0.1'
```

## API Usage
The `Either<L, R>` data type supports a broad spectrum of features, below there is a list of all supported
functionalities.

-  `map`

### Description
```java
<T> Either<L, T> map(Function<R, T> fn);
```
The `map` method applies a function(`fn`) to the values inside the data type,
returning a new data type if and only if the `Either<L, R>` type is instantiated
to the `Right<R>` type. The `map` method adheres to the 
functor laws(identity and composition of morphisms), which allows the `Either<L, R>` data type
to be classified as a functor.

### Usage
The `map` method can be used to apply a computation to the value inside a functor:
```java
public class Main {
    // ...
    public static void main(String[] args) {
        var resDivision = division(15, 3);
        var resSquared = resDivision.map(x -> x * x);

        switch (resSquared) {
            case Left<Error, Double> err -> System.err.println(err.value().getMessage());
            case Right<Error, Double> val -> System.out.println(val.value()); // prints 25.0
        }
    }
}
```

- `bimap`

### Description
```java
<T, K> Either<T, K> bimap(Function<L, T> onLeft, Function<R, K> onRight);
```

The `bimap` method applies the `onLeft` method to the `Left<L>` subtype or the
`onRight` to the `Right<R>`.

### Usage
```java
public class Main {
    public static void main(String[] args) {
        var sc = new Scanner(System.in);

        // Read from stdin
        System.out.print("Enter a divisor: ");
        var input = sc.nextInt();

        // Divide a fixed dividend by user input divisor
        var divRes = division(15, input);

        // Apply a function regardless of the type of Either
        // On the left we uppercase the error message
        // On the right we square the result
        var bimapRes = divRes.bimap(
            err -> new Error(err.toString().toUpperCase()),
            val -> val * val
        );

        switch (bimapRes) {
            case Left<Error, Double> err -> System.err.println(err.value().getMessage());
            case Right<Error, Double> val -> System.out.println(val.value());
        }
    }
}
```

- `isLeft`

### Description
```java
boolean isLeft();
```

`isLeft` returns true whether `Either<L, R>` is instantiated to the `Left<L>`, false otherwise

### Usage
```java
public class Main {
    public static void main(String[] args) {
        var sc = new Scanner(System.in);

        // Read from stdin
        System.out.print("Enter a divisor: ");
        var input = sc.nextInt();

        // Divide a fixed dividend by user input divisor
        var divRes = division(15, input);
        
        if(divRes.isLeft()) {
            System.out.println("Cannot divide by zero");
        } else {
            System.out.println(divRes.fromRight(-1.0));
        }
    }
}
```

- `isRight`

### Description
```java
boolean isRight();
```

`isRight` returns true whether `Either<L, R>` is instantiated to the `Right<L>`, false otherwise

### Usage
```java
public class Main {
    public static void main(String[] args) {
        var sc = new Scanner(System.in);

        // Read from stdin
        System.out.print("Enter a divisor: ");
        var input = sc.nextInt();

        // Divide a fixed dividend by user input divisor
        var divRes = division(15, input);

        if(divRes.isRight()) {
            System.out.println(divRes.fromRight(-1.0));
        } else {
            System.out.println("Cannot divide by zero");
        }
    }
}
```

- `fromLeft`

### Description
```java
L fromLeft(L defaultValue);
```

`fromLeft` returns the content of the `Left<L>` value or a default value

### Usage
```java
public class Main {
    public static void main(String[] args) {
        var sc = new Scanner(System.in);

        // Read from stdin
        System.out.print("Enter a divisor: ");
        var input = sc.nextInt();

        // Divide a fixed dividend by user input divisor
        var divRes = division(15, input);
        
        // Prints out the error message or nothing  
        System.out.println(divRes.fromLeft(new Error("")).getMessage());
    }
}
```

- `fromRight`

### Description
```java
L fromLeft(L defaultValue);
```

`fromRight` returns the content of the `Right<R>` value or a default value

### Usage
```java
public class Main {
    public static void main(String[] args) {
        var sc = new Scanner(System.in);

        // Read from stdin
        System.out.print("Enter a divisor: ");
        var input = sc.nextInt();

        // Divide a fixed dividend by user input divisor
        var divRes = division(15, input);

        // Prints out the actual value or nothing
        System.out.println(divRes.fromRight(0.0));
    }
}
```

- `toOptional`

### Description
```java
Optional<R> toOptional();
```

`toOptional` converts an `Either<L, R>` data type to a `java.util.Optional`,
where the `Right<R>` becomes a non-null `Optional<R>` and the `Left<L>`
becomes a null `Optional`.

### Usage
```java
public class Main {
    public static void main(String[] args) {
        var sc = new Scanner(System.in);

        // Read from stdin
        System.out.print("Enter a divisor: ");
        var input = sc.nextInt();

        // Divide a fixed dividend by user input divisor
        var divRes = division(15, input).toOptional();

        // Prints out the actual value or nothing
        divRes.ifPresent(System.out::println);
    }
}
```

- `swap`

### Description
```java
Either<R, L> swap();
```

`swap` returns an `Either<R, L>` type with `Left<>` and `Right<>` swapped.

### Usage
```java
public class Main {
    public static void main(String[] args) {
        Either<String, Integer> val = new Left<>("generic error");
        Either<Integer, String> res = val.swap();

        System.out.println(res.isLeft()); // Prints false
        System.out.println(res.isRight()); // Prints true
    }
}
```

## License
This software is released under the MIT license. 
You can find a copy of the license with this repository or by visiting 
the [following page](https://choosealicense.com/licenses/mit/).
