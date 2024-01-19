package org.main.DIDsystem.raw;

import java.math.BigInteger;

public class KeyFactory {

    //十进制私钥转16进制hexPrivateKey
    public static String toHexKey(String key){
        BigInteger priKey = new BigInteger(key);
        return priKey.toString(16);
    }

    public static String DIDtoAddress(String DID){
        int startIndex = DID.indexOf("0x");
        return DID.substring(startIndex);
    }


}
