package org.bachelor.architecture.ms.console.vo;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2019/1/10
 */
public class RateLimitVo {


    private String keyResolver;
    private int replenishRate;

    private int burstCapacity;

    public int getReplenishRate() {
        return replenishRate;
    }

    public void setReplenishRate(int replenishRate) {
        this.replenishRate = replenishRate;
    }

    public int getBurstCapacity() {
        return burstCapacity;
    }

    public void setBurstCapacity(int burstCapacity) {
        this.burstCapacity = burstCapacity;
    }

    public String getKeyResolver() {
        return keyResolver;
    }

    public void setKeyResolver(String keyResolver) {
        this.keyResolver = keyResolver;
    }
}
