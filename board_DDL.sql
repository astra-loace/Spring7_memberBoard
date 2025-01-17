-- 2024년 12월 26일~ 회원전용 게시판
-- 1) 회원테이블
USE scit;
DROP TABLE IF EXISTS scit.board_user;

CREATE TABLE scit.board_user 
(
	user_id    varchar(50)
	, user_pwd varchar(100) NOT NULL
	, user_name     varchar(50)  NOT NULL  -- 실명
	, email    varchar(50)
	, roles    varchar(50)  DEFAULT 'ROLE_USER'
	, enabled  char(1)      DEFAULT '1'
		, CONSTRAINT boarduser_userid  PRIMARY KEY(user_id)
		, CONSTRAINT boarduser_roles   CHECK (roles   IN ('ROLE_USER', 'ROLE_ADMIN'))
		, CONSTRAINT boarduser_enabled CHECK (enabled IN ('1', '0'))
)

-- 2) 게시판 테이블
--    첨부파일 관련 컬럼 포함 (나중 추가)

DROP TABLE IF EXISTS scit.reply;
DROP TABLE IF EXISTS scit.board;

CREATE TABLE scit.board
(
	board_seq       int AUTO_INCREMENT 
	, board_writer  varchar(50)  NOT NULL
	, board_title   varchar(200) DEFAULT 'Untitled'
	, board_content varchar(4000)
	, hit_count     int DEFAULT 0           -- 조회수
	, create_date   datetime DEFAULT current_timestamp
	, update_date   datetime DEFAULT current_timestamp
		, CONSTRAINT board_boardseq PRIMARY KEY (board_seq)
)

-- 3) 댓글 테이블  (게시글과 1:다 관계 형성)
create table scit.reply
(
   reply_seq int auto_increment,
   board_seq int -- FK
   , reply_writer varchar(50) not null
   , reply_content varchar(1000) not null
   , create_date datetime default current_timestamp
      , constraint reply_replyseq primary key (reply_seq)
      , constraint reply_boardseq foreign key (board_seq) references scit.board(board_seq)
      on delete cascade
)

commit;
select * from board;
select * from board_user;
select * from reply;