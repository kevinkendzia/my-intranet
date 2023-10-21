package de.kkendzia.myintranet.ei.ui.views.other.mandant.components;

import com.vaadin.flow.component.textfield.TextField;
import de.kkendzia.myintranet.domain.shared.mandant.Mandant;
import de.kkendzia.myintranet.ei.ui.components.form.AbstractForm;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.*;

public class MandantForm extends AbstractForm<Mandant>
{
    public MandantForm()
    {
        add(
                new TextField(getTranslation(ID)), 2,
                (field, binder) -> binder
                        .forField(field)
                        .withConverter(
                                Long::parseLong,
                                String::valueOf)
                        .bindReadOnly(Mandant::getId));
        add(
                new TextField(getTranslation(KEY)),
                (field, binder) -> binder
                        .forField(field)
                        .asRequired()
                        .bind(
                                Mandant::getKey,
                                Mandant::setKey));
        add(
                new TextField(getTranslation(NAME)),
                (field, binder) -> binder
                        .forField(field)
                        .asRequired()
                        .bind(
                                Mandant::getName,
                                Mandant::setName));
    }
}
