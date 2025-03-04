CREATE TABLE IF NOT EXISTS database_connection () INHERITS (public.database_connection);

ALTER TABLE database_connection ADD CONSTRAINT pk_database_connection PRIMARY KEY (id);

CREATE TRIGGER tr_set_schema_database_connection BEFORE INSERT ON database_connection FOR EACH ROW EXECUTE PROCEDURE public.trigger_set_schema();
