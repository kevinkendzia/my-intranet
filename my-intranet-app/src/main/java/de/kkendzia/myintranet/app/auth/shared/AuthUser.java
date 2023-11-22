package de.kkendzia.myintranet.app.auth.shared;

import de.kkendzia.myintranet.domain.user.EIUser;

public record AuthUser(
        EIUser.EIUserID id,
        String username,
        String password)
{
    // just record
}
