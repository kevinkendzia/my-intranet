package de.kkendzia.myintranet.domain.ah.mitgliedsform;

import de.kkendzia.myintranet.domain._core.AbstractAggregateRoot;
import de.kkendzia.myintranet.domain._core.AbstractID;

import java.util.UUID;

/**
 * Mitgliedsform
 */
public class MitgliedsForm extends AbstractAggregateRoot<MitgliedsForm, MitgliedsForm.MitgliedsFormID>
{
    private String name;

    public MitgliedsForm()
    {
        super();
    }

    public MitgliedsForm(final String name)
    {
        super();
        this.name = name;
    }

    public MitgliedsForm(final MitgliedsFormID id, final String name)
    {
        super(id);
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    //region TYPES
    public static class MitgliedsFormID extends AbstractID
    {
        public MitgliedsFormID(final UUID value)
        {
            super(value);
        }

        public MitgliedsFormID()
        {
        }
    }
    //endregion
}
