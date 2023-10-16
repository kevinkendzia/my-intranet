package de.kkendzia.myintranet.ei.ui.views.search;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;
import de.kkendzia.myintranet.ei.ui.layout.EIMainLayout;

import static de.kkendzia.myintranet.ei.core.search.SearchParameters.SEARCH_TEXT;

@Route(value = "search", layout = EIMainLayout.class)
public class SearchView extends AbstractEIView<VerticalLayout>
{
    private Span spSearchText = new Span();
    public SearchView()
    {
        registerQueryParameter(SEARCH_TEXT);

        // TODO
        VerticalLayout root = getContent();
        root.add(new H1("SEARCH"));
        root.add(new Paragraph("Shows search results for arbitrary search text!"));
        root.add(spSearchText);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event)
    {
        super.beforeEnter(event);
        String searchText = getFirstQueryParameterValue(SEARCH_TEXT);
        spSearchText.setText(searchText);
    }
}
