package de.kkendzia.myintranet.ei.ui.views.mandant.detail;

import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import de.kkendzia.myintranet.ei._framework.parameters.HasViewParameter;
import de.kkendzia.myintranet.ei._framework.view.AbstractEIView;
import de.kkendzia.myintranet.ei._framework.view.page.SaveablePage;
import de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.ErrorKeys.MessageKeys;
import de.kkendzia.myintranet.ei.ui.components.tabs.PagedTabs;
import de.kkendzia.myintranet.ei.ui.components.tabs.PagedTabs.PagedTab;
import de.kkendzia.myintranet.ei.ui.components.toolbar.ToolbarConfiguration;
import de.kkendzia.myintranet.ei.ui.layouts.TabsLayout;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.layouts.main.drawer.menu.provider.annotation.MenuRoute;
import de.kkendzia.myintranet.ei.ui.views.mandant.MandantRoutes;
import de.kkendzia.myintranet.ei.ui.views.mandant.detail.MandantDetailPresenter.EditMode;
import de.kkendzia.myintranet.ei.ui.views.mandant.detail.pages.MandantDetailPage;
import de.kkendzia.myintranet.ei.ui.views.mandant.detail.pages.MandantDetailsPage;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.*;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.MandantKeys.MANDANT;
import static de.kkendzia.myintranet.ei.ui.components.notification.EINotificationFactory.showError;
import static de.kkendzia.myintranet.ei.ui.components.notification.EINotificationFactory.showSuccess;
import static de.kkendzia.myintranet.ei.ui.layouts.main.drawer.EIDrawer.EIMenuKeys.MANDANTEN;
import static java.util.Objects.requireNonNull;

@Route(value = MandantDetailView.NAV, layout = EIMainLayout.class)
@MenuRoute(label = CREATE, parent = MANDANTEN, position = 0)
@PermitAll
public class MandantDetailView extends AbstractEIView<TabsLayout<MandantDetailPage>> implements HasViewParameter<String>
{
    public static final String NAV = MandantRoutes.NAV_ROOT;
    private final MandantDetailPresenter presenter;

    @Autowired
    public MandantDetailView(MandantDetailPresenter presenter)
    {
        this.presenter = requireNonNull(presenter, "presenter can't be null!");

        setPageTitle(getTranslation(MANDANT));
        setToolbarConfig(
                () ->
                {
                    String title = getTranslation(MANDANT) + " " + presenter.getSheet().getName();
                    ToolbarConfiguration tabConfig = getContent().getTabs().getSelectedPage().getToolbarConfig();

                    return new ToolbarConfiguration.Builder()
                            .title(title)
                            .action(getTranslation(SAVE), this::saveTabs)
                            .config(tabConfig)
                            .build();
                });

        TabsLayout<MandantDetailPage> root = getContent();
        root.addSelectedPageChangeListener(e -> fireToolbarChange(e.isFromClient()));
        root.add(new PagedTab<>(getTranslation(DETAILS), new MandantDetailsPage(presenter)));
    }

    private void saveTabs()
    {
        PagedTabs<MandantDetailPage> tabs = getContent().getTabs();
        List<MandantDetailPage> pages = tabs.getPages();

        boolean error = false;
        boolean empty = true;

        for (MandantDetailPage page : pages)
        {
            if (page instanceof SaveablePage saveablePage
                    && (presenter.getMode() == EditMode.CREATE || saveablePage.hasChanges()))
            {
                if (saveablePage.validate().hasErrors())
                {
                    // 21.10.2023 KK TODO: Optimize! (Reset?)
//                        tabs
//                                .getOptionalTab(page)
//                                .ifPresent(p -> p.add(VaadinIcon.EXCLAMATION.create()));

                    error = true;
                    break;
                }
                saveablePage.onSave();
                empty = false;
            }
        }

        if (error)
        {
            showError(getTranslation(MessageKeys.VALIDATION_ERROR));
        }
        else if (empty)
        {
            showError(getTranslation(MessageKeys.NO_CHANGES));
        }
        else
        {
            presenter.save();
            getContent().refreshPages();
            setViewParameter(presenter.getSheet().getId().getValue().toString());
            showSuccess(getTranslation(SUCCESS));
        }
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter)
    {
        HasViewParameter.super.setParameter(event, parameter);
    }

    @Override
    public void beforeEnterView(BeforeEnterEvent event)
    {
        String id = getViewParameter();
        if (id == null)
        {
            event.forwardTo(NAV, "new");
            return;
        }

        presenter.init(Objects.equals(id, "new") ? null : id);
        getContent().refreshPages();
    }
}
