package com.shilec.xlogger;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>Author:    shijiale</p>
 * <p>Date:      2018-04-03 15:17</p>
 * <p>Email:     shilec@126.com</p>
 * <p>Describe:</p>
 */
public class XLogger implements IXLogger {

    public static final String DEFAULT_SAVE_TAG = "default";

    static Config sConfig;

    static IXLogger sInstance;

    public synchronized static IXLogger getDefault() {
        if(sConfig == null) {
            throw new IllegalStateException("you must init xlogger first!");
        }
        if (sInstance == null) {
            sInstance = new XLogger();
        }
        return sInstance;
    }

    private ExecutorService mThreadPool;

    private XLogger() {
        mThreadPool = Executors.newSingleThreadExecutor();
    }

    public static void init(Config config) {
        if(sConfig != null) {
            return;
        }
        sConfig = config;
    }

    private void log(int level, boolean isNeedSave,String tag,
                     String msg, Throwable throwable) {
        if (!sConfig.builder.isDebug) {
            return;
        }

        if (tag == null) {
            tag = throwable.getStackTrace()[1].getClassName();
            tag = tag.substring(tag.lastIndexOf(".") + 1,tag.length());
        }
        String log = sConfig.builder.formater.format(tag, msg, throwable);
        switch (level) {
            case Log.INFO:
                Log.i(tag, log);
                break;
            case Log.DEBUG:
                Log.d(tag, log);
                break;
            case Log.ERROR:
                Log.e(tag, log);
                break;
            default:
                Log.i(tag, log);
        }

        if(!isNeedSave) {
            return;
        }
        log2file(tag,msg,throwable);
    }

    private void log2file(final String tag,final String msg,final Throwable throwable) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    writeLog(tag,msg,throwable);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        mThreadPool.execute(runnable);
    }

    private void writeLog(String tag,String msg,Throwable throwable) throws IOException {
        String path = sConfig.builder.pathsMap.get(tag);
        msg = sConfig.builder.formater.formatFileLog(tag,msg,throwable);
        if(path == null) {
            path = sConfig.builder.pathsMap.get(DEFAULT_SAVE_TAG);
        }
        File temp = new File(path);
        if(!temp.exists()) {
            temp.mkdirs();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH");
        String file_name = sdf.format(new Date(System.currentTimeMillis())) + ".xlog";
        File file = new File(path + File.separator + file_name);
        FileOutputStream fos = new FileOutputStream(file,true);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        bw.write(msg);
        bw.newLine();
        bw.flush();
        bw.close();
    }

    @Override
    public void i(String msg) {
        log(Log.INFO,false,null,msg,new Throwable());
    }

    @Override
    public void i(String tag, String msg) {
        log(Log.INFO,false,tag,msg,new Throwable());
    }

    @Override
    public void i2file(String msg) {
        log(Log.INFO,true,null,msg,new Throwable());
    }

    @Override
    public void i2file(String tag, String msg) {
        log(Log.INFO,true,tag,msg,new Throwable());
    }

    @Override
    public void e(String error) {
        log(Log.ERROR,false,null,error,new Throwable());
    }

    @Override
    public void e(String tag, String error) {
        log(Log.ERROR,false,tag,error,new Throwable());
    }

    @Override
    public void e2file(String tag, String error) {
        log(Log.ERROR,true,tag,error,new Throwable());
    }

    @Override
    public void e2file(String error) {
        log(Log.ERROR,true,null,error,new Throwable());
    }

    @Override
    public void d(String debug) {
        log(Log.DEBUG,false,null,debug,new Throwable());
    }

    @Override
    public void d(String tag, String debug) {
        log(Log.DEBUG,false,null,debug,new Throwable());
    }

    @Override
    public void d2file(String debug) {
        log(Log.DEBUG,true,null,debug,new Throwable());
    }

    @Override
    public void d2file(String tag, String msg) {
        log(Log.ERROR,false,null,msg,new Throwable());
    }
}
