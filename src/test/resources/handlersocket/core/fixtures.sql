drop table if exists hs_test_table;
create table hs_test_table (
   id int unsigned not null auto_increment primary key,
   `name` varchar(24) not null,
   birthday date not null,
   address varchar(128),
   notes text,
   unique key hs_test_name_bd_uk (`name`, birthday),
   index hs_test_bday_idx (birthday),
   index hs_test_name_addr_idx (`name`, address),
   index hs_test_notes_idx (notes(128))
);
insert into hs_test_table (id, `name`, birthday, address, notes) values
(1, 'Elizabeth Taylor', '1932-02-27', null, null),
(2, 'John Turturro', '1957-02-27', null, null),
(3, 'Kate Hudson', '1979-04-19', null, null),
(4, 'Elizabeth Taylor''s twin', '1932-02-27', null, null)
;
