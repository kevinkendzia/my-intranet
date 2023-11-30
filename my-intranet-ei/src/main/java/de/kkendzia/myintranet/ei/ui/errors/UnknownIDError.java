package de.kkendzia.myintranet.ei.ui.errors;

import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.ParentLayout;
import de.kkendzia.myintranet.domain._core.ID;
import de.kkendzia.myintranet.ei._framework.view.AbstractEIView;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayout;
import org.springframework.http.HttpStatus;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.ErrorKeys.MessageKeys.ID_NOT_FOUND;

@ParentLayout(EIMainLayout.class)
public class UnknownIDError extends AbstractEIView<EIErrotLayout>
        implements HasErrorParameter<UnknownIDError.UnknownIDException>
{
    @Override
    public int setErrorParameter(
            BeforeEnterEvent event,
            ErrorParameter<UnknownIDException> parameter)
    {
        getContent().setDescription(getTranslation(ID_NOT_FOUND));
        return HttpStatus.NOT_FOUND.value();
    }

    //region TYPES
    public static class UnknownIDException extends RuntimeException
    {
        private final ID id;

        public UnknownIDException(final ID id)
        {
            super("ID %s was NOT found!".formatted(id));
            this.id = id;
        }

        public ID getId()
        {
            return id;
        }
    }
    //endregion
}
