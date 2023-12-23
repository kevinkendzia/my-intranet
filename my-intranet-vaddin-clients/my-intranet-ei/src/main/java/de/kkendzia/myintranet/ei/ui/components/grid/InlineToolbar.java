package de.kkendzia.myintranet.ei.ui.components.grid;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.function.SerializableRunnable;

import java.util.ArrayList;
import java.util.List;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.EDIT;

public class InlineToolbar extends Composite<HorizontalLayout>
{
    public InlineToolbar(List<InlineAction> actions)
    {
        HorizontalLayout root = getContent();
        root.setPadding(false);
        actions.forEach(a -> root.add(new Button(a.label(), a.icon(), e -> a.action().run())));
    }

    public InlineToolbar(Builder builder)
    {
        this(builder.actions);
    }

    public record InlineAction(
            String label,
            Component icon,
            SerializableRunnable action)
    {
        public static <T> InlineAction editAction(Grid<T> grid, T item)
        {
            return new InlineAction(
                    grid.getTranslation(EDIT),
                    VaadinIcon.EDIT.create(),
                    () ->
                    {
                        Editor<T> editor = grid.getEditor();
                        if (editor.isOpen())
                        {
                            editor.cancel();
                        }
                        editor.editItem(item);
                    });
        }
    }

    public static class Builder
    {
        private List<InlineAction> actions = new ArrayList<>();

        public Builder action(InlineAction action)
        {
            actions.add(action);
            return this;
        }

        public Builder action(String label, Component icon, SerializableRunnable action)
        {
            return action(new InlineAction(label, icon, action));
        }

        public InlineToolbar build()
        {
            return new InlineToolbar(this);
        }
    }
}
