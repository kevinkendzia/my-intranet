package de.kkendzia.myintranet.ei.ui.components.toolbar;

import com.vaadin.flow.function.SerializableRunnable;
import com.vaadin.flow.function.SerializableSupplier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;

public record ToolbarConfiguration(
        String title,
        List<ToolbarAction> actions) implements Serializable
{
    public ToolbarConfiguration
    {
        requireNonNull(title, "title can't be null!");
        requireNonNull(actions, "actions can't be null!");
    }

    public ToolbarConfiguration(String title)
    {
        this(title, emptyList());
    }

    public ToolbarConfiguration(Builder builder)
    {
        this(builder.title, builder.actions);
    }

    public record ToolbarAction(
            String label,
            SerializableRunnable action)
    {
        // just a record
    }

    public static class Builder
    {
        private String title;
        private List<ToolbarAction> actions = new ArrayList<>();

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

        public Builder action(
                String label,
                SerializableRunnable action)
        {
            actions.add(new ToolbarAction(label, action));
            return this;
        }

        public Builder actions(List<ToolbarAction> actions)
        {
            this.actions = actions;
            return this;
        }

        public Builder config(ToolbarConfigSupplier configSupplier)
        {
            ToolbarConfiguration config = configSupplier.get();
            return config(config);
        }
        public Builder config(ToolbarConfiguration config)
        {
            if(config == null)
            {
                return this;
            }

            if(title != null && config.title() != null && !Objects.equals(title, config.title()))
            {
                throw new IllegalStateException("Can't merge ToolbarConfigs with different titles! \"%s\" != \"%s\"".formatted(title, config.title()));
            }
            actions.addAll(config.actions());

            return this;
        }

        public ToolbarConfiguration build()
        {
            return new ToolbarConfiguration(this);
        }
    }

    @FunctionalInterface
    public interface ToolbarConfigSupplier extends SerializableSupplier<ToolbarConfiguration>
    {
    }
}
