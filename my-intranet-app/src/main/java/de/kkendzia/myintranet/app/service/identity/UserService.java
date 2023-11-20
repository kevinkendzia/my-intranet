package de.kkendzia.myintranet.app.service.identity;

import de.kkendzia.myintranet.app.service._framework.ApplicationService;
import de.kkendzia.myintranet.domain.user.EIUser;
import de.kkendzia.myintranet.domain.user.EIUserAction;
import de.kkendzia.myintranet.domain.user.EIUserDAO;
import org.springframework.stereotype.Service;

@Service
public class UserService implements ApplicationService
{
    private final EIUserDAO eiUserDAO;
    // 19.11.2023 KK TODO:
//    private final EIUserActionDAO eiUserActionDAO;

    public UserService(final EIUserDAO eiUserDAO)
    {
        this.eiUserDAO = eiUserDAO;
    }

    public EIUser loadUserByUsername(String username)
    {
        return eiUserDAO.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Couldn't find user with \"" + username + "\"!"));
    }

    public void addPreviousUserAction(EIUserAction action)
    {
        // 19.11.2023 KK TODO:
    }

    public void addFavoriteUserAction(EIUserAction action)
    {
        // 19.11.2023 KK TODO:
    }
}
