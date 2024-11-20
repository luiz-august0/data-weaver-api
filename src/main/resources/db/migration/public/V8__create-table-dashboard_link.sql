CREATE TABLE IF NOT EXISTS public.dashboard_link (
    id INT4 NOT NULL,
    id_dashboard INT4 NOT NULL,
    title varchar(150) not null,
    link text not null,
    "order" int4 not null,
    schema varchar(100)
);

CREATE SEQUENCE IF NOT EXISTS public.gen_id_dashboard_link
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

ALTER TABLE public.dashboard_link ADD CONSTRAINT pk_dashboard_link PRIMARY KEY (id);

alter table public.dashboard_link add constraint fk_dashboard_link_dashboard
foreign key (id_dashboard) references public.dashboard(id);

CREATE TRIGGER tr_set_schema_dashboard_link BEFORE INSERT ON dashboard_link FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();