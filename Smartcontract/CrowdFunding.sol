pragma solidity ^0.7.0;

import "./Campaign.sol";
import "./ICrowdFunding.sol";
import "./ISaleItem.sol";

contract CrowdFunding is Campaign, ICrowdFunding, ISaleItem{
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

    /**투자 게시글 생성 
       게시글에대한 해시값, 목표금액, 마감일이 파라미터로 넘겨짐
       투자를 받는 사람의 주소, 목표금액, 모인금액, 투자자들의 개별 투자금액을위한 mapping
       거래의 마감일, 마감일이 종료되었는지 아닌지
     */
    
    //버전0.6.0부터는 override시 어느 곳으로부터 파생되었는지 명시 해주어야함
    function createCampaign (string memory _uniqueCode, uint256 _fundingGoal, uint _deadline) public override(ICrowdFunding){
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
    function FundingCampign(string memory _uniqueCode) payable public override(ICrowdFunding) {
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
    function DueDay(string memory _uniqueCode) campaignNotClosed(_uniqueCode) campaignOwner(_uniqueCode) becomeDuedate(_uniqueCode) public override(ICrowdFunding) {
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

     /** 물건 판매
    @param _uniqueCode 게시글에 대한 해시값
    */
    function SaleItem(string memory _uniqueCode, uint count) payable public override(ISaleItem){
        campaignStruct storage tempCamp = campaigns[_uniqueCode];
        //판매를 하면 해당 금액 만큼 profit에 돈을 저장해두고 일주일후 DistributeProfit을 호출함
        tempCamp.totalSell += count;
        DistributeProfit(_uniqueCode, msg.value);
    }

    /** 수익 분배
     @param _uniqueCode 게시글에 대한 해시값
    */
    function DistributeProfit(string memory _uniqueCode, uint256 money) payable public override(ISaleItem){
        campaignStruct storage tempCamp = campaigns[_uniqueCode];

        address[] memory investgatorsList = tempCamp.investgators;
        uint256 investgatorsCount = investgatorsList.length;
        // 판매자에게 나누어지게 될 몫
        address(uint160(tempCamp.creator)).transfer((money*30)/100);
        // 투자자들에게 나누어지게 될 몫
        uint256 share = (money*70)/100;

        for(uint256 i=0; i<investgatorsCount; i++){

            address investgator = investgatorsList[i];
            //토큰으로 바꿔서 보내줘야됨
            address(uint160(investgator)).transfer((share)*(tempCamp.investRate[investgator]/tempCamp.fundraiseAmount));
        }
    }



    

    
}