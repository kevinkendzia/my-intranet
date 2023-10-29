package de.kkendzia.myintranet.domain.shared.regulierer;

import de.kkendzia.myintranet.domain._framework.AbstractEntity;

/**
 * Regulierer
 */
public class Regulator extends AbstractEntity
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
