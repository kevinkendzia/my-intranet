package de.kkendzia.myintranet.ei.ui.views.mandant.detail;

import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import de.kkendzia.myintranet.app._framework.cqrs.command.CommandMediator;
import de.kkendzia.myintranet.app._framework.cqrs.query.QueryMediator;
import de.kkendzia.myintranet.app.mandant.queries.FindMandantByID;
import de.kkendzia.myintranet.domain.mandant.Mandant;
import de.kkendzia.myintranet.domain.mandant.Mandant.MandantID;
import de.kkendzia.myintranet.domain.mandant.MandantSetting;
import de.kkendzia.myintranet.ei.core.presenter.EIPresenter;
import de.kkendzia.myintranet.ei.core.presenter.Presenter;
import de.kkendzia.myintranet.ei.core.utils.Result;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

import static com.vaadin.flow.data.provider.DataProvider.fromFilteringCallbacks;

@Presenter
public class MandantDetailPresenter implements EIPresenter
{
    private static final String DATE_FORMAT = "dd.MM.yyyy";

    private final QueryMediator quMediator;
    private final CommandMediator cmdMediator;

    // STATE
    private Mandant mandant;

    public MandantDetailPresenter(final QueryMediator quMediator, final CommandMediator cmdMediator)
    {
        this.quMediator = quMediator;
        this.cmdMediator = cmdMediator;
    }

    public void loadMandantById(MandantID id)
    {
        this.mandant = quMediator.fetchOne(new FindMandantByID(id)).getData();
    }

    public void createMandant()
    {
        this.mandant = new Mandant("x", "unknown");
    }

    public void updateMandant()
    {
        cmdMediator.execute(new CreateMandant());
        cmdMediator.execute(new UpdateMandant());

        if (mandant.getId() <= 0)
        {
            this.mandant = mandantDAO.create(mandant);
        }
        else
        {
            this.mandant = mandantDAO.update(mandant);
        }
    }

    public DataProvider<SettingItem, SettingsFilter> createSettingsDataProvider()
    {
        return fromFilteringCallbacks(
                this::fetchSettings,
                this::countSettings);
    }

    private Stream<SettingItem> fetchSettings(Query<SettingItem, SettingsFilter> query)
    {
        return mandantSettingDAO
                .findAllBy(query.getFilter().orElseThrow().mandantId())
                .filter(x -> query.getFilter()
                        .map(SettingsFilter::text)
                        .filter(s -> !s.isBlank())
                        .map(f -> Objects.equals(x.getName(), f))
                        .orElse(true))
                .skip(query.getOffset())
                .limit(query.getLimit())
                .map(MandantDetailPresenter::mapToPresentation);
    }

    private int countSettings(Query<SettingItem, SettingsFilter> query)
    {
        // COUNT
        return Math.toIntExact(mandantSettingDAO
                .findAllBy(query.getFilter().orElseThrow().mandantId())
                .filter(x -> query.getFilter()
                        .map(SettingsFilter::text)
                        .filter(s -> !s.isBlank())
                        .map(f -> Objects.equals(x.getName(), f))
                        .orElse(true))
                .count());
    }

    public Mandant getMandant()
    {
        return this.mandant;
    }

    public void saveSettings(
            Collection<SettingItem> items)
    {
        for (SettingItem item : items)
        {
            saveSetting(item);
        }
    }

    public void saveSetting(SettingItem item)
    {
        MandantSetting setting = mapToModel(mandant.getId(), item);
        if (setting.getId() <= 0)
        {
            mandantSettingDAO.create(setting);
        }
        else
        {
            mandantSettingDAO.update(setting);
        }
    }

    public void deleteSetting(SettingItem setting)
    {
        mandantSettingDAO.deleteById(setting.getId());
    }

    private static SettingItem mapToPresentation(MandantSetting setting)
    {
        try
        {
            Class<?> type = Class.forName(setting.getType());

            Object value = null;
            if (type == String.class)
            {
                value = setting.getValue();
            }
            else if (type == Integer.class)
            {
                value = setting.getValue() != null && !setting.getValue().isBlank()
                        ? Integer.parseInt(setting.getValue())
                        : -1;
            }
            else if (type == LocalDate.class)
            {
                value = DateTimeFormatter.ofPattern(DATE_FORMAT).parse(setting.getValue());
            }

            return new SettingItem(setting.getId(), setting.getName(), type, value);
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    private static MandantSetting mapToModel(
            long mandantId,
            SettingItem setting)
    {
        String type = setting.getType().getName();

        String value = null;
        if (setting.getType() == String.class)
        {
            value = (String) setting.getValue();
        }
        else if (setting.getType() == LocalDate.class)
        {
            value = DateTimeFormatter.ofPattern(DATE_FORMAT).format((LocalDate) setting.getValue());
        }
        else
        {
            value = String.valueOf(setting.getValue());
        }

        return new MandantSetting(setting.getId(), setting.getName(), type, value, mandantId);
    }

    public Result<Void> addSetting(SettingItem setting)
    {
        MandantSetting entity = mapToModel(mandant.getId(), setting);
        if (!mandantSettingDAO.exists(entity.getMandantId(), entity.getName()))
        {
            mandantSettingDAO.create(entity);
            return Result.success();
        }
        else
        {
            return Result.failure(AddSettingFailure.ALREADY_EXISTS);
        }
    }

    public enum AddSettingFailure implements Result.Failure
    {
        ALREADY_EXISTS
    }

    public record SettingsFilter(
            MandantID mandantId,
            String text)
    {
        // just a record
    }

    public static class SettingItem
    {
        private long id;
        private String name;
        private Class<?> type;
        private Object value;

        public SettingItem(
                long id,
                String name,
                Class<?> type,
                Object value)
        {
            this.id = id;
            this.name = name;
            this.type = type;
            this.value = value;
        }

        public long getId()
        {
            return id;
        }

        public void setId(long id)
        {
            this.id = id;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public Class<?> getType()
        {
            return type;
        }

        public void setType(Class<?> type)
        {
            this.type = type;
        }

        public Object getValue()
        {
            return value;
        }

        public void setValue(Object value)
        {
            this.value = value;
        }
    }
}
