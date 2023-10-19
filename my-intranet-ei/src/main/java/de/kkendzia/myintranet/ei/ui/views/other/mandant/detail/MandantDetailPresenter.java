package de.kkendzia.myintranet.ei.ui.views.other.mandant.detail;

import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import de.kkendzia.myintranet.domain.shared.mandant.Mandant;
import de.kkendzia.myintranet.domain.shared.mandant.MandantDAO;
import de.kkendzia.myintranet.domain.shared.mandant.MandantSetting;
import de.kkendzia.myintranet.domain.shared.mandant.MandantSettingDAO;
import de.kkendzia.myintranet.ei.core.annotations.Presenter;
import de.kkendzia.myintranet.ei.ui.errors.UnknownIDError.UnknownIDException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

import static com.vaadin.flow.data.provider.DataProvider.fromFilteringCallbacks;

@Presenter
public class MandantDetailPresenter
{
    private static final String DATE_FORMAT = "dd.MM.yyyy";

    @Autowired
    private MandantDAO mandantDAO;
    @Autowired
    private MandantSettingDAO mandantSettingDAO;
    private Mandant mandant;

    public Mandant loadMandantById(long id)
    {
        this.mandant = mandantDAO.finaOneById(id).orElseThrow(UnknownIDException::new);
        return mandant;
    }

    public void updateMandant()
    {
        if (mandant.getId() <= 0)
        {
            mandantDAO.create(mandant);
        }
        else
        {
            mandantDAO.update(mandant);
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
                .filter(x -> query.getFilter().map(f -> Objects.equals(x.getName(), f.text())).orElse(true))
                .skip(query.getOffset())
                .limit(query.getLimit())
                .map(MandantDetailPresenter::mapToPresentation);
    }

    public Mandant getMandant()
    {
        return this.mandant;
    }

    public record SettingsFilter(
            long mandantId,
            String text)
    {
        // just a record
    }

    private int countSettings(Query<SettingItem, SettingsFilter> query)
    {
        // COUNT
        return Math.toIntExact(mandantSettingDAO
                .findAllBy(query.getFilter().orElseThrow().mandantId())
                .filter(x -> query.getFilter().map(f -> Objects.equals(x.getName(), f.text())).orElse(true))
                .count());
    }

    public void saveSettings(
            long mandantId,
            Collection<SettingItem> items)
    {
        for (SettingItem item : items)
        {
            MandantSetting setting = mapToModel(mandantId, item);
            if (setting.getId() <= 0)
            {
                mandantSettingDAO.create(setting);
            }
            else
            {
                mandantSettingDAO.update(setting);
            }
        }
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
                value = Integer.parseInt(setting.getValue());
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
