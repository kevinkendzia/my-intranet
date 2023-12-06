package de.kkendzia.myintranet.domain._shared;

public class ImageFile extends File
{
    private final byte[] preview;

    public ImageFile(final String filename, final String mimeType, final byte[] data, byte[] preview)
    {
        // 06.12.2023 KK TODO: Check if mimetype is image?!
        super(filename, mimeType, data);
        this.preview = preview;
    }

    public byte[] getPreview()
    {
        return preview;
    }
}
