package de.kkendzia.myintranet.ei.ui.views.other.mandant.detail.pages;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import de.kkendzia.myintranet.ei.core.i18n.TranslationKeys;
import de.kkendzia.myintranet.ei.core.view.page.AbstractLazyPage;
import de.kkendzia.myintranet.ei.ui.views.other.mandant.components.SettingForm;
import de.kkendzia.myintranet.ei.ui.views.other.mandant.detail.MandantDetailPresenter;
import de.kkendzia.myintranet.ei.ui.views.other.mandant.detail.MandantDetailPresenter.SettingItem;
import de.kkendzia.myintranet.ei.ui.views.other.mandant.detail.MandantDetailPresenter.SettingsFilter;

import java.time.LocalDate;
import java.util.List;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.*;
import static de.kkendzia.myintranet.ei.core.utils.GridColumnFactory.*;

public class MandantSettingsPage extends AbstractLazyPage<VerticalLayout> implements MandantDetailPage
{
    private Grid<SettingItem> grid = new Grid<>();

    private final MandantDetailPresenter presenter;
    private final ConfigurableFilterDataProvider<SettingItem, Void, SettingsFilter> dataProvider;

    public MandantSettingsPage(MandantDetailPresenter presenter)
    {
        this.presenter = presenter;
        this.dataProvider = presenter.createSettingsDataProvider().withConfigurableFilter();

//        setToolbarConfig(
//                new ToolbarConfig.Builder()
//                        .action(
//                                getTranslation(SAVE) + " " + getTranslation(SETTINGS),
//                                () -> )
//                        .build());
        initUI();
    }

    private void initUI()
    {
        TextField txtFilter = new TextField();
        txtFilter.setPlaceholder(getTranslation(FILTER));
        txtFilter.setValueChangeMode(ValueChangeMode.LAZY);
        txtFilter.setClearButtonVisible(true);

        Button btnAdd = new Button(getTranslation(ADD), VaadinIcon.PLUS.create());
        btnAdd.addClickListener(e ->
        {
            SettingForm form = new SettingForm();
            form.setBean(new SettingItem(0, "new", String.class, ""));

            Dialog dlg = new Dialog(getTranslation(CREATE));
            dlg.add(form);
            Dialog.DialogFooter footer = dlg.getFooter();
            footer.add(new Button(getTranslation(CREATE), ev ->
            {
                if (form.validate())
                {
                    presenter.addSetting(form.getBean());
                    dataProvider.refreshAll();
                    dlg.close();
                }
            }));
            footer.add(new Button(getTranslation(CANCEL), ev -> dlg.close()));
            dlg.open();
        });

        addCollapsedColumn(grid, getTranslation(ID), SettingItem::getId);
        addCollapsedColumn(grid, getTranslation(NAME), SettingItem::getName);
        Grid.Column<SettingItem> colType = addCollapsedColumn(grid, getTranslation(TYPE), SettingItem::getType);
        Grid.Column<SettingItem> colValue = addExpandedColumn(grid, getTranslation(VALUE), SettingItem::getValue);
        addSpacerColumn(grid);
        Grid.Column<SettingItem> colEdit = addCollapsedColumn(
                grid,
                getTranslation("actions"),
                new ComponentRenderer<>(itm -> new Button(getTranslation(EDIT),
                        VaadinIcon.EDIT.create(),
                        e -> grid.getEditor().editItem(itm))));

        Binder<SettingItem> binder = new Binder<>();
        Editor<SettingItem> editor = grid.getEditor();
        editor.setBinder(binder);

        Select<Class<?>> selType = new Select<>();
        selType.setPlaceholder(getTranslation("types"));
        selType.setItems(List.of(String.class, Integer.class, LocalDate.class));
        colType.setEditorComponent(selType);
        binder.forField(selType).bind(SettingItem::getType, SettingItem::setType);

//        TextField txtValue = new TextField();
//        txtValue.setPlaceholder("string value");
//        binder.forField(txtValue)
//                .withConverter(x -> (Object) x, y -> (String) y)
//                .bind(SettingItem::getValue, SettingItem::setValue);
//
//        IntegerField intValue = new IntegerField();
//        intValue.setPlaceholder("int value");
//        binder.forField(intValue)
//                .withConverter(x -> (Object) x, y -> (int) y)
//                .bind(SettingItem::getValue, SettingItem::setValue);
//
//        DatePicker dpValue = new DatePicker();
//        dpValue.setPlaceholder("date value");
//        binder.forField(dpValue)
//                .withConverter(x -> (Object) x, y -> (LocalDate) y)
//                .bind(SettingItem::getValue, SettingItem::setValue);
//
//
//        Span spUnknownType = new Span(getTranslation("not.editable"));

        ValueEditor valueEditor = new ValueEditor();
//        valueEditor.setPlaceholder("value");
        binder.forField(valueEditor)
//                .withConverter(x -> x, y -> y)
                .bind(SettingItem::getValue, SettingItem::setValue);

        colValue.setEditorComponent(x ->
        {
            return valueEditor;
//            if (x.getType() == String.class)
//            {
//                return txtValue;
//            }
//            else if (x.getType() == Integer.class)
//            {
//                return intValue;
//            }
//            else if (x.getType() == LocalDate.class)
//            {
//                return dpValue;
//            }
//            return spUnknownType;
        });

        colEdit.setEditorComponent(itm ->
        {
            HorizontalLayout hl = new HorizontalLayout();
            hl.setPadding(false);

            hl.add(new Button(getTranslation(CANCEL), VaadinIcon.CLOSE.create(), e -> editor.cancel()));
            hl.add(new Button(getTranslation(TranslationKeys.SUBMIT), VaadinIcon.CHECK.create(), e -> editor.save()));
            return hl;
        });

        editor.addSaveListener(e ->
        {
            SettingItem itm = e.getItem();
            presenter.saveSetting(itm);
            dataProvider.refreshItem(itm);
        });

        grid.setDataProvider(this.dataProvider);
        grid.addItemDoubleClickListener(e -> grid.getEditor().editItem(e.getItem()));

        txtFilter.addValueChangeListener(e -> this.dataProvider.setFilter(new SettingsFilter(presenter.getMandant()
                .getId(), e.getValue())));

        HorizontalLayout hlTop = new HorizontalLayout();
        hlTop.setPadding(false);
        hlTop.addAndExpand(txtFilter);
        hlTop.add(btnAdd);

        VerticalLayout root = getContent();
        root.setAlignItems(FlexComponent.Alignment.STRETCH);
        root.setPadding(false);
        root.add(hlTop);
        root.addAndExpand(grid);
    }

    @Override
    protected void onLoad()
    {
        long id = presenter.getMandant().getId();
        dataProvider.setFilter(new SettingsFilter(id, ""));
    }

    // 21.10.2023 KK TODO: replace with converter?
    public static class ValueEditor extends CustomField<Object>
    {
        private TextField txtValue = new TextField();

        public ValueEditor()
        {
            super(null);
            txtValue.setPlaceholder(getTranslation(TranslationKeys.VALUE));
            add(txtValue);
        }

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
