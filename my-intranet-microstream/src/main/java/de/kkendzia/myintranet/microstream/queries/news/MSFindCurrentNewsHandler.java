package de.kkendzia.myintranet.microstream.queries.news;

import de.kkendzia.myintranet.app._framework.cqrs.query.paged.Paging;
import de.kkendzia.myintranet.app._framework.result.ListResult;
import de.kkendzia.myintranet.app._shared.failures.SearchFailure;
import de.kkendzia.myintranet.app.news.queries.FindCurrentNews;
import de.kkendzia.myintranet.app.news.queries.FindCurrentNews.FindCurrentNewsHandler;
import de.kkendzia.myintranet.app.news.queries.FindCurrentNews.NewsItem;
import de.kkendzia.myintranet.domain.news.News;
import de.kkendzia.myintranet.microstream._framework.AbstractMSQueryHandler;
import org.eclipse.store.storage.types.StorageManager;
import org.springframework.stereotype.Component;

@Component
public class MSFindCurrentNewsHandler extends AbstractMSQueryHandler<News> implements FindCurrentNewsHandler
{
    public MSFindCurrentNewsHandler(final StorageManager storageManager)
    {
        super(storageManager);
    }

    @Override
    public ListResult<NewsItem, SearchFailure> fetchAll(final FindCurrentNews query, final Paging paging)
    {
        return ListResult.success(
                applyPaging(getRoot().getNews().values().stream(), paging)
                        .map(x -> new NewsItem(x.getTimestamp(), x.getTitle(), x.getMessage()))
                        .toList());
    }
}
