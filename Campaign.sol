pragma solidity ^0.7.0;

contract Campaign {

    // ê²ìê¸ìëí í´ìê°, í¬ìë¥¼ ë°ë ì¬ëì ì£¼ì, ëª©íê¸ì¡, ëª¨ì¸ê¸ì¡
    // í¬ìíì¬ëë¤ì ê°ë³ í¬ìê¸ì¡, í¬ììë¤ì ì£¼ì, ê±°ëì ë§ê°ì¼, ë§ê°ì¼ì´ ì¢ë£ëìëì§ ìëì§(ë§ê°ì´ëë©´ true)
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