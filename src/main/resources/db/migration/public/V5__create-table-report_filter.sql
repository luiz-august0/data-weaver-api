CREATE TABLE IF NOT EXISTS public.report_filter (
    id INT4 NOT NULL,
    id_report int4 not null,
    label varchar(50),
    parameter varchar(50) not null,
    type varchar(20) not null,
    sql text not null,
    standard_value varchar(20),
    "order" int4 not null,
    schema varchar(100)
);

CREATE SEQUENCE IF NOT EXISTS public.gen_id_report_filter
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

ALTER TABLE public.report_filter ADD CONSTRAINT pk_report_filter PRIMARY KEY (id);
alter table public.report_filter add constraint fk_report_filter_report
foreign key (id_report) references public.report(id);

CREATE TRIGGER tr_set_schema_report_filter BEFORE INSERT ON report_filter FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();