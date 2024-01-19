//package org.main.DIDsystem.service;
//
//import com.webank.weid.protocol.base.WeIdPrivateKey;
//import org.fisco.bcos.sdk.client.Client;
//import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
//import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
//import org.fisco.bcos.sdk.transaction.model.dto.CallResponse;
//import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
//import org.main.DIDsystem.config.SystemConfig;
//import org.main.DIDsystem.model.bo.*;
//import org.main.DIDsystem.raw.School_Token;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Service;
//
//import java.math.BigInteger;
//import java.security.KeyPair;
//import java.util.ArrayList;
//import java.util.Objects;
//
//import static org.main.DIDsystem.raw.ERC4907.*;
//
//
//@Service
//public class TokenService {
//    @Autowired private Client client;
//    @Autowired private SystemConfig systemConfig;
//    @Autowired ERC4907Service erc4907Service;
//    private final AssembleTransactionProcessor txProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(
//            client, client.getCryptoSuite().createKeyPair(systemConfig.getHexPrivateKey()),"src/main/resources/abi/", "src/main/resources/bin/ecc/");
//
//    @Bean
//    public TokenService() throws Exception {
//
//    }
//
//
//    private Boolean CreateToken(String to, BigInteger tokenId, String url, String location) throws Exception {
//        School_TokenMintInputBO bo = new School_TokenMintInputBO(to, tokenId, url, location);
//        TransactionResponse transactionResponse =  txProcessor.sendTransactionAndGetResponseByContractLoader("ERC4907", "0x102fe0badb3b6768fe069f5930cc61d221e61e38", "mint", bo.toArgs());
//        return transactionResponse.getReturnCode() == 0;
//    }
//
//
//    private Boolean SetTokenUser(BigInteger tokenId, String user, BigInteger useTime) throws Exception {
//        ERC4907SetUserInputBO bo = new ERC4907SetUserInputBO(tokenId, user, useTime);
//        TransactionResponse transactionResponse =  txProcessor.sendTransactionAndGetResponseByContractLoader("ERC4907", ERC_CONTRACT_ADDRESS, "setUser", bo.toArgs());
//        return transactionResponse.getReturnCode() == 0;
//    }
//
//
//    public String requestViewData(String function, String privateKey, BigInteger tokenId) throws Exception {
//
//        AssembleTransactionProcessor Processor = TransactionProcessorFactory.createAssembleTransactionProcessor(
//                        client, client.getCryptoSuite().createKeyPair(privateKey),"src/main/resources/abi/", "src/main/resources/bin/ecc/");
//        BO bo = null;
//        switch (function){
//            case FUNC_IMAGEURL:
//                bo = new ERC4907ImageurlInputBO(tokenId); break;
//            case FUNC_LOACTION:
//                bo = new ERC4907LoactionInputBO(tokenId);break;
//            case FUNC_OWNEROF:
//                bo = new ERC4907OwnerOfInputBO(tokenId); break;
//        }
//        assert bo != null;
//        CallResponse transactionResponse = Processor.sendCallByContractLoader(ERC_NAME, ERC_CONTRACT_ADDRESS, function, bo.toArgs());
//
//
//        return transactionResponse.toString();
//    }
//
//    public String setEvent(String function, String privateKey, ArrayList<Objects> param) throws Exception {
//        AssembleTransactionProcessor Processor = TransactionProcessorFactory.createAssembleTransactionProcessor(
//                client, client.getCryptoSuite().createKeyPair(privateKey),"src/main/resources/abi/", "src/main/resources/bin/ecc/");
//
//
//
//
//        ERC4907SetUserInputBO bo = new ERC4907SetUserInputBO(BigInteger.valueOf(11), "0x32d7a613aef6e058cdd160a6ed20325de34cd9cc", BigInteger.valueOf(100000));
//        TransactionResponse transactionResponse = Processor.sendTransactionAndGetResponseByContractLoader("ERC4907", ERC_CONTRACT_ADDRESS, "setUser", bo.toArgs());
//        return transactionResponse.toString();
//    }
//
//
//}
