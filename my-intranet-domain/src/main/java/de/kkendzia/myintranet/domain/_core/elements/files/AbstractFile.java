package de.kkendzia.myintranet.domain._core.elements.files;

import de.kkendzia.myintranet.domain._core.elements.AbstractAggregateRoot;
import de.kkendzia.myintranet.domain._core.elements.AbstractEntity;
import de.kkendzia.myintranet.domain._core.elements.ID;

import static java.util.Objects.requireNonNull;

public class AbstractFile<A extends AbstractAggregateRoot<A, ?>, I extends ID> extends AbstractEntity<A, I>
{
    private String filename;
    private String mimeType;
    private byte[] data;

    public AbstractFile(final I id, final String filename, final String mimeType, final byte[] data)
    {
        super(id);
        this.filename = requireNonNull(filename, "filename can't be null!");
        this.mimeType = requireNonNull(mimeType, "mimeType can't be null!");
        this.data = requireNonNull(data, "data can't be null!");
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(final String filename)
    {
        this.filename = requireNonNull(filename, "filename can't be null!");
    }

    public String getMimeType()
    {
        return mimeType;
    }

    public void setMimeType(final String mimeType)
    {
        this.mimeType = requireNonNull(mimeType, "mimeType can't be null!");
    }

    public byte[] getData()
    {
        return data;
    }

    public void setData(final byte[] data)
    {
        this.data = requireNonNull(data, "data can't be null!");
    }
}
