package de.kkendzia.myintranet.ei.ui.views.other.mandant.components;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
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

//        HasObjectValue valueEditor = new HasObjectValue();
//        add(
//                valueEditor,
//                (field, binder) -> binder.forField(field)
//                        .asRequired()
//                        .bind(SettingItem::getValue, SettingItem::setValue));
    }
}
