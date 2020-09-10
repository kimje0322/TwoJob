pragma solidity ^0.7.0;

import "./Campaign.sol";

contract CrowdFunding is Campaign{
    mapping(string => campaignStruct) campaigns;

    /** modifier ë functionì ì¤í(íë)ì ì½ê² ë³ê²½ê°ë¥íëë¡íë©°, function ì¤íì ì¡°ê±´ì ì²´í¬ í ì ìë¤
     * @param _uniqueCode í´ë¹ìº íì¸ì í´ìê°
     */
    modifier campaignNotClosed(string memory _uniqueCode) {require(!campaigns[_uniqueCode].closed); _;}
    
    /** ê²ìê¸ ìì±ìë§ íì©
     * @param _uniqueCode í´ë¹ìº íì¸ì í´ìê°  
     */
    modifier campaignOwner(string memory _uniqueCode) {require(msg.sender == campaigns[_uniqueCode].creator); _;}

    /** ë§ê°ì¼ì´ ì§ë¬ëì§ íì¸íë modifier
     * @param _uniqueCode í´ë¹ìº íì¸ì í´ìê°
     * nowë ëì´ì ì§ìíì§ ìê¸°ëë¬¸ì block.timestampë¥¼ ì¬ì©í¨ ëê°ê° ê°ì ìë¯¸
     */
    modifier becomeDuedate(string memory _uniqueCode) {require(block.timestamp >= campaigns[_uniqueCode].deadline);_;}


    /**  createCampaignìëí event
     *@param _uniqueCode ê²ìê¸ìëí í´ìê°
     *@param _creator ê²ììì ì£¼ì
     *@param _fundingGoal ëª©í ê¸ì¡
     *@param _fundraiseAmount íì¬ ê¸ì¡
     *@param _deadline ë§ê°ì¼
     */
    event noticeCreateCampaign(
        string  _uniqueCode,
        address _creator,
        uint256 _fundingGoal,
        uint256 _fundraiseAmount,
        uint _deadline
    );

    /**í¬ì ê²ìê¸ ìì± 
       ê²ìê¸ìëí í´ìê°, ëª©íê¸ì¡, ë§ê°ì¼ì´ íë¼ë¯¸í°ë¡ ëê²¨ì§
       í¬ìë¥¼ ë°ë ì¬ëì ì£¼ì, ëª©íê¸ì¡, ëª¨ì¸ê¸ì¡, í¬ììë¤ì ê°ë³ í¬ìê¸ì¡ììí mapping
       ê±°ëì ë§ê°ì¼, ë§ê°ì¼ì´ ì¢ë£ëìëì§ ìëì§
     */
    
    
    function createCampaign (string memory _uniqueCode, uint256 _fundingGoal, uint _deadline) public {
        campaigns[_uniqueCode].uniqueCode = _uniqueCode;
        campaigns[_uniqueCode].creator = msg.sender;
        campaigns[_uniqueCode].fundingGoal = _fundingGoal;
        campaigns[_uniqueCode].fundraiseAmount = 0;
        //mappingì ì´ê¸°íìì¼ì£¼ê¸°ìí´ ê²ììì ì£¼ìë¥¼ ê°ì§ê³  ì´ê¸°íë¥¼í´ì¤ ì¬ì©íì§ë ìììì 
        //campaigns[_uniqueCode].investRate[msg.sender] = 0;
        campaigns[_uniqueCode].deadline = _deadline;
        campaigns[_uniqueCode].closed = false;

        campaignStruct storage tempCamp = campaigns[_uniqueCode];
        emit noticeCreateCampaign(tempCamp.uniqueCode, tempCamp.creator, tempCamp.fundingGoal, tempCamp.fundraiseAmount, tempCamp.deadline);
    }

    /**í¹ì  ê²ìê¸ì í¬ìíê¸°
     *@param _uniqueCode í¬ìíê³ ì¶ì ê²ìê¸ìëí í´ìê°
     */
    function FundingCampign(string memory _uniqueCode) payable public {
        campaignStruct storage tempCamp = campaigns[_uniqueCode];
        
        //ê²ìê¸ ìì±ìë í¬ìë¥¼ ë§ëë¤
        require(msg.sender != tempCamp.creator);

        tempCamp.investgators.push(msg.sender);
        tempCamp.fundraiseAmount += msg.value;
        tempCamp.investRate[msg.sender] += msg.value;
    }

    /**
     * ë§ê°ì¼ì´ ëë©´ ëª©íê¸ì¡ì ëë¬ íìê²½ì° íë© ê¸ì¡ì ìì±ììê² ì¤ 
     * @param _uniqueCode ê²ìê¸ìëí í´ìê°
     */
    function DueDay(string memory _uniqueCode) campaignNotClosed(_uniqueCode) campaignOwner(_uniqueCode) becomeDuedate(_uniqueCode) public {
        campaignStruct storage tempCamp = campaigns[_uniqueCode];

        if(tempCamp.fundingGoal > tempCamp.fundraiseAmount){
            // í¬ììë¤ìê² í¬ìí ê¸ì¡ ë§í¼ ëì´ ì¡ê¸ë¨
            uint256 len = tempCamp.investgators.length;
            uint count = 0;
            for(uint i=0; i<len; i++){
                address invastgator = tempCamp.investgators[count++];
                //0.5.0ë¶í°ë addressìì trasferë¥¼ í  ì ìê³  ëë²ì addressë¥¼ uint160ì¼ë¡ íë³í íë¤ì ë¤ì addressë¡ íë³í í´ì£¼ì´ì¼ ì¡ê¸ì´ ê°ë¥í´ ì§
                address(uint160(invastgator)).transfer(tempCamp.investRate[invastgator]);
            }

        }else{
            // creatorìê² í¬ìê¸ì¡ì´ ì¡ê¸ë¨
            msg.sender.transfer(tempCamp.fundraiseAmount);
        }

        tempCamp.closed = true;
    }

    
}