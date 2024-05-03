-- Actors
INSERT INTO public.actor (active, first_name, last_name, last_update)
VALUES (true, 'John', 'Doe', CURRENT_TIMESTAMP),
       (true, 'Jane', 'Smith', CURRENT_TIMESTAMP),
       (true, 'Michael', 'Johnson', CURRENT_TIMESTAMP);

-- Addresses
INSERT INTO public.address (address, address2, district, city_id, postal_code, phone, last_update)
VALUES ('123 Main St', 'Apt 101', 'Downtown', 1, '12345', '555-1234', CURRENT_TIMESTAMP),
       ('456 Elm St', NULL, 'Suburbia', 2, '67890', '555-5678', CURRENT_TIMESTAMP),
       ('789 Oak St', 'Unit 3', 'Rural', 3, '54321', '555-9876', CURRENT_TIMESTAMP);

-- Categories
INSERT INTO public.category (name, last_update)
VALUES ('Action', CURRENT_TIMESTAMP),
       ('Comedy', CURRENT_TIMESTAMP),
       ('Drama', CURRENT_TIMESTAMP);

-- Cities
INSERT INTO public.city (city, country_id, last_update)
VALUES ('New York', 1, CURRENT_TIMESTAMP),
       ('Los Angeles', 1, CURRENT_TIMESTAMP),
       ('London', 2, CURRENT_TIMESTAMP);

-- Countries
INSERT INTO public.country (country, active, last_update)
VALUES ('United States', true, CURRENT_TIMESTAMP),
       ('United Kingdom', true, CURRENT_TIMESTAMP),
       ('Canada', true, CURRENT_TIMESTAMP);

-- Customers
INSERT INTO public.customer (store_id, first_name, last_name, email, address_id, active, create_date, last_update, activebool)
VALUES (1, 'Alice', 'Johnson', 'alice@example.com', 1, true, CURRENT_DATE, CURRENT_TIMESTAMP, 1),
       (2, 'Bob', 'Smith', 'bob@example.com', 2, true, CURRENT_DATE, CURRENT_TIMESTAMP, 1),
       (1, 'Charlie', 'Brown', 'charlie@example.com', 3, true, CURRENT_DATE, CURRENT_TIMESTAMP, 1);

-- Film Lists
INSERT INTO public.film_list (title, description, category, price, length, rating, actors)
VALUES ('Film 1', 'Description for Film 1', 'Action', 9.99, 120, 7.5, 'John Doe, Jane Smith'),
       ('Film 2', 'Description for Film 2', 'Comedy', 8.99, 100, 6.8, 'Michael Johnson'),
       ('Film 3', 'Description for Film 3', 'Drama', 7.99, 110, 8.2, 'John Doe, Jane Smith, Michael Johnson');

-- Inventories
INSERT INTO public.inventory (film_id, store_id, last_update, active)
VALUES (1, 1, CURRENT_TIMESTAMP, true),
       (2, 1, CURRENT_TIMESTAMP, true),
       (3, 2, CURRENT_TIMESTAMP, true);

-- Languages
INSERT INTO public.language (name, active, last_update)
VALUES ('English', true, CURRENT_TIMESTAMP),
       ('Spanish', true, CURRENT_TIMESTAMP),
       ('French', true, CURRENT_TIMESTAMP);

-- Staff
INSERT INTO public.staff (first_name, last_name, address_id, email, store_id, active, username, password, picture, last_update)
VALUES ('John', 'Doe', 1, 'john@example.com', 1, true, 'john_doe', 'password123', NULL, CURRENT_TIMESTAMP),
       ('Jane', 'Smith', 2, 'jane@example.com', 2, true, 'jane_smith', 'password123', NULL, CURRENT_TIMESTAMP),
       ('Michael', 'Johnson', 3, 'michael@example.com', 1, true, 'michael_johnson', 'password123', NULL, CURRENT_TIMESTAMP);

-- Stores
INSERT INTO public.store (manager_staff_id, address_id, active, last_update)
VALUES (1, 1, true, CURRENT_TIMESTAMP),
       (2, 2, true, CURRENT_TIMESTAMP),
       (3, 3, true, CURRENT_TIMESTAMP);
