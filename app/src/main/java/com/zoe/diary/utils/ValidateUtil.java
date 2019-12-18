package com.zoe.diary.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author zoe
 * created 2019/12/18 11:43
 */

public class ValidateUtil {

    public static boolean validateEmail(String email) {
        //查找邮箱
        //mailRegex是整体邮箱正则表达式，mailName是@前面的名称部分，mailDomain是后面的域名部分
        String mailRegex, mailName, mailDomain;
        mailName = "^[0-9a-z]+\\w*";       //^表明一行以什么开头；^[0-9a-z]表明要以数字或小写字母开头；\\w*表明匹配任意个大写小写字母或数字或下划线
        mailDomain = "([0-9a-z]+\\.)+[0-9a-z]+$";       //***.***.***格式的域名，其中*为小写字母或数字;第一个括号代表有至少一个***.匹配单元，而[0-9a-z]$表明以小写字母或数字结尾
        mailRegex = mailName + "@" + mailDomain;          //邮箱正则表达式      ^[0-9a-z]+\w*@([0-9a-z]+\.)+[0-9a-z]+$
        Pattern pattern = Pattern.compile(mailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
