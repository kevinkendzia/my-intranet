package de.kkendzia.myintranet.domain.news;

import de.kkendzia.myintranet.domain._core.AbstractAggregateRoot;
import de.kkendzia.myintranet.domain._core.AbstractID;

import java.time.LocalDateTime;
import java.util.UUID;

public class News extends AbstractAggregateRoot<News, News.NewsID>
{
    private LocalDateTime timestamp;
    private String title;
    private String message;

    public LocalDateTime getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(final LocalDateTime timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(final String title)
    {
        this.title = title;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(final String message)
    {
        this.message = message;
    }

    public static class NewsID extends AbstractID
    {
        public NewsID(final UUID value)
        {
            super(value);
        }

        public NewsID(final String value)
        {
            super(value);
        }

        public NewsID()
        {
        }
    }
}
