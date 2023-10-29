package de.kkendzia.myintranet.ei.ui.views.ah.create.content;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import de.kkendzia.myintranet.domain.ah.Ah.Ahnr;
import de.kkendzia.myintranet.domain.shared.mandant.Mandant;
import de.kkendzia.myintranet.ei.core.i18n.TranslationKeys;
import de.kkendzia.myintranet.ei.ui.components.form.AbstractForm;
import de.kkendzia.myintranet.ei.ui.views.ah.create.content.AhCoreDataForm.AhCoreData;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.*;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.Validation.REQUIRED;
import static de.kkendzia.myintranet.ei.core.utils.DataProviderUtil.emptyDataProvider;

public class AhCoreDataForm extends AbstractForm<AhCoreData>
{
    private final RadioButtonGroup<Mandant> rbgMandant = new RadioButtonGroup<>(getTranslation(MANDANT));
    private final IntegerField txtAhnr = new IntegerField(getTranslation(AHNR));
    private final TextField txtMatchcode = new TextField(getTranslation(MATCHCODE));
    private final DatePicker dpEnter = new DatePicker(getTranslation(TranslationKeys.ENTER_DATE));

    private DataProvider<Mandant, Void> dpMandant = emptyDataProvider();

    public AhCoreDataForm()
    {
        rbgMandant.setItemLabelGenerator(Mandant::getName);

        add(
                rbgMandant, 2,
                (field, binder) -> binder.forField(field)
                        .asRequired(getTranslation(REQUIRED))
                        .bind(AhCoreData::getMandant, AhCoreData::setMandant));
        add(
                txtAhnr,
                (field, binder) -> binder.forField(field)
                        .asRequired(getTranslation(REQUIRED))
                        .withConverter(Ahnr::new, Ahnr::value)
                        .bind(AhCoreData::getAhnr, AhCoreData::setAhnr));
        add(
                txtMatchcode,
                (field, binder) -> binder.forField(field)
                        .asRequired(getTranslation(REQUIRED))
                        .bind(AhCoreData::getMatchcode, AhCoreData::setMatchcode));
        add(
                dpEnter,
                (field, binder) -> binder.forField(field)
                        .bind(AhCoreData::getEnterDate, AhCoreData::setEnterDate));
    }

    public void setMandantItems(Collection<Mandant> mandantItems)
    {
        this.dpMandant = DataProvider.ofCollection(mandantItems).withConfigurableFilter();
        rbgMandant.setItems(dpMandant);
    }

    private List<Mandant> getMandantItems()
    {
        return dpMandant.fetch(new Query<>()).toList();
    }

    public Optional<Mandant> findMandantItemById(long id)
    {
        return getMandantItems().stream().filter(m -> Objects.equals(m.getId(), id)).findFirst();
    }

    public static class AhCoreData
    {
        private Ahnr ahnr;
        private String matchcode;
        private Mandant mandant;
        private LocalDate enterDate;

        public AhCoreData(
                Ahnr ahnr,
                String matchcode,
                Mandant mandant,
                LocalDate enterDate)
        {
            this.ahnr = ahnr;
            this.matchcode = matchcode;
            this.mandant = mandant;
            this.enterDate = enterDate;
        }

        public Ahnr getAhnr()
        {
            return ahnr;
        }

        public void setAhnr(Ahnr ahnr)
        {
            this.ahnr = ahnr;
        }

        public String getMatchcode()
        {
            return matchcode;
        }

        public void setMatchcode(String matchcode)
        {
            this.matchcode = matchcode;
        }

        public Mandant getMandant()
        {
            return mandant;
        }

        public void setMandant(Mandant mandant)
        {
            this.mandant = mandant;
        }

        public LocalDate getEnterDate()
        {
            return enterDate;
        }

        public void setEnterDate(LocalDate enterDate)
        {
            this.enterDate = enterDate;
        }
    }
}