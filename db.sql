# hanbyuk 데이터베스가 이미 존재하다면 삭제
DROP DATABASE IF EXISTS hanbyuk;

# hanbyuk 데이터베이스 생성
CREATE DATABASE hanbyuk;

# hanbyuk 데이터베이스 사용
USE hanbyuk;

# 게시물 테이블 생성
CREATE TABLE article (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(200) NOT NULL,
    `body` LONG NOT NULL,
    boardId INT(10) NOT NULL
);

# 게시판 테이블 생성
CREATE TABLE board (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `name` CHAR(100) NOT NULL,
    `code` CHAR(100) NOT NULL
);

# 행사안내 게시판 생성
INSERT INTO board 
SET regDate = NOW(),
updateDate = NOW(),
`name` = '행사안내',
`code` = 'event';

# 공연안내 게시판 생성
INSERT INTO board 
SET regDate = NOW(),
updateDate = NOW(),
`name` = '공연안내',
`code` = 'performance';

# 시설안내 게시판 생성
INSERT INTO board 
SET regDate = NOW(),
updateDate = NOW(),
`name` = '시설안내',
`code` = 'facility';


CREATE TABLE `file` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME,
    updateDate DATETIME,
    delDate DATETIME,
	delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0,
	relTypeCode CHAR(50) NOT NULL,
	relId INT(10) UNSIGNED NOT NULL,
    originFileName VARCHAR(100) NOT NULL,
    fileExt CHAR(10) NOT NULL,
    typeCode CHAR(20) NOT NULL,
    type2Code CHAR(20) NOT NULL,
    fileSize INT(10) UNSIGNED NOT NULL,
    fileExtTypeCode CHAR(10) NOT NULL,
    fileExtType2Code CHAR(10) NOT NULL,
    fileNo TINYINT(2) UNSIGNED NOT NULL,
    `body` LONGBLOB
);

DESC `file`;

# 게시물 작성시, 내용이 비어있어도 게시물 생성 허용
ALTER TABLE article MODIFY COLUMN `body` LONG NULL;

SELECT *
FROM article;
SELECT *
FROM `file`;

TRUNCATE `file`;
TRUNCATE article;


