package de.kkendzia.myintranet.domain.mandant;

import de.kkendzia.myintranet.domain._core.elements.AbstractID;
import de.kkendzia.myintranet.domain._core.elements.files.AbstractFile;

import java.util.UUID;

public class MandantFile extends AbstractFile<Mandant, MandantFile.MandantFileID>
{
    public MandantFile(final MandantFileID id, final String filename, final String mimeType, final byte[] data)
    {
        super(id, filename, mimeType, data);
    }

    public MandantFile(final String filename, final String mimeType, final byte[] data)
    {
        this(new MandantFileID(), filename, mimeType, data);
    }

    public static class MandantFileID extends AbstractID
    {
        public MandantFileID(final UUID value)
        {
            super(value);
        }

        public MandantFileID(final String value)
        {
            super(value);
        }

        public MandantFileID()
        {
        }
    }
}
