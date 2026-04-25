package sharebuy.domain.menu.provider;

import org.springframework.stereotype.Component;
import sharebuy.domain.alarm.repository.AlarmRepository;
import sharebuy.domain.page.domain.TopNavComponent;
import sharebuy.domain.user.entity.User;

import java.util.UUID;

import static sharebuy.domain.page.domain.TopNavComponent.ALARM;

@Component
public final class AlarmProvider implements TopNavProvider {

    private final AlarmRepository alarmRepository;

    public AlarmProvider(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }


    @Override
    public TopNavComponent getType() {
        return ALARM;
    }

    @Override
    public Object getValue(User user) {

        if (user == null || user.getId() == null) {
            return 0;
        }

        UUID userId = user.getId();
        return alarmRepository.countByUserIdAndReadFalse(userId);
    }

}
