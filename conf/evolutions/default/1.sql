# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table availability (
  availability_id           bigint not null,
  person_id                 bigint,
  from_date                 timestamp,
  to_date                   timestamp,
  constraint pk_availability primary key (availability_id))
;

create table competence (
  competence_id             integer not null,
  name                      varchar(255),
  constraint pk_competence primary key (competence_id))
;

create table competenceProfile (
  competence_profile_id     integer not null,
  person                    bigint,
  competence_competence_id  integer,
  years_of_experience       float,
  constraint pk_competenceProfile primary key (competence_profile_id))
;

create table person (
  person_id                 bigint not null,
  username                  varchar(255),
  email                     varchar(255),
  surname                   varchar(255),
  name                      varchar(255),
  role                      integer,
  password                  varchar(255),
  ssn                       varchar(255),
  constraint ck_person_role check (role in (0,1)),
  constraint pk_person primary key (person_id))
;

create sequence availability_seq;

create sequence competence_seq;

create sequence competenceProfile_seq;

create sequence person_seq;

alter table competenceProfile add constraint fk_competenceProfile_person_1 foreign key (person) references person (person_id) on delete restrict on update restrict;
create index ix_competenceProfile_person_1 on competenceProfile (person);
alter table competenceProfile add constraint fk_competenceProfile_competenc_2 foreign key (competence_competence_id) references competence (competence_id) on delete restrict on update restrict;
create index ix_competenceProfile_competenc_2 on competenceProfile (competence_competence_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists availability;

drop table if exists competence;

drop table if exists competenceProfile;

drop table if exists person;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists availability_seq;

drop sequence if exists competence_seq;

drop sequence if exists competenceProfile_seq;

drop sequence if exists person_seq;

