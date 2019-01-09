package online.hthang.truyenonline.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Huy Thang on 16/11/2018
 * @project truyenonline
 */
public class ConstantsListUtils {

    public static final List<Integer> LIST_STORY_DISPLAY = Collections.unmodifiableList(
            new ArrayList<Integer>() {{
                add(ConstantsUtils.STORY_STATUS_COMPLETED);
                add(ConstantsUtils.STORY_STATUS_GOING_ON);
            }}
    );

    public static final List<Integer> LIST_CHAPTER_DISPLAY = Collections.unmodifiableList(
            new ArrayList<Integer>() {{
                add(ConstantsUtils.CHAPTER_ACTIVED);
                add(ConstantsUtils.CHAPTER_VIP_ACTIVED);
            }}
    );
}
