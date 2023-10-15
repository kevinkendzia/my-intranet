package de.kkendzia.myintranet.ei.core.view.sidebar;

import com.vaadin.flow.function.SerializableRunnable;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public record SidebarConfig(List<SidebarConfigEntry> entries)
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
}
