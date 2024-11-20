CREATE TABLE IF NOT EXISTS dashboard_link () INHERITS (public.dashboard_link);

ALTER TABLE dashboard_link ADD CONSTRAINT pk_dashboard_link PRIMARY KEY (id);

alter table dashboard_link add constraint fk_dashboard_link_dashboard
foreign key (id_dashboard) references dashboard(id);

CREATE TRIGGER tr_set_schema_dashboard_link BEFORE INSERT ON dashboard_link FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();
