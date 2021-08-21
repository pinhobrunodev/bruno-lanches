INSERT INTO tb_category(name,created_At) values ('PASTA',NOW());
INSERT INTO tb_category(name,created_At) values ('PIZZA',NOW());
INSERT INTO tb_category(name,created_At) values ('SANDWICH',NOW());
INSERT INTO tb_category(name,created_At) values ('DRINK',NOW());
INSERT INTO tb_category(name,created_At) values ('VEGAN',NOW());

INSERT INTO tb_product(name,description,price,created_At) values ('SPAGHETTI BOLOGNESE','A DELICIOUS PASTA MADE WITH LOTS OF LOVE',20.00,NOW());
INSERT INTO tb_product(name,description,price,created_At) values ('SPAGHETTI WITH GARLIC AND OIL','A PERFECT SPAGHETTI FOR YOUR MEAL',25.00,NOW());
INSERT INTO tb_product(name,description,price,created_At) values ('PEPPERONI PIZZA','A DELICIOUS PEPPERONI PIZZA MADE WITH THE BEST INGREDIENTS',15.00,NOW());
INSERT INTO tb_product(name,description,price,created_At) values ('MOZZARELLA PIZZA','A DELICIOUS MOZZARELLA PIZZA MADE WITH THE BEST INGREDIENTS',15.00,NOW());
INSERT INTO tb_product(name,description,price,created_At) values ('X-BURGER','A DELICIOUS X-BURGER',10.00,NOW());
INSERT INTO tb_product(name,description,price,created_At) values ('AMERICAN BURGER','A DELICIOUS SANDWICH WITH LOTS OF SPICE AND LOVE',10.00,NOW());
INSERT INTO tb_product(name,description,price,created_At) values ('TOFU SANDWICH','A DELICIOUS AND NATURAL SANDWICH WITH LOTS OF LOVE',15.00,NOW());
INSERT INTO tb_product(name,description,price,created_At) values ('COKE','A DELICIOUS COLD DRINK',2.00,NOW());
INSERT INTO tb_product(name,description,price,created_At) values ('BEER','A DELICIOUS BEER',3.00,NOW());
INSERT INTO tb_product(name,description,price,created_At) values ('REDBULL','A DRINK TO KEEP YOU AWAKE',4.00,NOW());
INSERT INTO tb_product(name,description,price,created_At) values ('ORANGE JUICE','A VERY TASTY AND NATURAL JUICE',1.50,NOW());


INSERT INTO tb_product_category(category_id,product_id) values (1,1);
INSERT INTO tb_product_category(category_id,product_id) values (1,2);
INSERT INTO tb_product_category(category_id,product_id) values (5,2);
INSERT INTO tb_product_category(category_id,product_id) values (5,7);
INSERT INTO tb_product_category(category_id,product_id) values (2,3);
INSERT INTO tb_product_category(category_id,product_id) values (2,4);
INSERT INTO tb_product_category(category_id,product_id) values (3,5);
INSERT INTO tb_product_category(category_id,product_id) values (3,6);
INSERT INTO tb_product_category(category_id,product_id) values (3,7);
INSERT INTO tb_product_category(category_id,product_id) values (4,8);
INSERT INTO tb_product_category(category_id,product_id) values (4,9);
INSERT INTO tb_product_category(category_id,product_id) values (4,10);
INSERT INTO tb_product_category(category_id,product_id) values (4,11);




INSERT  INTO tb_role(authority) values ('ROLE_ADMIN');
INSERT  INTO tb_role(authority) values ('ROLE_CLIENT');
INSERT  INTO tb_role(authority) values ('ROLE_DRIVER');

INSERT INTO tb_user(name,phone,password,email,cpf,date,address,created_At ) values ('ADMIN','759813X5XXX','$2a$10$g5yuLWjfvAaNetPOf.3fa.4koCUajzFim20nSIhZqT2xzNO2gI13i','admin@admin.com','ADMINCPF','1999-11-21','ADMIN ADDRESS',NOW());

INSERT INTO tb_user_role(user_id,role_id) values (1,1);



