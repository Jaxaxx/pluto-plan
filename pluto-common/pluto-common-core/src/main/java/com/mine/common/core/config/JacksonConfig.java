//package com.mine.common.core.config;
//
//import cn.hutool.core.date.DatePattern;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.mine.common.core.jackson.MyJavaTimeModule;
//import org.springframework.boot.autoconfigure.AutoConfigureBefore;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
//import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.ZoneId;
//import java.util.Locale;
//import java.util.TimeZone;
//
///**
// * @Description JacksonConfig
// * @Author
// * @Date
// */
//@Configuration
//@ConditionalOnClass(ObjectMapper.class)
//@AutoConfigureBefore(JacksonAutoConfiguration.class)
//public class JacksonConfig {
//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer customizer() {
//        return builder -> {
//            builder.locale(Locale.CHINA);
//            builder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
//            builder.simpleDateFormat(DatePattern.NORM_DATETIME_PATTERN);
//            builder.modules(new MyJavaTimeModule());
//            builder.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//        };
//    }
//}
