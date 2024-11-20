CREATE TABLE IF NOT EXISTS dashboard_report () INHERITS (public.dashboard_report);

ALTER TABLE dashboard_report ADD CONSTRAINT pk_dashboard_report PRIMARY KEY (id);

alter table dashboard_report add constraint fk_dashboard_report_dashboard
foreign key (id_dashboard) references dashboard(id);

alter table dashboard_report add constraint fk_dashboard_report_report
foreign key (id_report) references report(id);

CREATE TRIGGER tr_set_schema_dashboard_report BEFORE INSERT ON dashboard_report FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();
