pragma solidity ^0.5.0;

import "./SafeMath.sol";
import "./TJToken.sol";
import "./Context.sol";
import "./Ownable.sol";
contract CrowdFunding is Context, Ownable{
  using SafeMath for uint256;
  enum State { Ready, Open, Close, Sell, SellClose }
  // 게시글에대한 해시값, 투자를 받는 사람의 주소, 목표금액, 모인금액
  // 투자한사람들의 개별 투자금액, 투자자들의 주소, 거래의 마감일, 마감일이 종료되었는지 아닌지(마감이되면 true)
  // 총 판매갯수
  struct campaignStruct{
      string uniqueCode;
      address creator;
      uint256 fundingGoal;
      uint256 fundraiseAmount;
      mapping (address => uint256) investRate;
      address[] investgators;
      uint deadline;
      State state;
      uint256 totalSell;
      mapping (string => uint) receipt;
      uint usedfund;
      uint receiptNum;
      string[] recieptImg;
  }
    mapping(string => campaignStruct) campaigns;
    //modifier inState(State _state) { require(state == _state); _; }
    event CampaignCreate(address from, string code, uint amount);
    event Funding(address from, string code, uint amount);
    event FundingSuccess(string code);
    event FundingFail(string code);
    event Distribute(address to, uint amount);

    function createCampaign (string memory _uniqueCode, uint256 _fundingGoal, uint numberOfDays) public {
        require(campaigns[_uniqueCode].state != State.Open);
        campaigns[_uniqueCode].uniqueCode = _uniqueCode;
        campaigns[_uniqueCode].creator = _msgSender();
        campaigns[_uniqueCode].fundingGoal = _fundingGoal;
        campaigns[_uniqueCode].fundraiseAmount = 0;
        campaigns[_uniqueCode].investRate[_msgSender()] = 0;
        campaigns[_uniqueCode].deadline = now + (numberOfDays * 1 days);
        campaigns[_uniqueCode].state = State.Open;
        campaigns[_uniqueCode].usedfund = 0;
        campaigns[_uniqueCode].receiptNum = 0;
        emit CampaignCreate(_msgSender(), _uniqueCode, _fundingGoal);
    }

    function FundingCampign(address _tokenAddress, address _from, string memory _uniqueCode, uint _fundingValue) public {
        campaignStruct storage tempCamp = campaigns[_uniqueCode];
        //게시글 작성자는 투자를 막는다
        require(_msgSender() != tempCamp.creator, "you are creator");
        require(tempCamp.state == State.Open, "please make campaign");
        //require(now < tempCamp.deadline);
        TJToken(_tokenAddress).increaseAllowanceToOut(_from, address(this), _fundingValue);
        TJToken(_tokenAddress).transferFrom(_from, address(this) , _fundingValue);
        campaigns[_uniqueCode].investgators.push(_msgSender());
        campaigns[_uniqueCode].fundraiseAmount += (_fundingValue);
        campaigns[_uniqueCode].investRate[_msgSender()] += (_fundingValue);
        emit Funding(_msgSender(), _uniqueCode, _fundingValue);
    }

    function receiveFunds(address _tokenAddress, string memory _uniqueCode) public {
        campaignStruct storage tempCamp = campaigns[_uniqueCode];
        require(tempCamp.state == State.Open);
        require(tempCamp.fundingGoal <= tempCamp.fundraiseAmount);
        //require(now >= tempCamp.deadline);
        require(_msgSender() == tempCamp.creator);
        TJToken(_tokenAddress).transfer(tempCamp.creator, tempCamp.fundraiseAmount);
        tempCamp.state = State.Sell;
        emit FundingSuccess(_uniqueCode);
    }

    function Refund(address _tokenAddress,string memory _uniqueCode) public {
      campaignStruct storage tempCamp = campaigns[_uniqueCode];
      require(tempCamp.state == State.Open);
      require(tempCamp.fundraiseAmount < tempCamp.fundingGoal);
      //require(now >= tempCamp.deadline);
      uint256 len = tempCamp.investgators.length;
      uint count = 0;
      for(uint i=0; i<len; i++){
            address investgator = tempCamp.investgators[count++];
            TJToken(_tokenAddress).transfer(investgator, tempCamp.investRate[investgator]);
      }
      tempCamp.state = State.Close;
      emit FundingFail(_uniqueCode);
    }

    function getBalance(address _tokenAddress) public view returns (uint256) {
      return TJToken(_tokenAddress).balanceOf(address(this));
    }
    
    function getCampaign (string memory _uniqueCode) public view returns (string memory, address, uint256, uint256, uint, State){
        campaignStruct storage tempCamp = campaigns[_uniqueCode];
        return (tempCamp.uniqueCode, tempCamp.creator, tempCamp.fundingGoal,tempCamp.fundraiseAmount, tempCamp.investRate[_msgSender()], tempCamp.state);
    }
    function getAddress() public view returns (address){
      return address(this);
    }
    function getPeopleNum(string memory _uniqueCode) public view returns (uint256){
      return campaigns[_uniqueCode].investgators.length;
    }
    function test(string memory _uniqueCode) public returns(bool){
      campaignStruct storage tempCamp = campaigns[_uniqueCode];
      //TJToken(_tokenAddress).increaseAllowanceToOut(_to, address(this), 10);
      // TJToken(_msgSender()).allowance(_to, address(this));
      //return TJToken(_tokenAddress).transfer(tempCamp.creator, tempCamp.fundingGoal);
      return true;
    }
    function usefund(string memory _uniqueCode, string memory _imgName, uint pay) public {
      require(campaigns[_uniqueCode].state == State.Sell);
      require(_msgSender() == campaigns[_uniqueCode].creator);
      campaigns[_uniqueCode].receipt[_imgName] += pay;
      campaigns[_uniqueCode].receiptNum += 1;
      campaigns[_uniqueCode].usedfund += pay;
    }
    //function getCitizenAddress()public view returns( address  [] memory){
    //return citizenArray;
    //  }
    function getReciept(string memory _uniqueCode) public view returns (String[] memory){
      return campaigns[_uniqueCode].recieptImg;
    }
    function SaleItem(address _tokenAddress, string memory _uniqueCode, uint _count, uint _money) public onlyOwner{
          campaignStruct storage tempCamp = campaigns[_uniqueCode];
          require(tempCamp.state == State.Sell);
          tempCamp.totalSell += _count;
          DistributeProfit(_tokenAddress, _uniqueCode, _count, _money);
      }
    function DistributeProfit(address _tokenAddress, string memory _uniqueCode, uint _count, uint _money) private{
        campaignStruct storage tempCamp = campaigns[_uniqueCode];

        address[] memory investgatorsList = tempCamp.investgators;
        uint256 investgatorsCount = investgatorsList.length;
        uint totalMoney = _money * _count;
        // 판매자에게 나누어지게 될 몫
        TJToken(_tokenAddress).transfer(tempCamp.creator, (totalMoney*50)/100);
        // 투자자들에게 나누어지게 될 몫
        uint256 share = (totalMoney*50)/100;
        for(uint256 i=0; i<investgatorsCount; i++){
            address investgator = investgatorsList[i];
            //토큰으로 바꿔서 보내줘야됨
            uint value = (share)*(tempCamp.investRate[investgator]/tempCamp.fundraiseAmount);
            TJToken(_tokenAddress).transfer(investgator, value);
            emit Distribute(investgator, value);
        }
    }
}
