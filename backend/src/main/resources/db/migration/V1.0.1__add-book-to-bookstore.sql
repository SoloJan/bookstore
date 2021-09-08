CREATE TABLE public.book
(
    id bigint NOT NULL,
    isbn bigint NOT NULL,
    title character varying(256),
    author character varying(256)
);

alter table public.book add primary key (id);

INSERT INTO public.book (id, isbn, title, author)
VALUES (1, 9788126568147, 'Java for dummies', 'Barry Burd'),
       (2, 9783897218765, 'JavaScript: The Good Parts', 'Douglas Crockford');

