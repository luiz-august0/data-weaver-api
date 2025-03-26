CREATE TABLE IF NOT EXISTS public.database_connection (
    id INT4 NOT NULL,
    host varchar(255) not null,
    port INT4 not null,
    database varchar(255) not null,
    username varchar(255) not null,
    schema varchar(100)
);

CREATE SEQUENCE IF NOT EXISTS public.gen_id_database_connection
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

ALTER TABLE public.database_connection ADD CONSTRAINT pk_database_connection PRIMARY KEY (id);

CREATE TRIGGER tr_set_schema_database_connection BEFORE INSERT ON database_connection FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();