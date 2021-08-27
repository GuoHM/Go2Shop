SET SQL_SAFE_UPDATES = 0;
DELETE FROM authdb.tb_user_authority;
DELETE FROM authdb.tb_authority;
DELETE FROM authdb.tb_security_user;

/*
-- Query: SELECT * FROM authdb.tb_authority
LIMIT 0, 1000

-- Date: 2021-08-21 22:53
*/
INSERT INTO authdb.tb_authority (`ID`,`AUTHORITY_NAME`) VALUES (1,'BUYER');
INSERT INTO authdb.tb_authority (`ID`,`AUTHORITY_NAME`) VALUES (2,'SELLER');

/*
-- Query: SELECT * FROM authdb.tb_security_user
LIMIT 0, 1000

-- Date: 2021-08-21 22:54
*/
INSERT INTO authdb.tb_security_user (`ID`,`USERNAME`,`PASSWORD`,`USER_ID`,`ENABLED`) VALUES (1,'haoming','$2a$12$65aXCVGoNocErfoa0oGbNuVT6eZIc5L2MANKb3Q2200lHrumUiBH.','1','A');
INSERT INTO authdb.tb_security_user (`ID`,`USERNAME`,`PASSWORD`,`USER_ID`,`ENABLED`) VALUES (2,'go2shop','$2a$12$wc1L3Qkbu7hIK39qQS0nFOzTMz3d3LBX2jVZOu3ciVJ06DUOyC0Xa','2','A');


/*
-- Query: SELECT * FROM authdb.tb_user_authority
LIMIT 0, 1000

-- Date: 2021-08-21 22:54
*/
INSERT INTO authdb.tb_user_authority (`ID`,`USER_ID`,`AUTHORITY_ID`) VALUES (1,1,1);
INSERT INTO authdb.tb_user_authority (`ID`,`USER_ID`,`AUTHORITY_ID`) VALUES (2,2,2);

SET SQL_SAFE_UPDATES = 1;

