package de.kkendzia.myintranet.ei.ui.views.home;

import com.vaadin.flow.data.provider.DataProvider;
import de.kkendzia.myintranet.ei.core.presenter.EIPresenter;
import de.kkendzia.myintranet.ei.core.presenter.Presenter;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@Presenter
public class HomeViewPresenter implements EIPresenter
{
    public DataProvider<ActionItem, Void> createActionDataProvider(boolean favorites)
    {
        return DataProvider.fromCallbacks(
                query -> fetchActionItems(favorites, query.getOffset(), query.getLimit()),
                query -> Math.toIntExact(countActionItems(favorites)));
    }

    public long countActionItems(final boolean favorites)
    {
        return 5;
    }

    public Stream<ActionItem> fetchActionItems(final boolean favorites, int offset, final int limit)
    {
        return IntStream.range(offset, limit)
                .boxed()
                .map(i -> new ActionItem("Action " + i, "ah/search"));
    }

    public DataProvider<NewsItem, Void> createNewsDataProvider()
    {
        return DataProvider.fromCallbacks(
                query -> fetchNewsItems(query.getOffset(), query.getLimit()),
                query -> Math.toIntExact(countNewsItems()));
    }

    public long countNewsItems()
    {
        return 5;
    }

    public Stream<NewsItem> fetchNewsItems(int offset, final int limit)
    {
        return IntStream.range(offset, limit)
                .boxed()
                .map(i -> new NewsItem("News " + i, "Meeeeeeeeeesssssssssssssaaaaaaaaaaaaaaaaaaaage!", false));
    }

    public record ActionItem(
            String title,
            String route)
    {
        // just a record
    }

    public record NewsItem(
            String title,
            String message,
            boolean seen)
    {
        // just a record
    }
}
