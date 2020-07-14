package cn.org.bachelor.architecture.ms.gateway.ratelimit;

import io.github.bucket4j.*;
import org.springframework.cloud.gateway.filter.ratelimit.AbstractRateLimiter;
import org.springframework.cloud.gateway.support.ConfigurationService;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2019/1/4
 */
public class InMemoryRateLimiter extends AbstractRateLimiter<InMemoryRateLimiter.Config> {

    public static final String CONFIGURATION_PROPERTY_NAME = "in-memory-rate-limiter";

    private InMemoryRateLimiter.Config defaultConfig;

    private final Map<String, Bucket> ipBucketMap = new ConcurrentHashMap<>();

    public InMemoryRateLimiter() {
        super(InMemoryRateLimiter.Config.class, CONFIGURATION_PROPERTY_NAME, new ConfigurationService());
    }

    public InMemoryRateLimiter(int defaultReplenishRate, int defaultBurstCapacity) {
        super(Config.class, CONFIGURATION_PROPERTY_NAME, new ConfigurationService());
        this.defaultConfig = new InMemoryRateLimiter.Config()
                .setReplenishRate(defaultReplenishRate)
                .setBurstCapacity(defaultBurstCapacity);
    }

    @Override
    public Mono<Response> isAllowed(String routeId, String id) {
        InMemoryRateLimiter.Config routeConfig = getConfig().get(routeId);
        if (routeConfig == null) {
            routeConfig = getConfig().get("defaultFilters");
            if (routeConfig == null) {
                if (defaultConfig == null) {
                    throw new IllegalArgumentException("No Configuration found for route " + routeId);
                }
                routeConfig = defaultConfig;
            }
        }

        // How many requests per second do you want a user to be allowed to do?
        int replenishRate = routeConfig.getReplenishRate();

        // How much bursting do you want to allow?
        int burstCapacity = routeConfig.getBurstCapacity();

        Bucket bucket = ipBucketMap.computeIfAbsent(id, k -> {
            Refill refill = Refill.greedy(replenishRate, Duration.ofSeconds(1));
            Bandwidth limit = Bandwidth.classic(burstCapacity, refill);
            return Bucket4j.builder().addLimit(limit).build();
        });

        // tryConsume returns false immediately if no tokens available with the bucket
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            // the limit is not exceeded
            return Mono.just(new Response(true, probe.getRemainingTokens()));
        } else {
            // limit is exceeded
            return Mono.just(new Response(false, -1));
        }
    }

    @Validated
    public static class Config {
        private int replenishRate = 2;

        private int burstCapacity = 0;

        public int getReplenishRate() {
            return replenishRate;
        }

        public InMemoryRateLimiter.Config setReplenishRate(int replenishRate) {
            this.replenishRate = replenishRate;
            return this;
        }

        public int getBurstCapacity() {
            return burstCapacity;
        }

        public InMemoryRateLimiter.Config setBurstCapacity(int burstCapacity) {
            this.burstCapacity = burstCapacity;
            return this;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "replenishRate=" + replenishRate +
                    ", burstCapacity=" + burstCapacity +
                    '}';
        }
    }
}


