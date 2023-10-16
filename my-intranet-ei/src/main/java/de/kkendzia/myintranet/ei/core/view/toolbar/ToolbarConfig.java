package de.kkendzia.myintranet.ei.core.view.toolbar;

import com.vaadin.flow.function.SerializableRunnable;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public record ToolbarConfig(String title, List<ToolbarConfigEntry> entries)
{
    public ToolbarConfig(Builder builder)
    {
        this(builder.title, unmodifiableList(builder.entries));
    }

    public record ToolbarConfigEntry(String label, SerializableRunnable action)
    {
        // just a record
    }

    public static class Builder
    {
        private String title;
        private List<ToolbarConfigEntry> entries = new ArrayList<>();

        public Builder title(String title)
        {
            this.title = title;
            return this;
        }

        public Builder add(ToolbarConfigEntry entry)
        {
            entries.add(entry);
            return this;
        }
        public Builder action(String label, SerializableRunnable action)
        {
            entries.add(new ToolbarConfigEntry(label, action));
            return this;
        }

        public ToolbarConfig build()
        {
            return new ToolbarConfig(this);
        }
    }
}
