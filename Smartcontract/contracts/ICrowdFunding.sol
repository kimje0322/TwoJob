pragma solidity ^0.7.0;

interface ICrowdFunding{
    
    function createCampaign (string memory _uniqueCode, uint256 _fundingGoal, uint _deadline, address _creator)external;

    /**특정 게시글에 투자하기
     *@param _uniqueCode 투자하고싶은 게시글에대한 해시값
     */
    function FundingCampign(string memory _uniqueCode, address _from, uint256 _sendingMoney) payable external;

    /**
     * 마감일이 되면 목표금액에 도달 했을경우 펀딩 금액을 작성자에게 줌 
     * @param _uniqueCode 게시글에대한 해시값
     */
    function DueDay(string memory _uniqueCode) external;
    
    
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
}