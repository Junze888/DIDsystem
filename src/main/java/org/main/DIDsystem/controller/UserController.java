package org.main.DIDsystem.controller;

import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.crypto.keypair.ECDSAKeyPair;
import org.main.DIDsystem.config.SystemConfig;
import org.main.DIDsystem.raw.KeyFactory;
import org.main.DIDsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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
    @PostMapping({"/index","/"})
    @ResponseBody
    public int UserLogin(@RequestParam("DID") String DID, @RequestParam("priKey") String privateKey, @RequestParam("isAdmin") boolean isAdmin ) {
        if (userService.isDIDExist(DID)){
            return -1;
        }
        if (isAdmin){
            List<String> adminKeyList = Arrays.asList(this.config.getHexPrivateKey().split(","));
            if (adminKeyList.contains(privateKey)){
                userSet.add(privateKey);
                return 1;
            }
        }
        CryptoKeyPair ecdsaKeyPair = new ECDSAKeyPair().createKeyPair(privateKey);
        String address = ecdsaKeyPair.getAddress();
        if (KeyFactory.DIDtoAddress(DID).equals(address)) {
            userSet.add(privateKey);
            return 1;
        }

        return 0;
    }


    @PostMapping("/create")
    public String createUser(@RequestParam("type") String type){
        return userService.createDID(type);
    }






}
