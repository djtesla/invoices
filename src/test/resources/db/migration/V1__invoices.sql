create table invoices (id bigint not null auto_increment, comment varchar(255), customer_name varchar(255), due_date date, issue_date date, primary key (id));