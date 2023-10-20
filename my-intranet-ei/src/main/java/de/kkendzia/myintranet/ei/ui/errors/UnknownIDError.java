package de.kkendzia.myintranet.ei.ui.errors;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.ParentLayout;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;
import de.kkendzia.myintranet.ei.ui.layout.EIMainLayout;
import org.springframework.http.HttpStatus;

@ParentLayout(EIMainLayout.class)
public class UnknownIDError extends AbstractEIView<VerticalLayout> implements HasErrorParameter<UnknownIDError.UnknownIDException>
{
    private final H1 hTitle = new H1(getTranslation("label.error"));
    private final H2 hDescription = new H2(getTranslation("label.error"));

    public UnknownIDError()
    {
        VerticalLayout root = getContent();
        root.setAlignItems(Alignment.CENTER);
        root.setJustifyContentMode(JustifyContentMode.CENTER);
        root.add(hTitle);
        root.add(hDescription);
    }

    @Override
    public int setErrorParameter(
            BeforeEnterEvent event,
            ErrorParameter<UnknownIDException> parameter)
    {
        hDescription.setText(getTranslation("label.error.notFound"));
        return HttpStatus.NOT_FOUND.value();
    }

    public static class UnknownIDException extends RuntimeException
    {
        //   TODO: Implement!
    }
}
