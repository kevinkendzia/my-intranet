package de.kkendzia.myintranet.ei.core.view.toolbar;

import com.vaadin.flow.function.SerializableRunnable;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public record ToolbarConfig(List<ToolbarConfigEntry> entries)
{
    public ToolbarConfig(Builder builder)
    {
        this(unmodifiableList(builder.entries));
    }

    public record ToolbarConfigEntry(String label, SerializableRunnable action)
    {
        // just a record
    }

    public static class Builder
    {
        private List<ToolbarConfigEntry> entries = new ArrayList<>();

        public Builder add(ToolbarConfigEntry entry)
        {
            entries.add(entry);
            return this;
        }

        public ToolbarConfig build()
        {
            return new ToolbarConfig(this);
        }
    }
}
