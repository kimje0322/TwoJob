## DB 설계

### DB이름 

#### tojob

### 생성할 Table들

#### 1. member(회원관리) 액세스 토큰을 들고 있기위한 용도

##### oauth_id (PK)

##### access_token

##### name

##### profile_img

##### provider_name

##### email

#### 2. wallets(회원 지갑 관리) 

##### oauth_id (PK,FK)

#####  adderess

##### balance(Token갯수)

#### 3. transactions (블록의 해시코드를 관리할값)

##### id

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

#### 4. 



#### 5. 

