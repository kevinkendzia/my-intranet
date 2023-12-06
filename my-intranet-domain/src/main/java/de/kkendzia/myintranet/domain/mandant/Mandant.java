package de.kkendzia.myintranet.domain.mandant;

import de.kkendzia.myintranet.domain._core.elements.AbstractAggregateRoot;
import de.kkendzia.myintranet.domain._core.elements.AbstractID;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public final class Mandant extends AbstractAggregateRoot<Mandant, Mandant.MandantID>
{
    public static final String PROPERTY_NAME = "name";

    private String shortName;
    private String longName;
    private Set<MandantFile> files;

    public Mandant(final MandantID id, final String shortName, final String longName, final Set<MandantFile> files)
    {
        super(id);
        this.shortName = requireNonNull(shortName, "shortName can't be null!");
        this.longName = requireNonNull(longName, "longName can't be null!");
        this.files = requireNonNull(files, "files can't be null!");
    }

    public Mandant(
            MandantID id,
            String shortName,
            String longName)
    {
        this(id, shortName, longName, new HashSet<>());
    }

    public Mandant(final String shortName, final String longName)
    {
        this(new MandantID(), shortName, longName, new HashSet<>());
    }

    public String getShortName()
    {
        return shortName;
    }

    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }

    public String getLongName()
    {
        return longName;
    }

    public void setLongName(String longName)
    {
        this.longName = longName;
    }

    public Set<MandantFile> getFiles()
    {
        return files;
    }

    public void setFiles(final Set<MandantFile> files)
    {
        this.files = files;
    }

    //region TYPES
    public static class MandantID extends AbstractID
    {
        public MandantID(final UUID value)
        {
            super(value);
        }

        public MandantID(final String value)
        {
            super(value);
        }

        public MandantID()
        {
            super();
        }
    }
    //endregion
}
