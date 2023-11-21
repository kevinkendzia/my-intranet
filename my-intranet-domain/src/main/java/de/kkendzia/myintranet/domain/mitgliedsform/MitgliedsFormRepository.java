package de.kkendzia.myintranet.domain.mitgliedsform;

import de.kkendzia.myintranet.domain._core.repository.AggregateLookup;
import de.kkendzia.myintranet.domain._core.repository.AssociationResolver;
import de.kkendzia.myintranet.domain.mitgliedsform.MitgliedsForm.MitgliedsFormID;

public interface MitgliedsFormRepository
        extends
        AggregateLookup<MitgliedsForm, MitgliedsFormID>,
        AssociationResolver<MitgliedsForm, MitgliedsFormID>
{
}
