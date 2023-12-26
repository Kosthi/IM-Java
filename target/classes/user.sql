SET foreign_key_checks = 0;

DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    id           bigint unsigned auto_increment
        primary key,
    account      varchar(20)  not null,
    password     char(32)     not null,
    username     varchar(20)  null,
    sex          char(1)      null,
    age          tinyint      null,
    telephone    char(11)     null,
    address      varchar(255) null,
    label        varchar(255) null,
    head         varchar(255) null,
    background   varchar(255) null,
    gmt_create   DATETIME DEFAULT CURRENT_TIMESTAMP,
    gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    constraint user_pk
        unique (account)
);

DROP TABLE IF EXISTS friendship;
CREATE TABLE friendship
(
    user_id1     bigint unsigned                       not null,
    user_id2     bigint unsigned                       not null,
    status       enum ('active', 'blocked', 'pending') not null,
    gmt_create   DATETIME DEFAULT CURRENT_TIMESTAMP,
    gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    primary key (user_id1, user_id2),
    foreign key (user_id1) references user (id),
    foreign key (user_id2) references user (id)
);

DROP TABLE IF EXISTS message;
CREATE TABLE message
(
    id           bigint unsigned auto_increment
        primary key,
    sender_id    bigint unsigned not null,
    receiver_id  bigint unsigned not null,
    content      text            not null,
    gmt_create   DATETIME DEFAULT CURRENT_TIMESTAMP,
    gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    foreign key (sender_id) references user (id),
    foreign key (receiver_id) references user (id)
);

DROP TABLE IF EXISTS session;
CREATE TABLE session
(
    id           bigint unsigned auto_increment
        primary key,
    user_id      bigint unsigned not null,
    is_online    bool     DEFAULT FALSE,
    gmt_create   DATETIME DEFAULT CURRENT_TIMESTAMP,
    gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    foreign key (user_id) references user (id)
);

SET foreign_key_checks = 1;

insert into user (account, password, username, sex, age, telephone)
values ('11', '8f0f2c3db8fdbe545ef96cb433894709', '测试1', '男', 21, '17326771745');

insert into user (account, password, username, sex, age, telephone)
values ('12', '8f0f2c3db8fdbe545ef96cb433894709', '测试2', '男', 18, '15068488287');

insert into user (account, password, username, sex, age, telephone)
values ('13', '8f0f2c3db8fdbe545ef96cb433894709', '测试3', '女', 18, '15068488287');

insert into user (account, password, username, sex, age, telephone)
values ('14', '8f0f2c3db8fdbe545ef96cb433894709', '测试4', '男', 18, '15068488287');

insert into user (account, password, username, sex, age, telephone)
values ('15', '8f0f2c3db8fdbe545ef96cb433894709', '测试5', '男', 18, '15068488287');

insert into friendship (user_id1, user_id2, status)
values (1, 2, 'active');

insert into friendship (user_id1, user_id2, status)
values (2, 1, 'active');

insert into friendship (user_id1, user_id2, status)
values (1, 3, 'active');

insert into friendship (user_id1, user_id2, status)
values (3, 1, 'active');

insert into friendship (user_id1, user_id2, status)
values (2, 4, 'active');

insert into friendship (user_id1, user_id2, status)
values (4, 2, 'active');

insert into message (sender_id, receiver_id, content)
values (1, 1, 'woshi1');

insert into message (sender_id, receiver_id, content)
values (1, 2, 'nihao2');

insert into message (sender_id, receiver_id, content)
values (2, 1, 'nihao1');

insert into session (user_id, is_online)
values (1, false);

insert into session (user_id, is_online)
values (2, true);
