package de.kkendzia.myintranet.app.mandant.commands;

import de.kkendzia.myintranet.app._framework.cqrs.command.CommandHandler.IDCommand;
import de.kkendzia.myintranet.app._framework.cqrs.command.CommandHandler.IDCommandHandler;
import de.kkendzia.myintranet.app._framework.result.SingleResult;
import de.kkendzia.myintranet.app._shared.failures.CreateFailure;
import de.kkendzia.myintranet.app.mandant._shared.MandantSheet;
import de.kkendzia.myintranet.domain.mandant.Mandant.MandantID;
import de.kkendzia.myintranet.domain.mandant.MandantRepository;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

public record UpdateMandant(MandantSheet sheet) implements IDCommand<MandantID, CreateFailure>
{
    @Component
    public static class UpdateMandantHandler implements IDCommandHandler<UpdateMandant, MandantID, CreateFailure>
    {
        private final MandantRepository repository;

        public UpdateMandantHandler(final MandantRepository repository)
        {
            this.repository = requireNonNull(repository, "repository can't be null!");
        }

        @Override
        public Class<UpdateMandant> getCommandClass()
        {
            return UpdateMandant.class;
        }

        @Override
        public SingleResult<MandantID, CreateFailure> call(final UpdateMandant command)
        {
            final var mandant = repository.getByID(command.sheet().getId());
            repository.add(mandant);
            return SingleResult.success(mandant.getId());
        }
    }
}
