insert into users(`username`, `password`, `create_at`)
values ('testuser1', '123456', '20250615000000');

insert into posts(`title`, `content`, `user_id`, `create_at`)
values ('title1', 'content1', 1, '20250615121010');
insert into posts(`title`, `content`, `user_id`, `create_at`)
values ('title2', 'content2', 1, '20250616121000');
insert into posts(`title`, `content`, `user_id`, `create_at`)
values ('title3', 'content3', 1, '20250616121000');

insert into images_meta(`file_name`, `uploader`, `used`, `create_at`)
values ('car.jpg', 1, false, '20250616121005');
insert into images_meta(`file_name`, `uploader`, `used`, `post_id`, `create_at`)
values ('car.jpg', 1, true, 3,'20250616121005');
