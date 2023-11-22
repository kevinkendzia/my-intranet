package de.kkendzia.myintranet.microstream.queries.auth;

import de.kkendzia.myintranet.app.useractions.queries.FindUserIDByUsername;
import de.kkendzia.myintranet.app.useractions.queries.FindUserIDByUsername.Failure;
import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.domain.user.EIUser.EIUserID;
import de.kkendzia.myintranet.microstream._core.MyIntranetRoot;
import de.kkendzia.myintranet.microstream._framework.AbstractMSQueryHandler;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class FindUserIDByUsernameMSHandler
        extends AbstractMSQueryHandler<FindUserIDByUsername, EIUserID, Failure>
        implements FindUserIDByUsername.FindUserIDByUsernameHandler
{
    public FindUserIDByUsernameMSHandler(final MyIntranetRoot root, final StorageManager storageManager)
    {
        super(root, storageManager);
    }

    @Override
    public ListQueryResult<EIUserID, Failure> fetchAll(final FindUserIDByUsername query, final Paging paging)
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

        return ListQueryResult.success(List.of(u.getId()));
    }
}
