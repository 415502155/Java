package com.bestinfo.arithmetic.struct;

/**
 *
 * @author chenliping
 */
public enum province_value {

    PRO_GUANGDONG_VALUE(44),
    PRO_SHANGHAI_VALUE(31);
    private final int value;

    private province_value(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
