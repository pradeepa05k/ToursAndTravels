-- Drop table if already exist
drop table if exists bus;

-- Install extension to create UUID
create extension if not exists "uuid-ossp";

-- Specified value for bus_type
create type bus_types AS enum ('Any','AC sleeper','AC sleeper seater','Non-AC sleeper','Non-AC sleeper seater','Ordinary','Air Conditioned');

-- User Info table
create table bus(
    bus_id uuid default uuid_generate_v4(),
    bus_name varchar(128) not null,
    bus_type bus_types not null,
	arrival_time varchar(10) not null,
    departure_time varchar(10) not null,
	boarding_point text[] not null,
	dropping_point text[] not null,
	no_of_seats integer not null,
	bus_fare numeric,
	duration varchar(10),
	
	user_created varchar(36) not null,
	user_updated varchar(36) not null,
	date_created timestamp not null default current_timestamp,
	date_updated timestamp not null default current_timestamp, 
	-- date_created varchar(36) not null,
	-- date_updated varchar(36) not null,
	    
    constraint bus_pk primary key(bus_id),
    constraint bus_fare_check check(bus_fare > 0)
);

-- Index for retrieval using boarding_point and dropping_point
create index ix_bus_boarding_point_dropping_point on bus(boarding_point, dropping_point);
