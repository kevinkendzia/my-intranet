package de.kkendzia.myintranet.ei.ui.views.ah._shared.components.forms;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import de.kkendzia.myintranet.domain.country.Country;
import de.kkendzia.myintranet.ei.ui.components.form.AbstractForm;
import de.kkendzia.myintranet.app.ah.commands.AhAdressData;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.*;
import static java.util.Objects.requireNonNull;

public class AhAdressDataForm extends AbstractForm<AhAdressData>
{
    private final TextField txtLine1 = new TextField(getTranslation(ADRESS_LINE1));
    private final TextField txtLine2 = new TextField(getTranslation(ADRESS_LINE2));
    private final TextField txtStreet = new TextField(getTranslation(STREET));
    private final TextField txtZip = new TextField(getTranslation(ZIP));
    private final TextField txtCity = new TextField(getTranslation(CITY));
    private final ComboBox<Country> cboCountry = new ComboBox<>(getTranslation(COUNTRY));
    private Collection<Country> countryItems;

    public AhAdressDataForm()
    {
        add(
                txtLine1,
                2,
                (field, binder) -> binder.forField(field).bind(AhAdressData::getLine1, AhAdressData::setLine1));
        add(
                txtLine2,
                2,
                (field, binder) -> binder.forField(field).bind(AhAdressData::getLine2, AhAdressData::setLine2));
        add(
                txtStreet,
                2,
                (field, binder) -> binder.forField(field).bind(AhAdressData::getStreet, AhAdressData::setStreet));
        add(txtZip, (field, binder) -> binder.forField(field).bind(AhAdressData::getZip, AhAdressData::setZip));
        add(txtCity, (field, binder) -> binder.forField(field).bind(AhAdressData::getCity, AhAdressData::setCity));
        add(
                cboCountry,
                2,
                (field, binder) -> binder.forField(field)
                        .bind(AhAdressData::getCountry, AhAdressData::setCountry));
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
