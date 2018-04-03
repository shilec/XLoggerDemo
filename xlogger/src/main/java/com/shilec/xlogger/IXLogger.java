package com.shilec.xlogger;

/**
 * <p>Author:    shijiale</p>
 * <p>Date:      2018-04-03 15:08</p>
 * <p>Email:     shilec@126.com</p>
 * <p>Describe:</p>
 */
public interface IXLogger {

    void i(String msg);

    void i(String tag, String msg);

    void i2file(String msg);

    void i2file(String tag, String msg);

    void e(String error);

    void e(String tag, String error);

    void e2file(String tag, String error);

    void e2file(String error);

    void d(String debug);

    void d(String tag, String debug);

    void d2file(String debug);

    void d2file(String tag, String msg);
}
