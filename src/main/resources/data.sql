-- Limpa a tabela antes de inserir novos dados para evitar duplicatas ao reiniciar a aplicação
DELETE FROM products;

-- Insere os produtos na tabela 'products'
-- A ordem das colunas é: name, description, price, image_url, featured, on_sale, sale_price

-- Produto em Destaque (Goku)
INSERT INTO products (name, description, price, image_url, featured, on_sale, sale_price) VALUES ('Action Figure Goku Super Saiyan', 'Figura de ação detalhada do Goku em sua forma Super Saiyan.', 249.90, '/images/product-goku.jpg', true, false, NULL);

-- Produto em Destaque e Promoção (Caneca The Witcher)
INSERT INTO products (name, description, price, image_url, featured, on_sale, sale_price) VALUES ('Caneca The Witcher', 'Caneca de cerâmica com o símbolo da Escola do Lobo.', 59.90, '/images/product-caneca.jpg', true, true, 39.90);

-- Produto em Promoção (Camiseta Star Wars)
INSERT INTO products (name, description, price, image_url, featured, on_sale, sale_price) VALUES ('Camiseta Star Wars', 'Camiseta de algodão com estampa clássica de Star Wars.', 89.90, '/images/product-camiseta.jpg', false, true, 69.90);

-- Produto em Destaque (Funko Pop! Hatsune Miku)
INSERT INTO products (name, description, price, image_url, featured, on_sale, sale_price) VALUES ('Funko Pop! Hatsune Miku', 'Boneco colecionável Funko Pop! da Hatsune Miku.', 129.90, '/images/product-miku.jpg', true, false, NULL);

-- Novo Produto em Destaque e Promoção (Manga Naruto)
INSERT INTO products (name, description, price, image_url, featured, on_sale, sale_price) VALUES ('Manga Naruto Volume 1', 'Primeiro volume do popular mangá Naruto.', 34.90, '/images/product-naruto.jpg', false, true, 29.90);

-- Novo Produto em Promoção (Poster Cyberpunk 2077)
INSERT INTO products (name, description, price, image_url, featured, on_sale, sale_price) VALUES ('Poster Cyberpunk 2077', 'Poster de alta qualidade do jogo Cyberpunk 2077.', 49.90, '/images/product-cyberpunk.jpg', false, true, 39.90);

-- Produto Comum (Exemplo)
INSERT INTO products (name, description, price, image_url, featured, on_sale, sale_price) VALUES ('Chaveiro The Mandalorian', 'Chaveiro de metal com o capacete do Mandalorian.', 29.90, '/images/product-mandalorian.jpg', false, false, NULL);

-- Novo Produto (Chainsaw Man)
INSERT INTO products (name, description, price, image_url, featured, on_sale, sale_price) VALUES ('Pelúcia Pochita Chainsaw Man', 'Pelúcia macia do personagem Pochita de Chainsaw Man.', 89.90, '/images/product-pochita.jpg', false, false, NULL);