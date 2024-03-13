package org.main.DIDsystem.controller;

import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.crypto.keypair.ECDSAKeyPair;
import org.main.DIDsystem.config.SystemConfig;
import org.main.DIDsystem.model.CommonResponse;
import org.main.DIDsystem.model.UserDetails;
import org.main.DIDsystem.raw.KeyFactory;
import org.main.DIDsystem.raw.UsersCache;
import org.main.DIDsystem.service.ServiceManager;
import org.main.DIDsystem.service.UserService;
import org.main.DIDsystem.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Controller
public class UserController {
    @Autowired
    private SystemConfig config;
    @Autowired private UserService userService;
    @Autowired
    ServiceManager serviceManager;
    public static List<String> adminKeyList;
    @PostConstruct
    public void init(){
        adminKeyList = Arrays.asList(this.config.getHexPrivateKey().split(","));
    }
    @PostMapping({"/login"})
    @ResponseBody
    public CommonResponse UserLogin(@RequestParam("DID") String DID, @RequestParam("priKey") String privateKey, @RequestParam("isAdmin") boolean isAdmin) {
        System.out.println("接收到的数据" + DID + privateKey);
        if (!userService.isDIDExist(DID)) {
            System.out.println("账户不存在，请检查账户是否正确");
            return CommonResponse.fail("DID not exited");
        }
        UsersCache.userMap.put(DID, new UsersCache.user(privateKey, isAdmin));
        System.out.println("uesr DID: " + DID);
        System.out.println("user pri: " + privateKey);
        if (isAdmin) {
            if (!adminKeyList.contains(privateKey)) {
                UsersCache.userMap.remove(DID);
                return CommonResponse.fail("private key wrong");
            }
        } else {
            BigInteger privateKeyInt = new BigInteger(privateKey);
            CryptoKeyPair ecdsaKeyPair = new ECDSAKeyPair().createKeyPair(privateKeyInt);
            String address = ecdsaKeyPair.getAddress();
            System.out.println("address" + address);
            if (!KeyFactory.DIDtoAddress(DID).equals(address)) {
                UsersCache.userMap.remove(DID);
                return CommonResponse.fail("private key wrong");
            }
        }
        String jwt = TokenUtil.generateJwt(DID,3600000); //默认一个小时过期
        System.out.println("发放的jwt： "+jwt);
        return CommonResponse.ok(null,jwt);
    }

    @PostMapping("/register")
    @ResponseBody
    public CommonResponse createUser(@RequestParam("nickname") String nickname,@RequestParam("userID") String userID, @RequestParam("grade") String grade, @RequestParam("email") String email){
        if (!Objects.equals(userID, "1")){
            return CommonResponse.fail("UserID is Invalid");
        }
        String msg = "nickname：" + nickname + ";  email： " + email;
        String data = userService.createDID( "Grade" + grade, msg);
        System.out.println(data);
        return CommonResponse.ok( null, data);
    }

    @PostMapping("/userDetail")
    @ResponseBody
    public UserDetails loadUserDetail(@RequestParam("DID") String DID) throws IOException {
        System.out.println("接收到的DID："+DID);
        if (!userService.isDIDExist(DID)) {
            System.out.println("账户不存在，请检查账户是否正确");
            return null;
        }

        return userService.documentToJson(DID);

    }

}
