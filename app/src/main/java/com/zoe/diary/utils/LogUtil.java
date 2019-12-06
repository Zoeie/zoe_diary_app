package com.zoe.diary.utils;

import android.util.Log;

import com.zoe.diary.BuildConfig;

/**
 * @author: cjl
 * @date: 2018/6/28 15:45
 * @desc: 日志工具类  打印出的日志格式：方法名(类名:行号)
 */

public abstract class LogUtil {

    private static String className;  //类名
    private static String methodName; //方法名
    private static int lineNumber;   //行数
    private static final int LEVEL_VERBOSE = 1;
    private static final int LEVEL_DEBUG = 2;
    private static final int LEVEL_INFO = 3;
    private static final int LEVEL_WARN = 4;
    private static final int LEVEL_ERROR = 5;

    private static void getLogInfo(StackTraceElement[] traceElements) {
        className = traceElements[1].getFileName();
        methodName = traceElements[1].getMethodName();
        lineNumber = traceElements[1].getLineNumber();
    }

    public static void v(String message) {
        getLogInfo(new Throwable().getStackTrace());
        String log = createLog(message, LEVEL_VERBOSE);
        if (BuildConfig.DEBUG) {
            Log.v(className, log);
        }
    }

    public static void d(String message) {
        getLogInfo(new Throwable().getStackTrace());
        String log = createLog(message, LEVEL_DEBUG);
        if (BuildConfig.DEBUG) {
            Log.d(className, log);
        }
    }

    public static void i(String message) {
        getLogInfo(new Throwable().getStackTrace());
        String log = createLog(message, LEVEL_INFO);
        if (BuildConfig.DEBUG) {
            Log.i(className, log);
        }
    }

    public static void w(String message) {
        getLogInfo(new Throwable().getStackTrace());
        String log = createLog(message, LEVEL_WARN);
        if (BuildConfig.DEBUG) {
            Log.w(className, log);
        }
    }

    public static void e(String message) {
        getLogInfo(new Throwable().getStackTrace());
        String log = createLog(message, LEVEL_ERROR);
        if (BuildConfig.DEBUG) {
            Log.e(className, log);
        }
    }

    private static String createLog(String log, int level) {
        //TODO 写入本地log
        return methodName + "(" + className + ":" + lineNumber + ")" + log;
    }

}
