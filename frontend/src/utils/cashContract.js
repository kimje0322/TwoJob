import Web3 from 'web3';
import { BLOCKCHAIN_URL, BLOCKCHAIN_WEBSOCKET_URL, CASH_CONTRACT_ADDRESS } from '@/config';
import { CASH_CONTRACT_ABI } from '@/config/ABIs.js';

function createCashContract(web3) {
  return new web3.eth.Contract(CASH_CONTRACT_ABI, CASH_CONTRACT_ADDRESS);
}

export function buyCash(amount, privateKey, onConfirm, onFail) {

}

export function leaveDeposit(options, walletAddress, privateKey, onConfirm, onFail) {
  
}

export function getBalance(walletAddress, onConfirm, onFail) {

}
