package org.main.DIDsystem.service;

import com.webank.weid.protocol.base.WeIdPrivateKey;
import com.webank.weid.protocol.request.ServiceArgs;
import com.webank.weid.protocol.response.CreateWeIdDataResult;
import com.webank.weid.protocol.response.ResponseData;
import com.webank.weid.rpc.WeIdService;
import com.webank.weid.service.impl.WeIdServiceImpl;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.main.DIDsystem.raw.KeyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Import;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Import(WeIdServiceImpl.class )
@Scope("prototype")
@Slf4j
public class UserService {
    private final WeIdService weIdService;
    @Autowired   //Spring 官方推荐使用构造方法注入或 Setter 方法注入来替代字段注入
    public UserService(WeIdService weIdService) {
        this.weIdService = weIdService;
    }
    public String createDID(String type){
        String weId = null;
        String publicKey = null;
        String privateKey = null;
        try {
            ResponseData<CreateWeIdDataResult> resultResponseData = weIdService.createWeId();
            Pattern pattern = Pattern.compile("weId=([^,]+).*publicKey=([^)]+).*privateKey=([^)]+)");
            //正则表达式：weId=[^.],不匹配. (+) .* 匹配任何字符串，；
            //([^,]+) 表示匹配一个或多个非,字符，并将匹配的结果作为一个分组。
            Matcher matcher = pattern.matcher(resultResponseData.toString());
            if (matcher.find()){
            ServiceArgs serviceArgs = new ServiceArgs();
            serviceArgs.setType(type);
            serviceArgs.setServiceEndpoint("深圳大学DID管理系统");
            weId = matcher.group(1);
            publicKey = matcher.group(2);
            privateKey = matcher.group(3);

            WeIdPrivateKey weIdPrivateKey = new WeIdPrivateKey();
            weIdPrivateKey.setPrivateKey(privateKey);
            weIdService.setService(weId, serviceArgs ,weIdPrivateKey); //这里可以不设置参数
            }else {
                log.error("Regex pattern did not match.DID create error");
            }
        }catch (NullPointerException exception){
            log.error("create DID false! cause:{}",exception.getMessage());
        }
        return "weId:" + weId + "\n"+ "PubKey:" + publicKey + "\n" + "priKey:" + KeyFactory.toHexKey(privateKey);
    }

    public String getDIDDocument(String DID){
        ResponseData<String> response = weIdService.getWeIdDocumentJson(DID);
        return response.getResult();
    }

    public boolean isDIDExist(String DID){
        return weIdService.isWeIdExist(DID).getResult();
    }
}
