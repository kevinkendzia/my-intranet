package de.kkendzia.myintranet.ei.ui.components.adress;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import de.kkendzia.myintranet.domain.shared.Adress;
import de.kkendzia.myintranet.domain.shared.Country;
import de.kkendzia.myintranet.ei.ui.components.form.AbstractForm;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.*;

public class AdressForm extends AbstractForm<Adress>
{
    public AdressForm()
    {
        this(null);
    }

    public AdressForm(String label)
    {
        super(label);
    }

    @Override
    protected void initForm(
            FormLayout form,
            Binder<Adress> binder)
    {
        TextField txtLine1 = new TextField(getTranslation(ADRESS_LINE1));
        TextField txtLine2 = new TextField(getTranslation(ADRESS_LINE2));
        TextField txtStreet = new TextField(getTranslation(STREET));
        TextField txtZip = new TextField(getTranslation(ZIP));
        TextField txtCity = new TextField(getTranslation(CITY));
        ComboBox<Country> cboCountry = new ComboBox<>(getTranslation(COUNTRY));

        form.add(txtLine1, 2);
        form.add(txtLine2, 2);
        form.add(txtStreet, 2);
        form.add(txtZip, 1);
        form.add(txtCity, 1);
        form.add(cboCountry, 2);

        // TODO Bindings
    }
}
