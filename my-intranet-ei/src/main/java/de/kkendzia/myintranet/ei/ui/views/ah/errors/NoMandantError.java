package de.kkendzia.myintranet.ei.ui.views.ah.errors;

import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.ParentLayout;
import de.kkendzia.myintranet.ei._framework.view.AbstractEIView;
import de.kkendzia.myintranet.ei.core.navigation.Navigate;
import de.kkendzia.myintranet.ei.ui.errors.ErrotLayout;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.views.mandant.detail.MandantDetailView;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletResponse;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.ErrorKeys.MessageKeys.NO_MANDANT;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.MandantKeys.CREATE;

@ParentLayout(EIMainLayout.class)
@PermitAll
public class NoMandantError extends AbstractEIView<ErrotLayout>
        implements HasErrorParameter<NoMandantError.NoMandantException>
{
    public NoMandantError()
    {
        final var root = getContent();
        root.setDescription(getTranslation(NO_MANDANT));
        root.addNavigationAction(
                getTranslation(CREATE),
                Navigate.to(MandantDetailView.class, "new"));
    }

    @Override
    public int setErrorParameter(final BeforeEnterEvent event, final ErrorParameter<NoMandantException> parameter)
    {
        // TODO: Better code?
        return HttpServletResponse.SC_BAD_REQUEST;
    }

    public static class NoMandantException extends RuntimeException
    {
        public NoMandantException()
        {
            super("No mandant found!");
        }
    }
}
