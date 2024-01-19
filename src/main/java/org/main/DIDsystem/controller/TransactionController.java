package org.main.DIDsystem.controller;

import org.main.DIDsystem.model.bo.*;
import org.main.DIDsystem.service.ERC4907Service;
import org.main.DIDsystem.service.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

import static org.main.DIDsystem.raw.ERC4907.*;
import static org.main.DIDsystem.raw.UsersCache.serviceMap;
import static org.main.DIDsystem.raw.UsersCache.userSet;

@Controller
public class TransactionController {
    @Autowired ServiceManager serviceManager;

    @PostConstruct
    public void init() throws Exception {
        if (userSet.isEmpty()) return;
        for (String privateKey : userSet) {
            serviceManager.addUser(privateKey);
        }
        userSet.clear();
    }

    @PostMapping("/Transaction")
    public String RequestData(@RequestParam("Address") String address, @RequestParam("function") String function, @RequestParam("tokenId") String Id) {
        ERC4907Service service = serviceMap.get(address);
        BigInteger tokenId = new BigInteger(Id);
        try {
        switch (function) {
            case FUNC_IMAGEURL:
                return service.imageurl(new ERC4907ImageurlInputBO(tokenId)).toString();
            case FUNC_LOACTION:
                return service.loaction(new ERC4907LoactionInputBO(tokenId)).toString();
            case FUNC_OWNEROF:
                return service.ownerOf(new ERC4907OwnerOfInputBO(tokenId)).toString();
            case FUNC_ISEXPIRED:
                return service.isExpired(new ERC4907IsExpiredInputBO(tokenId)).toString();
            case FUNC_USEREXPIRES:
                return service.userExpires(new ERC4907UserExpiresInputBO(tokenId)).toString();
            case FUNC_USEREXPIRETIME:
                return service.userExpireTime(new ERC4907UserExpireTimeInputBO(tokenId)).toString();
            case FUNC_USEROF:
                return service.userOf(new ERC4907UserOfInputBO(tokenId)).toString();
            case "name":
                return service.name().toString();
            case "symbol":
                return service.symbol().toString();
            default:
                return "request is illegul!";
            }
        }catch (Exception e){
            return e.toString();
        }
    }

    @PostMapping("/AdminTransaction")
    public String RequestData(@RequestParam("Address") String address, @RequestParam("function") String function, @RequestParam("params") List<String> params){
        ERC4907Service service = serviceMap.get(address);
        try {
            switch (function) {
                case FUNC_MINT:
                    return service.mint(new ERC4907MintInputBO(params.get(0), new BigInteger(params.get(1)), params.get(2), params.get(3))).toString();
                case FUNC_SETUSER:
                    return service.setUser(new ERC4907SetUserInputBO(new BigInteger(params.get(0)), params.get(1), new BigInteger(params.get(2) ))).toString();
                default:
                    return "request is illegul!";
            }
        }catch (Exception e){
            return e.toString();
        }
    }


}