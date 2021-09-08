CREATE TABLE public.stock
(
    id bigint NOT NULL,
    store_id bigint NOT NULL,
    book_id bigint NOT NULL,
    stock_count int
);

alter table public.stock
    add primary key (id);
alter table public.stock
    ADD FOREIGN KEY (store_id)
        REFERENCES bookstore(id);
alter table public.stock
    ADD FOREIGN KEY (book_id)
        REFERENCES book(id);
