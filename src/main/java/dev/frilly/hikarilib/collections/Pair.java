package dev.frilly.hikarilib.collections;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * Simple data class as a 2-tuple.
 *
 * @param <A> The type of the first value.
 * @param <B> The type of the second value.
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
@Value
public class Pair<A, B> {

    /**
     * -- GETTER --
     * Gets the first value.
     */
    A first;

    /**
     * -- GETTER --
     * Gets the second value.
     */
    B second;

}
