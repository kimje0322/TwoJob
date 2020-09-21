package com.blocker.wrapper;

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
public class ICrowdFunding extends Contract {
    public static final String BINARY = "0x";

    public static final String FUNC_CREATECAMPAIGN = "createCampaign";

    public static final String FUNC_FUNDINGCAMPIGN = "FundingCampign";

    public static final String FUNC_DUEDAY = "DueDay";

    public static final Event NOTICECREATECAMPAIGN_EVENT = new Event("noticeCreateCampaign", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected ICrowdFunding(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ICrowdFunding(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ICrowdFunding(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ICrowdFunding(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
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

    @Deprecated
    public static ICrowdFunding load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ICrowdFunding(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ICrowdFunding load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ICrowdFunding(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ICrowdFunding load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ICrowdFunding(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ICrowdFunding load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ICrowdFunding(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ICrowdFunding> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ICrowdFunding.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ICrowdFunding> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ICrowdFunding.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ICrowdFunding> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ICrowdFunding.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ICrowdFunding> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ICrowdFunding.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
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
