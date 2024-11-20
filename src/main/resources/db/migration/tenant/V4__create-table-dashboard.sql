CREATE TABLE IF NOT EXISTS dashboard () INHERITS (public.dashboard);

ALTER TABLE dashboard ADD CONSTRAINT pk_dashboard PRIMARY KEY (id);

CREATE TRIGGER tr_set_schema_dashboard BEFORE INSERT ON dashboard FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();