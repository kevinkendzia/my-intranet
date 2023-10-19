package de.kkendzia.myintranet.ei.ui.views.other.mandant.components;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import de.kkendzia.myintranet.domain.shared.mandant.Mandant;
import de.kkendzia.myintranet.ei.ui.components.form.AbstractForm;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.ID;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.NAME;

public class MandantForm extends AbstractForm<Mandant>
{
    @Override
    protected void initForm(
            FormLayout form,
            Binder<Mandant> binder)
    {
        TextField txtId = new TextField(getTranslation(ID));
        TextField txtName = new TextField(getTranslation(NAME));

        form.add(txtId);
        form.add(txtName);

        binder
                .forField(txtId)
                .withConverter(
                        Long::parseLong,
                        String::valueOf)
                .bindReadOnly(Mandant::getId);
        binder
                .forField(txtName)
                .asRequired()
                .bind(
                        Mandant::getName,
                        Mandant::setName);
    }
}
