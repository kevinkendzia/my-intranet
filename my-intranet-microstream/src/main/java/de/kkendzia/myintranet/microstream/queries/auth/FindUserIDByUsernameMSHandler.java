package de.kkendzia.myintranet.microstream.queries.auth;

import de.kkendzia.myintranet.app._framework.result.ListResult;
import de.kkendzia.myintranet.app.useractions.queries.FindUserIDByUsername;
import de.kkendzia.myintranet.app.useractions.queries.FindUserIDByUsername.Failure;
import de.kkendzia.myintranet.app.useractions.queries.FindUserIDByUsername.FindUserIDByUsernameHandler;
import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.domain.user.EIUser.EIUserID;
import de.kkendzia.myintranet.microstream._framework.AbstractMSQueryHandler;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class FindUserIDByUsernameMSHandler
        extends AbstractMSQueryHandler
        implements FindUserIDByUsernameHandler
{
    public FindUserIDByUsernameMSHandler(final StorageManager storageManager)
    {
        super(storageManager);
    }

    @Override
    public ListResult<EIUserID, Failure> fetchAll(final FindUserIDByUsername query)
    {
        // TODO: optimize failures
        final EIUser u =
                getRoot()
                        .getEiUsers()
                        .values()
                        .stream()
                        .filter(x -> Objects.equals(x.getUserName(), query.userName()))
                        .findFirst()
                        .orElseThrow();

        return ListResult.success(List.of(u.getId()));
    }
}
