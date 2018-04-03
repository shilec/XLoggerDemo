package com.shilec.xlogger;

/**
 * <p>Author:    shijiale</p>
 * <p>Date:      2018-04-03 15:18</p>
 * <p>Email:     shilec@126.com</p>
 * <p>Describe:</p>
 */
public interface ILoggerFormater {

    String format(String tag, String msg, Throwable throwable);

    String formatFileLog(String tag, String msg, Throwable throwable);
}
