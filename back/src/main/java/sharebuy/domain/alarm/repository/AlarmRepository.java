package sharebuy.domain.alarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sharebuy.domain.alarm.entity.Alarm;

import java.util.List;
import java.util.UUID;
@Repository
public interface AlarmRepository extends JpaRepository<Alarm, UUID> {
    int countByUserIdAndReadFalse(UUID userId);
    List<Alarm> findByUserIdAndReadFalse(UUID userId);

}
