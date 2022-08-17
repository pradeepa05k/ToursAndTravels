-- Drop table if already exist
drop table if exists user_info;

-- Install extension to create UUID
create extension if not exists "uuid-ossp";

-- User Info table
create table user_info(
    user_info_id uuid default uuid_generate_v4(),
    username varchar(128) not null,
    email_id varchar(255),
	password varchar(255) not null,
    mobile_no varchar(15) not null,
	is_admin boolean,
	
	date_created timestamp not null default current_timestamp,
	date_updated timestamp not null default current_timestamp, 
	    
    constraint user_info_pk primary key(user_info_id)
);

-- Index for retrieval using Mobile Number
create index ix_user_info_mobile_no on user_info(mobile_no);

-- Index for retrieval using Username
create index ix_user_info_username on user_info(username);

-- timestamp not null default current_timestamp on update current_timestamp
-- date_created date not null default timestamp,
-- date_updated date not null default timestamp,