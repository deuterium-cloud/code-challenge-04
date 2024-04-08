create table if not exists character_class
(
    id            varchar(255) not null primary key,
    name          varchar(255) unique,
    description   varchar(1023)
);

create table if not exists character
(
    id                  varchar(255) not null primary key,
    name                varchar(255) unique,
    health              integer,
    mana                integer,
    base_strength       integer,
    base_agility        integer,
    base_faith          integer,
    base_intelligence   integer,
    created_by          varchar(255) not null,

    character_class_id  varchar(255),
    constraint FK_character_character_class_id foreign key (character_class_id) references character_class(id)
);

create table if not exists item
(
    id                  varchar(255) not null primary key,
    name                varchar(255) not null,
    description         varchar(1023) not null,
    bonus_strength      integer,
    bonus_agility       integer,
    bonus_intelligence  integer,
    bonus_faith         integer
);

create table if not exists character_item
(
    id                  varchar(255) not null primary key,
    character_id        varchar(255),
    item_id             varchar(255),

    constraint FK_character_item_character_id foreign key (character_id) references character(id),
    constraint FK_character_item_item_id foreign key (item_id) references item(id)
);

