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
import org.main.DIDsystem.model.bo.School_TokenImageurlInputBO;
import org.main.DIDsystem.model.bo.School_TokenLoactionInputBO;
import org.main.DIDsystem.model.bo.School_TokenMintInputBO;
import org.main.DIDsystem.model.bo.School_TokenOwnerOfInputBO;
import org.main.DIDsystem.model.bo.School_TokenTransferInputBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@Data
public class School_TokenService {
  public static final String ABI = org.main.DIDsystem.utils.IOUtil.readResourceAsString("abi/School_Token.abi");

  public static final String BINARY = org.main.DIDsystem.utils.IOUtil.readResourceAsString("bin/ecc/School_Token.bin");

 // public static final String SM_BINARY = org.main.DIDsystem.utils.IOUtil.readResourceAsString("bin/sm/School_Token.bin");

  @Value("${system.contract.school_TokenAddress}")
  private String address;

  @Autowired
  private Client client;

  AssembleTransactionProcessor txProcessor;

  @PostConstruct
  public void init() throws Exception {
    this.txProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(this.client, this.client.getCryptoSuite().getCryptoKeyPair());
  }

  public CallResponse symbol() throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "symbol", Arrays.asList());
  }

  public CallResponse imageurl(School_TokenImageurlInputBO input) throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "imageurl", input.toArgs());
  }

  public TransactionResponse mint(School_TokenMintInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "mint", input.toArgs());
  }

  public TransactionResponse transfer(School_TokenTransferInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "transfer", input.toArgs());
  }

  public CallResponse ownerOf(School_TokenOwnerOfInputBO input) throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "ownerOf", input.toArgs());
  }

  public CallResponse name() throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "name", Arrays.asList());
  }

  public CallResponse loaction(School_TokenLoactionInputBO input) throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "loaction", input.toArgs());
  }
}
