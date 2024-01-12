pragma solidity ^0.6.10;

contract School_Token {

// Token name
    string private _name;

    // Token symbol
    string private _symbol;

    mapping(uint256 => address) private _owners;

    mapping(address  => uint256) private _balances;

    mapping(uint256  => address) private _tokenApprovals;

    mapping(uint256  => string) private  _imageUrl; 

    mapping(uint256  => string) private  _location; 

    mapping(address  => mapping(address => bool)) private _operatorApprovals;


    constructor(string memory name_, string memory symbol_) public {
        _name = name_;
        _symbol = symbol_;
    }
    event Transfer(address indexed from, address indexed to, uint256 indexed tokenId);
    


     function ownerOf(uint256 tokenId) public view virtual returns (address) {
        return _requireOwned(tokenId);
    }

    function _ownerOf(uint256 tokenId) internal view virtual returns (address) {
        return _owners[tokenId];
    }

    function name() public view virtual returns (string memory) {
        return _name;
    }

    function imageurl(uint256 tokenId) public view virtual returns (string memory) {
        return _imageUrl[tokenId];
    }

    function loaction(uint256 tokenId) public view virtual returns (string memory) {
        return _location[tokenId];
    }

     function symbol() public view virtual returns (string memory) {
        return _symbol;
    }

    function _requireOwned(uint256 tokenId) internal view returns (address) {
        address owner = _ownerOf(tokenId);
        if (owner == address(0)) {
           return address(0);
        }
        return owner;
    }

//先不做授权
    // function approve(address to, uint256 tokenId) public virtual {
    //     _approve(to, tokenId, _msgSender());//这里需要导入context合约
    // }

    // function _approve(address to, uint256 tokenId, address auth) internal {
    //     _approve(to, tokenId, auth, true);
    // }

   function mint (address to, uint256 tokenId, string memory url, string memory location) public  {
         
        if (to != address(0)){
            _owners[tokenId] = to;
            _imageUrl[tokenId] = url;
            _location[tokenId] = location;
        }
   }


    function transfer(address from, address to, uint256 tokenId) public virtual {
        
        // Setting an "auth" arguments enables the `_isAuthorized` check which verifies that the token exists
        // (from != 0). Therefore, it is not needed to verify that the return value is not 0 here.
        address previousOwner = _ownerOf(tokenId);
        if (previousOwner == from) {
            _owners[tokenId] = to;

        emit Transfer(from, to, tokenId);
        }
    }
}