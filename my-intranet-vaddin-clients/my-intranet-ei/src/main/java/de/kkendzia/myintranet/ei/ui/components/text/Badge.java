package de.kkendzia.myintranet.ei.ui.components.text;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.shared.HasThemeVariant;
import com.vaadin.flow.component.shared.ThemeVariant;

public class Badge extends Composite<Span> implements HasText, HasThemeVariant<Badge.BadgeVariant>
{
    public Badge(String text)
    {
        Span root = getContent();
        root.getElement().getThemeList().add("badge");
        root.setText(text);
    }

    public enum BadgeVariant implements ThemeVariant
    {
        PRIMARY("primary"),
        ERROR("error"),
        SUCCESS("success"),
        CONTRAST("contrast");

        private String variantName;

        BadgeVariant(String variantName)
        {
            this.variantName = variantName;
        }

        @Override
        public String getVariantName()
        {
            return variantName;
        }
    }
}
