package org.main.DIDsystem.raw;

import org.main.DIDsystem.config.SystemConfig;
import org.main.DIDsystem.service.ERC4907Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class UsersCache {

    public static CopyOnWriteArraySet<String> userSet = new CopyOnWriteArraySet<>();  //用户登录时存储登录公私钥；
    public static Map<String, ERC4907Service> serviceMap = new ConcurrentHashMap<>();

}
