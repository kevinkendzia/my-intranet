package de.kkendzia.myintranet.ei.ui.views.other.mandant.detail.pages;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import de.kkendzia.myintranet.ei.core.view.page.AbstractLazyPage;
import de.kkendzia.myintranet.ei.ui.views.other.mandant.detail.MandantDetailPresenter;
import de.kkendzia.myintranet.ei.ui.views.other.mandant.detail.MandantDetailPresenter.SettingItem;
import de.kkendzia.myintranet.ei.ui.views.other.mandant.detail.MandantDetailPresenter.SettingsFilter;

import java.time.LocalDate;
import java.util.List;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.ID;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.NAME;
import static de.kkendzia.myintranet.ei.core.utils.GridColumnFactory.*;

public class MandantSettingsPage extends AbstractLazyPage<VerticalLayout> implements MandantDetailPage
{
    private final ConfigurableFilterDataProvider<SettingItem, Void, SettingsFilter> dataProvider;
    private Grid<SettingItem> grid = new Grid<>();
    private final MandantDetailPresenter presenter;

    public MandantSettingsPage(MandantDetailPresenter presenter)
    {
        this.presenter=presenter;

        TextField txtFilter = new TextField(getTranslation("filter"));
        txtFilter.setPlaceholder(getTranslation("filter"));
        txtFilter.setValueChangeMode(ValueChangeMode.LAZY);

        addCollapsedColumn(grid, getTranslation(ID), SettingItem::getId);
        addCollapsedColumn(grid, getTranslation(NAME), SettingItem::getName);
        Grid.Column<SettingItem> colType = addCollapsedColumn(grid, getTranslation("TYPE"), SettingItem::getType);
        Grid.Column<SettingItem> colValue = addExpandedColumn(grid, getTranslation("value"), SettingItem::getValue);
        addSpacerColumn(grid);

        Binder<SettingItem> binder = new Binder<>();
        Editor<SettingItem> editor = grid.getEditor();
        editor.setBinder(binder);

        Select<Class<?>> selType = new Select<>();
        selType.setPlaceholder(getTranslation("types"));
        selType.setItems(List.of(String.class, Integer.class, LocalDate.class));
        colType.setEditorComponent(selType);
        binder.forField(selType).bind(SettingItem::getType, SettingItem::setType);

        TextField txtValue = new TextField();
        txtValue.setPlaceholder("string value");
        binder.forField(txtValue).withConverter(x -> (Object)x, y->(String)y).bind(SettingItem::getValue, SettingItem::setValue);

        IntegerField intValue = new IntegerField();
        intValue.setPlaceholder("int value");
        binder.forField(intValue).withConverter(x -> (Object)x, y->(int)y).bind(SettingItem::getValue, SettingItem::setValue);

        DatePicker dpValue = new DatePicker();
        dpValue.setPlaceholder("date value");
        binder.forField(dpValue).withConverter(x -> (Object)x, y->(LocalDate) y).bind(SettingItem::getValue, SettingItem::setValue);

        Span spUnknownType = new Span(getTranslation("not.editable"));

        colValue.setEditorComponent(x ->
        {
            if(x.getType() == String.class)
            {
                return txtValue;
            }
            else if(x.getType() == Integer.class)
            {
                return intValue;
            }
            else if(x.getType() == LocalDate.class)
            {
                return dpValue;
            }
            return spUnknownType;
        });

        dataProvider = presenter.createSettingsDataProvider().withConfigurableFilter();
        grid.setDataProvider(dataProvider);

        txtFilter.addValueChangeListener(e -> dataProvider.setFilter(new SettingsFilter(presenter.getMandant().getId(), e.getValue())));

        VerticalLayout root = getContent();
        root.setAlignItems(FlexComponent.Alignment.STRETCH);
        root.add(txtFilter);
        root.addAndExpand(grid);
    }

    @Override
    protected void onLoad()
    {
        long id = presenter.getMandant().getId();
        dataProvider.setFilter(new SettingsFilter(id, ""));
    }
}
