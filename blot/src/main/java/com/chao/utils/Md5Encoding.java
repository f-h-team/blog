package com.chao.utils;

import cn.hutool.crypto.SecureUtil;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Md5Encoding implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return SecureUtil.md5(charSequence.toString());
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {

        return s.equals(SecureUtil.md5(charSequence.toString()));
    }
}
