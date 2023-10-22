package de.kkendzia.myintranet.ei.ui.views.mandant.detail.pages;

import com.vaadin.flow.component.button.Button;
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
import de.kkendzia.myintranet.ei.core.utils.Result;
import de.kkendzia.myintranet.ei.core.view.page.AbstractLazyPage;
import de.kkendzia.myintranet.ei.ui.components.fields.HasObjectValue;
import de.kkendzia.myintranet.ei.ui.components.grid.InlineToolbar;
import de.kkendzia.myintranet.ei.ui.views.mandant.components.SettingForm;
import de.kkendzia.myintranet.ei.ui.views.mandant.detail.MandantDetailPresenter;
import de.kkendzia.myintranet.ei.ui.views.mandant.detail.MandantDetailPresenter.SettingItem;
import de.kkendzia.myintranet.ei.ui.views.mandant.detail.MandantDetailPresenter.SettingsFilter;

import java.time.LocalDate;
import java.util.List;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.*;
import static de.kkendzia.myintranet.ei.core.utils.GridColumnFactory.*;
import static de.kkendzia.myintranet.ei.ui.components.grid.InlineToolbar.InlineAction.editAction;
import static de.kkendzia.myintranet.ei.ui.components.notification.EINotificationFactory.showError;

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
                    Result<Void> result = presenter.addSetting(form.getBean());
                    if (result.isSuccess())
                    {
                        dataProvider.refreshAll();
                        dlg.close();
                    }
                    else
                    {
                        showError(getTranslation("error.alreadyExists"));
                    }
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
                getTranslation(ACTIONS),
                new ComponentRenderer<>(itm ->
                        new InlineToolbar.Builder()
                                .action(editAction(grid, itm))
                                .action(getTranslation(DELETE), VaadinIcon.CLOSE.create(), () ->
                                {
                                    presenter.deleteSetting(itm);
                                    dataProvider.refreshAll();
                                })
                                .build()));

        Binder<SettingItem> binder = new Binder<>();
        Editor<SettingItem> editor = grid.getEditor();
        editor.setBinder(binder);
        editor.setBuffered(true);

        Select<Class<?>> selType = new Select<>();
        selType.setPlaceholder(getTranslation("types"));
        selType.setItems(List.of(String.class, Integer.class, LocalDate.class));
        colType.setEditorComponent(selType);
        binder.forField(selType).bind(SettingItem::getType, SettingItem::setType);

        HasObjectValue delegate = new HasObjectValue();
        binder
                .forField(delegate)
                .bind(SettingItem::getValue, SettingItem::setValue);

        colValue.setEditorComponent(x -> delegate.as(x.getType()));

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
}
