package com.shilec.xlogger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>Author:    shijiale</p>
 * <p>Date:      2018-04-03 15:24</p>
 * <p>Email:     shilec@126.com</p>
 * <p>Describe:</p>
 */
public class DefaultLoggerFormater implements ILoggerFormater {

    @Override
    public String format(String tag, String msg, Throwable throwable) {
        StringBuilder sb = new StringBuilder();

        StackTraceElement[] stackTrace = throwable.getStackTrace();
        sb.append("[ ");
        sb.append(stackTrace[1].getClassName() + "." + stackTrace[1].getMethodName());
        sb.append(" : ");
        sb.append(stackTrace[1].getLineNumber());

        sb.append(" ]\r\n");
        sb.append(msg);
        return sb.toString();
    }

    @Override
    public String formatFileLog(String tag, String msg, Throwable throwable) {
        String log = format(tag,msg,throwable);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        log = "[" + sdf.format(new Date(System.currentTimeMillis())) + "]" + "\n" + log;
        return log;
    }
}
