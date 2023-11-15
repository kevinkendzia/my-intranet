package de.kkendzia.myintranet.ei.ui.views.ah._shared.model;

import de.kkendzia.myintranet.domain.shared.mitgliedsform.MembershipForm;
import de.kkendzia.myintranet.domain.shared.regulierer.Regulator;
import de.kkendzia.myintranet.domain.shared.verband.Association;

public class AhMemberData
{
    private Regulator regulator;
    private Association association;
    private MembershipForm membershipForm;

    public AhMemberData(Regulator regulator, Association association, MembershipForm membershipForm)
    {
        this.regulator = regulator;
        this.association = association;
        this.membershipForm = membershipForm;
    }

    public Regulator getRegulator()
    {
        return regulator;
    }

    public void setRegulator(Regulator regulator)
    {
        this.regulator = regulator;
    }

    public Association getAssociation()
    {
        return association;
    }

    public void setAssociation(Association association)
    {
        this.association = association;
    }

    public MembershipForm getMembershipForm()
    {
        return membershipForm;
    }

    public void setMembershipForm(MembershipForm membershipForm)
    {
        this.membershipForm = membershipForm;
    }
}
