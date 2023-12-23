package de.kkendzia.myintranet.ei.utils;

import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.function.ValueProvider;

import java.util.Collection;
import java.util.stream.Stream;

public final class DataProviderUtils
{
    private DataProviderUtils()
    {
        // No Instance!
    }

    public static <T, F> DataProvider<T, F> emptyDataProvider()
    {
        return DataProvider.fromFilteringCallbacks(query -> Stream.empty(), query -> 0);
    }

    public static <T> DataProvider<T, String> stringFilterDataProvider(
            Collection<T> items,
            ValueProvider<T, String> valueProvider)
    {
        return DataProvider.ofCollection(items).filteringBySubstring(valueProvider);
    }

}
