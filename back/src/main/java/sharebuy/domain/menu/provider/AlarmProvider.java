package sharebuy.domain.menu.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sharebuy.domain.alarm.repository.AlarmRepository;
import sharebuy.domain.menu.domain.TopNavComponent;
import sharebuy.domain.menu.entity.Menus;
import sharebuy.domain.user.entity.Users;

import java.util.UUID;

import static sharebuy.domain.menu.domain.TopNavComponent.ALARM;

@Component
public final class AlarmProvider implements TopNavProvider {

    @Autowired
    private AlarmRepository alarmRepository;


    @Override
    public TopNavComponent getType() {
        return ALARM;
    }

    @Override
    public Object getValue(Users user, Menus menu) {
        UUID userId = user.getId();
        return alarmRepository.countByUserIdAndReadFalse(userId);
    }

}
