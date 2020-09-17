## DB 설계

### DB이름 

#### tojob



### 생성할 Table들



#### 1. member(회원관리) 액세스 토큰을 들고 있기위한 용도

##### user_id(PK) (oauth값)

##### access_token (accesstoken)

##### name (user 이름)

##### profile_img (user의 profile Img)

##### provider_name (토큰값을 준 도메인 ex. 네이버인지 카카오인지 구글인지)

##### email (email)



#### 2. wallets(회원 지갑 관리) 

##### user_id(PK,FK) (member에있는 user_id(oauth 값))

#####  address (유저 지갑의 고유 해시값)

##### balance (Token갯수)



#### 3. transactions (블록의 해시코드를 관리할값)

##### transactions_id

##### hash (트랜젝션이 일어날때 얻는 해시값)

##### nonce 

##### block_hash 

##### block_number transaction_index 

##### from_hash 

##### to_hash 

##### value 

##### gas_price 

##### gas 

##### input  

##### creates

##### publick_key

##### raw 

##### r

##### s

##### v 

##### stored_at



#### 4. FundContents (투자에관한 글을 관리)

##### num(PK) (SellingContents의 FK와 조인하기위해 사용할 number (auto_increment로 지정))

##### oauth_id(FK) (member에있는 user_id(oauth 값))

##### subject (제목)

##### content (내용)

##### latestBlock (해당 게시글의 마지막블록index)



#### 5. SellingContents (판매에관한 글을 관리)

##### num(PK) (PK를 지정해주기위한 number (auto_increment로 지정))

##### fundContentNum(FK) (FundContent에있는 num) => 판매 게시글이 투자게시글로부터 나오기때문에

##### subject (제목)

##### content (내용)

##### latestBlock (해당 게시글의 마지막블록index)



#### 6. Images (모든 사진 파일의 경로를 관리)

##### num(PK) (PK를 지정해주기위한 number (auto_increment로 지정))

##### FundContentsnum(FK) (FundContent에있는 num) => 판매 게시글이 투자게시글로부터 나오기때문에

##### Imgpath (사진의 경로)



















































































































































































































