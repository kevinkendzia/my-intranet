package de.kkendzia.myintranet.ei.ui.views.ah._shared.components.forms;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import de.kkendzia.myintranet.app.ah._shared.AhSheet;
import de.kkendzia.myintranet.domain.mitgliedsform.MitgliedsForm;
import de.kkendzia.myintranet.domain.regulierer.Regulierer;
import de.kkendzia.myintranet.domain.verband.Verband;
import de.kkendzia.myintranet.ei.ui.components.form.AbstractForm;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.AhKeys.MITGLIEDSFORM;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.AhKeys.VERBAND;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.REGULIERER;
import static de.kkendzia.myintranet.ei.utils.DataProviderUtils.emptyDataProvider;

public class AhMemberDataForm extends AbstractForm<AhSheet.MembershipSection>
{
    private DataProvider<Regulierer, String> dpRegulator = emptyDataProvider();
    private DataProvider<Verband, String> dpAssociation = emptyDataProvider();
    private DataProvider<MitgliedsForm, String> dpMembershipForm = emptyDataProvider();

    public AhMemberDataForm(Binder<AhSheet.MembershipSection> binder)
    {
        super(binder);
        ComboBox<Regulierer> cboRegulator = new ComboBox<>(getTranslation(REGULIERER));
        add(
                cboRegulator,
                (field, b) -> b.forField(field)
                        .bind(AhSheet.MembershipSection::getRegulator, AhSheet.MembershipSection::setRegulator));
        ComboBox<Verband> cboAssociation = new ComboBox<>(getTranslation(VERBAND));
        add(
                cboAssociation,
                (field, b) -> b.forField(field)
                        .bind(AhSheet.MembershipSection::getAssociation, AhSheet.MembershipSection::setAssociation));
        ComboBox<MitgliedsForm> cboMembershipForm = new ComboBox<>(getTranslation(MITGLIEDSFORM));
        add(
                cboMembershipForm,
                (field, b) -> b.forField(field)
                        .bind(
                                AhSheet.MembershipSection::getMembershipForm,
                                AhSheet.MembershipSection::setMembershipForm));
    }

    public void setRegulatorItems(Collection<Regulierer> items)
    {
        this.dpRegulator = DataProvider.ofCollection(items).filteringBySubstring(Regulierer::getName);
    }

    private List<Regulierer> getRegulatorItems()
    {
        return dpRegulator.fetch(new Query<>()).toList();
    }

    public void setAssociationItems(Collection<Verband> items)
    {
        this.dpAssociation = DataProvider.ofCollection(items).filteringBySubstring(Verband::getName);
    }

    private List<Verband> getAssociationItems()
    {
        return dpAssociation.fetch(new Query<>()).toList();
    }

    public void setMembershipFormItems(Collection<MitgliedsForm> items)
    {
        this.dpMembershipForm = DataProvider.ofCollection(items).filteringBySubstring(MitgliedsForm::getName);
    }

    private List<MitgliedsForm> getMembershipFormItems()
    {
        return dpMembershipForm.fetch(new Query<>()).toList();
    }

    public Optional<Regulierer> findRegulatorItemById(long id)
    {
        return getRegulatorItems().stream().filter(x -> Objects.equals(x.getId(), id)).findFirst();
    }

    public Optional<Verband> findAssociationItemById(long id)
    {
        return getAssociationItems().stream().filter(x -> Objects.equals(x.getId(), id)).findFirst();
    }

    public Optional<MitgliedsForm> findMembershipFormItemById(long id)
    {
        return getMembershipFormItems().stream().filter(x -> Objects.equals(x.getId(), id)).findFirst();
    }

}
