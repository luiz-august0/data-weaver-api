CREATE TABLE IF NOT EXISTS report_filter () INHERITS (report_filter);

ALTER TABLE report_filter ADD CONSTRAINT pk_report_filter PRIMARY KEY (id);
alter table report_filter add constraint fk_report_filter_report
foreign key (id_report) references report(id);

CREATE TRIGGER tr_set_schema_report_filter BEFORE INSERT ON report_filter FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();