package com.bestinfo.arithmetic.struct;

/**
 *
 * @author chenliping
 */
public enum province_id {

    PRO_GUANGDONG(0),
    PRO_SHANGHAI(1);
    private final int id;

    private province_id(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
