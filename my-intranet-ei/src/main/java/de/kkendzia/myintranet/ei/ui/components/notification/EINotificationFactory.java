package de.kkendzia.myintranet.ei.ui.components.notification;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;

public final class EINotificationFactory
{

    public static final int DEFAULT_DURATION = 5000;

    private EINotificationFactory()
    {
        // No Instance!
    }

    public static void showSuccess(String message)
    {
        Notification n = new Notification(message);
        n.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        n.setPosition(Position.MIDDLE);
        n.setDuration(DEFAULT_DURATION);
        n.open();
    }
    public static void showError(String message)
    {
        Notification n = new Notification(message);
        n.addThemeVariants(NotificationVariant.LUMO_ERROR);
        n.setPosition(Position.MIDDLE);
        n.setDuration(DEFAULT_DURATION);
        n.open();
    }
    public static void showInfo(String message)
    {
        Notification n = new Notification(message);
        n.setPosition(Position.BOTTOM_END);
        n.setDuration(DEFAULT_DURATION);
        n.open();
    }
}
