package com.shilec.xlogger;

import android.os.Environment;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Author:    shijiale</p>
 * <p>Date:      2018-04-03 15:12</p>
 * <p>Email:     shilec@126.com</p>
 * <p>Describe:</p>
 */
public class Config {

    private Config() {

    }

    Builder builder;

    public static class Builder {
        ILoggerFormater formater;

        boolean isDebug;

        Map<String,String> pathsMap = new HashMap<>();

        public Builder() {

        }

        public Builder setLogSavePath(Map<String,String> pathsMap) {
            this.pathsMap = pathsMap;
            return this;
        }

        public Builder addLogSavePath(String tag,String path) {
            pathsMap.put(tag,path);
            return this;
        }

        public Builder setFormater(ILoggerFormater formater) {
            this.formater = formater;
            return this;
        }

        public Builder enableDebug() {
            isDebug = true;
            return this;
        }

        public Builder disableDebug() {
            isDebug = false;
            return this;
        }

        public Config build() {
            Config config = new Config();
            if(formater == null) {
                formater = new DefaultLoggerFormater();
            }

            if(pathsMap.isEmpty()) {
                String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                absolutePath += File.separator + "xlogger";
                pathsMap.put(XLogger.DEFAULT_SAVE_TAG,absolutePath);
            }
            config.builder = this;
            return config;
        }
    }
}
