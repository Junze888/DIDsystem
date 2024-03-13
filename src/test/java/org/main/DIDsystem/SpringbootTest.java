package org.main.DIDsystem;

import com.webank.weid.rpc.WeIdService;
import com.webank.weid.service.impl.WeIdServiceImpl;
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
import org.main.DIDsystem.model.bo.ERC4907UserExpiresInputBO;
import org.main.DIDsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class SpringbootTest {
    @Autowired private Client client;
    @Autowired private SystemConfig systemConfig;
    @Test
    public void deploy() throws Exception {
        /**
         * 测试密钥配置和本地发起交易上链
         */

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
        //交易提交处理器
        AssembleTransactionProcessor txProcessor =
                TransactionProcessorFactory.createAssembleTransactionProcessor(
                        client, client.getCryptoSuite().createKeyPair(systemConfig.getHexPrivateKey()),"src/main/resources/abi/", "src/main/resources/bin/ecc/");
        assertNotNull(txProcessor);


        //CallResponse callResponse= txProcessor.sendCallByContractLoader("ERC4907","0x102fe0badb3b6768fe069f5930cc61d221e61e38", "userOf", bo.toArgs());

        ArrayList<Object> params = new ArrayList<>();

//        //测试调用token的useInfo数据交易
//        params.add(BigInteger.valueOf(11));
//        CallResponse callResponse = txProcessor.sendCallByContractLoader("ERC4907","0x102fe0badb3b6768fe069f5930cc61d221e61e38","userOf",params);
//        System.out.println(callResponse.getReturnCode() + "  "+callResponse.getValues());

        /**
         * 测试erc4907合约的setUser函数
         *
         * TransactionResponse{
         * transactionReceipt=TransactionReceipt{transactionHash='0xf04c85ca5ea08414413b218127ed1d02fd71147439d70fdb86f8388dc83f767b',
         * transactionIndex='0x0', root='0x8b4e33d3b9e09900608cf2620a7b283195dfcb5cab879f49121e71b84bb91135',
         * blockNumber='0x737',
         * blockHash='0xd0bdb7f09327732295334e6c504b733f67ad2be43beaa758e1368145ec702a51',
         * from='0x36651e8ae6dfff2cc2181245622437ee1c026552',
         * to='0x102fe0badb3b6768fe069f5930cc61d221e61e38',
         * gasUsed='0x7f22',
         * remainGas='null',
         * contractAddress='0x0000000000000000000000000000000000000000',
         * logs=[Logs{address='0x102fe0badb3b6768fe069f5930cc61d221e61e38',topics=[0x4e06b4e7000e659094299b3533b47b6aa8ad048e95e872d23d1f4ee55af89cfe,0x000000000000000000000000000000000000000000000000000000000000000b, 0x00000000000000000000000032d7a613aef6e058cdd160a6ed20325de34cd9cc],
         * data='0x00000000000000000000000000000000000000000000000000000000000186a0'}],
         * logsBloom='0x00000000000000000000004001000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000100000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000002000000002000000000000000000000008000000800000000000000000000000000000010000040000004000000000',
         * status='0x0',
         * statusMsg='None',
         * input='0xe030565e000000000000000000000000000000000000000000000000000000000000000b00000000000000000000000032d7a613aef6e058cdd160a6ed20325de34cd9cc00000000000000000000000000000000000000000000000000000000000186a0', output='0x0000000000000000000000000000000000000000000000000000000000000001',
         * txProof=null, receiptProof=null, message='Success'}, contractAddress='0x0000000000000000000000000000000000000000',
         * values='[true]', events='{"UpdateUser":[[100000]]}',
         * receiptMessages='Success', returnObject=[true],
         * returnABIObject=[ABIObject{name='', type=VALUE, valueType=BOOL, booValueType=true}]}
         */
        //ERC4907SetUserInputBO bo = new ERC4907SetUserInputBO(BigInteger.valueOf(11), "0x32d7a613aef6e058cdd160a6ed20325de34cd9cc", BigInteger.valueOf(100000));//时间单位是ms
        //ERC4907UserOfInputBO bo = new ERC4907UserOfInputBO(BigInteger.valueOf(11));
        ERC4907UserExpiresInputBO bo = new ERC4907UserExpiresInputBO(BigInteger.valueOf(11));
        TransactionResponse transactionResponse = txProcessor.sendTransactionAndGetResponseByContractLoader("ERC4907", "0x102fe0badb3b6768fe069f5930cc61d221e61e38", "userExpires", bo.toArgs());
        System.out.println(transactionResponse);



//        ABICodec abiCodec = new ABICodec(client.getCryptoSuite());
//        String setMethodSignature = "setUser(uint256, address, uint64)";
//        String abiEncoded = abiCodec.encodeMethodByInterface(setMethodSignature, bo.toArgs());
//        // ……
//        TransactionReceipt transactionReceipt = txProcessor.sendTransactionAndGetReceipt("0x102fe0badb3b6768fe069f5930cc61d221e61e38", abiEncoded, client.getCryptoSuite().getCryptoKeyPair());
//        System.out.println(transactionReceipt);
    }

    /**
     * 测试DID的创建和获取
     */
    @Test
    public void testDID(){
        WeIdService weIdService = new WeIdServiceImpl();
        UserService userService = new UserService(weIdService);
        System.out.println(userService.createDID("学生","学号123"));
        /**
         * weId:did:weid:666:0x45a83aadb9a65ab09394523cc3955ab83e9c4f24
         * PubKey:12666863461727102589636399329891772112481336874648810238695507591097454141725005826118618957651755221877194471710279308804650110348847726225104470634145550
         * priKey:219d569f0e53fa78004baed6f6641af4ab56064111301cf9184083bfe1d1b69b
         */
        System.out.println(userService.getDIDDocument("did:weid:666:0x4fa8596c14db55ba312e694eb23f97cf69f71b07"));
        /**
         * {"@context" : "https://github.com/WeBankFinTech/WeIdentity/blob/master/context/v1",
         *   "id" : "did:weid:666:0x4fa8596c14db55ba312e694eb23f97cf69f71b07",
         *   "authentication" : [ {
         *     "id" : "did:weid:666:0x4fa8596c14db55ba312e694eb23f97cf69f71b07#keys-aad7bdba",
         *     "type" : "Ed25519VerificationKey2020",
         *     "controller" : "did:weid:666:0x4fa8596c14db55ba312e694eb23f97cf69f71b07",
         *     "publicKeyMultibase" : "z5rrf3ryzMgNKyrpE4X4vqSgtfP6pHSGys2LQ62MKERxg44tCdsY1doxPNCQBrwiy1nPkXJU71fYmsZ
         *     "publicKey" : "4356025450468955315999308929660566268412110637940425819644063541636830628703432573897553914210573269540678219534130159749712403594534533411772089495008597"
         *   } ],
         *   "service" : [{
         *     "id" : "did:weid:666:0x4fa8596c14db55ba312e694eb23f97cf69f71b07#b2447e18",
         *     "type" : "学生DID",
         *     "serviceEndpoint" : "|姓名:June |学号:2022 |专业:通信工程*"
         *   } ]
         */
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
