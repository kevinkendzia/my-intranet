package de.kkendzia.myintranet.ei.ui.views.mandant.components;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import de.kkendzia.myintranet.domain.mandant.Mandant;
import de.kkendzia.myintranet.ei.ui.components.form.AbstractForm;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.*;

public class MandantForm extends AbstractForm<Mandant>
{
    public MandantForm(Binder<Mandant> binder)
    {
        super(binder);

        add(
                new TextField(getTranslation(ID)), 2,
                (field, b) -> b
                        .forField(field)
                        .withConverter(
                                Long::parseLong,
                                String::valueOf)
                        .bindReadOnly(Mandant::getId));
        add(
                new TextField(getTranslation(KEY)),
                (field, b) -> b
                        .forField(field)
                        .asRequired()
                        .bind(
                                Mandant::getKey,
                                Mandant::setKey));
        add(
                new TextField(getTranslation(NAME)),
                (field, b) -> b
                        .forField(field)
                        .asRequired()
                        .bind(
                                Mandant::getName,
                                Mandant::setName));
    }
}
