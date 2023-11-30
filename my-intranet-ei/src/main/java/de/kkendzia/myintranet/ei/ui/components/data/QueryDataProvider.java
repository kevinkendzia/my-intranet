package de.kkendzia.myintranet.ei.ui.components.data;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.function.SerializableBiFunction;
import com.vaadin.flow.function.SerializableFunction;
import de.kkendzia.myintranet.app._framework.cqrs.query.QueryMediator;
import de.kkendzia.myintranet.app._framework.cqrs.query.paged.PagedQuery;
import de.kkendzia.myintranet.app._framework.result.ListResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Stream.concat;

public class QueryDataProvider<T> extends AbstractBackEndDataProvider<T, String>
{
    private final transient QueryMediator quMediator;
    private List<QueryFactory<?, T>> queryFactories;

    public QueryDataProvider(QueryMediator quMediator, final List<QueryFactory<?, T>> queryFactories)
    {
        this.quMediator = requireNonNull(quMediator, "quMediator can't be null!");
        this.queryFactories = requireNonNull(queryFactories, "queryFactories can't be null!");
    }

    @SafeVarargs
    public QueryDataProvider(QueryMediator quMediator, final QueryFactory<?, T>... queryFactories)
    {
        this(quMediator, List.of(queryFactories));
    }

    public QueryDataProvider(QueryMediator quMediator)
    {
        this(quMediator, new ArrayList<>());
    }

    @Override
    protected Stream<T> fetchFromBackEnd(final Query<T, String> query)
    {
        if (queryFactories.isEmpty())
        {
            return Stream.empty();
        }

        final var data = queryFactories
                .stream()
                .flatMap(factory -> process(query, factory));

        if (query.getInMemorySorting() != null)
        {
            return data
                    .sorted(query.getInMemorySorting())
                    .skip(query.getOffset())
                    .limit(query.getLimit());
        }

        return data
                .skip(query.getOffset())
                .limit(query.getLimit());
    }

    private <R> Stream<T> process(final Query<T, String> query, final QueryFactory<R, T> factory)
    {
        final var searchText = getFilterText(query);
        final PagedQuery<R, ?> q = factory.create(searchText);
        final ListResult<R, ?> r = quMediator.fetchAll(q);

        final var header = factory.hasHeaderItem() && r.count() > 0 ? factory.createHeaderItem(searchText) : null;
        final var footer = factory.hasFooterItem() ? factory.createFooterItem(searchText) : null;

        // TODO: optimize?
        return concat(
                concat(
                        header != null ? Stream.of(header) : Stream.empty(),
                        r.stream().map(source -> factory.map(source, searchText))),
                footer != null ? Stream.of(footer) : Stream.empty());
    }

    @Override
    protected int sizeInBackEnd(final Query<T, String> query)
    {
        if (queryFactories.isEmpty())
        {
            return 0;
        }
        return queryFactories
                .stream()
                .mapToLong(f ->
                {
                    final PagedQuery<?, ?> q = f.create(getFilterText(query));
                    final var count = quMediator.count(q);
                    return count + f.getItemCountOffset(count);
                })
                .mapToInt(Math::toIntExact)
                .sum();
    }

    public void setQueryFactories(final List<QueryFactory<?, T>> queryFactories)
    {
        requireNonNull(queryFactories, "queryFactories can't be null!");
        this.queryFactories = queryFactories;
    }

    public void addQueryFactory(final QueryFactory<?, T> queryFactory)
    {
        requireNonNull(queryFactory, "queryFactory can't be null!");
        this.queryFactories.add(queryFactory);
    }

    //region STATIC
    private static <T> String getFilterText(final Query<T, String> query)
    {
        return query.getFilter().orElse("");
    }
    //endregion

    //region TYPES
    public interface QueryFactory<R, T> extends Serializable
    {
        PagedQuery<R, ?> create(String searchText);

        T map(R source, final String searchText);

        boolean hasHeaderItem();

        boolean hasFooterItem();

        int getItemCountOffset(long count);

        T createHeaderItem(String searchText);

        T createFooterItem(String searchText);
    }

    public static class DefaultQueryFactory<R, T> implements QueryFactory<R, T>
    {
        private final SerializableFunction<String, PagedQuery<R, ?>> factoryDelegate;
        private final SerializableBiFunction<R, String, T> mapperDelegate;
        private final SerializableFunction<String, T> headerItemFactory;
        private final SerializableFunction<String, T> footerItemFactory;

        public DefaultQueryFactory(
                final SerializableFunction<String, PagedQuery<R, ?>> factoryDelegate,
                final SerializableBiFunction<R, String, T> mapperDelegate,
                SerializableFunction<String, T> headerItemFactory,
                SerializableFunction<String, T> footerItemFactory)
        {
            this.factoryDelegate = requireNonNull(factoryDelegate, "factoryDelegate can't be null!");
            this.mapperDelegate = requireNonNull(mapperDelegate, "mapperDelegate can't be null!");
            this.headerItemFactory = headerItemFactory;
            this.footerItemFactory = footerItemFactory;
        }

        public DefaultQueryFactory(
                final SerializableFunction<String, PagedQuery<R, ?>> factoryDelegate,
                final SerializableBiFunction<R, String, T> mapperDelegate)
        {
            this(factoryDelegate, mapperDelegate, null, null);
        }

        @Override
        public PagedQuery<R, ?> create(final String searchText)
        {
            return factoryDelegate.apply(searchText);
        }

        @Override
        public T map(final R source, final String searchText)
        {
            return mapperDelegate.apply(source, searchText);
        }

        @Override
        public boolean hasHeaderItem()
        {
            return headerItemFactory != null;
        }

        @Override
        public boolean hasFooterItem()
        {
            return footerItemFactory != null;
        }

        @Override
        public int getItemCountOffset(long count)
        {
            if (hasHeaderItem() && hasFooterItem() && count > 0)
            {
                return 2;
            }
            else if (hasHeaderItem() && count > 0 || hasFooterItem())
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }

        @Override
        public T createHeaderItem(String searchText)
        {
            return requireNonNull(headerItemFactory.apply(searchText), "HeaderItem can't be null!");
        }

        @Override
        public T createFooterItem(final String searchText)
        {
            return requireNonNull(footerItemFactory.apply(searchText), "FooterItem can't be null!");
        }
    }
    //endregion
}
