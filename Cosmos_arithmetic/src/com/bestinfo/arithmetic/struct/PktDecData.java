package com.bestinfo.arithmetic.struct;

/**
 *
 * @author chenliping
 */
public class PktDecData {

    private byte[] pktDecrypt;
    private byte[] msgKeyBright;

    public byte[] getPktDecrypt() {
        return pktDecrypt;
    }

    public void setPktDecrypt(byte[] pktDecrypt) {
        this.pktDecrypt = pktDecrypt;
    }

    public byte[] getMsgKeyBright() {
        return msgKeyBright;
    }

    public void setMsgKeyBright(byte[] msgKeyBright) {
        this.msgKeyBright = msgKeyBright;
    }   
    
}
