package de.kkendzia.myintranet.ei.ui.views.ah.detail.content;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import de.kkendzia.myintranet.domain.ah.Ah;
import de.kkendzia.myintranet.domain.shared.mandant.Mandant;
import de.kkendzia.myintranet.ei.core.EIComponent;

public class CoreDataForm extends EIComponent<VerticalLayout>
{
    private final Binder<Ah> binder = new Binder<>();

    public CoreDataForm()
    {
        ComboBox<Mandant> cboMandant = new ComboBox<>();
        TextField txtAhnr = new TextField();
        TextField txtMatchcode = new TextField();

        FormLayout form = new FormLayout();
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("10em", 2),
                new FormLayout.ResponsiveStep("15em", 3));

        form.add(cboMandant);
        form.add(txtAhnr);
        form.add(txtMatchcode);

        VerticalLayout root = getContent();
        root.addAndExpand(form);

//        binder.bind(cboMandant, Ah::mandant, Ah::mandant);
//        binder.bind(cboMandant, Ah::mandant, Ah::mandant);
//        binder.bind(cboMandant, Ah::mandant, Ah::mandant);
    }
}
