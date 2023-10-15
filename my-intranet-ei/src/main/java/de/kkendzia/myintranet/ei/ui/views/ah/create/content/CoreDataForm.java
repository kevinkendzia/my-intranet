package de.kkendzia.myintranet.ei.ui.views.ah.create.content;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import de.kkendzia.myintranet.domain.shared.Mandant;
import de.kkendzia.myintranet.ei.core.i18n.TranslationKeys;
import de.kkendzia.myintranet.ei.ui.components.form.AbstractForm;
import de.kkendzia.myintranet.ei.ui.views.ah.create.AhCreatePresenter;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.*;

public class CoreDataForm extends AbstractForm<AhCreatePresenter.AhData>
{
    public CoreDataForm(String label)
    {
        super(label);
    }

    @Override
    protected void initForm(FormLayout form, Binder<AhCreatePresenter.AhData> binder)
    {
        RadioButtonGroup<Mandant> rbgMandant = new RadioButtonGroup<>(getTranslation(MANDANT));
        rbgMandant.setItemLabelGenerator(x -> x.name());

        IntegerField txtAhnr = new IntegerField(getTranslation(AHNR));
        TextField txtMatchcode = new TextField(getTranslation(MATCHCODE));
        DatePicker dpEnter = new DatePicker(getTranslation(TranslationKeys.ENTER_DATE));
        ComboBox<?> cboMitgliedsForm = new ComboBox<>(getTranslation(TranslationKeys.MITGLIEDSFORM));
        ComboBox<?> cboVerband = new ComboBox<>(getTranslation(TranslationKeys.VERBAND));
        ComboBox<?> cboRegulierer = new ComboBox<>(getTranslation(TranslationKeys.REGULIERER));

        form.add(rbgMandant, 2);
        form.add(txtAhnr);
        form.add(txtMatchcode);
        form.add(dpEnter);
        form.add(cboMitgliedsForm);
        form.add(cboVerband);
        form.add(cboRegulierer);

        binder
                .forField(rbgMandant)
                .asRequired()
                .bind(
                        AhCreatePresenter.AhData::getMandant,
                        AhCreatePresenter.AhData::setMandant);

        // TODO Bindings
    }
}
