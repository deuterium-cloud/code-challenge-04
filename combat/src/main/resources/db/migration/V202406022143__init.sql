create table if not exists combat
(
    id                      uuid default gen_random_uuid(),
    challenger_id           varchar(255) not null,
    challenger_user_id      varchar(255) not null,
    challenger_health       integer,
    challenger_mana         integer,
    challenger_attack       integer,
    challenger_cast         integer,
    challenger_heal         integer,
    challenged_id           varchar(255) not null,
    challenged_user_id      varchar(255) not null,
    challenged_health       integer,
    challenged_mana         integer,
    challenged_attack       integer,
    challenged_cast         integer,
    challenged_heal         integer,
    status                  varchar(127) not null,
    primary key (id)
);


