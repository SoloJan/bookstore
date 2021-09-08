CREATE TABLE public.stock
(
    id bigint NOT NULL,
    store_id bigint NOT NULL,
    book_id bigint NOT NULL,
    title character varying(256),
    author character varying(256)
);

alter table public.stock
    add primary key (id);
alter table public.stock
    ADD FOREIGN KEY (store_id)
        REFERENCES bookstore(id);
alter table public.stock
    ADD FOREIGN KEY (book_id)
        REFERENCES bookstore(id);
