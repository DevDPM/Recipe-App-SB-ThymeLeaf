## Use to run mysql db docker image, optional if you're not using a local mysqldb
# docker run --name mysqldb -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql

# connect to mysql and run as root user
#Create Databases
CREATE DATABASE sfg_dev;
CREATE DATABASE sfg_prod;

#Create database service accounts
CREATE USER 'sfg_user_dev'@'localhost' IDENTIFIED WITH caching_sha2_password BY 'password';
CREATE USER 'sfg_user_prod'@'localhost' IDENTIFIED WITH caching_sha2_password BY 'password';
CREATE USER 'sfg_user_dev'@'%' IDENTIFIED WITH caching_sha2_password BY 'password';
CREATE USER 'sfg_user_prod'@'%' IDENTIFIED WITH caching_sha2_password BY 'password';

CREATE USER 'sfg_user_dev'@'localhost' IDENTIFIED BY 'password';
CREATE USER 'sfg_user_prod'@'localhost' IDENTIFIED BY 'password';
CREATE USER 'sfg_user_dev'@'%' IDENTIFIED BY 'password';
CREATE USER 'sfg_user_prod'@'%' IDENTIFIED BY 'password';

#Database grants
GRANT SELECT ON sfg_dev.* to 'sfg_user_dev'@'localhost';
GRANT INSERT ON sfg_dev.* to 'sfg_user_dev'@'localhost';
GRANT DELETE ON sfg_dev.* to 'sfg_user_dev'@'localhost';
GRANT UPDATE ON sfg_dev.* to 'sfg_user_dev'@'localhost';
GRANT SELECT ON sfg_prod.* to 'sfg_user_prod'@'localhost';
GRANT INSERT ON sfg_prod.* to 'sfg_user_prod'@'localhost';
GRANT DELETE ON sfg_prod.* to 'sfg_user_prod'@'localhost';
GRANT UPDATE ON sfg_prod.* to 'sfg_user_prod'@'localhost';
GRANT SELECT ON sfg_dev.* to 'sfg_user_dev'@'%';
GRANT INSERT ON sfg_dev.* to 'sfg_user_dev'@'%';
GRANT DELETE ON sfg_dev.* to 'sfg_user_dev'@'%';
GRANT UPDATE ON sfg_dev.* to 'sfg_user_dev'@'%';
GRANT SELECT ON sfg_prod.* to 'sfg_user_prod'@'%';
GRANT INSERT ON sfg_prod.* to 'sfg_user_prod'@'%';
GRANT DELETE ON sfg_prod.* to 'sfg_user_prod'@'%';
GRANT UPDATE ON sfg_prod.* to 'sfg_user_prod'@'%';