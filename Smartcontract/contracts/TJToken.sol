pragma solidity ^0.5.0;

import "./Context.sol";
import "./ERC20.sol";
import "./ERC20Detailed.sol";


contract TJToken is Context, ERC20, ERC20Detailed {

    constructor () public ERC20Detailed("ToJob", "TJ", 18) {
        _mint(_msgSender(), 10000000 * (10 ** uint256(decimals())));
    }
}
