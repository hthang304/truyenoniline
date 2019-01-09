package online.hthang.truyenonline.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Huy Thang
 */
@Entity
@Table(name = "story")
@Data
public class Story implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sID", unique = true, nullable = false)
    private Long sID;
    @Column(name = "vnName", nullable = false)
    private String vnName;
    @Column(name = "cnName")
    private String cnName;
    @Column(name = "cnLink")
    private String cnLink;
    @Column(name = "images", nullable = false, length = 150)
    private String images;
    @Column(name = "author", nullable = false)
    private String author;
    @Column(name = "infomation", columnDefinition = "TEXT")
    private String infomation;
    @Column(name = "countAppoint")
    private Integer countAppoint;
    @Column(name = "countView")
    private Integer countView;
    @Column(name = "rating", precision = 12, scale = 0)
    private Float rating;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "createDate", length = 19)
    private Date createDate;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "modifiedBy", length = 19)
    private Date modifiedBy;
    @Column(name = "price", precision = 22, scale = 0)
    private Double price;
    @Column(name = "timeDeal")
    private Integer timeDeal;
    @Column(name = "dealStatus")
    private Integer dealStatus;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "updateDate", length = 19)
    private Date updateDate;
    @Column(name = "status")
    private Integer status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userPosted", referencedColumnName = "uID", nullable = false)
    private User user;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "story_category",
            joinColumns = {@JoinColumn(name = "storyId", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "categoryId", nullable = false)})
    private List<Category> categoryList;


}
