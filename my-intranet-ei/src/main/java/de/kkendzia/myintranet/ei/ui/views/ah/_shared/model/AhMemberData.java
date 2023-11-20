package de.kkendzia.myintranet.ei.ui.views.ah._shared.model;

import de.kkendzia.myintranet.domain.ah.mitgliedsform.MitgliedsForm;
import de.kkendzia.myintranet.domain.ah.regulierer.Regulierer;
import de.kkendzia.myintranet.domain.ah.verband.Verband;

public class AhMemberData
{
    private Regulierer regulator;
    private Verband association;
    private MitgliedsForm membershipForm;

    public AhMemberData(Regulierer regulator, Verband association, MitgliedsForm membershipForm)
    {
        this.regulator = regulator;
        this.association = association;
        this.membershipForm = membershipForm;
    }

    public Regulierer getRegulator()
    {
        return regulator;
    }

    public void setRegulator(Regulierer regulator)
    {
        this.regulator = regulator;
    }

    public Verband getAssociation()
    {
        return association;
    }

    public void setAssociation(Verband association)
    {
        this.association = association;
    }

    public MitgliedsForm getMembershipForm()
    {
        return membershipForm;
    }

    public void setMembershipForm(MitgliedsForm membershipForm)
    {
        this.membershipForm = membershipForm;
    }
}
