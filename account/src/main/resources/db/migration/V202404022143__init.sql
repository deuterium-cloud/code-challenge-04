create table if not exists account
(
    id                  uuid not null primary key,
    email               varchar(255) unique,
    role                varchar(255) not null,
    password_hash       varchar(1023) not null
);

