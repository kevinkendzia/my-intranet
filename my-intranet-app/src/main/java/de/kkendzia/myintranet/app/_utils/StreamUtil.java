package de.kkendzia.myintranet.app._utils;

import java.util.Comparator;
import java.util.stream.Stream;

public class StreamUtil
{
    private StreamUtil()
    {
        // no instance
    }

    public static <T> Stream<T> sortOptionally(Stream<T> stream, Comparator<T> comparator)
    {
        if(comparator != null)
        {
            return stream.sorted(comparator);
        }
        return stream;
    }
}
