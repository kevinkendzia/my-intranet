package de.kkendzia.myintranet.app._framework.cqrs.query.paged;

import static de.kkendzia.myintranet.app._framework.cqrs.query.paged.Direction.ASC;
import static de.kkendzia.myintranet.app._framework.cqrs.query.paged.Direction.DESC;

public record Order(
        String property,
        Direction direction)
{
    public static Order asc(String property)
    {
        return new Order(property, ASC);
    }

    public static Order desc(String property)
    {
        return new Order(property, DESC);
    }
}
