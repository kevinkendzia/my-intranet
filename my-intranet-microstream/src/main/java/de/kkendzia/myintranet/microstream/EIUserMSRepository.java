package de.kkendzia.myintranet.microstream;

import de.kkendzia.myintranet.app.user.queries.user.EIUserIDByUserNameQuery;
import de.kkendzia.myintranet.app.user.queries.user.EIUserIDByUserNameQuery.EIUserIDByUserNameQueryHandler;
import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.domain.user.EIUserRepository;
import de.kkendzia.myintranet.microstream._core.MyIntranetRoot;
import de.kkendzia.myintranet.microstream._framework.AbstractMSRepository;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

@Component
public class EIUserMSRepository extends AbstractMSRepository<EIUser, EIUser.EIUserID> implements EIUserRepository
{
    public EIUserMSRepository(final MyIntranetRoot root, final StorageManager storageManager)
    {
        super(root, storageManager);
    }

    @Override
    protected List<EIUser> getRootCollection()
    {
        return getRoot().getEIUsers();
    }

    @Component
    public static class EIUserIDByUserNameQueryMSHandler extends EIUserIDByUserNameQueryHandler
    {
        private final MyIntranetRoot root;

        public EIUserIDByUserNameQueryMSHandler(final MyIntranetRoot root)
        {
            this.root = requireNonNull(root, "root can't be null!");
        }

        @Override
        public EIUser.EIUserID execute(final EIUserIDByUserNameQuery query)
        {
            // TODO
            final EIUser u = root.getEIUsers()
                    .stream()
                    .filter(x -> Objects.equals(x.getUserName(), query.userName()))
                    .findFirst()
                    .orElseThrow();

            return u.getId();
        }
    }
}
