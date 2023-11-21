package de.kkendzia.myintranet.app.user.queries.user;

import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler;
import de.kkendzia.myintranet.app._framework.cqrs.QueryHandler.AbstractQueryHandler;
import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.domain.user.EIUser.EIUserID;
import de.kkendzia.myintranet.domain.user.EIUserRepository;
import org.springframework.stereotype.Service;

public record EIUserIDByUserNameQuery(String userName) implements QueryHandler.Query<EIUserID>
{
    @Service
    public static class Handler extends AbstractQueryHandler<EIUserIDByUserNameQuery, EIUserID>
    {
        private EIUserRepository repository;

        public Handler(EIUserRepository repository)
        {
            super(EIUserIDByUserNameQuery.class);
        }

        @Override
        public EIUserID execute(final EIUserIDByUserNameQuery query)
        {
            final EIUser u = repository.getByUsername(query.userName());
            return u.getId();
        }
    }
}
