pragma solidity ^0.4.23;
import "./node_modules/zeppelin-solidity/contracts/math/SafeMath.sol";
import "./Control.sol";


contract Lobby is Control {

    struct table{
        address[] players;
        uint minToPlay;
    }
    //uses the control contract to make the person who creates this contract is the founder
    constructor() public {
        founderAddress = msg.sender;
        //maybe generate some empty tables to display to the client
    }
    
    event Deposit();
    event Withdraw();

    SafeMath math;
    //should this be public or private?
    mapping(address => table) tables;
    //we can't use floating point numbers
    //how can we store their ether if it comes as 0.5 eth?
    //should this be public or private?
    mapping(address => uint) userEther; 

    function deposit(address user) public {

        assignToTable(user);
    }

    function withdraw(address user) public {

    }

    function assignToTable(address user) public {

    }

    function removeFromTable(address user) public {

    }
    //could make this get called after a certain amount of time
    function shuffleTables() public {

    }
}