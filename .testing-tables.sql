DROP TABLE IF EXISTS campuses;

CREATE TABLE campuses
(
    id SERIAL PRIMARY KEY,
    name character varying(255) COLLATE pg_catalog."default"
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;



CREATE TABLE public.buildings
(
    id SERIAL PRIMARY KEY,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    campus_id integer NOT NULL,
    CONSTRAINT fk3mh9ywb7pw1runo1h79893bbi FOREIGN KEY (campus_id)
        REFERENCES public.campuses (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;


	
CREATE TABLE public.resources
(
    id SERIAL PRIMARY KEY,
    available_start_date timestamp without time zone,
    disabled boolean NOT NULL,
    has_computer boolean,
    has_ethernet boolean,
    has_microphone boolean,
    inactive boolean NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    number_of_outlets integer,
    reservable_after timestamp without time zone,
    reservable_before timestamp without time zone,
    retired boolean NOT NULL,
    type integer NOT NULL,
    building_id integer NOT NULL,
    useable_from timestamp without time zone,
    CONSTRAINT fk170c3hu5ipb6tif92w6xauwh6 FOREIGN KEY (building_id)
        REFERENCES public.buildings (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.resources
    OWNER to rms_resources_app_server;
ALTER TABLE public.buildings
    OWNER to rms_resources_app_server;	
ALTER TABLE public.campuses
    OWNER to rms_resources_app_server;
