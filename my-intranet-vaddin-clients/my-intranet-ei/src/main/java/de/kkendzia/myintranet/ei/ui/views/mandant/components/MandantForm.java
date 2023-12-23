package de.kkendzia.myintranet.ei.ui.views.mandant.components;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import de.kkendzia.myintranet.app.mandant._shared.MandantSheet;
import de.kkendzia.myintranet.domain._core.elements.AbstractID;
import de.kkendzia.myintranet.domain.mandant.Mandant.MandantID;
import de.kkendzia.myintranet.ei.ui.components.form.AbstractForm;

import java.util.UUID;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.*;

public class MandantForm extends AbstractForm<MandantSheet>
{
    public MandantForm(Binder<MandantSheet> binder)
    {
        super(binder);

        add(
                new TextField(getTranslation(ID)), 2,
                (field, b) -> b
                        .forField(field)
                        .withConverter(
                                UUID::fromString,
                                String::valueOf)
                        .withConverter(
                                MandantID::new,
                                AbstractID::getValue)
                        .bindReadOnly(MandantSheet::getId));
        add(
                new TextField(getTranslation(KEY)),
                (field, b) -> b
                        .forField(field)
                        .asRequired()
                        .bind(
                                MandantSheet::getKey,
                                MandantSheet::setKey));
        add(
                new TextField(getTranslation(NAME)),
                (field, b) -> b
                        .forField(field)
                        .asRequired()
                        .bind(
                                MandantSheet::getName,
                                MandantSheet::setName));
    }
}
