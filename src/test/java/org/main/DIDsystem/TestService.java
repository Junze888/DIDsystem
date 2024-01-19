package org.main.DIDsystem;

import org.apache.pdfbox.util.Hex;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.crypto.keypair.ECDSAKeyPair;
import org.junit.Test;
import org.main.DIDsystem.raw.KeyFactory;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestService {
    @Test
    public void testClass() {
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