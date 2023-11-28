package de.kkendzia.myintranet.app.mandant.commands;

import de.kkendzia.myintranet.app._framework.cqrs.command.CommandHandler;
import de.kkendzia.myintranet.app._framework.cqrs.command.CommandHandler.IDCommand;
import de.kkendzia.myintranet.app._framework.result.SingleResult;
import de.kkendzia.myintranet.app._shared.CreateFailure;
import de.kkendzia.myintranet.app.mandant._shared.MandantSheet;
import de.kkendzia.myintranet.domain.mandant.Mandant;
import de.kkendzia.myintranet.domain.mandant.Mandant.MandantID;
import de.kkendzia.myintranet.domain.mandant.MandantRepository;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

public record CreateMandant(MandantSheet sheet) implements IDCommand<MandantID, CreateFailure>
{
    @Component
    public static class CreateMandantHandler
            implements CommandHandler.IDCommandHandler<CreateMandant, MandantID, CreateFailure>
    {
        private final MandantRepository repository;

        public CreateMandantHandler(final MandantRepository repository)
        {
            this.repository = requireNonNull(repository, "repository can't be null!");
        }

        @Override
        public Class<CreateMandant> getCommandClass()
        {
            return CreateMandant.class;
        }

        @Override
        public SingleResult<MandantID, CreateFailure> call(final CreateMandant command)
        {
            final var mandant = new Mandant(command.sheet().getKey(), command.sheet().getName());
            repository.add(mandant);
            return SingleResult.success(mandant.getId());
        }
    }
}
