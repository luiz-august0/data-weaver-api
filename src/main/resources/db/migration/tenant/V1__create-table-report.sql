CREATE TABLE IF NOT EXISTS report () INHERITS (report);

ALTER TABLE report ADD CONSTRAINT pk_report PRIMARY KEY (id);
ALTER TABLE report ADD CONSTRAINT report_key_key UNIQUE (key);

CREATE TRIGGER tr_set_schema_report BEFORE INSERT ON report FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();