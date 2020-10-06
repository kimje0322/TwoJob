#지갑 내역을 저장하는 테이블
create table wallets(
user_id varchar(255) primary key,
address varchar(255),
balance int,
foreign key (user_id) references members(user_id) on delete cascade on update cascade
);

#유저 정보를 저장하는 테이블
create table members(
user_id varchar(255) primary key,
access_token varchar(255),
name varchar(255),
profile_img varchar(255),
provider_name varchar(255),
email varchar(255)
);

#트랜젝션이 일어나면 그에대한 값을 저장하는 테이블
create table transactions(
oauth_id int primary key,
hash varchar(255),
nonce varchar(255),
block_hash varchar(255),
block_number varchar(255),
transaction_index varchar(255),
from_hash varchar(255),
to_hash varchar(255),
value varchar(255),
gas_price varchar(255),
gas varchar(255),
input  varchar(255),
creates varchar(255),
publick_key varchar(255),
raw varchar(255),
r varchar(255),
s varchar(255),
v int,
storedat datetime default now()
);

 create table blocktransact(
   id int primary key not null auto_increment,
   blockhash varchar(255) not null,
    toaddress varchar(255) not null,
    compname varchar(255) not null,
    fromaddress varchar(255) not null,
    image varchar(255),
    val double,
    type varchar(20) not null,
    typeflag int not null,
    time timestamp,
    constraint foreign key (fromaddress) references wallets(address),
    constraint foreign key (toaddress) references investmentboard(address)
    
 );

#투자 게시글을 저장할 테이블
create table investmentboard(
address varchar(255) primary key,
identity varchar(255),
compName varchar(255),
introduce varchar(255),
url varchar(255),
pjtName varchar(255),
oneLineIntro varchar(255), 
deadLine varchar(255),
goalPrice int,
expectedSalePrice int,
picture varchar(255),
userid varchar(255) not null,
isfinish boolean,
createat datetime default now()
);

#투자게시글에서 사진을 저장하기위한 테이블
create table representpictureinvestment(
num int auto_increment primary key,
investaddress varchar(255),
imgpath varchar(255),
foreign key (investaddress) references investmentboard(address) on delete cascade on update cascade
);

#투자게시글중 editor보관 테이블
create table editorinvestment(
investaddress varchar(255) primary key,
editorhtml varchar(10000),
foreign key (investaddress) references investmentboard(address) on delete cascade on update cascade
);

#모든 테그 저장
create table tags(
num int auto_increment primary key,
tagname varchar(255) not null unique key
);

#모든 카테고리 저장
create table category(
num int auto_increment primary key,
categoryname varchar(255) not null unique key
);

#어느 board에 어떤 테그가 있는지 저장
create table boardtag(
num int auto_increment primary key,
tagname varchar(255) not null,
investaddress varchar(255),
foreign key (tagname) references tags(tagname) on delete cascade on update cascade,
foreign key (investaddress) references investmentboard(address) on delete cascade on update cascade
);

#어느 board에 어떤 카테고리가 있는지 저장
create table boardcategory(
num int auto_increment primary key,
categoryname varchar(255) not null,
investaddress varchar(255),
foreign key(categoryname) references category(categoryname) on delete cascade on update cascade,
foreign key(investaddress) references investmentboard(address) on delete cascade on update cascade
);

#판매 board에 데이터저장
create table saleboard(
address varchar(255) primary key,
investaddress varchar(255),
userid varchar(255) not null,
url varchar(255),
pjtname varchar(255),
startdate varchar(255),
saleprice int,
picture varchar(255),
isfinish boolean,
createat datetime default now(),
foreign key(investaddress) references investmentboard(address) on delete cascade on update cascade
);

#판매 board의 editor저장
create table editorsale(
address varchar(255) primary key,
editorhtml varchar(10000),
foreign key (address) references saleboard(address) on delete cascade on update cascade
);

#message내용 저장하는 테이블
create table chatmessage(
   mid int primary key not null auto_increment,
   room_id int not null,
    sender varchar(255),
    message text,
    time timestamp,
    type varchar(255)
 );
 
 #방의 정보를 저장하는 테이블
 create Table chatroom(
   roomId int not null primary key auto_increment,
    user1 varchar(255),
    user2 varchar(255)
 );
 
 #review 저장할 테이블
 create table review(
 num int auto_increment primary key,
 saleaddress varchar(255) not null,
 satisfied int,
 similar int,
 picture varchar(255),
 reviewexplain varchar(500),
 userid varchar(255) not null,
 createdate timestamp default current_timestamp,
 foreign key (saleaddress) references saleboard (address) on delete cascade on update cascade
 );
 
 #좋아요 저장할 테이블
 create table likeboard(
 num int auto_increment primary key,
 address varchar(255),
 userid varchar(255),
 ischecked boolean default true,
 createat timestamp default current_timestamp
 );
 
 #댓글 저장할 테이블
 create table commentboard(
 num int auto_increment primary key,
 address varchar(255),
 userid varchar(255),
 content varchar(500),
 createat timestamp default current_timestamp
 );