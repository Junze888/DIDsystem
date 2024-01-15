package org.main.DIDsystem.service;

import com.webank.weid.protocol.response.CreateWeIdDataResult;
import com.webank.weid.protocol.response.ResponseData;
import com.webank.weid.rpc.WeIdService;
import com.webank.weid.service.impl.WeIdServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Import;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Import(WeIdServiceImpl.class )
public class UserService {
    private final WeIdService weIdService;
    @Autowired   //Spring 官方推荐使用构造方法注入或 Setter 方法注入来替代字段注入
    public UserService(WeIdService weIdService){
        this.weIdService = weIdService;
    }
    public String createDID(){
        ResponseData<CreateWeIdDataResult> resultResponseData = weIdService.createWeId();
        Pattern pattern = Pattern.compile("weId=([^,]+).*publicKey=([^)]+).*privateKey=([^)]+)");
        //正则表达式：weId=[^.],不匹配. (+) .* 匹配任何字符串，；
        //([^,]+) 表示匹配一个或多个非,字符，并将匹配的结果作为一个分组。
        Matcher matcher = pattern.matcher(resultResponseData.toString());
        if (matcher.find()) {
            String weId = matcher.group(1);
            String publicKey = matcher.group(2);
            String privateKey = matcher.group(3);
        }
        return  resultResponseData.toString();
    }




}
