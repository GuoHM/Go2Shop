-- Create service account and reader users

DROP USER IF EXISTS 'go2shop-service-account';
CREATE USER 'go2shop-service-account' IDENTIFIED WITH mysql_native_password BY 'go2shop';
GRANT SELECT, INSERT, UPDATE, DELETE ON *.* TO 'go2shop-service-account';

DROP USER IF EXISTS 'go2shop-reader';
CREATE USER 'go2shop-reader' IDENTIFIED WITH mysql_native_password BY 'reader';
GRANT SELECT ON *.* TO 'go2shop-reader';
