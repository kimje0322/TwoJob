pragma solidity ^0.7.0;

contract Campaign {

    // 게시글에대한 해시값, 투자를 받는 사람의 주소, 목표금액, 모인금액
    // 투자한사람들의 개별 투자금액, 투자자들의 주소, 거래의 마감일, 마감일이 종료되었는지 아닌지(마감이되면 true)
    struct campaignStruct{
        string uniqueCode;
        address creator;
        uint256 fundingGoal;
        uint256 fundraiseAmount;
        mapping (address => uint256) investRate;
        address[] investgators;
        uint deadline;
        bool closed;
    }
}