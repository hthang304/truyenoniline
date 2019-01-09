package online.hthang.truyenonline.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * @author Huy Thang
 */
@Entity
@Table(name = "pay", schema = "")
@Data
public class Pay implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payID", unique = true, nullable = false)
    private Long payID;
    @JoinColumn(name = "payerID", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User payer;
    @JoinColumn(name = "receiverID", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User receiver;
    @Column(name = "price", precision = 22, scale = 0)
    private Double price;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createDate", length = 19)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createDate;
    @JoinColumn(name = "chID", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Chapter chapter;
    @JoinColumn(name = "sID", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Story story;
    @Column(name = "payStatus")
    private Integer payStatus;

}
