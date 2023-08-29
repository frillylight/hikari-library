package dev.frilly.hikarilib.collections;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * A simple triple of objects.
 *
 * @param <A> The type of the first object.
 * @param <B> The type of the second object.
 * @param <C> The type of the third object.
 */
@RequiredArgsConstructor(staticName = "of")
@Value
@Getter
public class Triple<A, B, C> {

    /**
     * -- GETTER --
     * Gets the first object.
     */
    A first;

    /**
     * -- GETTER --
     * Gets the second object.
     */
    B second;

    /**
     * -- GETTER --
     * Gets the third object.
     */
    C third;

}
