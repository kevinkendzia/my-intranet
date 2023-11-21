package de.kkendzia.myintranet.domain.user;

import de.kkendzia.myintranet.domain._core.AbstractAggregateRoot;
import de.kkendzia.myintranet.domain._core.AbstractID;
import de.kkendzia.myintranet.domain._core.association.MultiAssociation;
import de.kkendzia.myintranet.domain.role.Role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.*;
import static java.util.Objects.requireNonNull;

public class EIUser extends AbstractAggregateRoot<EIUser, EIUser.EIUserID>
{
    private String userName = "";
    private String firstName = "";
    private String lastName = "";
    private String password = "";
    private final List<EIUserAction> favoriteActions = new ArrayList<>();
    private final List<EIUserAction> recentActions = new ArrayList<>();

    // ASSOCIATIONS
    private MultiAssociation<Role, Role.RoleID> roles = new MultiAssociation.MultiAssociationImpl<>(emptyList());

    public EIUser(final String userName, final String firstName, final String lastName, final String password)
    {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public void addFavoriteAction(EIUserAction action)
    {
        requireNonNull(action, "action can't be null!");
        favoriteActions.add(action);
    }

    public void addRecentAction(EIUserAction action)
    {
        requireNonNull(action, "action can't be null!");
        recentActions.add(action);
    }

    // TODO: changePassword(), rename(), etc.


    //region GETTER
    public String getUserName()
    {
        return userName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getPassword()
    {
        return password;
    }

    public List<EIUserAction> getFavoriteActions()
    {
        return unmodifiableList(favoriteActions);
    }

    public List<EIUserAction> getRecentActions()
    {
        return unmodifiableList(recentActions);
    }

    public MultiAssociation<Role, Role.RoleID> getRoles()
    {
        return roles;
    }
    //endregion

    //region TYPES
    public static class EIUserID extends AbstractID
    {
        public EIUserID(final UUID value)
        {
            super(value);
        }

        public EIUserID()
        {
        }
    }
    //endregion
}
