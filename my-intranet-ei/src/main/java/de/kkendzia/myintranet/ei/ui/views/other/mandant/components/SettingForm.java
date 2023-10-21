package de.kkendzia.myintranet.ei.ui.views.other.mandant.components;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.textfield.TextField;
import de.kkendzia.myintranet.ei.core.i18n.TranslationKeys;
import de.kkendzia.myintranet.ei.ui.components.form.AbstractForm;
import de.kkendzia.myintranet.ei.ui.views.other.mandant.detail.MandantDetailPresenter.SettingItem;

import java.util.List;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.NAME;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.TYPE;

public class SettingForm extends AbstractForm<SettingItem>
{
    public SettingForm()
    {
        add(
                new TextField(getTranslation(NAME)),
                (field, binder) -> binder.forField(field)
                        .asRequired()
                        .bind(SettingItem::getName, SettingItem::setName));

        //   TODO: Implement!
        ComboBox<Class<?>> cboType = new ComboBox<>(getTranslation(TYPE));
        cboType.setItems(List.of(String.class, Integer.class));

        add(
                cboType,
                (field, binder) -> binder.forField(field)
                        .asRequired()
                        .bind(SettingItem::getType, SettingItem::setType));

        add(
                new ValueEditor(),
                (field, binder) -> binder.forField(field)
                        .asRequired()
                        .bind(SettingItem::getValue, SettingItem::setValue));
    }

    // 21.10.2023 KK TODO: replace with converter?
    public static class ValueEditor extends CustomField<Object>
    {
        private TextField txtValue = new TextField(getTranslation(TranslationKeys.VALUE));

        @Override
        protected Object generateModelValue()
        {
            return txtValue.getValue();
        }

        @Override
        protected void setPresentationValue(Object newPresentationValue)
        {
            txtValue.setValue(String.valueOf(newPresentationValue));
        }
    }
}
