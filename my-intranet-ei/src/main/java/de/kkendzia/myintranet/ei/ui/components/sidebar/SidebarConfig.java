package de.kkendzia.myintranet.ei.ui.components.sidebar;

import com.vaadin.flow.function.SerializableRunnable;
import com.vaadin.flow.function.SerializableSupplier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public record SidebarConfig(List<SidebarConfigEntry> entries) implements Serializable
{
    public SidebarConfig(Builder builder)
    {
        this(unmodifiableList(builder.entries));
    }

    public record SidebarConfigEntry(String label, SerializableRunnable action)
    {
        // just a record
    }

    public static class Builder
    {
        private List<SidebarConfigEntry> entries = new ArrayList<>();

        public Builder add(SidebarConfigEntry entry)
        {
            entries.add(entry);
            return this;
        }

        public SidebarConfig build()
        {
            return new SidebarConfig(this);
        }
    }

    @FunctionalInterface
    public interface SidebarConfigSupplier extends SerializableSupplier<SidebarConfig>
    {
    }
}
