package hajimete.meet.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MeetJudge.
 */
@Entity
@Table(name = "meet_judge")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MeetJudge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "rank")
    private Long rank;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public MeetJudge image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public MeetJudge imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Long getRank() {
        return rank;
    }

    public MeetJudge rank(Long rank) {
        this.rank = rank;
        return this;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MeetJudge meetJudge = (MeetJudge) o;
        if (meetJudge.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, meetJudge.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MeetJudge{" +
            "id=" + id +
            ", image='" + image + "'" +
            ", imageContentType='" + imageContentType + "'" +
            ", rank='" + rank + "'" +
            '}';
    }
}
