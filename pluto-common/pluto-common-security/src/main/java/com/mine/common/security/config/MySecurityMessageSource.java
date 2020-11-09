package com.mine.common.security.config;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.SpringSecurityMessageSource;

public class MySecurityMessageSource extends SpringSecurityMessageSource {

    // ~ Constructors
    // ===================================================================================================

    public MySecurityMessageSource() {
        setBasename("security.messages");
    }

    // ~ Methods
    // ========================================================================================================

    public static MessageSourceAccessor getAccessor() {
        return new MessageSourceAccessor(new MySecurityMessageSource());
    }

}
