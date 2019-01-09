package online.hthang.truyenonline.service;

import online.hthang.truyenonline.entity.User;
import online.hthang.truyenonline.projections.ConveterSummary;
import online.hthang.truyenonline.projections.TopConverter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * @author Huy Thang
 */
public interface UserService {

    User getUserByUserName(String username);

    Page< User > getPageUser();

    /**
     * Lấy List Top Converter
     *
     * @param page
     * @param size
     * @return List<TopConverter>
     */
    List< TopConverter > getTopConverter(int page, int size);

    /**
     * Cập Nhật User
     *
     * @param user
     * @return boolean
     */
    boolean registerUser(User user);

    /**
     * Kiểm tra Email đã tồn tại chưa
     *
     * @param email
     * @return boolean
     */
    boolean checkEmailExits(String email);

    /**
     * Kiểm tra UserName đã tồn tại chưa
     *
     * @param userName
     * @return boolean
     */
    boolean checkUserNameExits(String userName);

    /**
     * Lấy User
     *
     * @param userName
     * @param email
     * @return User
     */
    User getForgotUser(String userName, String email);

    /**
     * Cập Nhật User
     *
     * @param user
     * @return User
     */
    User updateUser(User user);

    /**
     * Lấy User Theo uID
     *
     * @param uID
     * @return Optional<User>
     */
    Optional< User > getUserByID(Long uID);

    /**
     * Cập nhật uDName của User
     *
     * @param uID
     * @param dName
     * @return void
     */
    void updateDnameOfUser(Long uID, String dName);

    /**
     * Cập nhật uDName và Gold của User
     *
     * @param uID
     * @param dName
     * @param price
     * @return void
     */
    void updateDnameAndGoldOfUser(Long uID, String dName, Double price);

    /**
     * Kiểm tra DName đã tồn tại chưa
     *
     * @param uID
     * @param dName
     * @return boolean
     */
    boolean checkUserdNameExits(Long uID, String dName);

    /**
     * Cập nhật notification  của User
     *
     * @param notification
     * @param uID
     * @return void
     */
    void updateNotificationOfUser(Long uID, String notification);

    /**
     * Cập nhật Avatar  của User
     *
     * @param avatar
     * @param uID
     * @param price
     * @return void
     */
    void updateAvatarOfUser(Long uID, String avatar, Double price);

    /**
     * Lấy Thông TIn Converter
     *
     * @param uID
     * @return ConverterSummary
     */
    ConveterSummary getConverterByID(Long uID);
}
