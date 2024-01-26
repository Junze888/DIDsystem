package org.main.DIDsystem;

import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.crypto.keypair.ECDSAKeyPair;
import org.fisco.bcos.sdk.crypto.keypair.SM2KeyPair;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.main.DIDsystem.config.SystemConfig;
import org.main.DIDsystem.model.bo.ERC4907SetUserInputBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class DemoPkey {
    @Autowired private Client client;
    @Autowired private SystemConfig systemConfig;
    @Test
    public void deploy() throws Exception {

            String abi =
                    org.apache.commons.io.IOUtils.toString(
                            Thread.currentThread()
                                    .getContextClassLoader()
                                    .getResource("abi/School_Token.abi"));
            String bin =
                    org.apache.commons.io.IOUtils.toString(
                            Thread.currentThread()
                                    .getContextClassLoader()
                                    .getResource("bin/ecc/School_Token.bin"));

        AssembleTransactionProcessor txProcessor =
                TransactionProcessorFactory.createAssembleTransactionProcessor(
                        client, client.getCryptoSuite().createKeyPair(systemConfig.getHexPrivateKey()),"src/main/resources/abi/", "src/main/resources/bin/ecc/");
        assertNotNull(txProcessor);


        //CallResponse callResponse= txProcessor.sendCallByContractLoader("ERC4907","0x102fe0badb3b6768fe069f5930cc61d221e61e38", "userOf", bo.toArgs());

        ArrayList<Object> params = new ArrayList<>();
        //测试部署合约，传参有问题
//        params.add("SZU");
//        params.add("SZU");
//        TransactionResponse response = txProcessor.deployByContractLoader("School_Token",params);
//        System.out.println(response);

//        //测试调用查询接口
//        params.add(BigInteger.valueOf(11));
//        CallResponse callResponse = txProcessor.sendCallByContractLoader("ERC4907","0x102fe0badb3b6768fe069f5930cc61d221e61e38","userOf",params);
//        System.out.println(callResponse.getReturnCode() + "  "+callResponse.getValues());


        ERC4907SetUserInputBO bo = new ERC4907SetUserInputBO(BigInteger.valueOf(11), "0x32d7a613aef6e058cdd160a6ed20325de34cd9cc", BigInteger.valueOf(100000));
        TransactionResponse transactionResponse = txProcessor.sendTransactionAndGetResponseByContractLoader("ERC4907", "0x102fe0badb3b6768fe069f5930cc61d221e61e38", "setUser", bo.toArgs());
        System.out.println(transactionResponse);



//        ABICodec abiCodec = new ABICodec(client.getCryptoSuite());
//        String setMethodSignature = "setUser(uint256, address, uint64)";
//        String abiEncoded = abiCodec.encodeMethodByInterface(setMethodSignature, bo.toArgs());
//        // ……
//        TransactionReceipt transactionReceipt = txProcessor.sendTransactionAndGetReceipt("0x102fe0badb3b6768fe069f5930cc61d221e61e38", abiEncoded, client.getCryptoSuite().getCryptoKeyPair());
//        System.out.println(transactionReceipt);

//        WeIdService weIdService = new WeIdServiceImpl();
//        assertNotNull(weIdService);
//        System.out.println(weIdService.getWeIdDocument("did:weid:666:0x4fa8596c14db55ba312e694eb23f97cf69f71b07"));
    }






    @Test
    public void keyGeneration() throws Exception {
        //ECDSA key generation
        CryptoKeyPair ecdsaKeyPair = new ECDSAKeyPair().generateKeyPair();
        System.out.println("ecdsa private key :"+ecdsaKeyPair.getHexPrivateKey());
        System.out.println("ecdsa public key :"+ecdsaKeyPair.getHexPublicKey());
        System.out.println("ecdsa address :"+ecdsaKeyPair.getAddress());
        //SM2 key generation
        CryptoKeyPair sm2KeyPair = new SM2KeyPair().generateKeyPair();
        System.out.println("sm2 private key :"+sm2KeyPair.getHexPrivateKey());
        System.out.println("sm2 public key :"+sm2KeyPair.getHexPublicKey());
        System.out.println("sm2 address :"+sm2KeyPair.getAddress());

    }


}
