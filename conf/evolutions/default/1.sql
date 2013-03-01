# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table availability (
  availability_id           bigint auto_increment not null,
  person_id                 bigint,
  from_date                 datetime,
  to_date                   datetime,
  constraint pk_availability primary key (availability_id))
;

create table competence (
  competence_id             bigint auto_increment not null,
  name                      varchar(255),
  constraint pk_competence primary key (competence_id))
;

create table competenceProfile (
  competence_profile_id     bigint auto_increment not null,
  person                    bigint,
  competence_competence_id  bigint,
  years_of_experience       float,
  constraint pk_competenceProfile primary key (competence_profile_id))
;

create table person (
  person_id                 bigint auto_increment not null,
  username                  varchar(255),
  email                     varchar(255),
  surname                   varchar(255),
  name                      varchar(255),
  role                      integer,
  password                  varchar(255),
  ssn                       varchar(255),
  is_hired                  tinyint(1) default 0,
  constraint ck_person_role check (role in (0,1)),
  constraint pk_person primary key (person_id))
;

alter table availability add constraint fk_availability_person_id_1 foreign key (person_id) references person (person_id) on delete restrict on update restrict;
create index ix_availability_person_id_1 on availability (person_id);
alter table competenceProfile add constraint fk_competenceProfile_person_2 foreign key (person) references person (person_id) on delete restrict on update restrict;
create index ix_competenceProfile_person_2 on competenceProfile (person);
alter table competenceProfile add constraint fk_competenceProfile_competenc_3 foreign key (competence_competence_id) references competence (competence_id) on delete restrict on update restrict;
create index ix_competenceProfile_competenc_3 on competenceProfile (competence_competence_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table availability;

drop table competence;

drop table competenceProfile;

drop table person;

SET FOREIGN_KEY_CHECKS=1;

