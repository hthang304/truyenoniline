package online.hthang.truyenonline.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Huy Thang
 */
@Entity
@Table(name = "category", uniqueConstraints = {@UniqueConstraint(columnNames = "metatitle"),
        @UniqueConstraint(columnNames = "name")})
@Data
@NoArgsConstructor
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Integer id;
    @Column(name = "name", unique = true, nullable = false, length = 150)
    private String name;
    @Column(name = "metatitle", unique = true, nullable = false, length = 150)
    private String metatitle;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "createDate", length = 19)
    private Date createDate;
    @Column(name = "createBy", length = 150)
    private String createBy;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "modifiedDate", length = 19)
    private Date modifiedDate;
    @Column(name = "modifiedBy", length = 150)
    private String modifiedBy;
    @Column(name = "status")
    private Integer status;
}
