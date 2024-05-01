show databases;

create database learnnow;

use learnnow;
show tables;

create table USER_DETAILS (
	USER_ID INT NOT NULL PRIMARY KEY,
    USERNAME VARCHAR(255) UNIQUE NOT NULL,
    FIRST_NAME VARCHAR(255) NOT NULL,
    LAST_NAME VARCHAR(255),
    DT_CREATED TIMESTAMP,
    EMAIL VARCHAR(40) UNIQUE,
    PASSPHRASE VARCHAR(255)
);

INSERT INTO USER_DETAILS (USER_ID, USERNAME, FIRST_NAME, LAST_NAME) VALUES
(1, "harshit.jain", "Harshit", "Jain"), ("2", "john.doe", "John", "Doe");

select * from USER_DETAILS;

alter table USER_DETAILS 
ADD COLUMN DT_CREATED TIMESTAMP;

alter table USER_DETAILS 
ADD COLUMN PASSPHRASE VARCHAR(255);

alter table USER_DETAILS 
DROP COLUMN EMAIL;

alter table USER_DETAILS 
ADD COLUMN EMAIL VARCHAR(40) UNIQUE;

UPDATE USER_DETAILS SET DT_CREATED = now() WHERE USER_ID = 2;

UPDATE USER_DETAILS SET PASSPHRASE = "john.doe" WHERE USER_ID = 2;

delete from USER_DETAILS WHERE USER_ID = 3;

select count(user_id) as total_users from user_details;

create table PREFERRED_LANGUAGE (
	USER_ID INT NOT NULL,
    PREF_LANGUAGE VARCHAR(255) NOT NULL,
    PRIMARY KEY (USER_ID, PREF_LANGUAGE)
);

INSERT INTO PREFERRED_LANGUAGE (USER_ID, PREF_LANGUAGE) VALUES
(1, "English"), (1, "Hindi"), (2, "English");

select * from PREFERRED_LANGUAGE WHERE USER_ID = 3;

select ud.user_id, first_name, last_name, PREF_LANGUAGE 
from user_details ud
INNER JOIN preferred_language pl ON ud.user_id = pl.user_id
where ud.username = "davis.herndon";

INSERT INTO PREFERRED_LANGUAGE (USER_ID, PREF_LANGUAGE) VALUES
(2, "Hindi");

drop table QUIZ_BANK;

create table QUIZ_BANK (
	QUIZ_ID INT NOT NULL,
    LANGUAGES VARCHAR(15) NOT NULL,
    QUESTION_ID INT NOT NULL,
    QUESTION VARCHAR(3000) NOT NULL,
    ANSWER VARCHAR(700) NOT NULL,
    OPTION_1 VARCHAR(700) NOT NULL,
    OPTION_2 VARCHAR(700) NOT NULL,
    OPTION_3 VARCHAR(700) NOT NULL,
    OPTION_4 VARCHAR(700) NOT NULL,
    PRIMARY KEY (QUIZ_ID, LANGUAGES, QUESTION_ID)
);

CREATE INDEX Quiz_Id_Idx ON QUIZ_BANK(QUIZ_ID);

CREATE INDEX Quiz_Question_Id_Idx ON QUIZ_BANK(QUESTION_ID);

insert into QUIZ_BANK (QUIZ_ID, LANGUAGES, QUESTION_ID, QUESTION, ANSWER, OPTION_1, OPTION_2, OPTION_3, OPTION_4) values 
(1, "English", 101, "Which of the following is a noun?", "Apple", "Run", "Apple", "Quickly", "Happy"),
(1, "English", 102, "What is the plural form of Child?", "Children", "Childs", "Children", "Childrens", "Childes"),
(1, "English", 103, "Which word is a verb?", "Walk", "Walk", "Dog", "Beautiful", "Red");

select * from QUIZ_BANK where LANGUAGES = "English";

drop table USER_ANSWERS;

create table USER_ANSWERS (
	USER_ID INT NOT NULL,
    QUIZ_ID INT NOT NULL,
    QUESTION_ID INT NOT NULL,
    ANSWER VARCHAR(700) NOT NULL,
    PRIMARY KEY (USER_ID, QUIZ_ID, QUESTION_ID),
    FOREIGN KEY (USER_ID) references USER_DETAILS(USER_ID),
    FOREIGN KEY (QUIZ_ID) references QUIZ_BANK(QUIZ_ID),
    FOREIGN KEY (QUESTION_ID) references QUIZ_BANK(QUESTION_ID)
);

insert into USER_ANSWERS (USER_ID, QUIZ_ID, QUESTION_ID, ANSWER) values 
(1, 1, 101, "Apple"), (1, 1, 102, "Childs"), (1, 1, 103, "Walk");

select * from USER_ANSWERS;

delete from USER_ANSWERS where user_id = 2;


