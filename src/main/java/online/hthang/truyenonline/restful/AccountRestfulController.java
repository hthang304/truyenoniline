package online.hthang.truyenonline.restful;

import online.hthang.truyenonline.entity.MyUserDetails;
import online.hthang.truyenonline.entity.User;
import online.hthang.truyenonline.exception.*;
import online.hthang.truyenonline.service.CloudinaryUploadService;
import online.hthang.truyenonline.service.PayService;
import online.hthang.truyenonline.service.UserService;
import online.hthang.truyenonline.utils.ConstantsUtils;
import online.hthang.truyenonline.utils.WebUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Optional;

/**
 * @author Huy Thang on 23/10/2018
 * @project truyenonline
 */

@RestController
@RequestMapping(value = "/api")
public class AccountRestfulController {

    private final static Logger logger = LoggerFactory.getLogger(AccountRestfulController.class);
    private final PayService payService;
    private final UserService userService;
    private final CloudinaryUploadService cloudinaryUploadService;

    @Autowired
    public AccountRestfulController(PayService payService, UserService userService, CloudinaryUploadService cloudinaryUploadService) {
        this.payService = payService;
        this.userService = userService;
        this.cloudinaryUploadService = cloudinaryUploadService;
    }

    @PostMapping(value = "/saveDName")
    @Transactional
    public ResponseEntity< Object > saveUserDisplayName(@RequestParam(value = "uDname") String uDname,
                                                        Principal principal)
            throws Exception {
        if (principal == null) {
            throw new HttpNotLoginException();
        }
        MyUserDetails myUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
        User user = myUser.getUser();
        Optional< User > optionalUser = userService.getUserByID(user.getUID());
        if (!optionalUser.isPresent()) {
            throw new HttpNotLoginException("Tài khoản không tồn tại");
        }
        user = optionalUser.get();
        if (user.getUStatus().equals(ConstantsUtils.STATUS_DENIED)) {
            throw new HttpUserLockedException();
        }
        String uDName = uDname.trim();
        if (uDName == null || uDName.isEmpty() || uDName.length() > 25) {
            throw new HttpSizeException("Ngoại hiệu không được để trống hoặc dài quá 25 ký tự!");
        }
        if (uDName.equalsIgnoreCase(user.getUDname())) {
            throw new HttpMyException("Ngoại hiệu này bạn đang sử dụng");
        }
        if (userService.checkUserdNameExits(user.getUID(), uDName)) {
            throw new HttpMyException("Ngoại hiệu đã tồn tại!");
        }
        if (user.getUDname() == null || user.getUDname().isEmpty()) {
            userService.updateDnameOfUser(user.getUID(), uDName);
            payService.savePay(user.getUID(), (double) 0, ConstantsUtils.PAY_DNAME_STATUS);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        if (user.getGold() < ConstantsUtils.PRICE_UPDATE_DNAME) {
            throw new HttpUserGoldException();
        }
        userService
                .updateDnameAndGoldOfUser(user.getUID(), uDName, ConstantsUtils.PRICE_UPDATE_DNAME);
        payService.savePay(user.getUID(), ConstantsUtils.PRICE_UPDATE_DNAME, ConstantsUtils.PAY_DNAME_STATUS);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/saveNotification")
    public ResponseEntity< Object > saveUserNotification(@RequestParam(value = "notification") String notification,
                                                         Principal principal)
            throws Exception {
        if (principal == null) {
            throw new HttpNotLoginException();
        }
        MyUserDetails myUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
        User user = myUser.getUser();
        Optional< User > optionalUser = userService.getUserByID(user.getUID());
        if (!optionalUser.isPresent()) {
            throw new HttpNotLoginException("Tài khoản không tồn tại");
        }
        user = optionalUser.get();
        if (user.getUStatus().equals(ConstantsUtils.STATUS_DENIED)) {
            throw new HttpUserLockedException();
        }
        if (notification.trim().length() > 255) {
            throw new HttpSizeException("Thông báo không được dài quá 255 ký tự!");
        }
        userService.updateNotificationOfUser(user.getUID(), notification.trim());
        logger.info("Đã Cập Nhật");
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping(value = "/upload")
    @Transactional
    public ResponseEntity< Object > saveUserAvatar(@RequestParam("userfile") MultipartFile uploadfile,
                                                   Principal principal) throws Exception {
        if (principal == null) {
            throw new HttpNotLoginException();
        }
        MyUserDetails myUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
        User user = myUser.getUser();
        Optional< User > optionalUser = userService.getUserByID(user.getUID());
        if (!optionalUser.isPresent()) {
            throw new HttpNotLoginException("Tài khoản không tồn tại");
        }
        user = optionalUser.get();
        if (user.getUStatus().equals(ConstantsUtils.STATUS_DENIED)) {
            throw new HttpUserLockedException();
        }
        if (user.getUAvatar() == null || user.getUAvatar().isEmpty()) {
            String url = cloudinaryUploadService.upload(uploadfile, user.getUName());
            userService.updateAvatarOfUser(user.getUID(), url, (double) 0);
            payService.savePay(user.getUID(), (double) 0, ConstantsUtils.PAY_AVATAR_STATUS);
            return new ResponseEntity<>(url, HttpStatus.OK);
        }
        if (user.getGold() < ConstantsUtils.PRICE_AVATAR_DNAME) {
            throw new HttpUserGoldException();
        }
        String fileExtension = FilenameUtils.getExtension(uploadfile.getOriginalFilename());
        if (!WebUtils.checkExtension(fileExtension)) {
            throw new HttpMyException("Chỉ upload ảnh có định dạng JPG | JPEG | PNG!");
        }
        if (uploadfile.getSize() > (20 * 1024 * 1024)) {
            throw new HttpSizeException("Kích thước ảnh upload tối đa là 20 Megabybtes!");
        }
        ;
        String url = cloudinaryUploadService.upload(uploadfile, user.getUName() + "-" + System.nanoTime());
        userService.updateAvatarOfUser(user.getUID(), url, ConstantsUtils.PRICE_AVATAR_DNAME);
        payService.savePay(user.getUID(),
                ConstantsUtils.PRICE_AVATAR_DNAME,
                ConstantsUtils.PAY_AVATAR_STATUS);
        return new ResponseEntity<>(url, HttpStatus.OK);
    }

}
