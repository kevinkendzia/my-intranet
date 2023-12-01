package de.kkendzia.myintranet.app.news.queries;

import de.kkendzia.myintranet.app._framework.cqrs.query.paged.PagedQuery;
import de.kkendzia.myintranet.app._shared.failures.SearchFailure;

import java.time.LocalDateTime;

public record FindCurrentNews() implements PagedQuery<FindCurrentNews.NewsItem, SearchFailure>
{
    public interface FindCurrentNewsHandler extends PagedQueryHandler<FindCurrentNews, NewsItem, SearchFailure>
    {
        @Override
        default Class<FindCurrentNews> getQueryClass()
        {
            return FindCurrentNews.class;
        }
    }

    public record NewsItem(
            LocalDateTime timestamp,
            String title,
            String message)
    {
        // just a record
    }
}
