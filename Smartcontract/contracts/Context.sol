pragma solidity ^0.5.0;

 /*
 *  현재 실행중인 context에대한 정보를 제공해준다.
 *  transaction의 sender와 data를 포함함.
 *  GSN 메타 transaction을 처리 할 때 계정 전송 및 실행 비용은 실제 발신자가 아닐 수 있습니다.
 *  따라서, msg.sender나 msg.data로 접근할 수 있지만, 그렇게 직접적으로 접근하면 안됨.
 */
contract Context {
    constructor () internal { }

    function _msgSender() internal view returns (address payable) {
        return msg.sender;
    }

    function _msgData() internal view returns (bytes memory) {
        this;
        return msg.data;
    }
}
