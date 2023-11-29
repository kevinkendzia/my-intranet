package de.kkendzia.myintranet.app.useractions.shared;

import java.time.LocalDateTime;

public record ActionItem(
        String title,
        String route,
        LocalDateTime timestamp)
{
    public ActionItem(final String title, final String route)
    {
        this(title, route, LocalDateTime.now());
    }
}
