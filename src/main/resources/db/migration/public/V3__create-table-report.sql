CREATE TABLE IF NOT EXISTS public.report (
    id INT4 NOT NULL,
    name varchar(150) not null,
    title varchar(150) not null,
    key varchar(150) not null,
    sql text not null,
    sql_totalizers text,
    active boolean default true,
    schema varchar(100)
);

CREATE SEQUENCE IF NOT EXISTS public.gen_id_report
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

ALTER TABLE public.report ADD CONSTRAINT pk_report PRIMARY KEY (id);
ALTER TABLE public.report ADD CONSTRAINT report_key_key UNIQUE (key);

CREATE TRIGGER tr_set_schema_report BEFORE INSERT ON report FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();