package de.kkendzia.myintranet.ei.ui.views.ah.detail;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import de.kkendzia.myintranet.ei.ui._framework.parameters.ParameterDefinition;
import de.kkendzia.myintranet.ei.ui._framework.view.AbstractEIView;
import de.kkendzia.myintranet.ei.ui.layout.EIMainLayout;

import static de.kkendzia.myintranet.ei.ui._framework.parameters.ParameterDefinition.longParam;

@Route(value = "ah", layout = EIMainLayout.class)
public class AhDetailView
        extends AbstractEIView<VerticalLayout>
    implements HasUrlParameter<Long>
{
    private static final ParameterDefinition<Long> PARAM_ID = longParam("id");
    private final H1 hTitle;

    public AhDetailView()
    {
        VerticalLayout root   = getContent();
        hTitle = new H1("AH CREATE");
        root.add(hTitle);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long id)
    {
        setParameter(PARAM_ID, id);
        hTitle.setText(hTitle.getText() + id);
    }
}
