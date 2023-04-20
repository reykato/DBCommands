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

insert into policy values (0, '$22,500', 55, '12-JUN-2007', '04-JUN-2034', 222004444, 'home_info');
insert into policy values (1, '$55,220', 900, '03-FEB-2023', '03-JUN-2024', 777220000, 'home_info');
insert into policy values (2, '$800,840', 1200, '08-JAN-1960', '19-NOV-1981', 888442222, 'home_info');
insert into policy values (3, '$5,000', 400, '27-AUG-2007', '27-AUG-2024', 222004444, 'car_info');
insert into policy values (4, '$12,000', 1100, '23-JUL-1999', '17-JUN-2008', 777334444, 'car_info');
insert into policy values (5, '$40,000', 3900, '12-APR-1973', '12-APR-2001', 111448888, 'car_info');

insert into home_info values(0, '46 Dauphin St, Campbelltown', 4200, 4, 3, 1250000, 0); 
insert into home_info values(1, '2017 Selene Av, Moravia', 800, 2, 1, 600000, 1); 
insert into home_info values(2, '1313 Lobotomy Lane, Chalk', 2500, 3, 2, 65000, 2); 

insert into car_info values('1G1YM3D78E9132314', 24000, 'Ford', 'Focus', 2007, 3); 
insert into car_info values('F77WH2994LF282F88', 80000, 'Buick', 'LeSabre', 1999, 4); 
insert into car_info values('JJ3K4J5OS889W3J48', 4000, 'Maserati', 'Mountaineer', 2022, 5);
