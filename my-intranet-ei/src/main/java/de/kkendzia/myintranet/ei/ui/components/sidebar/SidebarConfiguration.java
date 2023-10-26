package de.kkendzia.myintranet.ei.ui.components.sidebar;

import com.vaadin.flow.function.SerializableRunnable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public record SidebarConfiguration(SidebarHeader header, List<SidebarContent> content) implements Serializable
{
    public SidebarConfiguration(Builder builder)
    {
        this(builder.header, unmodifiableList(builder.actions));
    }

    public interface SidebarContent
    {

    }
    public record SidebarAction(String label, SerializableRunnable action) implements SidebarContent
    {
        // just a record
    }
    public record SidebarText(String text) implements SidebarContent
    {
        // just a record
    }

    public record SidebarHeader(String title, String description, List<SidebarAction> actions)
    {
        // just a record
    }

    public static class Builder
    {
        private SidebarHeader header;
        private final List<SidebarContent> actions = new ArrayList<>();

        public Builder header(SidebarHeader header)
        {
            this.header = header;
            return this;
        }

        public Builder action(SidebarAction entry)
        {
            actions.add(entry);
            return this;
        }

        public Builder text(SidebarText entry)
        {
            actions.add(entry);
            return this;
        }

        public SidebarConfiguration build()
        {
            return new SidebarConfiguration(this);
        }
    }

}
