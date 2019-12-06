package com.zoe.diary.net.log;

import androidx.annotation.NonNull;

import com.zoe.diary.utils.LogUtil;

import okhttp3.logging.HttpLoggingInterceptor;

public class HttpLogger implements HttpLoggingInterceptor.Logger {
    private StringBuilder mMessage = new StringBuilder();
    @Override
    public void log(@NonNull String message) {
        //请求或者响应开始
        if (message.startsWith("--> POST") || message.startsWith("--> GET")) {
            mMessage.setLength(0);
        }
        // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
        if ((message.startsWith("{") && message.endsWith("}"))
                || (message.startsWith("[") && message.endsWith("]"))) {
            //message = JsonUtils.formatJson(JsonUtils.decodeUnicode(message)); //暂时不进行json格式化
            mMessage.append(message.concat("\n"));
        } else if (message.startsWith("<--") && message.contains("http://")) {
            mMessage.append(message.concat("\n"));
        }
        // 响应结束，打印整条日志
        if (message.startsWith("<-- END HTTP")) {
            LogUtil.v(mMessage.toString());
        }
    }
}
