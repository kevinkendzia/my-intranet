package de.kkendzia.myintranet.domain.utils;

import java.util.function.BinaryOperator;
import java.util.function.Supplier;

public final class Reduce
{
    private Reduce()
    {
        // No Instance!
    }

    public static <T> BinaryOperator<T> toOnlyElement(Supplier<RuntimeException> throwableSupplier)
    {
        return (x1, x2) ->
        {
            throw throwableSupplier.get();
        };
    }
    public static <T> BinaryOperator<T> toOnlyElement()
    {
        return toOnlyElement(() -> new IllegalStateException("Found more than 1 Element!"));
    }
}
