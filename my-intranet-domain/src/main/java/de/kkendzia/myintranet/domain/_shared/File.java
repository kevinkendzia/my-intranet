package de.kkendzia.myintranet.domain._shared;

import de.kkendzia.myintranet.domain._core.elements.AbstractValueObject;

import java.util.Arrays;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class File extends AbstractValueObject
{
    private final String filename;
    private final String mimeType;
    private final byte[] data;

    public File(final String filename, final String mimeType, final byte[] data)
    {
        this.filename = requireNonNull(filename, "filename can't be null!");
        this.mimeType = requireNonNull(mimeType, "mimeType can't be null!");
        this.data = requireNonNull(data, "data can't be null!");
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        final File file = (File) o;
        return Objects.equals(getFilename(), file.getFilename()) && Objects.equals(
                getMimeType(),
                file.getMimeType()) && Arrays.equals(getData(), file.getData());
    }

    @Override
    public int hashCode()
    {
        int result = Objects.hash(getFilename(), getMimeType());
        result = 31 * result + Arrays.hashCode(getData());
        return result;
    }

    public String getFilename()
    {
        return filename;
    }

    public String getMimeType()
    {
        return mimeType;
    }

    public byte[] getData()
    {
        return data;
    }
}
