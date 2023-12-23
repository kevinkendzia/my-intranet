package de.kkendzia.myintranet.ei.ui.layouts.main.appbar;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.RouteConfiguration;
import de.kkendzia.myintranet.app.useractions._shared.ActionItem;
import de.kkendzia.myintranet.ei.core.session.EISession;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayoutPresenter;
import de.kkendzia.myintranet.ei.ui.layouts.main.drawer.menu.provider.annotation.AnnotationItemProvider;

import java.time.LocalDateTime;
import java.util.List;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.*;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.ErrorKeys.MessageKeys.VALIDATION_ERROR;
import static de.kkendzia.myintranet.ei.ui.components.notification.EINotificationFactory.showError;
import static java.util.Objects.requireNonNull;

public class UserAvatar extends Composite<Avatar>
{
    private final EISession session;
    private final EIMainLayoutPresenter presenter;

    public UserAvatar(EISession session, EIMainLayoutPresenter presenter)
    {
        this.session = requireNonNull(session, "session can't be null!");
        this.presenter = requireNonNull(presenter, "presenter can't be null!");
        createContextMenu();
    }

    private void createContextMenu()
    {
        ContextMenu ctx = new ContextMenu(getContent());
        ctx.setOpenOnClick(true);
        ctx.add(createSessionInfo());
        ctx.add(new Hr());
        createRecentActionsMenu(ctx);
        createFavoriteActionsMenu(ctx);
        ctx.add(new Hr());
        ctx.addItem(getTranslation(LOGOUT), e -> session.logout());
    }

    private VerticalLayout createSessionInfo()
    {
        final var vlInfo = new VerticalLayout();
        vlInfo.add(new Span(session.username()));
        vlInfo.add(new Span(String.valueOf(session)));
        return vlInfo;
    }

    private void createRecentActionsMenu(final ContextMenu ctx)
    {
        final var itm = ctx.addItem(getTranslation(RECENT));
        final var subMenu = itm.getSubMenu();

        List<ActionItem> actions = presenter.fetchRecentActions(5);
        if (!actions.isEmpty())
        {
            actions.forEach(a -> subMenu.addItem(
                    a.getTitle(),
                    e -> UI.getCurrent().navigate(a.getRoute())));
        }
        else
        {
            subMenu.addItem(getTranslation(EMPTY));
        }
    }

    private void createFavoriteActionsMenu(final ContextMenu ctx)
    {
        final var itm = ctx.addItem(getTranslation(FAVORITES));
        final var subMenu = itm.getSubMenu();
        final var favoriteActions = presenter.fetchFavoriteActions(5);

        subMenu.addItem(getTranslation(ADD), event -> addFavoriteAction(subMenu));
        subMenu.add(new Hr());

        if (!favoriteActions.isEmpty())
        {
            favoriteActions.forEach(a -> subMenu.addItem(
                    a.getTitle(),
                    e -> UI.getCurrent().navigate(a.getRoute())));
        }
        else
        {
            subMenu.addItem(getTranslation(EMPTY));
        }
    }

    private void addFavoriteAction(final SubMenu subMenuFavorites)
    {
        final var analyzer = new AnnotationItemProvider.AnnotationAnalyzer();
        final var currentView = UI.getCurrent().getCurrentView();
        final var url = RouteConfiguration.forSessionScope().getUrl(currentView.getClass());
        final var title = analyzer.extractLabel(currentView.getClass());

        final var binder = new Binder<ActionItem>();

        final var txtTitle = new TextField(getTranslation(NAME));
        binder.forField(txtTitle).bind(ActionItem::getTitle, ActionItem::setTitle);
        final var txtRoute = new TextField(getTranslation(VALUE));
        binder.forField(txtRoute).bind(ActionItem::getRoute, ActionItem::setRoute);

        final var vl = new VerticalLayout();
        vl.add(txtTitle);
        vl.add(txtRoute);

        final var dlg = new Dialog();
        dlg.setHeaderTitle(getTranslation(ADD));
        dlg.add(vl);
        dlg.getFooter().add(new Button(getTranslation(SUBMIT), e ->
        {
            if (binder.validate().isOk())
            {
                final var bean = binder.getBean();
                bean.setTimestamp(LocalDateTime.now());
                presenter.addFavoriteAction(bean);
                subMenuFavorites.addItem(bean.getTitle(), ev -> UI.getCurrent().navigate(url));
                dlg.close();
            }
            else
            {
                showError(getTranslation(VALIDATION_ERROR));
            }
        }));
        dlg.getFooter().add(new Button(getTranslation(CANCEL), e -> dlg.close()));

        binder.setBean(new ActionItem(getTranslation(title), url));
        dlg.open();
    }
}
