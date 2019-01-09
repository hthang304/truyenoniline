package online.hthang.truyenonline.projections;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * @author Huy Thang on 20/11/2018
 * @project truyenonline
 */
public interface StorySummary {

    Long getsID();

    String getvnName();

    String getcnName();

    String getcnLink();

    String getsImages();

    String getsAuthor();

    //Lấy Thông Tin
    String getsInfo();

    //Lấy Điểm đánh giá
    Float getsRating();

    //Lấy ID Converter
    @Value("#{target.sConverter.uID}")
    Long getuID();

    //Lấy Chapter Đầu Tiên
    @Value("#{@myComponent.getChapterHead(target.sID)}")
    ChapterSummary getchHead();

    //Lấy Chapter Mới Nhất
    @Value("#{@myComponent.getNewChapter(target.sID)}")
    ChapterSummary getchNew();

    List<CatogorySummary> getCategoryList();

    Integer getsStatus();
}
