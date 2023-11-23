package de.kkendzia.myintranet.app.ah.commands;

import de.kkendzia.myintranet.app._framework.cqrs.CommandHandler;
import de.kkendzia.myintranet.domain.ah.Ah;
import de.kkendzia.myintranet.domain.ah.AhAdress;
import de.kkendzia.myintranet.domain.ah.AhRepository;

import static java.util.Objects.requireNonNull;

public record CreateAh(
        AhCoreData core,
        AhAdressData adress,
        AhMemberData memberShip) implements CommandHandler.Command<CreateAh.Failure>
{
    public interface CreateAhHandler extends CommandHandler<CreateAh, Failure>
    {
        @Override
        default Class<CreateAh> getCommandClass()
        {
            return CreateAh.class;
        }
    }

    public enum Failure
    {

    }

    public static class CreateAhHandlerImpl implements CreateAhHandler
    {
        private final AhRepository ahRepository;

        public CreateAhHandlerImpl(final AhRepository ahRepository)
        {
            this.ahRepository = requireNonNull(ahRepository, "ahRepository can't be null!");
        }

        @Override
        public CommandResult<Failure> executeResult(final CreateAh command)
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

            ahRepository.add(
                    new Ah(
                            coreData.getAhnr(),
                            coreData.getMatchcode(),
                            coreData.getEnterDate(),
                            coreData.getMandant().getId(),
                            adress,
                            memberData.getRegulator().getId(),
                            memberData.getAssociation().getId(),
                            memberData.getMembershipForm().getId()));

            return CommandResult.success();
        }
    }
}
