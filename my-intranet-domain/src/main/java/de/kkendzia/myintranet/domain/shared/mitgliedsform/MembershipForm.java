package de.kkendzia.myintranet.domain.shared.mitgliedsform;

import de.kkendzia.myintranet.domain._framework.AbstractEntity;

/**
 * Mitgliedsform
 */
public class MembershipForm extends AbstractEntity
{
    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
