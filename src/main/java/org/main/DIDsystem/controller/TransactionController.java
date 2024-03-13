package org.main.DIDsystem.controller;

import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.main.DIDsystem.model.CommonResponse;
import org.main.DIDsystem.model.bo.*;
import org.main.DIDsystem.raw.KeyFactory;
import org.main.DIDsystem.service.ERC4907Service;
import org.main.DIDsystem.service.ServiceManager;
import org.main.DIDsystem.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.main.DIDsystem.raw.ERC4907.*;
import static org.main.DIDsystem.raw.UsersCache.serviceMap;

@Controller
public class TransactionController {
    @Autowired ServiceManager serviceManager;
    ERC4907Service erc4907Service;
    @PostConstruct
    public void init() throws Exception {
       erc4907Service = serviceManager.initERC4907ServiceManager().get("0x36651e8ae6dfff2cc2181245622437ee1c026552");
    }
    @PostMapping("/getUser")
    @ResponseBody
    public CommonResponse RequestTokenUser(@RequestParam("tokenId") String tokenId) throws Exception {
        TransactionResponse responseDate = erc4907Service.userOf(new ERC4907UserOfInputBO(new BigInteger(tokenId)));
        System.out.println(responseDate.getReceiptMessages());
        if (Objects.equals(responseDate.getReceiptMessages(), "Success")) return CommonResponse.ok(null,responseDate.getReturnObject().toString());
        return CommonResponse.fail("transaction failed!");
/**
 * receiptMessages='Success',
 * returnObject=[0x0000000000000000000000000000000000000000],
 * returnABIObject=[ABIObject{name='', type=VALUE, valueType=ADDRESS, addressValue=0x0000000000000000000000000000000000000000}]
 */
    }

    @PostMapping("/getUserTime")
    @ResponseBody
    public CommonResponse RequestTokenUseTime(@RequestParam("tokenId") String tokenId) throws Exception {
        TransactionResponse responseDate = erc4907Service.userExpires(new ERC4907UserExpiresInputBO(new BigInteger(tokenId)));
        System.out.println(responseDate.getReceiptMessages());
        String numberStr = null;
        if (Objects.equals(responseDate.getReceiptMessages(), "Success")) {
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(responseDate.getReturnObject().toString());
            if (matcher.find()) {
                numberStr = matcher.group();
            }
            assert numberStr != null;
            return CommonResponse.ok(null, Long.parseLong(numberStr));
        }
        else if (responseDate.getReceiptMessages().contains("expired")){
            return CommonResponse.ok(null,0);
        }
        return CommonResponse.fail("transaction failed!");
/**
 * receiptMessages='Success',
 * returnObject=[910092],
 * returnABIObject=[ABIObject{name='', type=VALUE, valueType=UINT, numericValue=910092}]
 */
    }

    @PostMapping("/setUser")
    @ResponseBody
    public CommonResponse RequestSetUser(@RequestParam("DID") String DID,@RequestParam("tokenId") String tokenId, @RequestParam("jwt") String jwt, @RequestParam("renterTime") String renterTime) throws Exception {
        System.out.println("收到Jwt" + jwt);
        if (TokenUtil.validateJwt(jwt)){
            System.out.println("jwt验证通过");
            ERC4907SetUserInputBO bo = new ERC4907SetUserInputBO(BigInteger.valueOf(Long.parseLong(tokenId)), KeyFactory.DIDtoAddress(DID), BigInteger.valueOf(Long.parseLong(renterTime) * 1000));//时间单位是ms
            TransactionResponse responseDate = erc4907Service.setUser(bo);
            if (Objects.equals(responseDate.getReceiptMessages(), "Success")){
                return CommonResponse.ok(null, null);
            }else {
                return CommonResponse.fail(responseDate.getReceiptMessages());
            }
        }
        else {
            System.out.println("jwt验证失败："+jwt);
            return CommonResponse.fail("please check jwt!");
        }
/**
 * values='[true]', events='{"UpdateUser":[[100000]]}',
 * receiptMessages='Success', returnObject=[true],
 * returnABIObject=[ABIObject{name='', type=VALUE, valueType=BOOL, booValueType=true}]}
 */
    }



    /**
     * 当前弃用。
     */
    @PostMapping("/Transaction")
    @ResponseBody
    public String RequestData(@RequestParam("Address") String authorizationHeader,@RequestParam("Address") String address, @RequestParam("function") String function, @RequestParam("tokenId") String Id) throws Exception {
        // 提取 Authorization 字段中的 token
        String[] parts = authorizationHeader.split(" ");
        String token = parts[1]; // 这里假设 token 格式为 "Bearer <token>"



        address = KeyFactory.DIDtoAddress(address);
        System.out.println("开始" + address);
        ERC4907Service service = serviceMap.get(address);
        System.out.println("服务器规模："+serviceMap.size() + service.symbol().getReturnMessage());
        BigInteger tokenId = new BigInteger(Id);
        try {
        switch (function) {
            case FUNC_IMAGEURL:
                return service.imageurl(new ERC4907ImageurlInputBO(tokenId)).toString();
            case FUNC_LOACTION:
                return service.loaction(new ERC4907LoactionInputBO(tokenId)).toString();
            case FUNC_OWNEROF:
                String a = service.ownerOf(new ERC4907OwnerOfInputBO(tokenId)).getValues();
                System.out.println(a);
                return a;
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
                return "request is illegal!";
            }
        }catch (Exception e){
            return e.toString();
        }
    }


    @PostMapping("/AdminTransaction")
    @ResponseBody
    public String RequestData(@RequestParam("Address") String address, @RequestParam("function") String function, @RequestParam("params") List<String> params){
        ERC4907Service service = serviceMap.get(address);
        try {
            switch (function) {
                case FUNC_MINT:
                    return service.mint(new ERC4907MintInputBO(params.get(0), new BigInteger(params.get(1)), params.get(2), params.get(3))).toString();
                case FUNC_SETUSER:
                    return service.setUser(new ERC4907SetUserInputBO(new BigInteger(params.get(0)), params.get(1), new BigInteger(params.get(2) ))).toString();
                default:
                    return "request is illegal!";
            }
        }catch (Exception e){
            return e.toString();
        }
    }


}