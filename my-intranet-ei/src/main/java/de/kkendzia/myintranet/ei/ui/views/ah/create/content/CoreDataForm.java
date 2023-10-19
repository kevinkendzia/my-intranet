package de.kkendzia.myintranet.ei.ui.views.ah.create.content;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import de.kkendzia.myintranet.domain.ah.Ah;
import de.kkendzia.myintranet.domain.shared.mandant.Mandant;
import de.kkendzia.myintranet.ei.core.i18n.TranslationKeys;
import de.kkendzia.myintranet.ei.ui.components.form.AbstractForm;
import de.kkendzia.myintranet.ei.ui.views.ah.create.AhCreatePresenter.AhData;

import java.util.Collection;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.*;

public class CoreDataForm extends AbstractForm<AhData>
{
    private RadioButtonGroup<Mandant> rbgMandant;

    public CoreDataForm(String label)
    {
        super(label);
    }

    @Override
    protected void initForm(
            FormLayout form,
            Binder<AhData> binder)
    {
        rbgMandant = new RadioButtonGroup<>(getTranslation(MANDANT));
        rbgMandant.setItemLabelGenerator(x -> x.getName());

        IntegerField txtAhnr = new IntegerField(getTranslation(AHNR));
        TextField txtMatchcode = new TextField(getTranslation(MATCHCODE));
        DatePicker dpEnter = new DatePicker(getTranslation(TranslationKeys.ENTER_DATE));
//        ComboBox<?> cboMitgliedsForm = new ComboBox<>(getTranslation(TranslationKeys.MITGLIEDSFORM));
//        ComboBox<?> cboVerband = new ComboBox<>(getTranslation(TranslationKeys.VERBAND));
//        ComboBox<?> cboRegulierer = new ComboBox<>(getTranslation(TranslationKeys.REGULIERER));

        form.add(rbgMandant, 2);
        form.add(txtAhnr);
        form.add(txtMatchcode);
        form.add(dpEnter);
//        form.add(cboMitgliedsForm);
//        form.add(cboVerband);
//        form.add(cboRegulierer);

        binder
                .forField(rbgMandant)
                .asRequired()
                .bind(
                        AhData::getMandant,
                        AhData::setMandant);

        binder
                .forField(txtAhnr)
                .asRequired()
                .withConverter(
                        Ah.Ahnr::new,
                        Ah.Ahnr::value)
                .bind(
                        AhData::getAhnr,
                        AhData::setAhnr);

        binder
                .forField(txtMatchcode)
                .asRequired()
                .bind(
                        AhData::getMatchcode,
                        AhData::setMatchcode);

        binder
                .forField(dpEnter)
                .bind(
                        AhData::getEnterDate,
                        AhData::setEnterDate);

        // TODO Bindings
    }

    public void setMandantItems(Collection<Mandant> mandantItems)
    {
        rbgMandant.setItems(mandantItems);
    }
}
