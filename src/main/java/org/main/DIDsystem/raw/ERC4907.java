package org.main.DIDsystem.raw;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.abi.FunctionEncoder;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Address;
import org.fisco.bcos.sdk.abi.datatypes.Bool;
import org.fisco.bcos.sdk.abi.datatypes.Event;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.Utf8String;
import org.fisco.bcos.sdk.abi.datatypes.generated.Uint256;
import org.fisco.bcos.sdk.abi.datatypes.generated.Uint64;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple3;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple4;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.eventsub.EventCallback;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class ERC4907 extends Contract {
    public static final String[] BINARY_ARRAY = {"60806040523480156200001157600080fd5b506040516200137938038062001379833981810160405260408110156200003757600080fd5b81019080805160405193929190846401000000008211156200005857600080fd5b838201915060208201858111156200006f57600080fd5b82518660018202830111640100000000821117156200008d57600080fd5b8083526020830192505050908051906020019080838360005b83811015620000c3578082015181840152602081019050620000a6565b50505050905090810190601f168015620000f15780820380516001836020036101000a031916815260200191505b50604052602001805160405193929190846401000000008211156200011557600080fd5b838201915060208201858111156200012c57600080fd5b82518660018202830111640100000000821117156200014a57600080fd5b8083526020830192505050908051906020019080838360005b838110156200018057808201518184015260208101905062000163565b50505050905090810190601f168015620001ae5780820380516001836020036101000a031916815260200191505b5060405250505081818160009080519060200190620001cf929190620001f3565b508060019080519060200190620001e8929190620001f3565b5050505050620002a2565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200023657805160ff191683800117855562000267565b8280016001018555821562000267579182015b828111156200026657825182559160200191906001019062000249565b5b5090506200027691906200027a565b5090565b6200029f91905b808211156200029b57600081600090555060010162000281565b5090565b90565b6110c780620002b26000396000f3fe608060405234801561001057600080fd5b50600436106100b45760003560e01c8063beabacc811610071578063beabacc814610492578063c2f1f14a14610500578063c6fec6841461056e578063cdd8b9dd14610615578063d9548e531461066b578063e030565e146106b1576100b4565b806306fdde03146100b95780632fb102cf1461013c5780636352211e146102b857806381f64ae2146103265780638fc88c48146103cd57806395d89b411461040f575b600080fd5b6100c161072b565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156101015780820151818401526020810190506100e6565b50505050905090810190601f16801561012e5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6102b66004803603608081101561015257600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291908035906020019064010000000081111561019957600080fd5b8201836020820111156101ab57600080fd5b803590602001918460018302840111640100000000831117156101cd57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192908035906020019064010000000081111561023057600080fd5b82018360208201111561024257600080fd5b8035906020019184600183028401116401000000008311171561026457600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192905050506107cd565b005b6102e4600480360360208110156102ce57600080fd5b81019080803590602001909291905050506108aa565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6103526004803603602081101561033c57600080fd5b81019080803590602001909291905050506108bc565b6040518080602001828103825283818151815260200191508051906020019080838360005b83811015610392578082015181840152602081019050610377565b50505050905090810190601f1680156103bf5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6103f9600480360360208110156103e357600080fd5b8101908080359060200190929190505050610971565b6040518082815260200191505060405180910390f35b610417610a32565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561045757808201518184015260208101905061043c565b50505050905090810190601f1680156104845780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6104fe600480360360608110156104a857600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610ad4565b005b61052c6004803603602081101561051657600080fd5b8101908080359060200190929190505050610bc9565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b61059a6004803603602081101561058457600080fd5b8101908080359060200190929190505050610c27565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156105da5780820151818401526020810190506105bf565b50505050905090810190601f1680156106075780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6106416004803603602081101561062b57600080fd5b8101908080359060200190929190505050610cdc565b604051808267ffffffffffffffff1667ffffffffffffffff16815260200191505060405180910390f35b6106976004803603602081101561068157600080fd5b8101908080359060200190929190505050610d15565b604051808215151515815260200191505060405180910390f35b610711600480360360608110156106c757600080fd5b8101908080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803567ffffffffffffffff169060200190929190505050610d99565b604051808215151515815260200191505060405180910390f35b606060008054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156107c35780601f10610798576101008083540402835291602001916107c3565b820191906000526020600020905b8154815290600101906020018083116107a657829003601f168201915b5050505050905090565b600073ffffffffffffffffffffffffffffffffffffffff168473ffffffffffffffffffffffffffffffffffffffff16146108a457836002600085815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508160056000858152602001908152602001600020908051906020019061087a929190610fbb565b50806006600085815260200190815260200160002090805190602001906108a2929190610fbb565b505b50505050565b60006108b582610f27565b9050919050565b6060600560008381526020019081526020016000208054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156109655780601f1061093a57610100808354040283529160200191610965565b820191906000526020600020905b81548152906001019060200180831161094857829003601f168201915b50505050509050919050565b600061097c82610d15565b156109bf57426008600084815260200190815260200160002060000160149054906101000a900467ffffffffffffffff1667ffffffffffffffff16039050610a2d565b6040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260118152602001807f546f6b656e20697320657870697265642100000000000000000000000000000081525060200191505060405180910390fd5b919050565b606060018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610aca5780601f10610a9f57610100808354040283529160200191610aca565b820191906000526020600020905b815481529060010190602001808311610aad57829003601f168201915b5050505050905090565b6000610adf82610f7e565b90508373ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff161415610bc357826002600084815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550818373ffffffffffffffffffffffffffffffffffffffff168573ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef60405160405180910390a45b50505050565b6000610bd482610d15565b15610c1d5760006008600084815260200190815260200160002090508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16915050610c22565b600090505b919050565b6060600660008381526020019081526020016000208054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610cd05780601f10610ca557610100808354040283529160200191610cd0565b820191906000526020600020905b815481529060010190602001808311610cb357829003601f168201915b50505050509050919050565b6000806008600084815260200190815260200160002090508060000160149054906101000a900467ffffffffffffffff16915050919050565b60007f1bbe5a9a2ccf6b6af5ee05d52aa443e39d0b0fe71b6fe72f698ed926d70227d060405160405180910390a160006008600084815260","20019081526020016000209050428160000160149054906101000a900467ffffffffffffffff1667ffffffffffffffff161115610d8e576001915050610d94565b60009150505b919050565b6000610da4846108aa565b73ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610e27576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260318152602001806110616031913960400191505060405180910390fd5b6000600860008681526020019081526020016000209050838160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508267ffffffffffffffff1642018160000160146101000a81548167ffffffffffffffff021916908367ffffffffffffffff1602179055508373ffffffffffffffffffffffffffffffffffffffff16857f4e06b4e7000e659094299b3533b47b6aa8ad048e95e872d23d1f4ee55af89cfe85604051808267ffffffffffffffff1667ffffffffffffffff16815260200191505060405180910390a360019150509392505050565b600080610f3383610f7e565b9050600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff161415610f74576000915050610f79565b809150505b919050565b60006002600083815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610ffc57805160ff191683800117855561102a565b8280016001018555821561102a579182015b8281111561102957825182559160200191906001019061100e565b5b509050611037919061103b565b5090565b61105d91905b80821115611059576000816000905550600101611041565b5090565b9056fe4552433732313a207472616e736665722063616c6c6572206973206e6f74206f776e6572206e6f7220617070726f766564a26469706673582212205a102bc4f2919f8b404bfe5804624752b8d6e1bdbdec4bfbe23d27a027178ebc64736f6c634300060a0033"};

    public static final String ERC_NAME = "ERC4907";

    public static final String ERC_CONTRACT_ADDRESS = "0x102fe0badb3b6768fe069f5930cc61d221e61e38";

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[{\"internalType\":\"string\",\"name\":\"name_\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"symbol_\",\"type\":\"string\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"from\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"to\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"uint256\",\"name\":\"tokenId\",\"type\":\"uint256\"}],\"name\":\"Transfer\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[],\"name\":\"UpdateTime\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"uint256\",\"name\":\"tokenId\",\"type\":\"uint256\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"user\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint64\",\"name\":\"expires\",\"type\":\"uint64\"}],\"name\":\"UpdateUser\",\"type\":\"event\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"tokenId\",\"type\":\"uint256\"}],\"name\":\"imageurl\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"tokenId\",\"type\":\"uint256\"}],\"name\":\"isExpired\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"tokenId\",\"type\":\"uint256\"}],\"name\":\"loaction\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"to\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"tokenId\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"url\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"location\",\"type\":\"string\"}],\"name\":\"mint\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"name\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"tokenId\",\"type\":\"uint256\"}],\"name\":\"ownerOf\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"tokenId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"user\",\"type\":\"address\"},{\"internalType\":\"uint64\",\"name\":\"useTime\",\"type\":\"uint64\"}],\"name\":\"setUser\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"symbol\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"from\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"to\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"tokenId\",\"type\":\"uint256\"}],\"name\":\"transfer\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"tokenId\",\"type\":\"uint256\"}],\"name\":\"userExpireTime\",\"outputs\":[{\"internalType\":\"uint64\",\"name\":\"\",\"type\":\"uint64\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"tokenId\",\"type\":\"uint256\"}],\"name\":\"userExpires\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"tokenId\",\"type\":\"uint256\"}],\"name\":\"userOf\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_IMAGEURL = "imageurl";

    public static final String FUNC_ISEXPIRED = "isExpired";

    public static final String FUNC_LOACTION = "loaction";

    public static final String FUNC_MINT = "mint";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_OWNEROF = "ownerOf";

    public static final String FUNC_SETUSER = "setUser";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_USEREXPIRETIME = "userExpireTime";

    public static final String FUNC_USEREXPIRES = "userExpires";

    public static final String FUNC_USEROF = "userOf";

    public static final Event TRANSFER_EVENT = new Event("Transfer",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    public static final Event UPDATETIME_EVENT = new Event("UpdateTime",
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event UPDATEUSER_EVENT = new Event("UpdateUser",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint64>() {}));
    ;

    protected ERC4907(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeTransferEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(TRANSFER_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeTransferEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(TRANSFER_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public List<UpdateTimeEventResponse> getUpdateTimeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(UPDATETIME_EVENT, transactionReceipt);
        ArrayList<UpdateTimeEventResponse> responses = new ArrayList<UpdateTimeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UpdateTimeEventResponse typedResponse = new UpdateTimeEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeUpdateTimeEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(UPDATETIME_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeUpdateTimeEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(UPDATETIME_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public List<UpdateUserEventResponse> getUpdateUserEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(UPDATEUSER_EVENT, transactionReceipt);
        ArrayList<UpdateUserEventResponse> responses = new ArrayList<UpdateUserEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UpdateUserEventResponse typedResponse = new UpdateUserEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.expires = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeUpdateUserEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(UPDATEUSER_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeUpdateUserEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(UPDATEUSER_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public String imageurl(BigInteger tokenId) throws ContractException {
        final Function function = new Function(FUNC_IMAGEURL,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public TransactionReceipt isExpired(BigInteger tokenId) {
        final Function function = new Function(
                FUNC_ISEXPIRED,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void isExpired(BigInteger tokenId, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_ISEXPIRED,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForIsExpired(BigInteger tokenId) {
        final Function function = new Function(
                FUNC_ISEXPIRED,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<BigInteger> getIsExpiredInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_ISEXPIRED,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public Tuple1<Boolean> getIsExpiredOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_ISEXPIRED,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<Boolean>(

                (Boolean) results.get(0).getValue()
                );
    }

    public String loaction(BigInteger tokenId) throws ContractException {
        final Function function = new Function(FUNC_LOACTION,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public TransactionReceipt mint(String to, BigInteger tokenId, String url, String location) {
        final Function function = new Function(
                FUNC_MINT,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(to),
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(tokenId),
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(url),
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(location)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void mint(String to, BigInteger tokenId, String url, String location, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_MINT,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(to),
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(tokenId),
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(url),
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(location)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForMint(String to, BigInteger tokenId, String url, String location) {
        final Function function = new Function(
                FUNC_MINT,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(to),
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(tokenId),
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(url),
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(location)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple4<String, BigInteger, String, String> getMintInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_MINT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple4<String, BigInteger, String, String>(

                (String) results.get(0).getValue(),
                (BigInteger) results.get(1).getValue(),
                (String) results.get(2).getValue(),
                (String) results.get(3).getValue()
                );
    }

    public String name() throws ContractException {
        final Function function = new Function(FUNC_NAME,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public String ownerOf(BigInteger tokenId) throws ContractException {
        final Function function = new Function(FUNC_OWNEROF,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public TransactionReceipt setUser(BigInteger tokenId, String user, BigInteger useTime) {
        final Function function = new Function(
                FUNC_SETUSER,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(tokenId),
                new org.fisco.bcos.sdk.abi.datatypes.Address(user),
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint64(useTime)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void setUser(BigInteger tokenId, String user, BigInteger useTime, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_SETUSER,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(tokenId),
                new org.fisco.bcos.sdk.abi.datatypes.Address(user),
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint64(useTime)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForSetUser(BigInteger tokenId, String user, BigInteger useTime) {
        final Function function = new Function(
                FUNC_SETUSER,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(tokenId),
                new org.fisco.bcos.sdk.abi.datatypes.Address(user),
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint64(useTime)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple3<BigInteger, String, BigInteger> getSetUserInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SETUSER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint64>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple3<BigInteger, String, BigInteger>(

                (BigInteger) results.get(0).getValue(),
                (String) results.get(1).getValue(),
                (BigInteger) results.get(2).getValue()
                );
    }

    public Tuple1<Boolean> getSetUserOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_SETUSER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<Boolean>(

                (Boolean) results.get(0).getValue()
                );
    }

    public String symbol() throws ContractException {
        final Function function = new Function(FUNC_SYMBOL,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public TransactionReceipt transfer(String from, String to, BigInteger tokenId) {
        final Function function = new Function(
                FUNC_TRANSFER,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(from),
                new org.fisco.bcos.sdk.abi.datatypes.Address(to),
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void transfer(String from, String to, BigInteger tokenId, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_TRANSFER,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(from),
                new org.fisco.bcos.sdk.abi.datatypes.Address(to),
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForTransfer(String from, String to, BigInteger tokenId) {
        final Function function = new Function(
                FUNC_TRANSFER,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(from),
                new org.fisco.bcos.sdk.abi.datatypes.Address(to),
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple3<String, String, BigInteger> getTransferInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_TRANSFER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple3<String, String, BigInteger>(

                (String) results.get(0).getValue(),
                (String) results.get(1).getValue(),
                (BigInteger) results.get(2).getValue()
                );
    }

    public BigInteger userExpireTime(BigInteger tokenId) throws ContractException {
        final Function function = new Function(FUNC_USEREXPIRETIME,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public TransactionReceipt userExpires(BigInteger tokenId) {
        final Function function = new Function(
                FUNC_USEREXPIRES,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void userExpires(BigInteger tokenId, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_USEREXPIRES,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForUserExpires(BigInteger tokenId) {
        final Function function = new Function(
                FUNC_USEREXPIRES,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<BigInteger> getUserExpiresInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_USEREXPIRES,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public Tuple1<BigInteger> getUserExpiresOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_USEREXPIRES,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public TransactionReceipt userOf(BigInteger tokenId) {
        final Function function = new Function(
                FUNC_USEROF,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void userOf(BigInteger tokenId, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_USEROF,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForUserOf(BigInteger tokenId) {
        final Function function = new Function(
                FUNC_USEROF,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<BigInteger> getUserOfInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_USEROF,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public Tuple1<String> getUserOfOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_USEROF,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public static ERC4907 load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new ERC4907(contractAddress, client, credential);
    }

    public static ERC4907 deploy(Client client, CryptoKeyPair credential, String name_, String symbol_) throws ContractException {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(name_),
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(symbol_)));
        return deploy(ERC4907.class, client, credential, getBinary(client.getCryptoSuite()), encodedConstructor);
    }

    public static class TransferEventResponse {
        public TransactionReceipt.Logs log;

        public String from;

        public String to;

        public BigInteger tokenId;
    }

    public static class UpdateTimeEventResponse {
        public TransactionReceipt.Logs log;
    }

    public static class UpdateUserEventResponse {
        public TransactionReceipt.Logs log;

        public BigInteger tokenId;

        public String user;

        public BigInteger expires;
    }
}
