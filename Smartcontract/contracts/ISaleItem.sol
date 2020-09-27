pragma solidity ^0.7.0;

interface ISaleItem{
    
   /** 물건 판매
    @param _uniqueCode 게시글에 대한 해시값
    @param _count 판매갯수
    */
    function SaleItem(string memory _uniqueCode, uint _count) payable external;

    /** 수익 분배
    @param _uniqueCode 게시글에 대한 해시값
    @param _money 투자자들에게 나눠줄 수익금
    */
    function DistributeProfit(string memory _uniqueCode, uint256 _money) payable external;
}