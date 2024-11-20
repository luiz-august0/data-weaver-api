CREATE TABLE IF NOT EXISTS public.dashboard (
    id INT4 NOT NULL,
    name varchar(150) not null,
    title varchar(150) not null,
    main boolean default false,
    schema varchar(100)
);

CREATE SEQUENCE IF NOT EXISTS public.gen_id_dashboard
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

ALTER TABLE public.dashboard ADD CONSTRAINT pk_dashboard PRIMARY KEY (id);

CREATE TRIGGER tr_set_schema_dashboard BEFORE INSERT ON dashboard FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();