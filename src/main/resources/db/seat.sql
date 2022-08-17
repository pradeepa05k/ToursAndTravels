-- Drop table if already exist
drop table if exists seat;

-- Install extension to create UUID
create extension if not exists "uuid-ossp";

-- Specified value for seat_type
create type seat_types AS enum ('Adjacent','Window');

-- User Info table
create table seat(
    seat_id uuid default uuid_generate_v4(),
    bus_id uuid,
    seat_name varchar(36) not null,
    seat_type seat_types not null,
	is_booked boolean,
    	
	user_created varchar(36) not null,
	user_updated varchar(36) not null,
	date_created timestamp not null default current_timestamp,
	date_updated timestamp not null default current_timestamp, 
	
	-- date_created varchar(36) not null,
	-- date_updated varchar(36) not null,
	    
    constraint seat_pk primary key(seat_id),
	constraint seat_bus_id_fk foreign key(bus_id) references bus(bus_id)
);

-- Index for retrieval using seat_name
create index ix_seat_seat_name on seat(seat_name);
