package de.kkendzia.myintranet.app.mandant._shared;

import de.kkendzia.myintranet.domain.mandant.Mandant;
import de.kkendzia.myintranet.domain.mandant.MandantFile;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

import static java.util.stream.Collectors.toSet;

public class MandantSheet
{
    private Mandant.MandantID id;
    private String key;
    private String name;
    private Set<File> files;

    public MandantSheet(final Mandant.MandantID id, final String key, final String name, final Set<File> files)
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
                        .map(f -> new File(f.getId(), f.getFilename(), f.getData()))
                        .collect(toSet()));
    }

    public Mandant.MandantID getId()
    {
        return id;
    }

    public void setId(final Mandant.MandantID id)
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
            MandantFile.MandantFileID id,
            String name,
            byte[] data)
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
            return Objects.equals(id, file.id) && Objects.equals(
                    name,
                    file.name) && Arrays.equals(data, file.data);
        }

        @Override
        public int hashCode()
        {
            int result = Objects.hash(id, name);
            result = 31 * result + Arrays.hashCode(data);
            return result;
        }

        @Override
        public String toString()
        {
            return new StringJoiner(", ", File.class.getSimpleName() + "[", "]")
                    .add("id=" + id)
                    .add("name='" + name + "'")
                    .add("data=" + Arrays.toString(data))
                    .toString();
        }
    }
    //endregion
}
