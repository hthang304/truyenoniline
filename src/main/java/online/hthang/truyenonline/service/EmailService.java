package online.hthang.truyenonline.service;

import online.hthang.truyenonline.entity.Mail;

/**
 * @author Huy Thang
 */
public interface EmailService {

    public boolean sendSimpleMessage(Mail mail);

}
