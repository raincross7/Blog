CREATE TABLE account (
    user_id           VARCHAR(20) PRIMARY KEY,
    password          VARCHAR(20) NOT NULL,
    name              VARCHAR(20) NOT NULL,
    birthday          VARCHAR(15) NOT NULL,
    phone_number      VARCHAR(15) NOT NULL,
    email			  VARCHAR(30) NOT NULL,
    registration_date VARCHAR(15) NOT NULL
);
INSERT INTO account (user_id, password, name, birthday, phone_number, email, registration_date)
VALUES
('alice94', 'qwerty', '홍길동', '1990-05-15', '010-1234-5678', 'random1@example.com', '2023-08-11'),
('bob85', 'asdfgh', '김철수', '1985-08-22', '010-2345-6789', 'test_email2@gmail.com','2023-01-12'),
('carol88', 'password', '이영희', '1995-02-10', '010-3456-7890', 'user123@yahoo.com', '2023-08-23'),
('dave79', 'parkmin', '박민준', '1980-11-30', '010-4567-8901', 'example_email@hotmail.com', '2023-03-04'),
('eve91', 'bowwow', '최지수', '1990-05-15', '010-5678-9012', 'demoemail@outlook.com', '2023-07-05'),
('qwe', 'asd', '테스트', '1992-07-18', '010-0000-1111', 'random_email_456@domain.com', '2000-12-31');

CREATE TABLE board (
    post_number   INT           AUTO_INCREMENT PRIMARY KEY,
    user_id       VARCHAR(20)   NOT NULL,
    title         VARCHAR(100)  NOT NULL,
    content       TEXT          NOT NULL,
    view_count    INT           DEFAULT 0 NOT NULL,
    creation_date VARCHAR(15)   NOT NULL,
    
    CONSTRAINT user_id_pk_board FOREIGN KEY(user_id) REFERENCES account(user_id) ON DELETE CASCADE
);
INSERT INTO board (user_id, title, content, creation_date)
VALUES
('bob85', '코딩의 세계', '코딩은 무한한 창조의 세계입니다. 함께 공유하고 배워보세요.','2023-11-03'),
('carol88', '책 읽기 모임', '매주 선정된 책을 읽고 함께 토론하는 독서 모임. 참여하실 분들을 환영합니다!', '2023-11-05'),
('eve91', '맛집 탐험', '지역 맛집부터 세계 각지의 다양한 음식을 즐기며 맛집 탐험의 여행.', '2023-11-05');

CREATE TABLE attachment (
	uid         VARCHAR(100) PRIMARY KEY,
	file_name   VARCHAR(100) NOT NULL,
	user_id     VARCHAR(20)  NOT NULL,
	post_number INT          NOT NULL,
	upload_date VARCHAR(15)  NOT NULL,
	
	CONSTRAINT user_id_pk_attachment FOREIGN KEY(user_id) REFERENCES account(user_id) ON DELETE CASCADE,
	CONSTRAINT post_number_pk_attachment FOREIGN KEY(post_number) REFERENCES board(post_number) ON DELETE CASCADE
);
INSERT INTO attachment (uid, file_name, user_id, post_number, upload_date)
VALUES
('d540a8ae-d031-4e53-991c-79e7d834c203', 'first.png', 'bob85', 1, '2023-11-03'),
('1a79a4d6-0de6-318e-8e5b-326e338ae533', 'second.png', 'eve91', 3, '2023-11-05');

CREATE TABLE comment (
	comm_number   INT AUTO_INCREMENT PRIMARY KEY,
	user_id       VARCHAR(20) NOT NULL,
	content       TEXT NOT NULL,
	post_number   INT NOT NULL,
	creation_date VARCHAR(15) NOT NULL,
	
	CONSTRAINT user_id_pk_comment FOREIGN KEY(user_id) REFERENCES account(user_id) ON DELETE CASCADE,
	CONSTRAINT post_number_pk_comment FOREIGN KEY(post_number) REFERENCES board(post_number) ON DELETE CASCADE
);
INSERT INTO comment (user_id, content, post_number, creation_date)
VALUES
('eve91', '코딩좋아', 1, '2023-11-05'),
('carol88', '코딩싫어', 1, '2023-11-06'),
('carol88', '책책책 책을 읽읍시다', 2, '2023-11-06'),
('qwe', '댓글테스트', 3, '2023-11-29');