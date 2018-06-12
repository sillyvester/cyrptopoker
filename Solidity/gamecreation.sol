pragma solidity ^0.4.19;
pragma experimental ABIEncoderV2;
//import "./safemath.sol";

contract Casino {
    
    //using safemath for uint256;

    uint64 gameCount = 0;  // a counter for the num of games, will act as each game’s gameId

    /// player struct with attributes address and balance
    struct Player {
        address playerId; /// should this be type address ?
        uint256 balance;
    }
    
   // a game struct which will consist of the gameId and the totalBalance for a game 
    struct Game {
        uint64 gameId;
        uint256 totBalance;
    }

    mapping (address => uint64) private playersToGames;
    Game[] public games;

    function createPlayer(uint256 _balance, uint64 _gameId) public {
        /// creates new player and binds to a table
        Player(msg.sender, _balance);
        playersToGames[msg.sender] = _gameId;
        games[_gameId].totBalance += _balance;
    }

//creates a new game with an associated gameId and adds it to the Games array
    function createGame() public {
        games.push(Game(gameCount,0));
        gameCount++;
    }

    //function for changing the game that a player is assigned to
    // the function will call the TBD settle function which will settle the player’s current game 
    // before switching the player over to a new game
    function changePlayerGame(Player p, uint64 gameIdentifier) public {
        //settle();
        playersToGames[p.playerId] = gameIdentifier;
    } 


}
