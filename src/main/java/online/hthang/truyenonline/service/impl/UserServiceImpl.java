package online.hthang.truyenonline.service.impl;

import online.hthang.truyenonline.entity.Role;
import online.hthang.truyenonline.entity.User;
import online.hthang.truyenonline.projections.ConveterSummary;
import online.hthang.truyenonline.projections.TopConverter;
import online.hthang.truyenonline.repository.RoleRepository;
import online.hthang.truyenonline.repository.UserRepository;
import online.hthang.truyenonline.service.UserService;
import online.hthang.truyenonline.utils.ConstantsListUtils;
import online.hthang.truyenonline.utils.ConstantsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Huy Thang
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;


    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User getUserByUserName(String username) {
        return userRepository.findByUName(username);
    }

    @Override
    public Page< User > getPageUser() {
        return userRepository.findAll(new PageRequest(0, 1));
    }

    @Override
    public List< TopConverter > getTopConverter(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page< TopConverter > result = userRepository
                .getTopConverter(ConstantsListUtils.LIST_CHAPTER_DISPLAY,
                        ConstantsListUtils.LIST_STORY_DISPLAY,
                        ConstantsUtils.STATUS_ACTIVED,
                        pageable);
        return result.getContent();
    }

    @Override
    public boolean registerUser(User user) {
        List< Role > roleList = new ArrayList<>();
        Role role = roleRepository.findById(ConstantsUtils.ROLE_USER).get();
        roleList.add(role);
        user.setRoleList(roleList);
        User newUser = userRepository.save(user);
        return newUser.getUID() != null;
    }

    @Override
    public boolean checkEmailExits(String email) {
        return userRepository.existsUserByUEmail(email);
    }

    @Override
    public boolean checkUserNameExits(String userName) {
        return userRepository.existsUserByUName(userName);
    }

    /**
     * Lấy User
     *
     * @param userName
     * @param email
     * @return User
     */
    @Override
    public User getForgotUser(String userName, String email) {
        return userRepository.findByUNameAndUEmail(userName, email);
    }

    /**
     * Cập Nhật User
     *
     * @param user
     * @return User
     */
    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional< User > getUserByID(Long uID) {
        return userRepository.findById(uID);
    }

    /**
     * Cập nhật uDName của User
     *
     * @param uID
     * @param dName
     * @return void
     */
    @Override
    public void updateDnameOfUser(Long uID, String dName) {
        User user = userRepository.findById(uID).get();
        user.setUDname(dName);
        userRepository.save(user);
    }

    /**
     * Cập nhật uDName và Gold của User
     *
     * @param uID
     * @param dName
     * @param price
     * @return void
     */
    @Override
    public void updateDnameAndGoldOfUser(Long uID, String dName, Double price) {
        User user = userRepository.findById(uID).get();
        user.setUDname(dName);
        user.setGold(user.getGold() - price);
        userRepository.save(user);
    }

    /**
     * Kiểm tra DName đã tồn tại chưa
     *
     * @param uID
     * @param dName
     * @return boolean
     */
    @Override
    public boolean checkUserdNameExits(Long uID, String dName) {
        return userRepository.existsByUIDNotAndAndUDname(uID, dName);
    }

    /**
     * Cập nhật notification  của User
     *
     * @param uID
     * @param notification
     * @return void
     */
    @Override
    public void updateNotificationOfUser(Long uID, String notification) {
        User user = userRepository.findById(uID).get();
        user.setNotification(notification);
        userRepository.save(user);
    }

    /**
     * Cập nhật Avatar  của User
     *
     * @param uID
     * @param avatar
     * @return void
     */
    @Override
    public void updateAvatarOfUser(Long uID, String avatar, Double price) {
        User user = userRepository.findById(uID).get();
        user.setUAvatar(avatar);
        user.setGold(user.getGold() - price);
        userRepository.save(user);
    }

    /**
     * Lấy Thông TIn Converter
     *
     * @param uID
     * @return ConverterSummary
     */
    @Override
    public ConveterSummary getConverterByID(Long uID) {
        return userRepository.findUserByUID(uID);
    }
}
