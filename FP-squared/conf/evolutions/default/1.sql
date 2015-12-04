# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table account (
  accountID                 bigint auto_increment not null,
  userName                  varchar(255),
  password                  varchar(255),
  team_teamID               bigint,
  constraint pk_account primary key (accountID))
;

create table checklist_item (
  checklistitemID           bigint auto_increment not null,
  checked                   tinyint(1) default 0,
  text                      varchar(255),
  task_taskID               bigint,
  constraint pk_checklist_item primary key (checklistitemID))
;

create table comment (
  commentID                 bigint auto_increment not null,
  text                      varchar(255),
  userName                  varchar(255),
  task_taskID               bigint,
  constraint pk_comment primary key (commentID))
;

create table sprint (
  sprintID                  bigint auto_increment not null,
  name                      varchar(255),
  start                     datetime(6),
  end                       datetime(6),
  finished                  tinyint(1) default 0,
  team_teamID               bigint,
  constraint pk_sprint primary key (sprintID))
;

create table story (
  storyID                   bigint auto_increment not null,
  start                     datetime(6),
  end                       datetime(6),
  finished                  tinyint(1) default 0,
  sprint_sprintID           bigint,
  constraint pk_story primary key (storyID))
;

create table task (
  taskID                    bigint auto_increment not null,
  name                      varchar(255),
  status                    varchar(255),
  story_storyID             bigint,
  constraint pk_task primary key (taskID))
;

create table team (
  teamID                    bigint auto_increment not null,
  name                      varchar(255),
  constraint pk_team primary key (teamID))
;

alter table account add constraint fk_account_team_1 foreign key (team_teamID) references team (teamID) on delete restrict on update restrict;
create index ix_account_team_1 on account (team_teamID);
alter table checklist_item add constraint fk_checklist_item_task_2 foreign key (task_taskID) references task (taskID) on delete restrict on update restrict;
create index ix_checklist_item_task_2 on checklist_item (task_taskID);
alter table comment add constraint fk_comment_task_3 foreign key (task_taskID) references task (taskID) on delete restrict on update restrict;
create index ix_comment_task_3 on comment (task_taskID);
alter table sprint add constraint fk_sprint_team_4 foreign key (team_teamID) references team (teamID) on delete restrict on update restrict;
create index ix_sprint_team_4 on sprint (team_teamID);
alter table story add constraint fk_story_sprint_5 foreign key (sprint_sprintID) references sprint (sprintID) on delete restrict on update restrict;
create index ix_story_sprint_5 on story (sprint_sprintID);
alter table task add constraint fk_task_story_6 foreign key (story_storyID) references story (storyID) on delete restrict on update restrict;
create index ix_task_story_6 on task (story_storyID);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table account;

drop table checklist_item;

drop table comment;

drop table sprint;

drop table story;

drop table task;

drop table team;

SET FOREIGN_KEY_CHECKS=1;

