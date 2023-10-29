package de.kkendzia.myintranet.domain.shared.verband;

import de.kkendzia.myintranet.domain._framework.AbstractEntity;

/**
 * Verband
 */
public class Association extends AbstractEntity
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
