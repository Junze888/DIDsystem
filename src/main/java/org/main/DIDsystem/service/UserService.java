package org.main.DIDsystem.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.webank.weid.protocol.base.WeIdPrivateKey;
import com.webank.weid.protocol.request.ServiceArgs;
import com.webank.weid.protocol.response.CreateWeIdDataResult;
import com.webank.weid.protocol.response.ResponseData;
import com.webank.weid.rpc.WeIdService;
import com.webank.weid.service.impl.WeIdServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.main.DIDsystem.config.SystemConfig;
import org.main.DIDsystem.model.UserDetails;
import org.main.DIDsystem.raw.UsersCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Import(WeIdServiceImpl.class )
@Scope("prototype")
@Slf4j
public class UserService {
    @Autowired
    private SystemConfig config;
    private final WeIdService weIdService;
    public static List<String> adminKeyList;
    @Autowired   //Spring 官方推荐使用构造方法注入或 Setter 方法注入来替代字段注入
    public UserService(WeIdService weIdService) {
        this.weIdService = weIdService;
    }
    @PostConstruct
    public void init(){
       adminKeyList = Arrays.asList(this.config.getHexPrivateKey().split(","));
    }


    public String createDID(String type, String msg){
        String weId = null;
        String publicKey = null;
        String privateKey = null;
        try {
            ResponseData<CreateWeIdDataResult> resultResponseData = weIdService.createWeId(); //这会随机创建
            Pattern pattern = Pattern.compile("weId=([^,]+).*publicKey=([^)]+).*privateKey=([^)]+)");
            //正则表达式：weId=[^.],不匹配. (+) .* 匹配任何字符串，；
            //([^,]+) 表示匹配一个或多个非,字符，并将匹配的结果作为一个分组。
            Matcher matcher = pattern.matcher(resultResponseData.toString());
            if (matcher.find()){
            ServiceArgs serviceArgs = new ServiceArgs();
            serviceArgs.setType(type);
            serviceArgs.setServiceEndpoint(msg);
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
        // 添加DID账户
        return "DID：" + weId + "\n" +
                "privateKey: " + privateKey + "\n" +
                "document: " + phaseDocument(getDIDDocument(weId));
    }

    public String getDIDDocument(String DID){
        ResponseData<String> response = weIdService.getWeIdDocumentJson(DID);
        return response.getResult();
    }

    private boolean addMessageInDocument(String msg, String priKey){
        return true;
    }

    public boolean isDIDExist(String DID){
        return weIdService.isWeIdExist(DID).getResult();
    }

    public boolean isUseMapContain(String DID){
        if (UsersCache.userMap.isEmpty()) return false;
        return UsersCache.userMap.get(DID) != null;

    }

    public String phaseDocument(String Document){
        try {
            // 使用Jackson库解析JSON字符串
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(Document);

            // 获取authentication部分
            JsonNode authenticationNode = rootNode.get("authentication");
            if (authenticationNode != null && authenticationNode.isArray()) {
                // 遍历authentication部分中的每个条目
                for (JsonNode authNode : authenticationNode) {
                    // 获取publicKeyMultibase和publicKey
                    JsonNode publicKeyMultibaseNode = authNode.get("publicKeyMultibase");
                    JsonNode publicKeyNode = authNode.get("publicKey");

                    if (publicKeyMultibaseNode != null && publicKeyNode != null) {
                        int length = 50;
                        String publicKeyMultibase = publicKeyMultibaseNode.asText();
                        String publicKey = publicKeyNode.asText();
                        ((ObjectNode) authNode).put("publicKeyMultibase", publicKeyMultibase.substring(0, length));
                        ((ObjectNode) authNode).put("publicKey", publicKey.substring(0, length));
                    }
                }
            }
            // 将修改后的JSON字符串返回
            return objectMapper.writeValueAsString(rootNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserDetails documentToJson(String DID) throws IOException {
        String nickname = null;
        String document =getDIDDocument(DID);
        System.out.println(document);
        ObjectMapper objectMapper = new ObjectMapper();
        // 解析JSON文档,找nickname
        JsonNode rootNode = objectMapper.readTree(document);
        JsonNode serviceNode = rootNode.get("service");
        for (JsonNode node : serviceNode) {
            JsonNode serviceEndpointNode = node.get("serviceEndpoint");
            String serviceEndpoint = serviceEndpointNode.asText();
            if (serviceEndpoint.startsWith("nickname")) {
                nickname = serviceEndpoint.substring(serviceEndpoint.indexOf("：") + 1, serviceEndpoint.indexOf(";"));

            }
        }
        return new UserDetails(DID, nickname, document);
    }

}
