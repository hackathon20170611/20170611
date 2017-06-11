package hajimete.meet.web.rest;

import hajimete.meet.MeetApp;
import hajimete.meet.domain.MeetJudge;
import hajimete.meet.repository.MeetJudgeRepository;
import hajimete.meet.service.MeetJudgeService;
import hajimete.meet.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MeetJudgeResource REST controller.
 *
 * @see MeetJudgeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MeetApp.class)
public class MeetJudgeResourceIntTest {

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Float DEFAULT_SCORE = 1F;
    private static final Float UPDATED_SCORE = 2F;

    @Autowired
    private MeetJudgeRepository meetJudgeRepository;

    @Autowired
    private MeetJudgeService meetJudgeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMeetJudgeMockMvc;

    private MeetJudge meetJudge;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MeetJudgeResource meetJudgeResource = new MeetJudgeResource(meetJudgeService);
        this.restMeetJudgeMockMvc = MockMvcBuilders.standaloneSetup(meetJudgeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MeetJudge createEntity(EntityManager em) {
        MeetJudge meetJudge = new MeetJudge()
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .score(DEFAULT_SCORE);
        return meetJudge;
    }

    @Before
    public void initTest() {
        meetJudge = createEntity(em);
    }

    @Test
    @Transactional
    public void createMeetJudge() throws Exception {
        int databaseSizeBeforeCreate = meetJudgeRepository.findAll().size();

        // Create the MeetJudge
        restMeetJudgeMockMvc.perform(post("/api/meet-judges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meetJudge)))
            .andExpect(status().isCreated());

        // Validate the MeetJudge in the database
        List<MeetJudge> meetJudgeList = meetJudgeRepository.findAll();
        assertThat(meetJudgeList).hasSize(databaseSizeBeforeCreate + 1);
        MeetJudge testMeetJudge = meetJudgeList.get(meetJudgeList.size() - 1);
        assertThat(testMeetJudge.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testMeetJudge.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testMeetJudge.getScore()).isEqualTo(DEFAULT_SCORE);
    }

    @Test
    @Transactional
    public void createMeetJudgeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = meetJudgeRepository.findAll().size();

        // Create the MeetJudge with an existing ID
        meetJudge.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeetJudgeMockMvc.perform(post("/api/meet-judges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meetJudge)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MeetJudge> meetJudgeList = meetJudgeRepository.findAll();
        assertThat(meetJudgeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMeetJudges() throws Exception {
        // Initialize the database
        meetJudgeRepository.saveAndFlush(meetJudge);

        // Get all the meetJudgeList
        restMeetJudgeMockMvc.perform(get("/api/meet-judges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meetJudge.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.doubleValue())));
    }

    @Test
    @Transactional
    public void getMeetJudge() throws Exception {
        // Initialize the database
        meetJudgeRepository.saveAndFlush(meetJudge);

        // Get the meetJudge
        restMeetJudgeMockMvc.perform(get("/api/meet-judges/{id}", meetJudge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(meetJudge.getId().intValue()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMeetJudge() throws Exception {
        // Get the meetJudge
        restMeetJudgeMockMvc.perform(get("/api/meet-judges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeetJudge() throws Exception {
        // Initialize the database
        meetJudgeService.save(meetJudge);

        int databaseSizeBeforeUpdate = meetJudgeRepository.findAll().size();

        // Update the meetJudge
        MeetJudge updatedMeetJudge = meetJudgeRepository.findOne(meetJudge.getId());
        updatedMeetJudge
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .score(UPDATED_SCORE);

        restMeetJudgeMockMvc.perform(put("/api/meet-judges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMeetJudge)))
            .andExpect(status().isOk());

        // Validate the MeetJudge in the database
        List<MeetJudge> meetJudgeList = meetJudgeRepository.findAll();
        assertThat(meetJudgeList).hasSize(databaseSizeBeforeUpdate);
        MeetJudge testMeetJudge = meetJudgeList.get(meetJudgeList.size() - 1);
        assertThat(testMeetJudge.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testMeetJudge.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testMeetJudge.getScore()).isEqualTo(UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void updateNonExistingMeetJudge() throws Exception {
        int databaseSizeBeforeUpdate = meetJudgeRepository.findAll().size();

        // Create the MeetJudge

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMeetJudgeMockMvc.perform(put("/api/meet-judges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meetJudge)))
            .andExpect(status().isCreated());

        // Validate the MeetJudge in the database
        List<MeetJudge> meetJudgeList = meetJudgeRepository.findAll();
        assertThat(meetJudgeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMeetJudge() throws Exception {
        // Initialize the database
        meetJudgeService.save(meetJudge);

        int databaseSizeBeforeDelete = meetJudgeRepository.findAll().size();

        // Get the meetJudge
        restMeetJudgeMockMvc.perform(delete("/api/meet-judges/{id}", meetJudge.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MeetJudge> meetJudgeList = meetJudgeRepository.findAll();
        assertThat(meetJudgeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeetJudge.class);
    }
}
