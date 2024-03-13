package org.main.DIDsystem.raw;

import lombok.Getter;
import lombok.Setter;
import org.main.DIDsystem.service.ERC4907Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class UsersCache {

    public static Map<String, user> userMap = new ConcurrentHashMap<>();  //用户登录时暂存储登录公私钥；
    public static Map<String, ERC4907Service> serviceMap = new ConcurrentHashMap<>();

    public static class user{
        @Getter
        private boolean isAdmin;
        @Getter
        private final String PrivateKey;

        public user(String priKey, boolean isAdmin){
            this.PrivateKey = priKey;
            this.isAdmin = isAdmin;
        }
    }

}
