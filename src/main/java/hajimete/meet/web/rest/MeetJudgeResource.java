package hajimete.meet.web.rest;

import com.codahale.metrics.annotation.Timed;
import hajimete.meet.domain.MeetJudge;
import hajimete.meet.service.MeetJudgeService;
import hajimete.meet.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MeetJudge.
 */
@RestController
@RequestMapping("/api")
public class MeetJudgeResource {

    private final Logger log = LoggerFactory.getLogger(MeetJudgeResource.class);

    private static final String ENTITY_NAME = "meetJudge";

    private final MeetJudgeService meetJudgeService;

    public MeetJudgeResource(MeetJudgeService meetJudgeService) {
        this.meetJudgeService = meetJudgeService;
    }

    /**
     * POST  /meet-judges : Create a new meetJudge.
     *
     * @param meetJudge the meetJudge to create
     * @return the ResponseEntity with status 201 (Created) and with body the new meetJudge, or with status 400 (Bad Request) if the meetJudge has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/meet-judges")
    @Timed
    public ResponseEntity<MeetJudge> createMeetJudge(@RequestBody MeetJudge meetJudge) throws URISyntaxException, IOException {
        log.debug("REST request to save MeetJudge : {}", meetJudge);
        if (meetJudge.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new meetJudge cannot already have an ID")).body(null);
        }
        MeetJudge result = meetJudgeService.save(meetJudge);
        return ResponseEntity.created(new URI("/api/meet-judges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /meet-judges : Updates an existing meetJudge.
     *
     * @param meetJudge the meetJudge to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated meetJudge,
     * or with status 400 (Bad Request) if the meetJudge is not valid,
     * or with status 500 (Internal Server Error) if the meetJudge couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/meet-judges")
    @Timed
    public ResponseEntity<MeetJudge> updateMeetJudge(@RequestBody MeetJudge meetJudge) throws URISyntaxException, IOException {
        log.debug("REST request to update MeetJudge : {}", meetJudge);
        if (meetJudge.getId() == null) {
            return createMeetJudge(meetJudge);
        }
        MeetJudge result = meetJudgeService.save(meetJudge);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, meetJudge.getId().toString()))
            .body(result);
    }

    /**
     * GET  /meet-judges : get all the meetJudges.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of meetJudges in body
     */
    @GetMapping("/meet-judges")
    @Timed
    public List<MeetJudge> getAllMeetJudges() {
        log.debug("REST request to get all MeetJudges");
        return meetJudgeService.findAll();
    }

    /**
     * GET  /meet-judges/:id : get the "id" meetJudge.
     *
     * @param id the id of the meetJudge to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the meetJudge, or with status 404 (Not Found)
     */
    @GetMapping("/meet-judges/{id}")
    @Timed
    public ResponseEntity<MeetJudge> getMeetJudge(@PathVariable Long id) {
        log.debug("REST request to get MeetJudge : {}", id);
        MeetJudge meetJudge = meetJudgeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(meetJudge));
    }

    /**
     * DELETE  /meet-judges/:id : delete the "id" meetJudge.
     *
     * @param id the id of the meetJudge to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/meet-judges/{id}")
    @Timed
    public ResponseEntity<Void> deleteMeetJudge(@PathVariable Long id) {
        log.debug("REST request to delete MeetJudge : {}", id);
        meetJudgeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
