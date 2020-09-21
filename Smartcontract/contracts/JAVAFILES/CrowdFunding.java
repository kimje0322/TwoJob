package javafiles;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.16.
 */
@SuppressWarnings("rawtypes")
public class CrowdFunding extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b506111c6806100206000396000f3fe60806040526004361061004a5760003560e01c80630393d5f91461004f57806309fc2329146101175780633020580b146101dc5780636df51223146102b8578063f7e472601461037d575b600080fd5b34801561005b57600080fd5b506101156004803603602081101561007257600080fd5b810190808035906020019064010000000081111561008f57600080fd5b8201836020820111156100a157600080fd5b803590602001918460018302840111640100000000831117156100c357600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610438565b005b6101da6004803603604081101561012d57600080fd5b810190808035906020019064010000000081111561014a57600080fd5b82018360208201111561015c57600080fd5b8035906020019184600183028401116401000000008311171561017e57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803590602001909291905050506107e2565b005b3480156101e857600080fd5b506102b6600480360360608110156101ff57600080fd5b810190808035906020019064010000000081111561021c57600080fd5b82018360208201111561022e57600080fd5b8035906020019184600183028401116401000000008311171561025057600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192908035906020019092919080359060200190929190505050610a3c565b005b61037b600480360360408110156102ce57600080fd5b81019080803590602001906401000000008111156102eb57600080fd5b8201836020820111156102fd57600080fd5b8035906020019184600183028401116401000000008311171561031f57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050919291929080359060200190929190505050610ed3565b005b6104366004803603602081101561039357600080fd5b81019080803590602001906401000000008111156103b057600080fd5b8201836020820111156103c257600080fd5b803590602001918460018302840111640100000000831117156103e457600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610f60565b005b806000816040518082805190602001908083835b6020831061046f578051825260208201915060208101905060208303925061044c565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060070160009054906101000a900460ff16156104bd57600080fd5b816000816040518082805190602001908083835b602083106104f457805182526020820191506020810190506020830392506104d1565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461058257600080fd5b826000816040518082805190602001908083835b602083106105b95780518252602082019150602081019050602083039250610596565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020600601544210156105fc57600080fd5b600080856040518082805190602001908083835b602083106106335780518252602082019150602081019050602083039250610610565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390209050806003015481600201541115610772576000816005018054905090506000805b8281101561076a5760008460050183806001019450815481106106a757fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690508073ffffffffffffffffffffffffffffffffffffffff166108fc8660040160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549081150290604051600060405180830381858888f1935050505015801561075b573d6000803e3d6000fd5b50508080600101915050610688565b5050506107be565b3373ffffffffffffffffffffffffffffffffffffffff166108fc82600301549081150290604051600060405180830381858888f193505050501580156107bc573d6000803e3d6000fd5b505b60018160070160006101000a81548160ff0219169083151502179055505050505050565b600080836040518082805190602001908083835b6020831061081957805182526020820191506020810190506020830392506107f6565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902090506060816005018054806020026020016040519081016040528092919081815260200182805480156108d457602002820191906000526020600020905b8160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001906001019080831161088a575b505050505090506000815190508260010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc6064601e87028161092c57fe5b049081150290604051600060405180830381858888f19350505050158015610958573d6000803e3d6000fd5b5060006064604686028161096857fe5b04905060005b82811015610a3357600084828151811061098457fe5b602002602001015190508073ffffffffffffffffffffffffffffffffffffffff166108fc87600301548860040160008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054816109f657fe5b0485029081150290604051600060405180830381858888f19350505050158015610a24573d6000803e3d6000fd5b5050808060010191505061096e565b50505050505050565b826000846040518082805190602001908083835b60208310610a735780518252602082019150602081019050602083039250610a50565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206000019080519060200190610abc9291906110f3565b50336000846040518082805190602001908083835b60208310610af45780518252602082019150602081019050602083039250610ad1565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550816000846040518082805190602001908083835b60208310610b9f5780518252602082019150602081019050602083039250610b7c565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060020181905550600080846040518082805190602001908083835b60208310610c105780518252602082019150602081019050602083039250610bed565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060030181905550806000846040518082805190602001908083835b60208310610c815780518252602082019150602081019050602083039250610c5e565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060060181905550600080846040518082805190602001908083835b60208310610cf25780518252602082019150602081019050602083039250610ccf565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060070160006101000a81548160ff021916908315150217905550600080846040518082805190602001908083835b60208310610d765780518252602082019150602081019050602083039250610d53565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902090507f97de0620dbdc87f5cebabad532cf603c5d7d6458d3373580015fca2830ab165e816000018260010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1683600201548460030154856006015460405180806020018673ffffffffffffffffffffffffffffffffffffffff168152602001858152602001848152602001838152602001828103825287818154600181600116156101000203166002900481526020019150805460018160011615610100020316600290048015610ebb5780601f10610e9057610100808354040283529160200191610ebb565b820191906000526020600020905b815481529060010190602001808311610e9e57829003601f168201915b5050965050505050505060405180910390a150505050565b600080836040518082805190602001908083835b60208310610f0a5780518252602082019150602081019050602083039250610ee7565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390209050818160080160008282540192505081905550610f5b83346107e2565b505050565b600080826040518082805190602001908083835b60208310610f975780518252602082019150602081019050602083039250610f74565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902090508060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141561102957600080fd5b80600501339080600181540180825580915050600190039060005260206000200160009091909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550348160030160008282540192505081905550348160040160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825401925050819055505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061113457805160ff1916838001178555611162565b82800160010185558215611162579182015b82811115611161578251825591602001919060010190611146565b5b50905061116f9190611173565b5090565b5b8082111561118c576000816000905550600101611174565b509056fea26469706673582212201b2974ec1374c6f47dcada7e7abc0c5373fdfa9a3e89c06cf99067a5f31c4adc64736f6c63430007000033";

    public static final String FUNC_CREATECAMPAIGN = "createCampaign";

    public static final String FUNC_FUNDINGCAMPIGN = "FundingCampign";

    public static final String FUNC_DUEDAY = "DueDay";

    public static final String FUNC_SALEITEM = "SaleItem";

    public static final String FUNC_DISTRIBUTEPROFIT = "DistributeProfit";

    public static final Event NOTICECREATECAMPAIGN_EVENT = new Event("noticeCreateCampaign", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected CrowdFunding(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CrowdFunding(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CrowdFunding(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CrowdFunding(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<NoticeCreateCampaignEventResponse> getNoticeCreateCampaignEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NOTICECREATECAMPAIGN_EVENT, transactionReceipt);
        ArrayList<NoticeCreateCampaignEventResponse> responses = new ArrayList<NoticeCreateCampaignEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NoticeCreateCampaignEventResponse typedResponse = new NoticeCreateCampaignEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._uniqueCode = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._creator = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._fundingGoal = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._fundraiseAmount = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse._deadline = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NoticeCreateCampaignEventResponse> noticeCreateCampaignEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, NoticeCreateCampaignEventResponse>() {
            @Override
            public NoticeCreateCampaignEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NOTICECREATECAMPAIGN_EVENT, log);
                NoticeCreateCampaignEventResponse typedResponse = new NoticeCreateCampaignEventResponse();
                typedResponse.log = log;
                typedResponse._uniqueCode = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._creator = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._fundingGoal = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._fundraiseAmount = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse._deadline = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<NoticeCreateCampaignEventResponse> noticeCreateCampaignEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NOTICECREATECAMPAIGN_EVENT));
        return noticeCreateCampaignEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> createCampaign(String _uniqueCode, BigInteger _fundingGoal, BigInteger _deadline) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CREATECAMPAIGN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_uniqueCode), 
                new org.web3j.abi.datatypes.generated.Uint256(_fundingGoal), 
                new org.web3j.abi.datatypes.generated.Uint256(_deadline)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> FundingCampign(String _uniqueCode) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FUNDINGCAMPIGN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_uniqueCode)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> DueDay(String _uniqueCode) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DUEDAY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_uniqueCode)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> SaleItem(String _uniqueCode, BigInteger _count) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SALEITEM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_uniqueCode), 
                new org.web3j.abi.datatypes.generated.Uint256(_count)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> DistributeProfit(String _uniqueCode, BigInteger _money) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DISTRIBUTEPROFIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_uniqueCode), 
                new org.web3j.abi.datatypes.generated.Uint256(_money)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static CrowdFunding load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CrowdFunding(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CrowdFunding load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CrowdFunding(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CrowdFunding load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new CrowdFunding(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CrowdFunding load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new CrowdFunding(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<CrowdFunding> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CrowdFunding.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CrowdFunding> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CrowdFunding.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<CrowdFunding> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CrowdFunding.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CrowdFunding> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CrowdFunding.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class NoticeCreateCampaignEventResponse extends BaseEventResponse {
        public String _uniqueCode;

        public String _creator;

        public BigInteger _fundingGoal;

        public BigInteger _fundraiseAmount;

        public BigInteger _deadline;
    }
}
