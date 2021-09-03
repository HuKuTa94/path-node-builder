package com.hukuta94.pathnodebuilder.domain.util;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Very very simple implementation of tuple for two objects
 * @param <A> the first object
 * @param <B> the second object
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tuple<A, B>
{
    private A objectA;
    private B objectB;
}

