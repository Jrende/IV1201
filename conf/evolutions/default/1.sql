# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table account (
  person_id                 bigint not null,
  username                  varchar(255),
  email                     varchar(255),
  surname                   varchar(255),
  name                      varchar(255),
  role                      integer,
  password                  varchar(255),
  ssn                       varchar(255),
  constraint ck_account_role check (role in (0,1)),
  constraint pk_account primary key (person_id))
;

create sequence account_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists account;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists account_seq;

