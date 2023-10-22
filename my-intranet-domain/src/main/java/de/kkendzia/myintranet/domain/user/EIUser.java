package de.kkendzia.myintranet.domain.user;

import de.kkendzia.myintranet.domain._framework.AbstractEntity;

public class EIUser extends AbstractEntity
{
    private String userName;
    private String firstName = "";
    private String lastName = "";
    private String password;

    public EIUser()
    {
        super();
    }

    public EIUser(long id)
    {
        super(id);
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
