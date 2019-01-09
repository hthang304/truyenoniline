package online.hthang.truyenonline.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Random;

public class WebUtils {

    public static boolean checkLongNumber(String number) {
        try {
            Long.parseLong(number);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean checkIntNumber(String number) {
        try {
            Integer.parseInt(number);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static int getRandomNumber() {
        int randomInt = 0;
        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(ConstantsUtils.CHAR_LIST.length());
        if (randomInt - 1 == -1) {
            return randomInt;
        } else {
            return randomInt - 1;
        }
    }

    public static String randomPassword() {
        StringBuffer randStr = new StringBuffer();
        for (int i = 0; i < ConstantsUtils.RANDOM_STRING_LENGTH; i++) {
            int number = getRandomNumber();
            char ch = ConstantsUtils.CHAR_LIST.charAt(number);
            randStr.append(ch);
        }
        return randStr.toString();
    }

    public static Integer checkPageNumber(String page) {
        int pagenumber = 1;

        // Kiểm tra page != null
        // Kiểm tra page có phải kiểu int
        // Kiểm tra page > 0
        if (page != null && WebUtils.checkIntNumber(page) && Integer.parseInt(page) > 0) {
            pagenumber = Integer.parseInt(page);
        }
        return pagenumber;
    }

    public static boolean checkExtension(String  fileExtension) {
        if (fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("jpeg")
                || fileExtension.equalsIgnoreCase("png")) {
            return true;
        }
        return false;
    }
}
