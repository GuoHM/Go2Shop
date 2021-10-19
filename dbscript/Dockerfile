FROM mysql

COPY AuthDB/TableScript/AuthDBScript.sql /docker-entrypoint-initdb.d
COPY AuthDB/DataScript/basic.sql /docker-entrypoint-initdb.d

COPY OrderDB/TableScript/OrderDBScript.sql /docker-entrypoint-initdb.d

COPY ProductDB/TableScript/ProductDBScript.sql /docker-entrypoint-initdb.d
COPY ProductDB/TableScript/ProductImagesDBScript.sql /docker-entrypoint-initdb.d
COPY ProductDB/TableScript/ProductReviewDBScript.sql /docker-entrypoint-initdb.d
COPY ProductDB/DataScript/productdb_tb_product.sql /docker-entrypoint-initdb.d
COPY ProductDB/DataScript/productdb_tb_product_images.sql /docker-entrypoint-initdb.d
COPY ProductDB/DataScript/productdb_tb_product_review.sql /docker-entrypoint-initdb.d

COPY ShoppingCardDB/TableScript/ShoppingCartDBScript.sql /docker-entrypoint-initdb.d
COPY ShoppingCardDB/DataScript/shoppingcartdb_tb_shopping_cart.sql /docker-entrypoint-initdb.d
COPY ShoppingCardDB/DataScript/shoppingcartdb_tb_shopping_cart_has_tb_product.sql /docker-entrypoint-initdb.d

COPY UserDB/TableScript/UserDBScript.sql /docker-entrypoint-initdb.d
COPY UserDB/DataScript/UserdataScript.sql /docker-entrypoint-initdb.d

COPY account.sql /docker-entrypoint-initdb.d