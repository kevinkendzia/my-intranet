package de.kkendzia.myintranet.ei.ui.views.mandant.detail;

import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import de.kkendzia.myintranet.ei.core.i18n.TranslationKeys;
import de.kkendzia.myintranet.ei.core.parameters.HasViewParameter;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;
import de.kkendzia.myintranet.ei.ui.layouts.TabsLayout;
import de.kkendzia.myintranet.ei.core.view.page.SaveablePage;
import de.kkendzia.myintranet.ei.ui.components.toolbar.ToolbarConfiguration;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.annotation.MenuRoute;
import de.kkendzia.myintranet.ei.ui.components.tabs.PagedTabs;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.views.mandant.detail.pages.MandantDetailPage;
import de.kkendzia.myintranet.ei.ui.views.mandant.detail.pages.MandantDetailsPage;
import de.kkendzia.myintranet.ei.ui.views.mandant.detail.pages.MandantSettingsPage;
import de.kkendzia.myintranet.ei.ui.views.mandant.routes.MandantRoutes;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.*;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.Notification.Error.Message;
import static de.kkendzia.myintranet.ei.ui.components.notification.EINotificationFactory.showError;
import static de.kkendzia.myintranet.ei.ui.components.notification.EINotificationFactory.showSuccess;
import static de.kkendzia.myintranet.ei.ui.layouts.main.EIDrawer.EIMenuKeys.MANDANTEN;

@Route(value = MandantDetailView.NAV, layout = EIMainLayout.class)
@MenuRoute(label = CREATE, parent = MANDANTEN, position = 0)
@PermitAll
public class MandantDetailView extends AbstractEIView<TabsLayout<MandantDetailPage>> implements HasViewParameter<Long>
{
    public static final String NAV = MandantRoutes.NAV_ROOT;
    private final MandantDetailPresenter presenter;

    @Autowired
    public MandantDetailView(MandantDetailPresenter presenter)
    {
        this.presenter = presenter;

        setPageTitle(getTranslation(TranslationKeys.MANDANT));
        setToolbarConfig(
                () ->
                {
                    String title = getTranslation(TranslationKeys.MANDANT) + " " + presenter.getMandant().getName();
                    ToolbarConfiguration tabConfig = getContent().getTabs().getSelectedPage().getToolbarConfig();

                    return new ToolbarConfiguration.Builder()
                            .title(title)
                            .action(getTranslation(SAVE), () -> saveTabs(presenter))
                            .config(tabConfig)
                            .build();
                });

        TabsLayout<MandantDetailPage> root = getContent();
        root.addSelectedPageChangeListener(e -> fireToolbarChange(e.isFromClient()));
        root.add(new PagedTabs.PagedTab<>(getTranslation(DETAILS), new MandantDetailsPage(presenter)));
        root.add(new PagedTabs.PagedTab<>(getTranslation(SETTINGS), new MandantSettingsPage(presenter)));
    }

    private void saveTabs(MandantDetailPresenter presenter)
    {
        PagedTabs<MandantDetailPage> tabs = getContent().getTabs();
        List<MandantDetailPage> pages = tabs.getPages();

        boolean error = false;
        boolean empty = true;

        for (MandantDetailPage page : pages)
        {
            if (page instanceof SaveablePage saveablePage
                    && (presenter.getMandant().isNew() || saveablePage.hasChanges()))
            {
                if (!saveablePage.validate())
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
            showError(getTranslation(Message.VALIDATION_ERROR));
        }
        else if (empty)
        {
            showError(getTranslation(Message.NO_CHANGES));
        }
        else
        {
            presenter.updateMandant();
            getContent().refreshPages();
            setViewParameter(presenter.getMandant().getId());
            showSuccess(getTranslation(SUCCESS));
        }
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Long parameter)
    {
        HasViewParameter.super.setParameter(event, parameter);
    }

    @Override
    public void beforeEnterView(BeforeEnterEvent event)
    {
        Long id = getViewParameter();
        if (id != null)
        {
            if (id == 0)
            {
                presenter.createMandant();
            }
            else
            {
                presenter.loadMandantById(id);
            }
            getContent().refreshPages();
        }
        else
        {
            event.forwardTo(MandantDetailView.class, 0L);
        }
    }
}
