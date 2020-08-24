package com.mine.common.security.util;

import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@UtilityClass
public class PasswordEncoderUtil {

    public Boolean matches(String raw, String encoded) {
        return new BCryptPasswordEncoder().matches(raw, encoded);
    }

    public String encode(String raw) {
        return new BCryptPasswordEncoder().encode(raw);
    }

}
