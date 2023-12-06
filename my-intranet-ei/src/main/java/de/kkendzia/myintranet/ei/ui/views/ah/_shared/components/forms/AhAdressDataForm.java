package de.kkendzia.myintranet.ei.ui.views.ah._shared.components.forms;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import de.kkendzia.myintranet.app.ah._shared.AhSheet;
import de.kkendzia.myintranet.domain.country.Country;
import de.kkendzia.myintranet.ei.ui.components.form.AbstractForm;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.AdressKeys;
import static java.util.Objects.requireNonNull;

public class AhAdressDataForm extends AbstractForm<AhSheet.AdressSection>
{
    // 05.12.2023 KK TODO: replace Country with DTO
    private final ComboBox<Country> cboCountry = new ComboBox<>(getTranslation(AdressKeys.COUNTRY));
    private Collection<Country> countryItems;

    public AhAdressDataForm(Binder<AhSheet.AdressSection> binder)
    {
        super(binder);
        TextField txtLine1 = new TextField(getTranslation(AdressKeys.ADRESS_LINE1));
        add(
                txtLine1,
                2,
                (field, b) -> b.forField(field)
                        .bind(AhSheet.AdressSection::getLine1, AhSheet.AdressSection::setLine1));
        TextField txtLine2 = new TextField(getTranslation(AdressKeys.ADRESS_LINE2));
        add(
                txtLine2,
                2,
                (field, b) -> b.forField(field)
                        .bind(AhSheet.AdressSection::getLine2, AhSheet.AdressSection::setLine2));
        TextField txtStreet = new TextField(getTranslation(AdressKeys.STREET));
        add(
                txtStreet,
                2,
                (field, b) -> b.forField(field)
                        .bind(AhSheet.AdressSection::getStreet, AhSheet.AdressSection::setStreet));
        TextField txtZip = new TextField(getTranslation(AdressKeys.ZIP));
        add(
                txtZip,
                (field, b) -> b.forField(field)
                        .bind(AhSheet.AdressSection::getZip, AhSheet.AdressSection::setZip));
        TextField txtCity = new TextField(getTranslation(AdressKeys.CITY));
        add(
                txtCity,
                (field, b) -> b.forField(field)
                        .bind(AhSheet.AdressSection::getCity, AhSheet.AdressSection::setCity));
        add(
                cboCountry,
                2,
                (field, b) -> b.forField(field)
                        .bind(AhSheet.AdressSection::getCountry, AhSheet.AdressSection::setCountry));
    }

    public void setCountryItems(Collection<Country> items)
    {
        this.countryItems = requireNonNull(items, "items can't be null!");
        cboCountry.setItems(countryItems);
    }

    private Collection<Country> getCountryItems()
    {
        return countryItems;
    }

    public Optional<Country> findCountryItemById(long id)
    {
        return getCountryItems().stream().filter(c -> Objects.equals(c.getId(), id)).findFirst();
    }

}
