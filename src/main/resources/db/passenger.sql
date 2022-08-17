-- Drop table if already exist
drop table if exists passenger;

-- Install extension to create UUID
create extension if not exists "uuid-ossp";

-- Specified value for gender
create type gender_type AS enum ('m', 'f', 'o');

-- User Info table
create table passenger(
    passenger_id uuid default uuid_generate_v4(),
    bus_id uuid,
    seat_id uuid,
    passenger_name varchar(36) not null,
	gender gender_type not null,
    age numeric,
	is_booked boolean,
    email_id varchar(255),
    mobile_no varchar(15) not null,
	boarding_point text[] not null,
	dropping_point text[] not null,
	date_of_journey date default current_date,
	
	user_created varchar(36) not null,
	user_updated varchar(36) not null,
	date_created timestamp not null default current_timestamp,
	date_updated timestamp not null default current_timestamp, 
	
	-- date_created varchar(36) not null,
	-- date_updated varchar(36) not null,
	    
    constraint passenger_pk primary key(passenger_id),    
	constraint passenger_bus_id_fk foreign key(bus_id) references bus(bus_id),
    constraint passenger_seat_id_fk foreign key(seat_id) references seat(seat_id)
);

-- Index for retrieval using seat_name
create index ix_passenger_passenger_name on passenger(passenger_name);
