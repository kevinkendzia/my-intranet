package de.kkendzia.myintranet.app.ah.commands;

import de.kkendzia.myintranet.app._framework.cqrs.command.CommandHandler.IDCommand;
import de.kkendzia.myintranet.app._framework.cqrs.command.CommandHandler.IDCommandHandler;
import de.kkendzia.myintranet.app._framework.result.SingleResult;
import de.kkendzia.myintranet.app._shared.CreateFailure;
import de.kkendzia.myintranet.domain.ah.Ah;
import de.kkendzia.myintranet.domain.ah.Ah.AhID;
import de.kkendzia.myintranet.domain.ah.AhAdress;
import de.kkendzia.myintranet.domain.ah.AhRepository;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

public record CreateAh(
        AhCoreData core,
        AhAdressData adress,
        AhMemberData memberShip)
        implements IDCommand<AhID, CreateFailure>
{
    public interface CreateAhHandler extends IDCommandHandler<CreateAh, AhID, CreateFailure>
    {
        @Override
        default Class<CreateAh> getCommandClass()
        {
            return CreateAh.class;
        }
    }

    @Component
    public static class CreateAhHandlerImpl implements CreateAhHandler
    {
        private final AhRepository ahRepository;

        public CreateAhHandlerImpl(final AhRepository ahRepository)
        {
            this.ahRepository = requireNonNull(ahRepository, "ahRepository can't be null!");
        }

        @Override
        public SingleResult<AhID, CreateFailure> call(final CreateAh command)
        {
            requireNonNull(command, "command can't be null!");

            AhCoreData coreData = requireNonNull(
                    command.core(),
                    "core can't be null!");
            AhAdressData adressData = requireNonNull(
                    command.adress(),
                    "adress can't be null!");
            AhMemberData memberData = requireNonNull(
                    command.memberShip,
                    "memberShip can't be null!");

            // TODO Adresssuche?
            AhAdress adress = new AhAdress(
                    adressData.getLine1(),
                    adressData.getLine2(),
                    adressData.getStreet(),
                    adressData.getZip(),
                    adressData.getCity(),
                    adressData.getCountry());

            final var ah = new Ah(
                    coreData.getAhnr(),
                    coreData.getMatchcode(),
                    coreData.getEnterDate(),
                    coreData.getMandant().id(),
                    adress,
                    memberData.getRegulator().getId(),
                    memberData.getAssociation().getId(),
                    memberData.getMembershipForm().getId());

            ahRepository.add(ah);

            return SingleResult.success(ah.getId());
        }
    }
}
