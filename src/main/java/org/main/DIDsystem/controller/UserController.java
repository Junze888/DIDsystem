package org.main.DIDsystem.controller;

import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.crypto.keypair.ECDSAKeyPair;
import org.main.DIDsystem.config.SystemConfig;
import org.main.DIDsystem.raw.KeyFactory;
import org.main.DIDsystem.service.ServiceManager;
import org.main.DIDsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

import static org.main.DIDsystem.raw.UsersCache.userSet;

@Controller
public class UserController {
    @Autowired
    private SystemConfig config;
    @Autowired private UserService userService;
    @Autowired
    ServiceManager serviceManager;
    @PostMapping ({"/login"})
    @ResponseBody
    public boolean UserLogin(@RequestParam("DID") String DID, @RequestParam("priKey") String privateKey, @RequestParam("isAdmin") boolean isAdmin ) throws Exception {
        if (!userService.isDIDExist(DID)){
            System.out.println("账户不存在");
            return false;
        }
        privateKey = KeyFactory.toHexKey(privateKey);
        if (isAdmin){
            List<String> adminKeyList = Arrays.asList(this.config.getHexPrivateKey().split(","));
            if (adminKeyList.contains(privateKey)){
                System.out.println("管理员登录成功");
                serviceManager.addUser(privateKey);
                return true;
            }
        }
        CryptoKeyPair ecdsaKeyPair = new ECDSAKeyPair().createKeyPair(privateKey);
        String address = ecdsaKeyPair.getAddress();
        if (KeyFactory.DIDtoAddress(DID).equals(address)) {
            System.out.println("用户登录成功");
            serviceManager.addUser(privateKey);
            return true;
        }
        System.out.println("需要创建新账户");
        return false;
    }


    @PostMapping("/create")
    public String createUser(@RequestParam("type") String type){
        return userService.createDID(type);
    }






}
