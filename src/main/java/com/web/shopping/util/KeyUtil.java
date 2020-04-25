package com.web.shopping.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class KeyUtil {

    /**
     * 生成唯一的主键
     * 格式: 时间+随机数
     * @return
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(number);
    }

    /**
     * 时间+随机数
     * @return
     */
    public static synchronized String genUniqueNO() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");

        return simpleDateFormat.format(now) + String.valueOf(number);
    }

    public static synchronized Long genUniqueLongKey() {
        return System.currentTimeMillis();
    }



}
