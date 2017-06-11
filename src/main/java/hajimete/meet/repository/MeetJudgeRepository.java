package hajimete.meet.repository;

import hajimete.meet.domain.MeetJudge;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the MeetJudge entity.
 */
@SuppressWarnings("unused")
public interface MeetJudgeRepository extends JpaRepository<MeetJudge, Long> {

}
