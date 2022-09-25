-- Create account table
create table if not exists account (
	id 			int             not null    auto_increment,
	created_ts 	timestamp 		not null,
	updated_ts 	timestamp 		not null,
	title 		varchar(25) 	not null,
	email       varchar(256)    not null    unique,
	balance 	decimal(15,2) 	not null,

	constraint pk_account__id primary key (id)
);

-- Create transaction table
create table if not exists account_transaction (
	id 			        int 	        	        not null    auto_increment,
	created_ts 	        timestamp 		            not null,
	updated_ts 	        timestamp 		            not null,
	reason 		        varchar(25) 	            not null,
	reference_id 		varchar(36) 	            not null,
	type 		        enum('DEBIT', 'CREDIT') 	not null,
	amount 		        decimal(15,2) 	            not null,
	starting_balance    decimal(15,2) 	            not null,
	ending_balance      decimal(15,2) 	            not null,
	account_id          int                 not null,

	constraint pk_account_transaction__id primary key (id),
	constraint fk_account_transaction__account_id foreign key (account_id) references account(id)
);
