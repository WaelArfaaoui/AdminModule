CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE public.actor (
                    actor_id SERIAL PRIMARY KEY,
                              active boolean,
                              first_name text,
                              last_name text,
                              last_update timestamp with time zone
);

CREATE TABLE public.address (
                                address_id SERIAL PRIMARY KEY,
                                address text,
                                address2 text,
                                district text,
                                city_id smallint,
                                postal_code text,
                                phone text,
                                last_update timestamp with time zone
);

CREATE TABLE public.category (
                                 category_id SERIAL PRIMARY KEY,
                                 name text,
                                 last_update timestamp with time zone
);

CREATE TABLE public.city (
                             city_id SERIAL PRIMARY KEY,
                             city text,
                             country_id smallint,
                             last_update timestamp with time zone
);

CREATE TABLE public.country (
                                country_id SERIAL PRIMARY KEY,
                                country text,
                                active boolean,
                                last_update timestamp with time zone
);

CREATE TABLE public.customer (
                                 customer_id SERIAL PRIMARY KEY,
                                 store_id smallint,
                                 first_name text,
                                 last_name text,
                                 email text,
                                 address_id smallint,
                                 active boolean,
                                 create_date date,
                                 last_update timestamp with time zone,
                                 activebool integer
);
 CREATE TABLE public.film_list (
                                  fid SERIAL PRIMARY KEY,
                                  title text,
                                  description text,
                                  category text,
                                  price numeric,
                                  length smallint,
                                  rating numeric,
                                  actors text
);

CREATE TABLE public.inventory (
                                  inventory_id SERIAL PRIMARY KEY,
                                  film_id smallint,
                                  store_id smallint,
                                  last_update timestamp with time zone,
                                  active boolean
);

CREATE TABLE public.language (
                                 language_id SERIAL PRIMARY KEY,
                                 name character varying,
                                 active boolean,
                                 last_update timestamp with time zone
);



CREATE TABLE public.staff (
                              staff_id SERIAL PRIMARY KEY,
                              first_name text,
                              last_name text,
                              address_id smallint,
                              email text,
                              store_id smallint,
                              active boolean,
                              username text,
                              password text,
                              picture bytea,
                              last_update timestamp with time zone
);

CREATE TABLE public.store (
                              store_id SERIAL PRIMARY KEY,
                              manager_staff_id smallint,
                              address_id smallint,
                              active boolean,
                              last_update timestamp with time zone
);
