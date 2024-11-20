CREATE TABLE IF NOT EXISTS public.template_email (
    id INT4 NOT NULL,
    template_email varchar(80) not null,
    html text not null
);

CREATE SEQUENCE IF NOT EXISTS public.gen_id_template_email
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

ALTER TABLE public.template_email ADD CONSTRAINT pk_template_email PRIMARY KEY (id);
ALTER TABLE public.template_email ADD CONSTRAINT template_email_template_email_key UNIQUE (template_email);