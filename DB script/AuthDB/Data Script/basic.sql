SET SQL_SAFE_UPDATES = 0;
DELETE FROM authdb.user_authority;
DELETE FROM authdb.authority;
DELETE FROM authdb.security_user;

/*
-- Query: SELECT * FROM authdb.authority
LIMIT 0, 1000

-- Date: 2021-08-21 22:53
*/
INSERT INTO authdb.authority (`ID`,`AUTHORITY_NAME`) VALUES (1,'BUYER');
INSERT INTO authdb.authority (`ID`,`AUTHORITY_NAME`) VALUES (2,'SELLER');

/*
-- Query: SELECT * FROM authdb.security_user
LIMIT 0, 1000

-- Date: 2021-08-21 22:54
*/
INSERT INTO authdb.security_user (`ID`,`USERNAME`,`PASSWORD`,`ENABLED`) VALUES (1,'haoming','$2a$12$65aXCVGoNocErfoa0oGbNuVT6eZIc5L2MANKb3Q2200lHrumUiBH.','A');
INSERT INTO authdb.security_user (`ID`,`USERNAME`,`PASSWORD`,`ENABLED`) VALUES (2,'go2shop','$2a$12$wc1L3Qkbu7hIK39qQS0nFOzTMz3d3LBX2jVZOu3ciVJ06DUOyC0Xa','A');


/*
-- Query: SELECT * FROM authdb.user_authority
LIMIT 0, 1000

-- Date: 2021-08-21 22:54
*/
INSERT INTO authdb.user_authority (`ID`,`USER_ID`,`AUTHORITY_ID`) VALUES (1,1,1);
INSERT INTO authdb.user_authority (`ID`,`USER_ID`,`AUTHORITY_ID`) VALUES (2,2,2);

SET SQL_SAFE_UPDATES = 1;

