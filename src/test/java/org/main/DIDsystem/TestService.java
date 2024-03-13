package org.main.DIDsystem;

import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.crypto.keypair.ECDSAKeyPair;
import org.junit.Test;
import org.main.DIDsystem.raw.KeyFactory;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class TestService {
    @Test
    public void testClass() {
        /**
         * 测试向上和向下转型
         */
       Bo bo = null;
       bo = new Boson();
        bo.toAgrs();
    }
    class Bo{
        public void toAgrs(){
            System.out.println("father");
        }
    }

    class Boson extends Bo{
        public void toArgs(){
            System.out.println("son");
        }
    }
    /**
     * 测试用户DID由DID转为FISCO BCOS区块链的交易地址形式
     * DID: weId=did:weid:666:0x4fa8596c14db55ba312e694eb23f97cf69f71b07
     * address: 0x4fa8596c14db55ba312e694eb23f97cf69f71b07
     *
     * 十进制的私钥转为十六进制：
     * 40819478044357692990538350952888836105837530313165394220648180635156872567660
     * 5a3f017e4cc0d036cb9ccbb96095bff36a1686a6536797334d70106a6ff61b6c
     */
    @Test
    public void testKey(){
        String DID = "weId=did:weid:666:0x4fa8596c14db55ba312e694eb23f97cf69f71b07";
        String pri = "40819478044357692990538350952888836105837530313165394220648180635156872567660";
        BigInteger privateKey = new BigInteger(pri);
        String hexpri = privateKey.toString(16);
//        System.out.println("hexPri="+privateKey.toString(16));
        CryptoKeyPair ecdsaKeyPair = new ECDSAKeyPair().createKeyPair(privateKey);
        assertEquals(KeyFactory.DIDtoAddress(DID), ecdsaKeyPair.getAddress());

    }
}