package hajimete.meet.service;

import hajimete.meet.domain.MeetJudge;
import hajimete.meet.repository.MeetJudgeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing MeetJudge.
 */
@Service
@Transactional
public class MeetJudgeService {

    private final Logger log = LoggerFactory.getLogger(MeetJudgeService.class);

    private final MeetJudgeRepository meetJudgeRepository;

    public MeetJudgeService(MeetJudgeRepository meetJudgeRepository) {
        this.meetJudgeRepository = meetJudgeRepository;
    }

    /**
     * Save a meetJudge.
     *
     * @param meetJudge the entity to save
     * @return the persisted entity
     */
    public MeetJudge save(MeetJudge meetJudge) {
        log.debug("Request to save MeetJudge : {}", meetJudge);
        MeetJudge result = meetJudgeRepository.save(meetJudge);
        return result;
    }

    /**
     * Get all the meetJudges.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MeetJudge> findAll() {
        log.debug("Request to get all MeetJudges");
        List<MeetJudge> result = meetJudgeRepository.findAll();

        return result;
    }

    /**
     * Get one meetJudge by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MeetJudge findOne(Long id) {
        log.debug("Request to get MeetJudge : {}", id);
        MeetJudge meetJudge = meetJudgeRepository.findOne(id);
        return meetJudge;
    }

    /**
     * Delete the  meetJudge by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MeetJudge : {}", id);
        meetJudgeRepository.delete(id);
    }
}
