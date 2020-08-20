package com.mine.common.feign.hystrix;

import com.mine.common.core.util.R;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

//FallbackFactory的优点是可以获取到异常信息
@Slf4j
@Component
public class MyFeignFallbackFactory implements FallbackFactory<R> {

    @Override
    public R create(Throwable throwable) {
        log.info(throwable.getLocalizedMessage());

        throwable.printStackTrace();

        String message = throwable.getMessage();
        return R.ok(message);
    }
}