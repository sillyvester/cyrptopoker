pragma solidity ^0.4.23;

contract Control {

    //lobbyContract: deals with depositing and withdrawing, assigning players to tables,
    // and shuffling the tables
    //postHandContract: deals with the ending of hands. Reassigns the amount of ether that
    // each player holds.
    address public founderAddress;
    address[] public devs;


    modifier onlyFounder() {
        require(msg.sender == founderAddress);
        _;
    }

    modifier onlyDev() {
        require(checkDev(msg.sender));
        _;
    }

    //function to check if the address is a dev
    //internal since it is only called within the contract
    //view since it reads data that is stored on blockchain but doenst change it
    function checkDev(address sentIn) internal view returns (bool) {
        for(uint i = 0; i < devs.length; i++){
            if(devs[i] == sentIn){
                return true;
            }
        }
        return false;
    }
    //function for adding dev
    function addDev(address _newDev) external onlyFounder {
        devs.push(_newDev);
    }

    // idea from cryptoKitties that is used for a lot of dapps 
    bool public paused = false;
    /// @dev Modifier to allow actions only when the contract IS NOT paused
    modifier whenNotPaused() {
        require(!paused);
        _;
    }

    /// @dev Modifier to allow actions only when the contract IS paused
    modifier whenPaused {
        require(paused);
        _;
    }

    /// @dev Called by any "C-level" role to pause the contract. Used only when
    ///  a bug or exploit is detected and we need to limit damage.
    function pause() external onlyDev whenNotPaused {
        paused = true;
    }

    /// @dev Unpauses the smart contract. Can only be called by the CEO, since
    ///  one reason we may pause the contract is when CFO or COO accounts are
    ///  compromised.
    /// @notice This is public rather than external so it can be called by
    ///  derived contracts.
    function unpause() public onlyFounder whenPaused {
        // can't unpause if contract was upgraded
        paused = false;
    }
    
}