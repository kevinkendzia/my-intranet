package de.kkendzia.myintranet.ei.utils;

import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;

public final class ResourceUtils
{
    private ResourceUtils()
    {
        // No Instance!
    }

    public static StreamResource streamResource(String name, byte[] data)
    {
        return new StreamResource(name, () -> new ByteArrayInputStream(data));
    }
}
