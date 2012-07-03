--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;

--///////////////// PATTERNS TABLE /////////////////--
CREATE SEQUENCE patterns_id_seq
	START WITH 1
	INCREMENT BY 1
	NO MINVALUE
	NO MAXVALUE
	CACHE 1;
	
CREATE TABLE patterns ( 
	pattern_id integer NOT NULL PRIMARY KEY DEFAULT NEXTVAL('patterns_id_seq'::regclass),
	p_id1 varchar(255),
	p_id2 varchar(255),
	type varchar(255),
	passed varchar(10)
);

ALTER SEQUENCE patterns_id_seq OWNED BY patterns.pattern_id;