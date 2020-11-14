package com.mine.common.dynamic.gateway.support;

import com.mine.common.dynamic.gateway.vo.RouteDefinitionVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description redis 保存路由信息，优先级比配置文件高
 * @Author
 * @Date
 */
@Slf4j
@Component
@AllArgsConstructor
@SuppressWarnings("all")
public class RedisRouteDefinitionWriter implements RouteDefinitionRepository {

    /**
     * 路由存放
     */
    String ROUTE_KEY = "gateway_route_key";

    
    private final RedisTemplate redisTemplate;

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(r -> {
            RouteDefinitionVO vo = new RouteDefinitionVO();
            BeanUtils.copyProperties(r, vo);
            log.info("保存路由信息{}", vo);
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.opsForHash().put(ROUTE_KEY, r.getId(), vo);
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        routeId.subscribe(id -> {
            log.info("删除路由信息{}", id);
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.opsForHash().delete(ROUTE_KEY, id);
        });
        return Mono.empty();
    }

    /**
     * 动态路由入口
     *
     * @return
     */
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(RouteDefinitionVO.class));
        List<RouteDefinitionVO> values = redisTemplate.opsForHash().values(ROUTE_KEY);
        List<RouteDefinition> definitionList = new ArrayList<>();
        values.forEach(vo -> {
            RouteDefinition routeDefinition = new RouteDefinition();
            BeanUtils.copyProperties(vo, routeDefinition);
            definitionList.add(vo);
        });
        log.debug("redis 中路由定义条数： {}， {}", definitionList.size(), definitionList);
        return Flux.fromIterable(definitionList);
    }
}
