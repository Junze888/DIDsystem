package org.main.DIDsystem.service;

import java.lang.Exception;
import java.lang.String;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.client.Client;
import org.main.DIDsystem.config.SystemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.main.DIDsystem.raw.UsersCache.serviceMap;

@Configuration
@Data
@Slf4j
public class ServiceManager {
  @Autowired
  private SystemConfig config;

  @Autowired
  private Client client;

  public static List<String> hexPrivateKeyList;

  @PostConstruct
  public void init() {
    hexPrivateKeyList = Arrays.asList(this.config.getHexPrivateKey().split(","));
  }

  /**
   * @notice: must use @Qualifier("School_TokenService") with @Autowired to get this Bean
   */
  @Bean("School_TokenService")
  public Map<String, School_TokenService> initSchool_TokenServiceManager() throws Exception {
    Map<String, School_TokenService> serviceMap = new ConcurrentHashMap<>(hexPrivateKeyList.size());
    for (int i = 0; i < hexPrivateKeyList.size(); i++) {
    	String privateKey = hexPrivateKeyList.get(i);
    	if (privateKey.startsWith("0x") || privateKey.startsWith("0X")) {
    		privateKey = privateKey.substring(2);
    	}
    	if (privateKey.isEmpty()) {
    		continue;
    	}
    	org.fisco.bcos.sdk.crypto.CryptoSuite cryptoSuite = new org.fisco.bcos.sdk.crypto.CryptoSuite(this.client.getCryptoType());
    	org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair cryptoKeyPair = cryptoSuite.createKeyPair(privateKey);
    	String userAddress = cryptoKeyPair.getAddress();
    	log.info("++++++++hexPrivateKeyList[{}]:{},userAddress:{}", i, privateKey, userAddress);
    	School_TokenService school_TokenService = new School_TokenService();
    	school_TokenService.setAddress(this.config.getContract().getSchool_TokenAddress());
    	school_TokenService.setClient(this.client);
    	org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor txProcessor =
    		org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory.createAssembleTransactionProcessor(this.client, cryptoKeyPair);
    	school_TokenService.setTxProcessor(txProcessor);
    	serviceMap.put(userAddress, school_TokenService);
    }
    log.info("++++++++School_TokenService map:{}", serviceMap);
    return serviceMap;
  }

	/**
   * @notice: must use @Qualifier("ERC4907Service") with @Autowired to get this Bean
   */


  @Bean("ERC4907Service")
  public Map<String, ERC4907Service> initERC4907ServiceManager() throws Exception {
    for (int i = 0; i < hexPrivateKeyList.size(); i++) {
    	String privateKey = hexPrivateKeyList.get(i);
    	if (privateKey.startsWith("0x") || privateKey.startsWith("0X")) {
    		privateKey = privateKey.substring(2);
    	}
    	if (privateKey.isEmpty()) {
    		continue;
    	}
    	org.fisco.bcos.sdk.crypto.CryptoSuite cryptoSuite = new org.fisco.bcos.sdk.crypto.CryptoSuite(this.client.getCryptoType());
    	org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair cryptoKeyPair = cryptoSuite.createKeyPair(privateKey);
    	String userAddress = cryptoKeyPair.getAddress();
    	log.info("++++++++hexPrivateKeyList[{}]:{},userAddress:{}", i, privateKey, userAddress);
    	ERC4907Service eRC4907Service = new ERC4907Service();
    	eRC4907Service.setAddress(this.config.getContract().getERC4907Address());
    	eRC4907Service.setClient(this.client);
    	org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor txProcessor =
    		org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory.createAssembleTransactionProcessor(this.client, cryptoKeyPair);
    	eRC4907Service.setTxProcessor(txProcessor);
    	serviceMap.put(userAddress, eRC4907Service);
    }
    log.info("++++++++ERC4907Service map:{}", serviceMap);
    return serviceMap;
  }

	public  void addUser(String privateKey) throws Exception {
		// 添加私钥到 hexPrivateKeyList
		hexPrivateKeyList.add(privateKey);
		// 创建新的 School_TokenService 实例
		ERC4907Service erc4907Service = new ERC4907Service();
		// 设置 School_TokenService 的属性
		erc4907Service.setAddress(this.config.getContract().getERC4907Address());
		erc4907Service.setClient(this.client);
		// 创建 CryptoKeyPair 对象
		org.fisco.bcos.sdk.crypto.CryptoSuite cryptoSuite = new org.fisco.bcos.sdk.crypto.CryptoSuite(this.client.getCryptoType());
		org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair cryptoKeyPair = cryptoSuite.createKeyPair(privateKey);
		// 创建 AssembleTransactionProcessor 对象
		org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor txProcessor = org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory.createAssembleTransactionProcessor(this.client, cryptoKeyPair);
		// 设置 School_TokenService 的 txProcessor 属性
		erc4907Service.setTxProcessor(txProcessor);

		// 将新的 School_TokenService 添加到 serviceMap
		String userAddress = cryptoKeyPair.getAddress();
		serviceMap.put(userAddress, erc4907Service);
	}

	public void removeUser(String privateKey) {
		// 从 hexPrivateKeyList 中移除私钥
		hexPrivateKeyList.remove(privateKey);

		// 根据私钥获取对应的用户地址
		org.fisco.bcos.sdk.crypto.CryptoSuite cryptoSuite = new org.fisco.bcos.sdk.crypto.CryptoSuite(this.client.getCryptoType());
		org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair cryptoKeyPair = cryptoSuite.createKeyPair(privateKey);
		String userAddress = cryptoKeyPair.getAddress();

		// 从 serviceMap 中移除对应的 School_TokenService
		serviceMap.remove(userAddress);
	}


}
