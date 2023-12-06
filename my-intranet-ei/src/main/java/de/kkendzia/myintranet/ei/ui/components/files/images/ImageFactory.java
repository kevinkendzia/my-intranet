package de.kkendzia.myintranet.ei.ui.components.files.images;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import de.kkendzia.myintranet.ei.core.constants.EITheme;

public final class ImageFactory
{
    private ImageFactory()
    {
        // No Instance!
    }

    public static Image create(EITheme.Image source)
    {
        final var img = new Image(source.getPath(), source.getName());
        img.addClassName(Overflow.HIDDEN);
        img.addClassName(Padding.XLARGE);
        img.getStyle().set("object-fit", "contain");
        return img;
    }
}
