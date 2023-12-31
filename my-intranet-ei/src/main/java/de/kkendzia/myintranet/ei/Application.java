package de.kkendzia.myintranet.ei;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import de.kkendzia.myintranet.ei.core.constants.EITheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Spring Boot application.
 * <p>
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
@SpringBootApplication(scanBasePackages = "de.kkendzia")
@Theme(value = EITheme.NAME, variant = Lumo.DARK)
@Push
public class Application implements AppShellConfigurator
{

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }
}
