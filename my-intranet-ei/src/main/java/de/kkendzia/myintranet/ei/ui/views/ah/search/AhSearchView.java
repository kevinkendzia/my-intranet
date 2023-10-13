package de.kkendzia.myintranet.ei.ui.views.ah.search;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import de.kkendzia.myintranet.ei.ui._framework.parameters.ParameterDefinition;
import de.kkendzia.myintranet.ei.ui._framework.search.SearchRoute;
import de.kkendzia.myintranet.ei.ui._framework.view.AbstractEIView;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.AnnotationItemProvider.MenuRoute;
import de.kkendzia.myintranet.ei.ui.layout.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.views.ah.search.AhSearchPresenter.SearchItem;
import org.springframework.beans.factory.annotation.Autowired;

import static de.kkendzia.myintranet.ei.ui._framework.parameters.ParameterDefinition.stringParam;
import static de.kkendzia.myintranet.ei.ui.layout.EIMainLayoutPresenter.SearchTarget.AH;
import static java.util.Collections.emptyList;

@Route(value = "ah/search", layout = EIMainLayout.class)
@MenuRoute(label = "menu.search", parent = "ah/search")
@SearchRoute(target = AH)
public class AhSearchView
        extends AbstractEIView<VerticalLayout>
        implements BeforeEnterObserver, AfterNavigationObserver
{
    private static final ParameterDefinition<String> PARAM_SEARCH_TEXT = stringParam("searchtext");
    private final Span spSearchText = new Span();

    private final AhSearchPresenter presenter;
    private final Grid<SearchItem> grid = new Grid<>();

    @Autowired
    public AhSearchView(AhSearchPresenter presenter)
    {
        this.presenter = presenter;

        setPageTitle(getTranslation("ah.search.pageTitle"));

        grid.addColumn(SearchItem::ahnr).setHeader(getTranslation("label.ahnr"));
        grid.addColumn(SearchItem::matchcode).setHeader(getTranslation("label.matchcode"));
        grid.addColumn(SearchItem::enterDate).setHeader(getTranslation("label.enterDate"));
        grid.addColumn(SearchItem::leaveDate).setHeader(getTranslation("label.exitDate"));

        VerticalLayout root = getContent();
        root.setHeightFull();
        root.add(new H3("AH SEARCH"));
        root.add(new HorizontalLayout(new Span("Searchtext:"), spSearchText));
        root.addAndExpand(grid);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event)
    {
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event)
    {
        String searchtext = event.getLocation().getQueryParameters().getParameters().getOrDefault("searchtext", emptyList()).stream().findAny().orElse("");
        spSearchText.setText(searchtext);
        grid.setItems(presenter.createSearchDataProvider(searchtext));
    }
}
