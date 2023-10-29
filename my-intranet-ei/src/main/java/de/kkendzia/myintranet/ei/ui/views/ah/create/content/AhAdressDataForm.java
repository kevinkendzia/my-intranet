package de.kkendzia.myintranet.ei.ui.views.ah.create.content;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import de.kkendzia.myintranet.domain.shared.adress.Country;
import de.kkendzia.myintranet.ei.ui.components.form.AbstractForm;
import de.kkendzia.myintranet.ei.ui.views.ah.create.content.AhAdressDataForm.AhAdressData;

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
        add(txtLine1, 2, (field, binder) -> binder.forField(field).bind(AhAdressData::getLine1, AhAdressData::setLine1));
        add(txtLine2, 2, (field, binder) -> binder.forField(field).bind(AhAdressData::getLine2, AhAdressData::setLine2));
        add(txtStreet, 2, (field, binder) -> binder.forField(field).bind(AhAdressData::getStreet, AhAdressData::setStreet));
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

    public static class AhAdressData
    {
        private String line1;
        private String line2;
        private String street;
        private String zip;
        private String city;
        private Country country;

        public AhAdressData(String line1, String line2, String street, String zip, String city, Country country)
        {
            this.line1 = line1;
            this.line2 = line2;
            this.street = street;
            this.zip = zip;
            this.city = city;
            this.country = country;
        }

        public String getLine1()
        {
            return line1;
        }

        public void setLine1(String line1)
        {
            this.line1 = line1;
        }

        public String getLine2()
        {
            return line2;
        }

        public void setLine2(String line2)
        {
            this.line2 = line2;
        }

        public String getStreet()
        {
            return street;
        }

        public void setStreet(String street)
        {
            this.street = street;
        }

        public String getZip()
        {
            return zip;
        }

        public void setZip(String zip)
        {
            this.zip = zip;
        }

        public String getCity()
        {
            return city;
        }

        public void setCity(String city)
        {
            this.city = city;
        }

        public Country getCountry()
        {
            return country;
        }

        public void setCountry(Country country)
        {
            this.country = country;
        }
    }
}
