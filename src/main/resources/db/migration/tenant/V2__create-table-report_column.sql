CREATE TABLE IF NOT EXISTS report_column () INHERITS (report_column);

ALTER TABLE report_column ADD CONSTRAINT pk_report_column PRIMARY KEY (id);
alter table report_column add constraint fk_report_column_report
foreign key (id_report) references report(id);

CREATE TRIGGER tr_set_schema_report_column BEFORE INSERT ON report_column FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();