INSERT INTO tb_category (name,created_At) values ('Pizza',NOW());
INSERT INTO tb_category (name,created_At) values ('Refrigerante',NOW());
INSERT INTO tb_category (name,created_At) values ('Bebidas',NOW());

INSERT INTO tb_product (name,description,price,created_At) values ('Coca-cola','Um delicioso refrigerante que cai bem com qualquer comida','5.50',NOW());
INSERT INTO tb_product (name,description,price,created_At) values ('Pepsi','Pepsi - A combinação perfeita','4.50',NOW());

INSERT INTO tb_product_category(category_id,product_id) values (2,1);
INSERT INTO tb_product_category(category_id,product_id) values (2,2);
INSERT INTO tb_product_category(category_id,product_id) values (3,1);
INSERT INTO tb_product_category(category_id,product_id) values (3,2);

INSERT  INTO tb_role(authority) values ('ADMIN');
INSERT  INTO tb_role(authority) values ('CLIENT');
INSERT  INTO tb_role(authority) values ('DRIVER');

INSERT INTO tb_user(name,phone,password,email,cpf,date,address,created_At ) values ('ADMIN','759813X5XXX','1234','admin@admin.com','ADMINCPF','1999-11-21','ADMIN ADDRESS',NOW());

INSERT INTO tb_user_role(user_id,role_id) values (1,1);



