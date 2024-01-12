package org.main.DIDsystem.service;

import java.lang.Exception;
import java.lang.String;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.transaction.model.dto.CallResponse;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.main.DIDsystem.model.bo.ERC4907ImageurlInputBO;
import org.main.DIDsystem.model.bo.ERC4907IsExpiredInputBO;
import org.main.DIDsystem.model.bo.ERC4907LoactionInputBO;
import org.main.DIDsystem.model.bo.ERC4907MintInputBO;
import org.main.DIDsystem.model.bo.ERC4907OwnerOfInputBO;
import org.main.DIDsystem.model.bo.ERC4907SetUserInputBO;
import org.main.DIDsystem.model.bo.ERC4907TransferInputBO;
import org.main.DIDsystem.model.bo.ERC4907UserExpireTimeInputBO;
import org.main.DIDsystem.model.bo.ERC4907UserExpiresInputBO;
import org.main.DIDsystem.model.bo.ERC4907UserOfInputBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@Data
public class ERC4907Service {
  public static final String ABI = org.main.DIDsystem.utils.IOUtil.readResourceAsString("abi/ERC4907.abi");

  public static final String BINARY = org.main.DIDsystem.utils.IOUtil.readResourceAsString("bin/ecc/ERC4907.bin");

  //public static final String SM_BINARY = org.main.DIDsystem.utils.IOUtil.readResourceAsString("bin/sm/ERC4907.bin");

  @Value("${system.contract.eRC4907Address}")
  private String address;

  @Autowired
  private Client client;

  AssembleTransactionProcessor txProcessor;

  @PostConstruct
  public void init() throws Exception {
    this.txProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(this.client, this.client.getCryptoSuite().getCryptoKeyPair());
  }

  public CallResponse userExpireTime(ERC4907UserExpireTimeInputBO input) throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "userExpireTime", input.toArgs());
  }

  public CallResponse symbol() throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "symbol", Arrays.asList());
  }

  public CallResponse imageurl(ERC4907ImageurlInputBO input) throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "imageurl", input.toArgs());
  }

  public TransactionResponse userOf(ERC4907UserOfInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "userOf", input.toArgs());
  }

  public TransactionResponse setUser(ERC4907SetUserInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "setUser", input.toArgs());
  }

  public TransactionResponse mint(ERC4907MintInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "mint", input.toArgs());
  }

  public TransactionResponse transfer(ERC4907TransferInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "transfer", input.toArgs());
  }

  public TransactionResponse isExpired(ERC4907IsExpiredInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "isExpired", input.toArgs());
  }

  public TransactionResponse userExpires(ERC4907UserExpiresInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "userExpires", input.toArgs());
  }

  public CallResponse ownerOf(ERC4907OwnerOfInputBO input) throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "ownerOf", input.toArgs());
  }

  public CallResponse name() throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "name", Arrays.asList());
  }

  public CallResponse loaction(ERC4907LoactionInputBO input) throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "loaction", input.toArgs());
  }
}
