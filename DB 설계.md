## DB 설계

### DB이름 

#### tojob



### 생성할 Table들



#### 1. member(회원관리) 액세스 토큰을 들고 있기위한 용도

##### user_id(PK)

##### access_token

##### name

##### profile_img

##### provider_name

##### email



#### 2. wallets(회원 지갑 관리) 

##### user_id(PK,FK)

#####  address

##### balance(Token갯수)



#### 3. transactions (블록의 해시코드를 관리할값)

##### transactions_id

##### hash 

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

##### num(PK)

##### oauth_id(member)(FK)

##### subject

##### content

##### latestBlock



#### 5. SellingContents (판매에관한 글을 관리)

##### num

##### fundContentNum(FK)

##### subject

##### content

##### latestBlock



#### 6. Images (모든 사진 파일의 경로를 관리)

##### num(PK)  

##### FundContentsnum

##### Imgpath 



















































































































































































































