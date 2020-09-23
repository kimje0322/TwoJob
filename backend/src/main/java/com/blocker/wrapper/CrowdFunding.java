package com.blocker.wrapper;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple6;
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
    public static final String BINARY = "0x608060405261232c806100136000396000f3fe608060405260043610610099576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806324dcb72f1461009e5780633020580b1461018657806338cc48311461026257806352bf36ec146102b957806354722da81461045757806393fa5ec614610553578063af526b2f14610665578063f8b2cb4f1461074d578063f9fbd554146107b2575b600080fd5b3480156100aa57600080fd5b50610184600480360360408110156100c157600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001906401000000008111156100fe57600080fd5b82018360208201111561011057600080fd5b8035906020019184600183028401116401000000008311171561013257600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610892565b005b34801561019257600080fd5b50610260600480360360608110156101a957600080fd5b81019080803590602001906401000000008111156101c657600080fd5b8201836020820111156101d857600080fd5b803590602001918460018302840111640100000000831117156101fa57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192908035906020019092919080359060200190929190505050610b80565b005b34801561026e57600080fd5b506102776110e3565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156102c557600080fd5b5061037f600480360360208110156102dc57600080fd5b81019080803590602001906401000000008111156102f957600080fd5b82018360208201111561030b57600080fd5b8035906020019184600183028401116401000000008311171561032d57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192905050506110eb565b60405180806020018773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018681526020018581526020018481526020018360048111156103d757fe5b60ff168152602001828103825288818151815260200191508051906020019080838360005b838110156104175780820151818401526020810190506103fc565b50505050905090810190601f1680156104445780820380516001836020036101000a031916815260200191505b5097505050505050505060405180910390f35b34801561046357600080fd5b506105516004803603608081101561047a57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001906401000000008111156104b757600080fd5b8201836020820111156104c957600080fd5b803590602001918460018302840111640100000000831117156104eb57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050919291929080359060200190929190803590602001909291905050506112a3565b005b34801561055f57600080fd5b506106636004803603608081101561057657600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001906401000000008111156105d357600080fd5b8201836020820111156105e557600080fd5b8035906020019184600183028401116401000000008311171561060757600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192908035906020019092919050505061136c565b005b34801561067157600080fd5b5061074b6004803603604081101561068857600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001906401000000008111156106c557600080fd5b8201836020820111156106d757600080fd5b803590602001918460018302840111640100000000831117156106f957600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050611a69565b005b34801561075957600080fd5b5061079c6004803603602081101561077057600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050611d80565b6040518082815260200191505060405180910390f35b3480156107be57600080fd5b50610878600480360360208110156107d557600080fd5b81019080803590602001906401000000008111156107f257600080fd5b82018360208201111561080457600080fd5b8035906020019184600183028401116401000000008311171561082657600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050611e5d565b604051808215151515815260200191505060405180910390f35b600080826040518082805190602001908083835b6020831015156108cb57805182526020820191506020810190506020830392506108a6565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902090506001600481111561090d57fe5b8160070160009054906101000a900460ff16600481111561092a57fe5b14151561093657600080fd5b806003015481600201541115151561094d57600080fd5b8060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16610990611ed7565b73ffffffffffffffffffffffffffffffffffffffff161415156109b257600080fd5b8273ffffffffffffffffffffffffffffffffffffffff1663a9059cbb8260010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1683600301546040518363ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200182815260200192505050602060405180830381600087803b158015610a7d57600080fd5b505af1158015610a91573d6000803e3d6000fd5b505050506040513d6020811015610aa757600080fd5b81019080805190602001909291905050505060038160070160006101000a81548160ff02191690836004811115610ada57fe5b02179055507f1f747251ecd96060609f566d19c5e17f3ee0a54d4341d90141a6797579fa96e2826040518080602001828103825283818151815260200191508051906020019080838360005b83811015610b41578082015181840152602081019050610b26565b50505050905090810190601f168015610b6e5780820380516001836020036101000a031916815260200191505b509250505060405180910390a1505050565b60016004811115610b8d57fe5b6000846040518082805190602001908083835b602083101515610bc55780518252602082019150602081019050602083039250610ba0565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060070160009054906101000a900460ff166004811115610c1457fe5b14151515610c2157600080fd5b826000846040518082805190602001908083835b602083101515610c5a5780518252602082019150602081019050602083039250610c35565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206000019080519060200190610ca392919061225b565b50610cac611ed7565b6000846040518082805190602001908083835b602083101515610ce45780518252602082019150602081019050602083039250610cbf565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550816000846040518082805190602001908083835b602083101515610d915780518252602082019150602081019050602083039250610d6c565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060020181905550600080846040518082805190602001908083835b602083101515610e045780518252602082019150602081019050602083039250610ddf565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060030181905550600080846040518082805190602001908083835b602083101515610e775780518252602082019150602081019050602083039250610e52565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206004016000610eb7611ed7565b73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208190555062015180810242016000846040518082805190602001908083835b602083101515610f355780518252602082019150602081019050602083039250610f10565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206006018190555060016000846040518082805190602001908083835b602083101515610fa95780518252602082019150602081019050602083039250610f84565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060070160006101000a81548160ff02191690836004811115610ffa57fe5b02179055507f041828d7f84528c56a0f45c878fe07d22cabe6a744c0fe0a3d74d38a069bd044611028611ed7565b8484604051808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001838152602001828103825284818151815260200191508051906020019080838360005b838110156110a2578082015181840152602081019050611087565b50505050905090810190601f1680156110cf5780820380516001836020036101000a031916815260200191505b5094505050505060405180910390a1505050565b600030905090565b60606000806000806000806000886040518082805190602001908083835b60208310151561112e5780518252602082019150602081019050602083039250611109565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390209050806000018160010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16826002015483600301548460040160006111a4611ed7565b73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020548560070160009054906101000a900460ff16858054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156112865780601f1061125b57610100808354040283529160200191611286565b820191906000526020600020905b81548152906001019060200180831161126957829003601f168201915b505050505095509650965096509650965096505091939550919395565b600080846040518082805190602001908083835b6020831015156112dc57805182526020820191506020810190506020830392506112b7565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902090506003600481111561131e57fe5b8160070160009054906101000a900460ff16600481111561133b57fe5b14151561134757600080fd5b82816008016000828254019250508190555061136585858585611edf565b5050505050565b600080836040518082805190602001908083835b6020831015156113a55780518252602082019150602081019050602083039250611380565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902090508060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1661141d611ed7565b73ffffffffffffffffffffffffffffffffffffffff16141515156114a9576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600f8152602001807f796f75206172652063726561746f72000000000000000000000000000000000081525060200191505060405180910390fd5b600160048111156114b657fe5b8160070160009054906101000a900460ff1660048111156114d357fe5b141515611548576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260148152602001807f706c65617365206d616b652063616d706169676e00000000000000000000000081525060200191505060405180910390fd5b8473ffffffffffffffffffffffffffffffffffffffff16633b66aad18530856040518463ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018281526020019350505050602060405180830381600087803b15801561161f57600080fd5b505af1158015611633573d6000803e3d6000fd5b505050506040513d602081101561164957600080fd5b8101908080519060200190929190505050508473ffffffffffffffffffffffffffffffffffffffff166323b872dd8530856040518463ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018281526020019350505050602060405180830381600087803b15801561173257600080fd5b505af1158015611746573d6000803e3d6000fd5b505050506040513d602081101561175c57600080fd5b8101908080519060200190929190505050506000836040518082805190602001908083835b6020831015156117a65780518252602082019150602081019050602083039250611781565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206005016117e4611ed7565b90806001815401808255809150509060018203906000526020600020016000909192909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050816000846040518082805190602001908083835b602083101515611880578051825260208201915060208101905060208303925061185b565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060030160008282540192505081905550816000846040518082805190602001908083835b6020831015156118fc57805182526020820191506020810190506020830392506118d7565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020600401600061193c611ed7565b73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825401925050819055507fc01ee4a877a9698da7c64514cb5fcb9c1e110a3be7ac3f0fd717d83a9ae95b636119ac611ed7565b8484604051808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001838152602001828103825284818151815260200191508051906020019080838360005b83811015611a26578082015181840152602081019050611a0b565b50505050905090810190601f168015611a535780820380516001836020036101000a031916815260200191505b5094505050505060405180910390a15050505050565b600080826040518082805190602001908083835b602083101515611aa25780518252602082019150602081019050602083039250611a7d565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020905060016004811115611ae457fe5b8160070160009054906101000a900460ff166004811115611b0157fe5b141515611b0d57600080fd5b80600201548160030154101515611b2357600080fd5b600081600501805490509050600080905060008090505b82811015611cb65760008460050183806001019450815481101515611b5b57fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690508673ffffffffffffffffffffffffffffffffffffffff1663a9059cbb828760040160008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020546040518363ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200182815260200192505050602060405180830381600087803b158015611c6c57600080fd5b505af1158015611c80573d6000803e3d6000fd5b505050506040513d6020811015611c9657600080fd5b810190808051906020019092919050505050508080600101915050611b3a565b5060028360070160006101000a81548160ff02191690836004811115611cd857fe5b02179055507f7df70f52aad14ff64c7ace761306e9d698d2b1f43ba53f9cc1137a975b951c36846040518080602001828103825283818151815260200191508051906020019080838360005b83811015611d3f578082015181840152602081019050611d24565b50505050905090810190601f168015611d6c5780820380516001836020036101000a031916815260200191505b509250505060405180910390a15050505050565b60008173ffffffffffffffffffffffffffffffffffffffff166370a08231306040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060206040518083038186803b158015611e1b57600080fd5b505afa158015611e2f573d6000803e3d6000fd5b505050506040513d6020811015611e4557600080fd5b81019080805190602001909291905050509050919050565b6000806000836040518082805190602001908083835b602083101515611e985780518252602082019150602081019050602083039250611e73565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902090506001915050919050565b600033905090565b600080846040518082805190602001908083835b602083101515611f185780518252602082019150602081019050602083039250611ef3565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390209050606081600501805480602002602001604051908101604052809291908181526020018280548015611fd357602002820191906000526020600020905b8160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019060010190808311611f89575b50505050509050600081519050600085850290508360010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc6064601e840281151561203457fe5b049081150290604051600060405180830381858888f19350505050158015612060573d6000803e3d6000fd5b50600060646046830281151561207257fe5b04905060008090505b8381101561224f576000858281518110151561209357fe5b906020019060200201519050600087600301548860040160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020548115156120f157fe5b04840290508b73ffffffffffffffffffffffffffffffffffffffff1663a9059cbb83836040518363ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200182815260200192505050602060405180830381600087803b15801561219957600080fd5b505af11580156121ad573d6000803e3d6000fd5b505050506040513d60208110156121c357600080fd5b8101908080519060200190929190505050507fc1d32ad5cca423e7dda2123dbf8c482f8e77d00b631c06e903a47f2cec1334df8282604051808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018281526020019250505060405180910390a15050808060010191505061207b565b50505050505050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061229c57805160ff19168380011785556122ca565b828001600101855582156122ca579182015b828111156122c95782518255916020019190600101906122ae565b5b5090506122d791906122db565b5090565b6122fd91905b808211156122f95760008160009055506001016122e1565b5090565b9056fea165627a7a72305820c1c189651e3038666e30c11a889bcbc5cd23510efddcff0fcc6662a57a6a68700029";

    public static final String FUNC_CREATECAMPAIGN = "createCampaign";

    public static final String FUNC_FUNDINGCAMPIGN = "FundingCampign";

    public static final String FUNC_RECEIVEFUNDS = "receiveFunds";

    public static final String FUNC_REFUND = "Refund";

    public static final String FUNC_GETBALANCE = "getBalance";

    public static final String FUNC_GETCAMPAIGN = "getCampaign";

    public static final String FUNC_GETADDRESS = "getAddress";

    public static final String FUNC_TEST = "test";

    public static final String FUNC_SALEITEM = "SaleItem";

    public static final Event CAMPAIGNCREATE_EVENT = new Event("CampaignCreate", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event FUNDING_EVENT = new Event("Funding", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event FUNDINGSUCCESS_EVENT = new Event("FundingSuccess", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event FUNDINGFAIL_EVENT = new Event("FundingFail", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event DISTRIBUTE_EVENT = new Event("Distribute", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
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

    public List<CampaignCreateEventResponse> getCampaignCreateEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CAMPAIGNCREATE_EVENT, transactionReceipt);
        ArrayList<CampaignCreateEventResponse> responses = new ArrayList<CampaignCreateEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CampaignCreateEventResponse typedResponse = new CampaignCreateEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.code = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CampaignCreateEventResponse> campaignCreateEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, CampaignCreateEventResponse>() {
            @Override
            public CampaignCreateEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CAMPAIGNCREATE_EVENT, log);
                CampaignCreateEventResponse typedResponse = new CampaignCreateEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.code = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CampaignCreateEventResponse> campaignCreateEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CAMPAIGNCREATE_EVENT));
        return campaignCreateEventFlowable(filter);
    }

    public List<FundingEventResponse> getFundingEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(FUNDING_EVENT, transactionReceipt);
        ArrayList<FundingEventResponse> responses = new ArrayList<FundingEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FundingEventResponse typedResponse = new FundingEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.code = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<FundingEventResponse> fundingEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, FundingEventResponse>() {
            @Override
            public FundingEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(FUNDING_EVENT, log);
                FundingEventResponse typedResponse = new FundingEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.code = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<FundingEventResponse> fundingEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FUNDING_EVENT));
        return fundingEventFlowable(filter);
    }

    public List<FundingSuccessEventResponse> getFundingSuccessEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(FUNDINGSUCCESS_EVENT, transactionReceipt);
        ArrayList<FundingSuccessEventResponse> responses = new ArrayList<FundingSuccessEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FundingSuccessEventResponse typedResponse = new FundingSuccessEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.code = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<FundingSuccessEventResponse> fundingSuccessEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, FundingSuccessEventResponse>() {
            @Override
            public FundingSuccessEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(FUNDINGSUCCESS_EVENT, log);
                FundingSuccessEventResponse typedResponse = new FundingSuccessEventResponse();
                typedResponse.log = log;
                typedResponse.code = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<FundingSuccessEventResponse> fundingSuccessEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FUNDINGSUCCESS_EVENT));
        return fundingSuccessEventFlowable(filter);
    }

    public List<FundingFailEventResponse> getFundingFailEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(FUNDINGFAIL_EVENT, transactionReceipt);
        ArrayList<FundingFailEventResponse> responses = new ArrayList<FundingFailEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FundingFailEventResponse typedResponse = new FundingFailEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.code = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<FundingFailEventResponse> fundingFailEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, FundingFailEventResponse>() {
            @Override
            public FundingFailEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(FUNDINGFAIL_EVENT, log);
                FundingFailEventResponse typedResponse = new FundingFailEventResponse();
                typedResponse.log = log;
                typedResponse.code = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<FundingFailEventResponse> fundingFailEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FUNDINGFAIL_EVENT));
        return fundingFailEventFlowable(filter);
    }

    public List<DistributeEventResponse> getDistributeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DISTRIBUTE_EVENT, transactionReceipt);
        ArrayList<DistributeEventResponse> responses = new ArrayList<DistributeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DistributeEventResponse typedResponse = new DistributeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.to = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DistributeEventResponse> distributeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, DistributeEventResponse>() {
            @Override
            public DistributeEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(DISTRIBUTE_EVENT, log);
                DistributeEventResponse typedResponse = new DistributeEventResponse();
                typedResponse.log = log;
                typedResponse.to = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DistributeEventResponse> distributeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DISTRIBUTE_EVENT));
        return distributeEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> createCampaign(String _uniqueCode, BigInteger _fundingGoal, BigInteger numberOfDays) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CREATECAMPAIGN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_uniqueCode), 
                new org.web3j.abi.datatypes.generated.Uint256(_fundingGoal), 
                new org.web3j.abi.datatypes.generated.Uint256(numberOfDays)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> FundingCampign(String _tokenAddress, String _from, String _uniqueCode, BigInteger _fundingValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FUNDINGCAMPIGN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_tokenAddress), 
                new org.web3j.abi.datatypes.Address(_from), 
                new org.web3j.abi.datatypes.Utf8String(_uniqueCode), 
                new org.web3j.abi.datatypes.generated.Uint256(_fundingValue)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> receiveFunds(String _tokenAddress, String _uniqueCode) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RECEIVEFUNDS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_tokenAddress), 
                new org.web3j.abi.datatypes.Utf8String(_uniqueCode)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> Refund(String _tokenAddress, String _uniqueCode) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REFUND, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_tokenAddress), 
                new org.web3j.abi.datatypes.Utf8String(_uniqueCode)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getBalance(String _tokenAddress) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETBALANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_tokenAddress)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple6<String, String, BigInteger, BigInteger, BigInteger, BigInteger>> getCampaign(String _uniqueCode) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCAMPAIGN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_uniqueCode)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint8>() {}));
        return new RemoteFunctionCall<Tuple6<String, String, BigInteger, BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple6<String, String, BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple6<String, String, BigInteger, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<String, String, BigInteger, BigInteger, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue());
                    }
                });
    }

    public RemoteFunctionCall<String> getAddress() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> test(String _uniqueCode) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TEST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_uniqueCode)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> SaleItem(String _tokenAddress, String _uniqueCode, BigInteger _count, BigInteger _money) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SALEITEM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_tokenAddress), 
                new org.web3j.abi.datatypes.Utf8String(_uniqueCode), 
                new org.web3j.abi.datatypes.generated.Uint256(_count), 
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

    public static class CampaignCreateEventResponse extends BaseEventResponse {
        public String from;

        public String code;

        public BigInteger amount;
    }

    public static class FundingEventResponse extends BaseEventResponse {
        public String from;

        public String code;

        public BigInteger amount;
    }

    public static class FundingSuccessEventResponse extends BaseEventResponse {
        public String code;
    }

    public static class FundingFailEventResponse extends BaseEventResponse {
        public String code;
    }

    public static class DistributeEventResponse extends BaseEventResponse {
        public String to;

        public BigInteger amount;
    }
}
