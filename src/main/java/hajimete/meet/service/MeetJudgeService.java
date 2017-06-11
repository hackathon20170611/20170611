package hajimete.meet.service;

import com.google.gson.Gson;
import hajimete.meet.domain.MeetJudge;
import hajimete.meet.repository.MeetJudgeRepository;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private static final String MEET_SCORE_URL = "https://api.a3rt.recruit-tech.co.jp/image_influence/v1/image_score";
    private static final String API_KEY = "RQVPHHgbajQOxwUUgeKJNKSJAnyYRegS";

    /**
     * Save a meetJudge.
     *
     * @param meetJudge the entity to save
     * @return the persisted entity
     */
    public MeetJudge save(MeetJudge meetJudge) throws IOException {
        log.debug("Request to save MeetJudge : {}", meetJudge);

        this.judge(meetJudge);


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

    private MeetJudge judge(MeetJudge meetJudge) throws IOException {
        String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0";
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("User-Agent", userAgent));

        HttpClient httpClient = HttpClientBuilder.create()
            .setDefaultHeaders(headers)
            .build();

        HttpPost method = new HttpPost(MEET_SCORE_URL);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.setCharset(Consts.UTF_8);
        multipartEntityBuilder.addTextBody("apikey", API_KEY);
        multipartEntityBuilder.addTextBody("predict", "1");
        multipartEntityBuilder.addBinaryBody("imagefile", meetJudge.getImage(), ContentType.DEFAULT_BINARY,
            "meet.jpg");

        method.setEntity(multipartEntityBuilder.build());
        HttpResponse response = httpClient.execute(method);
        int responseStatus = response.getStatusLine().getStatusCode();
        String body = EntityUtils.toString(response.getEntity(), "UTF-8");

        Gson gson = new Gson();
        Map map = gson.fromJson(body, Map.class);
        Map result = (Map) map.get("result");

        log.debug("=======");
        log.debug("responseStatus:" + responseStatus);
        log.debug("response:" + response);
        log.debug("body" + body);
        log.debug("=======");

        return meetJudge;
    }

}
