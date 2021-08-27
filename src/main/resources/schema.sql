create sequence hibernate_sequence start with 1 increment by 1
create table customers (id varchar(255) not null, email varchar(255), name varchar(255), primary key (id))
create table items (id varchar(255) not null, description varchar(255), name varchar(255), price float not null, stock_amount integer not null, primary key (id))
create table order_items (id bigint not null, amount integer not null, item_id varchar(255), price float not null, primary key (id))
create table orders (id varchar(255) not null, payment_method varchar(255), status varchar(255), customer_id varchar(255), primary key (id))
create table orders_items (order_id varchar(255) not null, items_id bigint not null)
alter table orders_items add constraint UK_7qrg5pfgjon82yhgwfqrdijm5 unique (items_id)
alter table orders add constraint FKpxtb8awmi0dk6smoh2vp1litg foreign key (customer_id) references customers
alter table orders_items add constraint FKol66sj9j6lm31o8rea1gw8ij0 foreign key (items_id) references order_items
alter table orders_items add constraint FKij1wwgx6o198ubsx1oulpopem foreign key (order_id) references orders
