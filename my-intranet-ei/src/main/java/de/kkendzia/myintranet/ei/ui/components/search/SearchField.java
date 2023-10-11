package de.kkendzia.myintranet.ei.ui.components.search;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.provider.BackEndDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.DataProviderListener;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.shared.Registration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchField<T, G> extends Composite<ComboBox<SearchField.Item<T>>>
{
    private SerializableFunction<T, G> groupingFunction;
    private boolean groupSelectable = false;

    public SearchField()
    {
        ComboBox<Item<T>> cbo = getContent();
//        cbo.setPageSize(1);
//        cbo.setItems(
//                DataProvider.fromFilteringCallbacks(
//                        query -> load(query.getFilter().orElse(""))
//                                .skip(query.getOffset())
//                                .limit(query.getLimit()),
//                        query -> Math.toIntExact(load(query.getFilter().orElse("")).count())));
        cbo.setRenderer(new ComponentRenderer<>(this::createItemComponent));
        cbo.addValueChangeListener(this::preventGroupSelection);
    }

    private Span createItemComponent(Item<T> itm)
    {
        Span span = new Span(itm.text());

        if (itm.separator())
        {
            span.getStyle().set("opacity", "0.5");
        }
        else
        {
            span.getStyle().set("padding-left", "10px");
            span.getStyle().set("pointer-events", "auto");
        }
        return span;
    }

    private void preventGroupSelection(AbstractField.ComponentValueChangeEvent<ComboBox<Item<T>>, Item<T>> e)
    {
        if (isGroupSelectable())
        {
            return;
        }

        if ((e.getValue() != null) && e.getValue().separator())
        {
            ComboBox<Item<T>> cbo = getContent();
            if ((e.getOldValue() != null))
            {
                cbo.setValue(e.getOldValue());
            }
            else
            {
                cbo.clear();
            }
        }
    }

    public void setItems(List<T> items)
    {
        if (groupingFunction != null)
        {
            Map<G, List<T>> groups = items.stream().collect(Collectors.groupingBy(groupingFunction));
            getContent().setItems(
                    groups
                            .entrySet()
                            .stream()
                            .flatMap(x ->
                                             Stream.concat(
                                                     Stream.of(new Item<T>(true, String.valueOf(x.getKey()), null)),
                                                     x.getValue().stream().map(y -> new Item<>(
                                                             false,
                                                             String.valueOf(y),
                                                             y))))
                            .toList());
        }
        else
        {
            getContent().setItems(items.stream().map(x -> new Item<>(false, String.valueOf(x), x)).toList());
        }
    }

    public void setItems(BackEndDataProvider<T, String> dataProvider)
    {
        getContent().setItems(new DataProvider<Item<T>, String>()
        {
            @Override
            public boolean isInMemory()
            {
                return false;
            }

            @Override
            public int size(Query<Item<T>, String> query)
            {
                return dataProvider.size(convertQuery(query));
            }

            @Override
            public Stream<Item<T>> fetch(Query<Item<T>, String> query)
            {
                return dataProvider
                        .fetch(convertQuery(query))
                        .flatMap(x -> Stream.concat(
                                Stream.of(new Item<T>(true, String.valueOf(x.getKey()), null)),
                                x.getValue().stream().map(y -> new Item<>(
                                        false,
                                        String.valueOf(y),
                                        y))));
            }

            private Query<T, String> convertQuery(Query<Item<T>, String> query)
            {
                if (query.getInMemorySorting() != null)
                {
                    throw new IllegalStateException("Can't set InMemorySorting!");
                }
                return new Query<>(
                        query.getOffset(),
                        query.getLimit(),
                        query.getSortOrders(),
                        null,
                        query.getFilter().orElse(null));
            }

            @Override
            public void refreshItem(Item<T> item)
            {

            }

            @Override
            public void refreshAll()
            {

            }

            @Override
            public Registration addDataProviderListener(DataProviderListener<Item<T>> listener)
            {
                return null;
            }
        });
    }

    public void setGroupingFunction(SerializableFunction<T, G> groupingFunction)
    {
        this.groupingFunction = groupingFunction;
    }

    public void setGroupSelectable(boolean groupSelectable)
    {
        this.groupSelectable = groupSelectable;
    }

    protected boolean isGroupSelectable()
    {
        return groupSelectable;
    }

    //    private static Stream<Item> load(String s)
//    {
//        return Stream.of(
//                        separator("AHs - Zeige alle AHs"),
//                        item("test1"),
//                        item("test2"),
//                        separator("VLs"),
//                        item("test3"),
//                        item("test4"),
//                        item("test5"),
//                        separator("Modelle/Artikel"),
//                        item("test6"),
//                        item("test7"),
//                        item("test8"),
//                        separator("Aktionen"),
//                        item("test9"),
//                        item("test10"),
//                        separator("Prospektseiten"),
//                        item("test11"))
//                .filter(x -> x.separator() || x.text().contains(s));
//    }

    public void setPlaceholder(String placeholder)
    {
        getContent().setPlaceholder(placeholder);
    }

    /*
     * TYPES
     */

    public record Item<T>(
            boolean separator,
            String text,
            T value)
    {
//        public static Item separator(String text)
//        {
//            return new Item(true, text);
//        }
//
//        public static Item item(String text)
//        {
//            return new Item(false, text);
//        }
    }
}
