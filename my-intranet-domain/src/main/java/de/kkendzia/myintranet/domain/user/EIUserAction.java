package de.kkendzia.myintranet.domain.user;

import de.kkendzia.myintranet.domain._core.elements.AbstractValueObject;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

public class EIUserAction extends AbstractValueObject
{
    public static final String PROPERTY_TIMESTAMP = "timestamp";

    private final String title;
    private final String route;
    private final LocalDateTime timestamp;

    public EIUserAction(final String title, final String route, LocalDateTime timestamp)
    {
        this.title = title;
        this.route = route;
        this.timestamp = timestamp;
    }

    public EIUserAction(final String title, final String route)
    {
        this(title, route, LocalDateTime.now());
    }

    //region OVERRIDES
    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        final EIUserAction that = (EIUserAction) o;
        return Objects.equals(getTitle(), that.getTitle()) && Objects.equals(
                getRoute(),
                that.getRoute()) && Objects.equals(getTimestamp(), that.getTimestamp());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getTitle(), getRoute(), getTimestamp());
    }

    @Override
    public String toString()
    {
        return new StringJoiner(", ", EIUserAction.class.getSimpleName() + "[", "]")
                .add("title='" + title + "'")
                .add("route='" + route + "'")
                .add("timestamp=" + timestamp)
                .toString();
    }
    //endregion

    //region GETTER
    public String getTitle()
    {
        return title;
    }

    public String getRoute()
    {
        return route;
    }

    public LocalDateTime getTimestamp()
    {
        return timestamp;
    }
    //endregion
}
