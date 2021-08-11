-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler  version: 0.9.2
-- PostgreSQL version: 12.0
-- Project Site: pgmodeler.io
-- Model Author: ---


-- Database creation must be done outside a multicommand file.
-- These commands were put in this file only as a convenience.
-- -- object: db_principal | type: DATABASE --
-- -- DROP DATABASE IF EXISTS db_principal;
-- CREATE DATABASE db_principal;
-- -- ddl-end --
-- 

-- object: public.pr_marca | type: TABLE --
-- DROP TABLE IF EXISTS public.pr_marca CASCADE;
CREATE TABLE public.pr_marca (
	id bigserial NOT NULL,
	nome text NOT NULL,
	date_modified timestamp,
	date_created timestamp,
	tenant_id text NOT NULL,
	CONSTRAINT pr_marca_pk PRIMARY KEY (id)

);
-- ddl-end --
-- ALTER TABLE public.pr_marca OWNER TO postgres;
-- ddl-end --

-- object: public.pr_categoria | type: TABLE --
-- DROP TABLE IF EXISTS public.pr_categoria CASCADE;
CREATE TABLE public.pr_categoria (
	id bigserial NOT NULL,
	nome text NOT NULL,
	status boolean,
	date_modified timestamp,
	date_created timestamp,
	tenant_id text NOT NULL,
	CONSTRAINT pr_categoria_pk PRIMARY KEY (id)

);
-- ddl-end --
-- ALTER TABLE public.pr_categoria OWNER TO postgres;
-- ddl-end --

-- object: public.pr_item | type: TABLE --
-- DROP TABLE IF EXISTS public.pr_item CASCADE;
CREATE TABLE public.pr_item (
	id bigserial NOT NULL,
	modelo text NOT NULL,
	descricao text,
	status boolean,
	date_modified timestamp,
	date_created timestamp,
	tenant_id text NOT NULL,
	id_pr_marca bigint,
	id_pr_categoria bigint,
	CONSTRAINT pr_item_pk PRIMARY KEY (id)

);
-- ddl-end --
-- ALTER TABLE public.pr_item OWNER TO postgres;
-- ddl-end --

-- object: pr_marca_fk | type: CONSTRAINT --
-- ALTER TABLE public.pr_item DROP CONSTRAINT IF EXISTS pr_marca_fk CASCADE;
ALTER TABLE public.pr_item ADD CONSTRAINT pr_marca_fk FOREIGN KEY (id_pr_marca)
REFERENCES public.pr_marca (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pr_categoria_fk | type: CONSTRAINT --
-- ALTER TABLE public.pr_item DROP CONSTRAINT IF EXISTS pr_categoria_fk CASCADE;
ALTER TABLE public.pr_item ADD CONSTRAINT pr_categoria_fk FOREIGN KEY (id_pr_categoria)
REFERENCES public.pr_categoria (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: public.crw_busca | type: TABLE --
-- DROP TABLE IF EXISTS public.crw_busca CASCADE;
CREATE TABLE public.crw_busca (
	id bigserial NOT NULL,
	link_url text NOT NULL,
	descricao text,
	range_inicial decimal,
	range_final decimal,
	status boolean,
	error_link boolean,
	condicao_produto text,
	scheduling_time timestamp,
	hora numeric,
	minuto numeric,
	tenant_id text NOT NULL,
	date_modified timestamp,
	date_created timestamp,
	site_base text,
	id_pr_item bigint,
	CONSTRAINT crw_busca_pk PRIMARY KEY (id)

);
-- ddl-end --
-- ALTER TABLE public.crw_busca OWNER TO postgres;
-- ddl-end --

-- object: pr_item_fk | type: CONSTRAINT --
-- ALTER TABLE public.crw_busca DROP CONSTRAINT IF EXISTS pr_item_fk CASCADE;
ALTER TABLE public.crw_busca ADD CONSTRAINT pr_item_fk FOREIGN KEY (id_pr_item)
REFERENCES public.pr_item (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: public.crw_item | type: TABLE --
-- DROP TABLE IF EXISTS public.crw_item CASCADE;
CREATE TABLE public.crw_item (
	id text NOT NULL,
	origem text NOT NULL,
	link_url text NOT NULL,
	descricao text,
	titulo text,
	valor decimal,
	condicao text,
	cidade text,
	tipo_anuncio text,
	ativo boolean,
	date_modified timestamp,
	date_created timestamp,
	CONSTRAINT crw_item_pk PRIMARY KEY (id,origem)

);
-- ddl-end --
-- ALTER TABLE public.crw_item OWNER TO postgres;
-- ddl-end --

-- object: public.crw_busca_item | type: TABLE --
-- DROP TABLE IF EXISTS public.crw_busca_item CASCADE;
CREATE TABLE public.crw_busca_item (
	id bigserial NOT NULL,
	ativo boolean NOT NULL,
	tenant_id text NOT NULL,
	id_crw_item text NOT NULL,
	id_crw_busca bigint NOT NULL,
	origem_crw_item text NOT NULL,
	CONSTRAINT crw_busca_item_pk PRIMARY KEY (id,id_crw_busca,id_crw_item,origem_crw_item)

);
-- ddl-end --
-- ALTER TABLE public.crw_busca_item OWNER TO postgres;
-- ddl-end --

-- object: crw_busca_fk | type: CONSTRAINT --
-- ALTER TABLE public.crw_busca_item DROP CONSTRAINT IF EXISTS crw_busca_fk CASCADE;
ALTER TABLE public.crw_busca_item ADD CONSTRAINT crw_busca_fk FOREIGN KEY (id_crw_busca)
REFERENCES public.crw_busca (id) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: crw_item_fk | type: CONSTRAINT --
-- ALTER TABLE public.crw_busca_item DROP CONSTRAINT IF EXISTS crw_item_fk CASCADE;
ALTER TABLE public.crw_busca_item ADD CONSTRAINT crw_item_fk FOREIGN KEY (id_crw_item,origem_crw_item)
REFERENCES public.crw_item (id,origem) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: scheduling_time | type: INDEX --
-- DROP INDEX IF EXISTS public.scheduling_time CASCADE;
CREATE INDEX scheduling_time ON public.crw_busca
	USING btree
	(
	  scheduling_time
	);
-- ddl-end --

-- object: ativo | type: INDEX --
-- DROP INDEX IF EXISTS public.ativo CASCADE;
CREATE INDEX ativo ON public.crw_busca_item
	USING btree
	(
	  ativo
	);
-- ddl-end --

-- object: tenant_id | type: INDEX --
-- DROP INDEX IF EXISTS public.tenant_id CASCADE;
CREATE INDEX tenant_id ON public.crw_busca_item
	USING btree
	(
	  tenant_id
	);
-- ddl-end --

-- object: tenant_id | type: INDEX --
-- DROP INDEX IF EXISTS public.tenant_id CASCADE;
CREATE INDEX tenant_id ON public.crw_busca
	USING btree
	(
	  tenant_id
	);
-- ddl-end --

-- object: public.tb_pessoa | type: TABLE --
-- DROP TABLE IF EXISTS public.tb_pessoa CASCADE;
CREATE TABLE public.tb_pessoa (
	pessoa_id bigserial NOT NULL,
	nome text NOT NULL,
	sobrenome text,
	data_nascimento date,
	sexo text,
	tenant_id text NOT NULL,
	cpf text,
	data_created timestamp,
	data_update timestamp,
	email text,
	usuario_id text,
	email_produto text,
	CONSTRAINT tb_pessoa_pk PRIMARY KEY (pessoa_id),
	CONSTRAINT email UNIQUE (email)

);
-- ddl-end --
-- ALTER TABLE public.tb_pessoa OWNER TO postgres;
-- ddl-end --

-- object: public.tb_telefone | type: TABLE --
-- DROP TABLE IF EXISTS public.tb_telefone CASCADE;
CREATE TABLE public.tb_telefone (
	telefone_id bigserial NOT NULL,
	numero numeric NOT NULL,
	tipo text,
	preferencia boolean NOT NULL,
	verificado boolean NOT NULL,
	pessoa_id_tb_pessoa bigint NOT NULL,
	CONSTRAINT tb_telefone_pk PRIMARY KEY (telefone_id,pessoa_id_tb_pessoa)

);
-- ddl-end --
-- ALTER TABLE public.tb_telefone OWNER TO postgres;
-- ddl-end --

-- object: tb_pessoa_fk | type: CONSTRAINT --
-- ALTER TABLE public.tb_telefone DROP CONSTRAINT IF EXISTS tb_pessoa_fk CASCADE;
ALTER TABLE public.tb_telefone ADD CONSTRAINT tb_pessoa_fk FOREIGN KEY (pessoa_id_tb_pessoa)
REFERENCES public.tb_pessoa (pessoa_id) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: public.tb_codigo_cadastro | type: TABLE --
-- DROP TABLE IF EXISTS public.tb_codigo_cadastro CASCADE;
CREATE TABLE public.tb_codigo_cadastro (
	codigo_id serial NOT NULL,
	codigo_gerado text NOT NULL,
	condicao boolean NOT NULL,
	data_created timestamp NOT NULL,
	data_expired timestamp,
	usuario_id bigint,
	CONSTRAINT tb_codigo_cadastro_pk PRIMARY KEY (codigo_id)

);
-- ddl-end --
-- ALTER TABLE public.tb_codigo_cadastro OWNER TO postgres;
-- ddl-end --

-- Appended SQL commands --
INSERT INTO public.tb_codigo_cadastro (
codigo_gerado,condicao,data_created,usuario_id) VALUES 
('mercadolivre',true,current_timestamp,null);
-- ddl-end --

-- object: public.tb_modulo | type: TABLE --
-- DROP TABLE IF EXISTS public.tb_modulo CASCADE;
CREATE TABLE public.tb_modulo (
	modulo_id serial NOT NULL,
	nome text NOT NULL,
	CONSTRAINT tb_modulo_pk PRIMARY KEY (modulo_id)

);
-- ddl-end --
-- ALTER TABLE public.tb_modulo OWNER TO postgres;
-- ddl-end --

-- Appended SQL commands --
INSERT INTO public.tb_modulo (nome) VALUES ('mercadolivre');
-- ddl-end --

-- object: public.tb_codigo_cadastro_tb_modulo | type: TABLE --
-- DROP TABLE IF EXISTS public.tb_codigo_cadastro_tb_modulo CASCADE;
CREATE TABLE public.tb_codigo_cadastro_tb_modulo (
	codigo_id_tb_codigo_cadastro integer NOT NULL,
	modulo_id_tb_modulo integer NOT NULL,
	CONSTRAINT tb_codigo_cadastro_tb_modulo_pk PRIMARY KEY (codigo_id_tb_codigo_cadastro,modulo_id_tb_modulo)

);
-- ddl-end --

-- object: tb_codigo_cadastro_fk | type: CONSTRAINT --
-- ALTER TABLE public.tb_codigo_cadastro_tb_modulo DROP CONSTRAINT IF EXISTS tb_codigo_cadastro_fk CASCADE;
ALTER TABLE public.tb_codigo_cadastro_tb_modulo ADD CONSTRAINT tb_codigo_cadastro_fk FOREIGN KEY (codigo_id_tb_codigo_cadastro)
REFERENCES public.tb_codigo_cadastro (codigo_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: tb_modulo_fk | type: CONSTRAINT --
-- ALTER TABLE public.tb_codigo_cadastro_tb_modulo DROP CONSTRAINT IF EXISTS tb_modulo_fk CASCADE;
ALTER TABLE public.tb_codigo_cadastro_tb_modulo ADD CONSTRAINT tb_modulo_fk FOREIGN KEY (modulo_id_tb_modulo)
REFERENCES public.tb_modulo (modulo_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: public.tb_pessoa_tb_modulo | type: TABLE --
-- DROP TABLE IF EXISTS public.tb_pessoa_tb_modulo CASCADE;
CREATE TABLE public.tb_pessoa_tb_modulo (
	pessoa_id_tb_pessoa bigint NOT NULL,
	modulo_id_tb_modulo integer NOT NULL,
	CONSTRAINT tb_pessoa_tb_modulo_pk PRIMARY KEY (pessoa_id_tb_pessoa,modulo_id_tb_modulo)

);
-- ddl-end --

-- object: tb_pessoa_fk | type: CONSTRAINT --
-- ALTER TABLE public.tb_pessoa_tb_modulo DROP CONSTRAINT IF EXISTS tb_pessoa_fk CASCADE;
ALTER TABLE public.tb_pessoa_tb_modulo ADD CONSTRAINT tb_pessoa_fk FOREIGN KEY (pessoa_id_tb_pessoa)
REFERENCES public.tb_pessoa (pessoa_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: tb_modulo_fk | type: CONSTRAINT --
-- ALTER TABLE public.tb_pessoa_tb_modulo DROP CONSTRAINT IF EXISTS tb_modulo_fk CASCADE;
ALTER TABLE public.tb_pessoa_tb_modulo ADD CONSTRAINT tb_modulo_fk FOREIGN KEY (modulo_id_tb_modulo)
REFERENCES public.tb_modulo (modulo_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --


