package de.kkendzia.myintranet.ei.ui.views.ah._shared.components.forms;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import de.kkendzia.myintranet.app.ah._shared.AhSheet.CoreSection;
import de.kkendzia.myintranet.app.mandant.queries.ListMandanten.MandantItem;
import de.kkendzia.myintranet.domain.ah.Ah.Ahnr;
import de.kkendzia.myintranet.domain.mandant.Mandant.MandantID;
import de.kkendzia.myintranet.ei.core.i18n.TranslationKeys;
import de.kkendzia.myintranet.ei.ui.components.form.AbstractForm;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.AhKeys.AHNR;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.MATCHCODE;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.MandantKeys.MANDANT;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.ValidationKeys.REQUIRED;
import static de.kkendzia.myintranet.ei.utils.DataProviderUtils.emptyDataProvider;

public class AhCoreDataForm extends AbstractForm<CoreSection>
{
    private final RadioButtonGroup<MandantItem> rbgMandant = new RadioButtonGroup<>(getTranslation(MANDANT));
    private DataProvider<MandantItem, Void> dpMandant = emptyDataProvider();

    public AhCoreDataForm(Binder<CoreSection> binder)
    {
        super(binder);
        rbgMandant.setItemLabelGenerator(MandantItem::name);

        add(
                rbgMandant, 2,
                (field, b) -> b
                        .forField(field)
                        .asRequired(getTranslation(REQUIRED))
                        .withConverter(
                                mandantItem -> mandantItem,
                                mandantItem -> mandantItem)
                        .bind(CoreSection::getMandant, CoreSection::setMandant));
        IntegerField txtAhnr = new IntegerField(getTranslation(AHNR));
        add(
                txtAhnr,
                (field, b) -> b
                        .forField(field)
                        .asRequired(getTranslation(REQUIRED))
                        .withConverter(Ahnr::new, Ahnr::value)
                        .bind(CoreSection::getAhnr, CoreSection::setAhnr));
        TextField txtMatchcode = new TextField(getTranslation(MATCHCODE));
        add(
                txtMatchcode,
                (field, b) -> b
                        .forField(field)
                        .asRequired(getTranslation(REQUIRED))
                        .bind(CoreSection::getMatchcode, CoreSection::setMatchcode));
        DatePicker dpEnter = new DatePicker(getTranslation(TranslationKeys.ENTER_DATE));
        add(
                dpEnter,
                (field, b) -> b
                        .forField(field)
                        .bind(CoreSection::getEnterDate, CoreSection::setEnterDate));
    }

    public void setMandantItems(Collection<MandantItem> mandantItems)
    {
        this.dpMandant = DataProvider.ofCollection(mandantItems).withConfigurableFilter();
        rbgMandant.setItems(dpMandant);
    }

    private List<MandantItem> getMandantItems()
    {
        return dpMandant.fetch(new Query<>()).toList();
    }

    public Optional<MandantItem> findMandantItemById(MandantID id)
    {
        return getMandantItems().stream().filter(m -> Objects.equals(m.id(), id)).findFirst();
    }
}
