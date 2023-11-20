package de.kkendzia.myintranet.ei.ui.views.ah._shared.components.forms;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import de.kkendzia.myintranet.domain.ah.mitgliedsform.MitgliedsForm;
import de.kkendzia.myintranet.domain.ah.regulierer.Regulierer;
import de.kkendzia.myintranet.domain.ah.verband.Verband;
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
    private final ComboBox<Regulierer> cboRegulator = new ComboBox<>(getTranslation(REGULIERER));
    private final ComboBox<Verband> cboAssociation = new ComboBox<>(getTranslation(VERBAND));
    private final ComboBox<MitgliedsForm> cboMembershipForm = new ComboBox<>(getTranslation(MITGLIEDSFORM));

    private DataProvider<Regulierer, String> dpRegulator = emptyDataProvider();
    private DataProvider<Verband, String> dpAssociation = emptyDataProvider();
    private DataProvider<MitgliedsForm, String> dpMembershipForm = emptyDataProvider();

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
