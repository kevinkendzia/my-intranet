package de.kkendzia.myintranet.app.mandant._shared;

import de.kkendzia.myintranet.domain.mandant.Mandant;
import de.kkendzia.myintranet.domain.mandant.Mandant.MandantID;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

import static java.util.stream.Collectors.toSet;

public class MandantSheet
{
    private MandantID id;
    private String key;
    private String name;
    private Set<File> files;

    public MandantSheet(final MandantID id, final String key, final String name, final Set<File> files)
    {
        this.id = id;
        this.key = key;
        this.name = name;
        this.files = files;
    }

    public MandantSheet(final Mandant mandant)
    {
        this(
                mandant.getId(),
                mandant.getShortName(),
                mandant.getLongName(),
                mandant.getFiles()
                        .stream()
                        .map(f -> new File(f.getFilename(), f.getMimeType(), f.getData()))
                        .collect(toSet()));
    }

    public MandantID getId()
    {
        return id;
    }

    public void setId(final MandantID id)
    {
        this.id = id;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(final String key)
    {
        this.key = key;
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public Set<File> getFiles()
    {
        return files;
    }

    public void setFiles(final Set<File> files)
    {
        this.files = files;
    }

    //region TYPES
    public record File(
            String name,
            String mimeType,
            byte[] preview)
    {
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
            return Objects.equals(name, file.name) && Objects.equals(
                    mimeType,
                    file.mimeType) && Arrays.equals(preview, file.preview);
        }

        @Override
        public int hashCode()
        {
            int result = Objects.hash(name, mimeType);
            result = 31 * result + Arrays.hashCode(preview);
            return result;
        }

        @Override
        public String toString()
        {
            return new StringJoiner(", ", File.class.getSimpleName() + "[", "]")
                    .add("name='" + name + "'")
                    .add("mimeType='" + mimeType + "'")
                    .add("preview=" + Arrays.toString(preview))
                    .toString();
        }
    }
    //endregion
}
