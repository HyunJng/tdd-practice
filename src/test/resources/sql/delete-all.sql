truncate table `posts`;
delete from `users`;
alter table users alter column id restart with 1;
alter table posts alter column id restart with 1;
