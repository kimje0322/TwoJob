
import Web3 from 'web3';
import {
  BLOCKCHAIN_URL,
  BLOCKCHAIN_WEBSOCKET_URL,
  ITEM_INVENTORY_CONTRACT_ADDRESS,
} from '../config';
import {
  ITEM_INVENTORY_CONTRACT_ABI,
  ESCROW_CONTRACT_ABI,
} from '../config/ABIs.js';

export function createWeb3() {
  var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
  return web3;
}

export function createFactoryContract(web3) {
  var inventoryContract = new web3.eth.Contract(
    ITEM_INVENTORY_CONTRACT_ABI,
    ITEM_INVENTORY_CONTRACT_ADDRESS,
  );
  return inventoryContract;
}

export function createEscrowContract(web3, contractAddress) {
  var escrowContract = new web3.eth.Contract(
    ESCROW_CONTRACT_ABI,
    contractAddress,
  );
  return escrowContract;
}

export function registerItem(
  options,
  privateKey,
  onConfirm,
  onFail
) {
  
}

export function deregisterItem(
  itemId,
  privateKey,
  onConfirm,
  onFail,
) {
  
}

export function purchaseItem(
  itemId,
  privateKey,
  onConfirm,
  onFail,
) {
  
}

export function cancel(
  escrowContractAddress,
  privateKey,
  onConfirm,
  onFail
) {

}

export function send(
  escrowContractAddress,
  privateKey,
  onConfirm,
  onFail
) {
  
}

export function confirm(
  escrowContractAddress,
  privateKey,
  onConfirm,
  onFail
) {
  
}

export function getPrice(itemId, onConfirm, onFail) {
  var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
  var contract = createFactoryContract(web3);
  contract.methods
    .items(itemId)
    .call()
    .then(res => {
      onConfirm(res.price);
    })
    .catch(err => {
      onFail(err);
    });
}
