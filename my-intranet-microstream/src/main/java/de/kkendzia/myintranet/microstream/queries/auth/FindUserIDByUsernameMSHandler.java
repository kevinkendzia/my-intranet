package de.kkendzia.myintranet.microstream.queries.auth;

import de.kkendzia.myintranet.app._framework.result.SingleResult;
import de.kkendzia.myintranet.app.useractions.queries.FindUserIDByUsername;
import de.kkendzia.myintranet.app.useractions.queries.FindUserIDByUsername.Failure;
import de.kkendzia.myintranet.app.useractions.queries.FindUserIDByUsername.FindUserIDByUsernameHandler;
import de.kkendzia.myintranet.domain._core.elements.AbstractEntity;
import de.kkendzia.myintranet.domain.user.EIUser.EIUserID;
import de.kkendzia.myintranet.microstream._framework.AbstractMSQueryHandler;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static de.kkendzia.myintranet.app._framework.result.SingleResult.failure;
import static de.kkendzia.myintranet.app.useractions.queries.FindUserIDByUsername.Failure.UNKNOWN;

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
    public SingleResult<EIUserID, Failure> fetchOne(final FindUserIDByUsername query)
    {
        return getRoot()
                .getEiUsers()
                .values()
                .stream()
                .filter(user -> Objects.equals(user.getUserName(), query.userName()))
                .findFirst()
                .map(AbstractEntity::getId)
                .map(SingleResult::<EIUserID, Failure>success)
                // TODO: Better Failure!
                .orElseGet(() -> failure(UNKNOWN));
    }
}
