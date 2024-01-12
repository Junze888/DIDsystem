pragma solidity ^0.6.10;
import "./School_Token.sol";



contract ERC4907 is School_Token{
     struct UserInfo 
    {
        address user;   // address of user role
        uint64 expires; // unix timestamp, user expires
    }
    mapping (uint256  => UserInfo) internal _users;
    event UpdateUser(uint256 indexed tokenId, address indexed user, uint64 expires);
    event UpdateTime();
    constructor(string memory name_, string memory symbol_) public School_Token(name_, symbol_) {
    // 构造函数的逻辑
}

     function _msgSender() internal view virtual returns (address) {
        return msg.sender;
    }


    function setUser(uint256 tokenId, address user, uint64 useTime) public virtual  returns(bool){
        require(msg.sender == ownerOf(tokenId), "ERC721: transfer caller is not owner nor approved");
        UserInfo storage info =  _users[tokenId];
        info.user = user;
        info.expires =uint64(block.timestamp + useTime);
        emit UpdateUser(tokenId, user, useTime);
        return true;
    }
    
    function isExpired(uint256 tokenId) public virtual returns(bool){
        emit UpdateTime();//用于产生交易出块，从而更新时间
        UserInfo storage info =  _users[tokenId];
        if(info.expires > block.timestamp){
            return true;
        }
        return false;
        
    }

    function userOf(uint256 tokenId)public virtual returns(address){
        if(isExpired(tokenId)){
            UserInfo storage info =  _users[tokenId];
            return  info.user; 
        }
        else{
            return address(0);
        }
    }
    
    function userExpireTime(uint256 tokenId)public view virtual returns(uint64){
        UserInfo storage info =  _users[tokenId];
        return  info.expires; 
    }

    function userExpires(uint256 tokenId) public  virtual  returns(uint256){
        if (isExpired(tokenId)) {
            return uint256(_users[tokenId].expires - block.timestamp);
        } else {
            revert("Token is expired!");
        }
    }
    

    function _beforeTokenTransfer(
        address from,
        address to,
        uint256 tokenId
    ) internal virtual  {

        if (from != to && _users[tokenId].user != address(0)) {
            delete _users[tokenId];
            emit UpdateUser(tokenId, address(0), 0);
        }
    }

}