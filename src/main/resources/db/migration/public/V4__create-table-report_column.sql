CREATE TABLE IF NOT EXISTS public.report_column (
    id INT4 NOT NULL,
    id_report int4 not null,
    field varchar(50) not null,
    html text not null,
    header_name varchar(50) not null,
    header_align varchar(8) not null,
    align varchar(8) not null,
    sort int4 not null,
    format varchar(20) not null default 'DEFAULT',
    schema varchar(100)
);

CREATE SEQUENCE IF NOT EXISTS public.gen_id_report_column
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

ALTER TABLE public.report_column ADD CONSTRAINT pk_report_column PRIMARY KEY (id);
alter table public.report_column add constraint fk_report_column_report
foreign key (id_report) references public.report(id);

CREATE TRIGGER tr_set_schema_report_column BEFORE INSERT ON report_column FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();