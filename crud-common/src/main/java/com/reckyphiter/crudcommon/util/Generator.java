package com.reckyphiter.crudcommon.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Recky Phiter
 */
public class Generator {

    public static String generateMessageId() {
        final String uuid = UUID.randomUUID().toString().replace("-", "");
        final String date = new SimpleDateFormat("MMddHHmmssSS").format(new Date());
        return date + uuid.substring(0,11);
    }
}
