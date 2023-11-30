package de.kkendzia.myintranet.ei.ui.layouts;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.shared.Registration;
import de.kkendzia.myintranet.ei.ui.components.navigation.ItemNavigationAction;
import de.kkendzia.myintranet.ei.ui.components.text.Counter;
import de.kkendzia.myintranet.ei.ui.components.text.CounterRow;

import static com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.STRETCH;

public class SearchLayout<T> extends Composite<VerticalLayout>
{
    private final Span spSearchText = new Span();
    private final Grid<T> grid = new Grid<>();
    private final Counter counter = new Counter();

    private ItemNavigationAction<T> navigationAction;
    private Registration regCounter;

    public SearchLayout()
    {
        grid.addItemClickListener(e ->
        {
            if (navigationAction != null)
            {
                navigationAction.execute(e.getItem());
            }
        });

        VerticalLayout root = getContent();
        root.addClassName("search-layout");
        root.setPadding(false);
        root.setAlignItems(STRETCH);
        root.setHeightFull();
        root.add(spSearchText);
        root.addAndExpand(grid);
        root.add(new CounterRow(counter));
    }

    @Override
    protected void onAttach(AttachEvent attachEvent)
    {
        super.onAttach(attachEvent);
        registerDataProviderListener(grid.getDataProvider());
    }

    @Override
    protected void onDetach(DetachEvent detachEvent)
    {
        super.onDetach(detachEvent);
        cleanUpRegistrations();
    }

    public void setItems(DataProvider<T, Void> dataProvider)
    {
        grid.setItems(dataProvider);
        cleanUpRegistrations();
        registerDataProviderListener(dataProvider);
    }

    private void registerDataProviderListener(DataProvider<T, ?> dataProvider)
    {
        regCounter = dataProvider.addDataProviderListener(e -> counter.setValue(dataProvider.size(new Query<>())));
    }

    private void cleanUpRegistrations()
    {
        if (regCounter != null)
        {
            regCounter.remove();
            regCounter = null;
        }
    }

    public void setNavigationAction(ItemNavigationAction<T> navigationAction)
    {
        this.navigationAction = navigationAction;
    }

    public Grid<T> getGrid()
    {
        return grid;
    }

    public Counter getCounter()
    {
        return counter;
    }

    public void setSearchText(String searchtext)
    {
        spSearchText.setText(getTranslation("search.description", searchtext));
    }
}
