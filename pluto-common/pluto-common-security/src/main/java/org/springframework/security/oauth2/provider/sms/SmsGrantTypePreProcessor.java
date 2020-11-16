package org.springframework.security.oauth2.provider.sms;

import com.mine.common.core.constant.RedisPrefixConstants;
import com.mine.common.core.constant.SecurityConstants;
import com.mine.common.core.util.WebUtils;
import com.mine.common.security.config.MySecurityMessageSource;
import com.mine.common.security.util.ApplicationContextAwareUtil;
import lombok.experimental.UtilityClass;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 验证码处理
 */
@UtilityClass
@SuppressWarnings("rawtypes")
public class SmsGrantTypePreProcessor {

    MessageSourceAccessor messages = MySecurityMessageSource.getAccessor();

    // ~ Static fields/initializers
    // =====================================================================================

    String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    String SPRING_SECURITY_FORM_SMS_KEY = "code";

    private RedisTemplate getRedisTemplate() {
        return ApplicationContextAwareUtil.getBean(SecurityConstants.REDIS_TEMPLATE_BEAN_NAME);
    }

    // ~ Methods
    // ========================================================================================================
    public void doHandler() throws BadCredentialsException {
        HttpServletRequest request = WebUtils.getRequest();
        final String username = request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
        final String code = request.getParameter(SPRING_SECURITY_FORM_SMS_KEY);
        final Object cacheCode = getRedisTemplate().opsForValue().get(RedisPrefixConstants.VERIFY_CODE + username);
        if (Objects.isNull(cacheCode) || !code.equals(cacheCode.toString())) {
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractAccessDecisionManager.invalidVerifyCode", "code is incorrect or expired"));
        }
    }
}
