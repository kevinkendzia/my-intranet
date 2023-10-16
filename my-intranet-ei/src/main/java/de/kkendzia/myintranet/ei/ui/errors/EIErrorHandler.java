package de.kkendzia.myintranet.ei.ui.errors;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.ErrorHandler;

public class EIErrorHandler implements ErrorHandler
{
    @Override
    public void error(ErrorEvent event)
    {
        // TODO
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("ERROR");
        dialog.add(new Paragraph("Some serious error happened and you should definitely fix!"));
        dialog.getFooter().add(new Button("Close", e -> dialog.close()));
        dialog.open();
    }
}
