CREATE TABLE IF NOT EXISTS public.dashboard_report (
    id INT4 NOT NULL,
    id_dashboard INT4 NOT NULL,
    id_report INT4 NOT NULL,
    sort int4 not null,
    schema varchar(100)
);

CREATE SEQUENCE IF NOT EXISTS public.gen_id_dashboard_report
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

ALTER TABLE public.dashboard_report ADD CONSTRAINT pk_dashboard_report PRIMARY KEY (id);

alter table public.dashboard_report add constraint fk_dashboard_report_dashboard
foreign key (id_dashboard) references public.dashboard(id);

alter table public.dashboard_report add constraint fk_dashboard_report_report
foreign key (id_report) references public.report(id);

CREATE TRIGGER tr_set_schema_dashboard_report BEFORE INSERT ON dashboard_report FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();