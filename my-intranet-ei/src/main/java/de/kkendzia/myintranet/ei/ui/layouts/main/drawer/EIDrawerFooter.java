package de.kkendzia.myintranet.ei.ui.layouts.main.drawer;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.ThemableLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.boot.info.BuildProperties;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class EIDrawerFooter extends Composite<Footer> implements ThemableLayout
{
    public EIDrawerFooter(BuildProperties buildProperties)
    {
        final var vlContent = new VerticalLayout();
        vlContent.setSpacing(false);
        vlContent.add(new Span(buildProperties.getGroup()));
        vlContent.add(new Span(buildProperties.getArtifact()));
        vlContent.add(new Span(format(buildProperties.getTime())));

        final var details = new Details("Version " + buildProperties.getVersion());
        details.setContent(vlContent);

        Footer root = getContent();
        root.add(details);
    }

    private static String format(Instant instant)
    {
        final var locale = VaadinSession.getCurrent().getLocale();
        final var formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(locale);
        return
                instant
                        .atZone(ZoneId.systemDefault())
                        .format(formatter);
    }
}
