package de.kkendzia.myintranet.ei.ui.views.ah._shared.components.forms;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import de.kkendzia.myintranet.domain.shared.mitgliedsform.MembershipForm;
import de.kkendzia.myintranet.domain.shared.regulierer.Regulator;
import de.kkendzia.myintranet.domain.shared.verband.Association;
import de.kkendzia.myintranet.ei.ui.components.form.AbstractForm;
import de.kkendzia.myintranet.ei.ui.views.ah._shared.model.AhMemberData;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.*;
import static de.kkendzia.myintranet.ei.core.utils.DataProviderUtil.emptyDataProvider;

public class AhMemberDataForm extends AbstractForm<AhMemberData>
{
    private final ComboBox<Regulator> cboRegulator = new ComboBox<>(getTranslation(REGULIERER));
    private final ComboBox<Association> cboAssociation = new ComboBox<>(getTranslation(VERBAND));
    private final ComboBox<MembershipForm> cboMembershipForm = new ComboBox<>(getTranslation(MITGLIEDSFORM));

    private DataProvider<Regulator, String> dpRegulator = emptyDataProvider();
    private DataProvider<Association, String> dpAssociation = emptyDataProvider();
    private DataProvider<MembershipForm, String> dpMembershipForm = emptyDataProvider();

    public AhMemberDataForm()
    {
        add(
                cboRegulator,
                (field, binder) -> binder.forField(field)
                        .bind(AhMemberData::getRegulator, AhMemberData::setRegulator));
        add(
                cboAssociation,
                (field, binder) -> binder.forField(field)
                        .bind(AhMemberData::getAssociation, AhMemberData::setAssociation));
        add(
                cboMembershipForm,
                (field, binder) -> binder.forField(field)
                        .bind(AhMemberData::getMembershipForm, AhMemberData::setMembershipForm));
    }

    public void setRegulatorItems(Collection<Regulator> items)
    {
        this.dpRegulator = DataProvider.ofCollection(items).filteringBySubstring(Regulator::getName);
    }

    private List<Regulator> getRegulatorItems()
    {
        return dpRegulator.fetch(new Query<>()).toList();
    }

    public void setAssociationItems(Collection<Association> items)
    {
        this.dpAssociation = DataProvider.ofCollection(items).filteringBySubstring(Association::getName);
    }

    private List<Association> getAssociationItems()
    {
        return dpAssociation.fetch(new Query<>()).toList();
    }

    public void setMembershipFormItems(Collection<MembershipForm> items)
    {
        this.dpMembershipForm = DataProvider.ofCollection(items).filteringBySubstring(MembershipForm::getName);
    }

    private List<MembershipForm> getMembershipFormItems()
    {
        return dpMembershipForm.fetch(new Query<>()).toList();
    }

    public Optional<Regulator> findRegulatorItemById(long id)
    {
        return getRegulatorItems().stream().filter(x -> Objects.equals(x.getId(), id)).findFirst();
    }

    public Optional<Association> findAssociationItemById(long id)
    {
        return getAssociationItems().stream().filter(x -> Objects.equals(x.getId(), id)).findFirst();
    }

    public Optional<MembershipForm> findMembershipFormItemById(long id)
    {
        return getMembershipFormItems().stream().filter(x -> Objects.equals(x.getId(), id)).findFirst();
    }

}
