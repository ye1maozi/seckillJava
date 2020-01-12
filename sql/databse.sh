#!/bin/bash

HOSTNAME="localhost"
PORT="3306"
NAME="root"
PASSWORD=$1
DATABASE="seckill"

# echo "connect mysql"
#mysql -h ${HOSTNAME} -P ${PORT} -u ${NAME} -p${PASSWORD} -e ""

echo "create database"
dropCreateData="DROP DATABASE IF EXISTS ${DATABASE};"
createStr="CREATE DATABASE IF NOT EXISTS ${DATABASE} DEFAULT CHARACTER SET utf8;"
#mysql -h ${HOSTNAME} -P ${PORT} -u ${NAME} -p${PASSWORD} -e "${dropCreateData}"
mysql -h ${HOSTNAME} -P ${PORT} -u ${NAME} -p${PASSWORD} -e "${createStr}"

# 分组（角色）
echo "create table sk_goods"
tableUser="create table IF NOT EXISTS sk_goods (
    id bigint(20) not null AUTO_INCREMENT comment '商品id',
    goods_name varchar(32) default null comment '商品名',
    goods_title varchar(64) default null comment '商品标题',
    goods_img varchar(64) default null comment '商品图片',
    goods_detail longtext comment '商品详情',
    goods_price decimal(10,2) default null comment '价格',
    goods_stock int(11) default 0 comment '库存',
    create_time datetime default null comment '创建日期',
    PRIMARY KEY(id)
)ENGINE=InnoDB default charset=utf8;"
mysql -h ${HOSTNAME} -P ${PORT} -u ${NAME} -p${PASSWORD} ${DATABASE} -e "${tableUser}"

# 分组（角色）
echo "create table sk_goods_seckill"
tableUser="create table IF NOT EXISTS sk_goods_seckill (
    id bigint(20) not null AUTO_INCREMENT comment '秒杀商品id',
    goods_id bigint(20) default null comment '商品id',
    seckill_price decimal(10,2) default 0 comment '价格',
    stock_count int(11)  default 0 comment '库存',
    start_date datetime default null comment '开始时间',
    end_date datetime default null comment '结束时间',
    version int(11) default null comment '并发版本控制',
 	create_time datetime default null comment '创建日期',
    PRIMARY KEY(id)
)ENGINE=InnoDB default charset=utf8;"
mysql -h ${HOSTNAME} -P ${PORT} -u ${NAME} -p${PASSWORD} ${DATABASE} -e "${tableUser}"

# 分组（角色）
echo "create table sk_user"
tableUser="create table IF NOT EXISTS sk_user (
    id bigint(20) not null AUTO_INCREMENT comment '用户id',
    nickname varchar(255) not null comment '昵称',
    password varchar(32) default null comment '密码',
    salt varchar(10) default null comment '混淆盐',
    head varchar(128) default null comment '头像',
 	create_time datetime default null comment '创建日期',
 	last_login_time datetime default null comment '登陆时间',
 	login_count int(11) default null comment '次数',
    PRIMARY KEY(id)
)ENGINE=InnoDB default charset=utf8;"
mysql -h ${HOSTNAME} -P ${PORT} -u ${NAME} -p${PASSWORD} ${DATABASE} -e "${tableUser}"


# 分组（角色）
echo "create table sk_order_info"
tableUser="create table IF NOT EXISTS sk_order_info (
    id bigint(20) not null AUTO_INCREMENT comment '订单号',
    user_id bigint(20) default null,
	goods_id bigint(20) default null,
	devlivery_addr_id bigint(20) default null,
	goods_name varchar(30) default null,
	goods_count int(11) default null,
	goods_price decimal(10,2) default null,
	order_channel tinyint(4) default null comment '订单渠道',
	status tinyint(4) default null comment '订单状态',
	pay_time datetime default null,
 	create_time datetime default null comment '创建日期',
    PRIMARY KEY(id)
)ENGINE=InnoDB default charset=utf8;"
mysql -h ${HOSTNAME} -P ${PORT} -u ${NAME} -p${PASSWORD} ${DATABASE} -e "${tableUser}"



# 分组（角色）
echo "create table sk_order"
tableUser="create table IF NOT EXISTS sk_order (
    id bigint(20) not null AUTO_INCREMENT comment '订单号',
    user_id bigint(20) default null,
	order_id bigint(20) default null,
	goods_id bigint(20) default null,
 	create_time datetime default null comment '创建日期',
    PRIMARY KEY(id),
    UNIQUE KEY u_uid_gid (user_id,goods_id) using btree
)ENGINE=InnoDB default charset=utf8;"
mysql -h ${HOSTNAME} -P ${PORT} -u ${NAME} -p${PASSWORD} ${DATABASE} -e "${tableUser}"


# CONSTRAINT fk_user_role_user foreign key(userId) references user (id),
# CONSTRAINT fk_user_role_role foreign key(groupId) references role (id)

read -p "Press any key to continue." var