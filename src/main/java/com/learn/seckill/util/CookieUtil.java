package com.learn.seckill.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
    public static final String COOKIE_NAME_TOKEN = "token_seckill";
    public static final int TOKEN_EXPIRE = 3600*24;//默认两天

    public static void addCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(TOKEN_EXPIRE);
        cookie.setPath("/");//设置为网站根目录
        response.addCookie(cookie);
    }

    public static String hasCookie(Cookie[] cookies){
        for (Cookie c:cookies
             ) {
            if (c.getName().equals(COOKIE_NAME_TOKEN)){
                return c.getValue();
            }
        }

        return null;
    }
}
