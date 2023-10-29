package de.kkendzia.myintranet.ei.core.utils;

import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.function.ValueProvider;

import java.util.Collection;
import java.util.stream.Stream;

public class DataProviderUtil
{
    public static <T, F> DataProvider<T, F> emptyDataProvider()
    {
        return DataProvider.fromFilteringCallbacks(query -> Stream.empty(), query -> 0);
    }

    public static <T> DataProvider<T, String> stringFilterDataProvider(Collection<T> items, ValueProvider<T, String> valueProvider)
    {
        return DataProvider.ofCollection(items).filteringBySubstring(valueProvider);
    }

}
