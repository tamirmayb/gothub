drop table if exists repositories;

create table repositories
(
    id                    bigint(20) not null auto_increment,
    name                  varchar(100) not null,
    description           varchar(500),
    language_used         varchar(50) not null,
    accessed              bigint(20),
    created_at            date,
    primary key (id)
);