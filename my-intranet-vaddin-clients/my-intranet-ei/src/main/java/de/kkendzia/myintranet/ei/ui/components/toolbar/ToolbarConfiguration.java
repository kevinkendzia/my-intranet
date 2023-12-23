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
        String preTitle,
        String title,
        String subTitle,
        List<ToolbarBadge> badges,
        List<ToolbarAction> actions) implements Serializable
{
    public ToolbarConfiguration
    {
        requireNonNull(title, "title can't be null!");
        requireNonNull(actions, "actions can't be null!");
    }

    public ToolbarConfiguration(String title)
    {
        this(null, title, null, emptyList(), emptyList());
    }

    public ToolbarConfiguration(Builder builder)
    {
        this(builder.preTitle, builder.title, builder.subTitle, builder.badges, builder.actions);
    }

    public record ToolbarAction(
            String label,
            SerializableRunnable action)
    {
        // just a record
    }

    public record ToolbarBadge(
            String label,
            SerializableRunnable action)
    {
        // just a record
    }

    public static class Builder
    {
        private String preTitle = "";
        private String title = "";
        private String subTitle = "";
        private List<ToolbarBadge> badges = new ArrayList<>();
        private List<ToolbarAction> actions = new ArrayList<>();

        public Builder preTitle(String preTitle)
        {
            this.preTitle = preTitle;
            return this;
        }

        public Builder title(String title)
        {
            this.title = title;
            return this;
        }

        public Builder subTitle(String subTitle)
        {
            this.subTitle = subTitle;
            return this;
        }

        public Builder badge(ToolbarBadge badge)
        {
            badges.add(badge);
            return this;
        }

        public Builder badge(
                String label,
                SerializableRunnable action)
        {
            badges.add(new ToolbarBadge(label, action));
            return this;
        }

        public Builder badge(String label)
        {
            badges.add(new ToolbarBadge(label, null));
            return this;
        }

        public Builder badges(List<ToolbarBadge> badges)
        {
            this.badges = badges;
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
            if (config == null)
            {
                return this;
            }

            if (title != null && config.title() != null && !Objects.equals(title, config.title()))
            {
                throw new IllegalStateException("Can't merge ToolbarConfigs with different titles! \"%s\" != \"%s\"".formatted(
                        title,
                        config.title()));
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
