pragma solidity ^0.7.0;

interface ISaleItem{
    
   /** 물건 판매
    @param _uniqueCode 게시글에 대한 해시값
    */
    function SaleItem(string memory _uniqueCode, uint count) payable external;

    /** 수익 분배
    @param _uniqueCode 게시글에 대한 해시값
    */
    function DistributeProfit(string memory _uniqueCode, uint256 money) payable external;
}