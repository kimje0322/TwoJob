import Web3 from 'web3';
import {
  BLOCKCHAIN_URL,
  PURCHASE_RECORD_CONTRACT_ADDRESS
} from '../config';
import {
  PURCHASE_RECORD_CONTRACT_ABI
} from '../config/ABIs.js';

export function createWeb3() {
  var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
  return web3;
}

export function createPurchaseRecordContract(web3) {
  var escrowContract = new web3.eth.Contract(
    PURCHASE_RECORD_CONTRACT_ABI,
    PURCHASE_RECORD_CONTRACT_ADDRESS,
  );

  return escrowContract;
}

export function getRecordNumber(escrowContractAddress, onConfirm, onFail) {
  
}

export function getRecord(escrowContractAddress, recordNo, onConfirm, onFail) {
  
}
