package de.kkendzia.myintranet.ei.ui.views.ah.create;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;
import de.kkendzia.myintranet.app.ah._shared.AhSheet;
import de.kkendzia.myintranet.domain.ah.Ah.Ahnr;
import de.kkendzia.myintranet.domain.mandant.Mandant.MandantID;
import de.kkendzia.myintranet.ei._framework.parameters.ParameterDefinition;
import de.kkendzia.myintranet.ei._framework.view.AbstractEIView;
import de.kkendzia.myintranet.ei.core.i18n.TranslationKeys;
import de.kkendzia.myintranet.ei.ui.components.form.FormBinder;
import de.kkendzia.myintranet.ei.ui.components.notification.EINotificationFactory;
import de.kkendzia.myintranet.ei.ui.components.toolbar.ToolbarConfiguration;
import de.kkendzia.myintranet.ei.ui.layouts.SectionLayout;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayout;
import de.kkendzia.myintranet.ei.ui.layouts.main.drawer.menu.provider.annotation.MenuRoute;
import de.kkendzia.myintranet.ei.ui.layouts.main.wrapper.sidebar.SidebarConfiguration;
import de.kkendzia.myintranet.ei.ui.layouts.main.wrapper.sidebar.SidebarConfiguration.SidebarAction;
import de.kkendzia.myintranet.ei.ui.layouts.main.wrapper.sidebar.SidebarConfiguration.SidebarHeader;
import de.kkendzia.myintranet.ei.ui.layouts.main.wrapper.sidebar.SidebarConfiguration.SidebarText;
import de.kkendzia.myintranet.ei.ui.views.ah._shared.components.forms.AhAdressDataForm;
import de.kkendzia.myintranet.ei.ui.views.ah._shared.components.forms.AhCoreDataForm;
import de.kkendzia.myintranet.ei.ui.views.ah._shared.components.forms.AhMemberDataForm;
import de.kkendzia.myintranet.ei.ui.views.ah.detail.AhDetailView;
import jakarta.annotation.security.PermitAll;

import java.time.LocalDate;

import static de.kkendzia.myintranet.ei._framework.parameters.ParameterDefinition.*;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.*;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.ErrorKeys.MessageKeys.VALIDATION_ERROR;
import static de.kkendzia.myintranet.ei.ui.components.notification.EINotificationFactory.showError;
import static java.util.Objects.requireNonNull;

@Route(value = "ah/new", layout = EIMainLayout.class)
@MenuRoute(label = "menu.create", parent = "ah", position = 0)
@PermitAll
public class AhCreateView extends AbstractEIView<SectionLayout>
{
    //region PARAMETERS
    private static final ParameterDefinition<Integer> PARAM_AHNR = intParam("ahnr");
    private static final ParameterDefinition<String> PARAM_MATCHCODE = stringParam("matchcode");
    private static final ParameterDefinition<String> PARAM_MANDANT_ID = stringParam("mandant");
    private static final ParameterDefinition<LocalDate> PARAM_ENTER_DATE = localDateParam("enter", "dd-MM-yyyy");
    private static final ParameterDefinition<String> PARAM_LINE1 = stringParam("line1");
    private static final ParameterDefinition<String> PARAM_LINE2 = stringParam("line2");
    private static final ParameterDefinition<String> PARAM_STREET = stringParam("street");
    private static final ParameterDefinition<String> PARAM_ZIP = stringParam("zip");
    private static final ParameterDefinition<String> PARAM_CITY = stringParam("city");
    private static final ParameterDefinition<Long> PARAM_COUNTRY_ID = longParam("country");
    // Regulierer
    private static final ParameterDefinition<Long> PARAM_REGULATOR_ID = longParam("regulierer");
    // Verband
    private static final ParameterDefinition<Long> PARAM_ASSOCIATION_ID = longParam("association");
    // Mitgliedsform
    private static final ParameterDefinition<Long> PARAM_MEMBERSHIPFORM_ID = longParam("membershipform");
    //endregion

    private final AhCoreDataForm frmCore = new AhCoreDataForm();
    private final AhAdressDataForm frmAdress = new AhAdressDataForm();
    private final AhMemberDataForm frmMember = new AhMemberDataForm();
    private final FormBinder<AhSheet> formBinder = new FormBinder<>();

    private final AhCreatePresenter presenter;
    private AhSheet request;

    public AhCreateView(AhCreatePresenter presenter)
    {
        this.presenter = requireNonNull(presenter, "presenter can't be null!");

        registerQueryParameter(PARAM_AHNR);

        setToolbarConfig(
                new ToolbarConfiguration.Builder()
                        .title(getTranslation("ah.create"))
                        .build());

        setRightSidebarConfig(
                new SidebarConfiguration.Builder()
                        .header(
                                new SidebarHeader(
                                        "ah.create",
                                        "Auf dieser Maske werden alle initial notwendigen Informationen gesammelt, um ein neues Anschlusshaus anlegen zu können. Weitere Daten können später über die Detail-Maske erfasst werden."))
                        .action(new SidebarAction(getTranslation(TranslationKeys.CREATE), this::create))
                        .text(new SidebarText(
                                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."))
                        .build());

        Button btnCreate = new Button(getTranslation(TranslationKeys.CREATE));
        btnCreate.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout footer = new HorizontalLayout();
        footer.setPadding(true);
        footer.setJustifyContentMode(JustifyContentMode.END);
        footer.add(btnCreate);

        SectionLayout root = getContent();
        root.addSection(getTranslation(COMMON), frmCore);
        root.addSection(getTranslation(AdressKeys.ADRESS), frmAdress);
        root.addSection(getTranslation(MEMBERSHIP), frmMember);
        root.add(footer);

        formBinder.bind(frmCore, AhSheet::coreSection);
        formBinder.bind(frmAdress, AhSheet::adressSection);
        formBinder.bind(frmMember, AhSheet::membershipSection);

        formBinder.addValueChangeListener(e -> formBinder.validate());
    }

    private void create()
    {
        if (formBinder.writeBeanIfValid())
        {
            final var id = presenter.create(request);
            EINotificationFactory.showSuccess(TranslationKeys.SUCCESS);
            UI.getCurrent().navigate(AhDetailView.class, id.toString());
        }
        else
        {
            showError(getTranslation(VALIDATION_ERROR));
        }
    }

    @Override
    protected void beforeEnterView(BeforeEnterEvent event)
    {
        frmCore.setMandantItems(presenter.loadMandantItems());

        request = new AhSheet(
                new AhSheet.CoreSection(
                        new Ahnr(qpValue(PARAM_AHNR, 1)),
                        qpValue(PARAM_MATCHCODE, "new"),
                        qpValue(PARAM_MANDANT_ID).map(MandantID::new)
                                .flatMap(frmCore::findMandantItemById)
                                .orElse(null),
                        qpValue(PARAM_ENTER_DATE, LocalDate.now()),
                        null),
                new AhSheet.AdressSection(
                        qpValue(PARAM_LINE1, ""),
                        qpValue(PARAM_LINE2, ""),
                        qpValue(PARAM_STREET, ""),
                        qpValue(PARAM_ZIP, ""),
                        qpValue(PARAM_CITY, ""),
                        qpValue(PARAM_COUNTRY_ID).flatMap(frmAdress::findCountryItemById).orElse(null)),
                new AhSheet.MembershipSection(
                        qpValue(PARAM_REGULATOR_ID).flatMap(frmMember::findRegulatorItemById).orElse(null),
                        qpValue(PARAM_ASSOCIATION_ID).flatMap(frmMember::findAssociationItemById).orElse(null),
                        qpValue(PARAM_MEMBERSHIPFORM_ID).flatMap(frmMember::findMembershipFormItemById).orElse(null)));

        formBinder.setBean(request);
    }
}
