CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE public.bookstore
(
    id bigint NOT NULL,
    name character varying(256)
);

alter table public.bookstore add primary key (id);

INSERT INTO public.bookstore (id, name)
VALUES (1, 'Jans bookstore');

