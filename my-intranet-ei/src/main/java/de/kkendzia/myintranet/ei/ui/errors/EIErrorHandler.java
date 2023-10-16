package de.kkendzia.myintranet.ei.ui.errors;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.ErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class EIErrorHandler implements ErrorHandler
{
    private static final Logger LOGGER = LoggerFactory.getLogger(EIErrorHandler.class);

    @Override
    public void error(ErrorEvent event)
    {
        LOGGER.error("Unknown error!", event.getThrowable());

        // TODO
        Paragraph p = new Paragraph("Some serious error happened and you should definitely fix!");
        p.getElement().appendChild(ElementFactory.createBr());
        p.add(new Text("Timestamp: " + LocalDateTime.now()));

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("ERROR");
        dialog.add(p);
        dialog.getFooter().add(new Button("Close", e -> dialog.close()));
        dialog.open();
    }
}
