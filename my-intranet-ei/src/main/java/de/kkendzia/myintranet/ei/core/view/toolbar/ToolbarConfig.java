package de.kkendzia.myintranet.ei.core.view.toolbar;

import com.vaadin.flow.function.SerializableRunnable;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public record ToolbarConfig(String title, List<ToolbarAction> actions)
{
    public ToolbarConfig(Builder builder)
    {
        this(builder.title, unmodifiableList(builder.actions));
    }

    public record ToolbarAction(String label, SerializableRunnable action)
    {
        // just a record
    }

    public static class Builder
    {
        private String title;
        private final List<ToolbarAction> actions = new ArrayList<>();

        public Builder title(String title)
        {
            this.title = title;
            return this;
        }

        public Builder action(ToolbarAction action)
        {
            actions.add(action);
            return this;
        }
        public Builder action(String label, SerializableRunnable action)
        {
            actions.add(new ToolbarAction(label, action));
            return this;
        }

        public ToolbarConfig build()
        {
            return new ToolbarConfig(this);
        }
    }
}
