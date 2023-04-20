drop table conditions;
drop table life_info;
drop table home_info;
drop table car_info;
drop table policy;
drop table customer;

create table customer (
    ssn int,
    firstName varchar (20) not null,
    lastName varchar (20) not null,
    contactInfo varchar (30) not null,
    dateOfBirth date not null,
    primary key (ssn)
);

create table policy (
    policy_ID int,
    --policy_ID varchar (20),
    coverage varchar (20) not null,
    monthly_payment int not null,
    start_date date not null,
    end_date date,
    owner int,
    --owner varchar (20),
	policy_type varchar (10),
    primary key (policy_ID),
    foreign key (owner) references customer(ssn)
);

create table car_info (
    vin varchar (30),
    mileagePerYear int not null,
    make varchar (20) not null,
    model varchar (20) not null,
    year numeric (4, 0) not null,
    --policy_ID varchar (20),
    policy_ID int,
    primary key (vin),
    foreign key (policy_ID) references policy
);

create table home_info (
    home_ID int,
    address varchar (30) not null,
    --area varchar (20) not null,
    area int not null, 
    bedCount int not null,
    bathCount int not null,
    price int not null,
    --policy_ID varchar (20),
    policy_ID int,
    primary key (home_ID),
    foreign key (policy_ID) references policy
);

create table life_info (
    policy_ID int,
    --policy_ID varchar (20),
    life_ID int,
    benefits int,
    primary key (life_ID),
    foreign key (policy_ID) references policy,
    foreign key (benefits) references customer(ssn)
);

create table conditions (
    life_ID int,
    existing_conditions varchar (30),
    primary key (life_ID, existing_conditions),
    foreign key (life_ID) references life_info
);

insert into customer values (222004444, 'Rodney', 'Dangerfield', '3232 Wallaby Way', '19-APR-2007');
insert into customer values (777220000, 'Max', 'Khezrimotlagh', '717-223-4495', '31-DEC-1975');
insert into customer values (777334444, 'Linda', 'Chang', 'lrc4532@psu.edu', '02-FEB-2002');
insert into customer values (111448888, 'Wilhelm', 'Blum', 'W257 Olmsted', '11-NOV-1988');
insert into customer values (888442222, 'Melissa', 'Null', '22 Areba Av, Middletown, PA', '07-MAR-1941');
