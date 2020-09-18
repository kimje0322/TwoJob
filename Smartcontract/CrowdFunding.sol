pragma solidity ^0.7.0;

import "./Campaign.sol";

contract CrowdFunding is Campaign{
    mapping(string => campaignStruct) campaigns;

    /** modifier 는 function의 실행(행동)을 쉽게 변경가능하도록하며, function 실행의 조건을 체크 할수 있다
     * @param _uniqueCode 해당캠페인의 해시값
     */
    modifier campaignNotClosed(string memory _uniqueCode) {require(!campaigns[_uniqueCode].closed); _;}
    
    /** 게시글 작성자만 허용
     * @param _uniqueCode 해당캠페인의 해시값  
     */
    modifier campaignOwner(string memory _uniqueCode) {require(msg.sender == campaigns[_uniqueCode].creator); _;}

    /** 마감일이 지났는지 확인하는 modifier
     * @param _uniqueCode 해당캠페인의 해시값
     * now는 더이상 지원하지 않기때문에 block.timestamp를 사용함 두개가 같은 의미
     */
    modifier becomeDuedate(string memory _uniqueCode) {require(block.timestamp >= campaigns[_uniqueCode].deadline);_;}


    /**  createCampaign에대한 event
     *@param _uniqueCode 게시글에대한 해시값
     *@param _creator 게시자의 주소
     *@param _fundingGoal 목표 금액
     *@param _fundraiseAmount 현재 금액
     *@param _deadline 마감일
     */
    event noticeCreateCampaign(
        string  _uniqueCode,
        address _creator,
        uint256 _fundingGoal,
        uint256 _fundraiseAmount,
        uint _deadline
    );

    /**투자 게시글 생성 
       게시글에대한 해시값, 목표금액, 마감일이 파라미터로 넘겨짐
       투자를 받는 사람의 주소, 목표금액, 모인금액, 투자자들의 개별 투자금액을위한 mapping
       거래의 마감일, 마감일이 종료되었는지 아닌지
     */
    
    
    function createCampaign (string memory _uniqueCode, uint256 _fundingGoal, uint _deadline) public {
        campaigns[_uniqueCode].uniqueCode = _uniqueCode;
        campaigns[_uniqueCode].creator = msg.sender;
        campaigns[_uniqueCode].fundingGoal = _fundingGoal;
        campaigns[_uniqueCode].fundraiseAmount = 0;
        //mapping을 초기화시켜주기위해 게시자의 주소를 가지고 초기화를해줌 사용하지는 않을예정
        //campaigns[_uniqueCode].investRate[msg.sender] = 0;
        campaigns[_uniqueCode].deadline = _deadline;
        campaigns[_uniqueCode].closed = false;

        campaignStruct storage tempCamp = campaigns[_uniqueCode];
        emit noticeCreateCampaign(tempCamp.uniqueCode, tempCamp.creator, tempCamp.fundingGoal, tempCamp.fundraiseAmount, tempCamp.deadline);
    }

    /**특정 게시글에 투자하기
     *@param _uniqueCode 투자하고싶은 게시글에대한 해시값
     */
    function FundingCampign(string memory _uniqueCode) payable public {
        campaignStruct storage tempCamp = campaigns[_uniqueCode];
        
        //게시글 작성자는 투자를 막는다
        require(msg.sender != tempCamp.creator);

        tempCamp.investgators.push(msg.sender);
        tempCamp.fundraiseAmount += msg.value;
        tempCamp.investRate[msg.sender] += msg.value;
    }

    /**
     * 마감일이 되면 목표금액에 도달 했을경우 펀딩 금액을 작성자에게 줌 
     * @param _uniqueCode 게시글에대한 해시값
     */
    function DueDay(string memory _uniqueCode) campaignNotClosed(_uniqueCode) campaignOwner(_uniqueCode) becomeDuedate(_uniqueCode) public {
        campaignStruct storage tempCamp = campaigns[_uniqueCode];

        if(tempCamp.fundingGoal > tempCamp.fundraiseAmount){
            // 투자자들에게 투자한 금액 만큼 돈이 송금됨
            uint256 len = tempCamp.investgators.length;
            uint count = 0;
            for(uint i=0; i<len; i++){
                address invastgator = tempCamp.investgators[count++];
                //0.5.0부터는 address에서 trasfer를 할 수 없고 두번의 address를 uint160으로 형변환 한뒤에 다시 address로 형변환 해주어야 송금이 가능해 짐
                address(uint160(invastgator)).transfer(tempCamp.investRate[invastgator]);
            }

        }else{
            // creator에게 투자금액이 송금됨
            msg.sender.transfer(tempCamp.fundraiseAmount);
        }

        tempCamp.closed = true;
    }

    
}