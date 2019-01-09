package online.hthang.truyenonline.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Double.*;

/**
 * @author Huy Thang
 */
public class ConstantsUtils {

    public static final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    public static final int RANDOM_STRING_LENGTH = 6;

    //Thời gian hiệu lực trả tiền xem Chương Vip (Giờ)
    public static final long CHAPTER_VIP_DEAL_DATE = 24;

    //Thời gian giãn cách tính lượt xem với mỗi Chapter (Phút)
    public static final long CHAPTER_FAVORITES_TIME = 30;
    //Trạng Thái Bị Ẩn
    public static final Integer CHAPTER_DENIED = 0;
    //Trạng Thái Chapter Free Hiện
    public static final Integer CHAPTER_ACTIVED = 1;
    //Trang Thái Chapter Vip Hiện
    public static final Integer CHAPTER_VIP_ACTIVED = 2;
    //Trạng thái bị ẩn
    public static Integer STATUS_DENIED = 0;
    //Trạng thái kích hoạt
    public static Integer STATUS_ACTIVED = 1;
    //Trạng thái truyện bị ẩn
    public static Integer STORY_STATUS_HIDDEN = 0;
    //Trạng thái truyện đang ra
    public static Integer STORY_STATUS_GOING_ON = 1;
    //Trang thái truyện hoàn thành
    public static Integer STORY_STATUS_COMPLETED = 2;
    // Size Swapper
    public static Integer PAGE_SIZE_SWAPPER = 5;
    //Số Story Trong Home Page
    public static Integer PAGE_SIZE_HOME = 18;
    // Số Story Default
    public static Integer PAGE_SIZE_DEFAULT = 20;
    // Page Default
    public static Integer PAGE_DEFAULT = 1;

    // Trạng Thái Nạp Tiền
    public static Integer RECHARGE_STATUS = 0;

    // Trạng Thái Thanh Toán Truyện
    public static Integer PAY_STATUS = 1;

    //Trạng Thái Thanh Toán Đổi Ngoại Hiệu
    public static Integer PAY_DNAME_STATUS = 2;

    //Trạng Thái Thanh Toán Đổi Avatar
    public static Integer PAY_AVATAR_STATUS = 3;

    // Trạng Thái Đề Cử
    public static Integer APPOINT_STATUS = 4;

    public static Integer RANK_SIZE = 10;

    public static Integer PAGE_SIZE_CHAPTER_OF_STORY = 45;

    public static Integer PAGE_SIZE_COMMENT_OF_STORY = 10;

    public static Integer ROLE_ADMIN = 1;

    public static Integer ROLE_SMOD = 2;

    public static Integer ROLE_CONVERTER = 3;

    public static Integer ROLE_USER = 4;

    //Link avartar default
    public static final String AVATAR_DEFAULT = "https://res.cloudinary.com/thang1988/image/upload/v1541176548/truyenmvc/logo.png";

    public static final Double PRICE_UPDATE_DNAME = valueOf(2000);

    public static final Double PRICE_AVATAR_DNAME = valueOf(1000);
}
