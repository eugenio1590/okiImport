--
-- PostgreSQL database dump
--

-- Started on 2015-03-31 00:39:51

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 523 (class 2612 OID 16386)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--

CREATE PROCEDURAL LANGUAGE plpgsql;


ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 140 (class 1259 OID 89384)
-- Dependencies: 3
-- Name: analista; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE analista (
    id_analista integer NOT NULL
);


ALTER TABLE public.analista OWNER TO postgres;

--
-- TOC entry 161 (class 1259 OID 89715)
-- Dependencies: 1861 3
-- Name: clasificacion_repuesto; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE clasificacion_repuesto (
    id_clasificacion_repuesto integer DEFAULT nextval(('Clasificacion_Repuesto_id_clasificacion_repuesto_seq'::text)::regclass) NOT NULL,
    descripcion character varying(50) NOT NULL,
    estatus character varying(50) NOT NULL
);


ALTER TABLE public.clasificacion_repuesto OWNER TO postgres;

--
-- TOC entry 160 (class 1259 OID 89713)
-- Dependencies: 3
-- Name: clasificacion_repuesto_id_clasificacion_repuesto_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE clasificacion_repuesto_id_clasificacion_repuesto_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.clasificacion_repuesto_id_clasificacion_repuesto_seq OWNER TO postgres;

--
-- TOC entry 1934 (class 0 OID 0)
-- Dependencies: 160
-- Name: clasificacion_repuesto_id_clasificacion_repuesto_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('clasificacion_repuesto_id_clasificacion_repuesto_seq', 1, false);


--
-- TOC entry 141 (class 1259 OID 89397)
-- Dependencies: 3
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cliente (
    id_cliente integer NOT NULL,
    id_tipo_cliente integer
);


ALTER TABLE public.cliente OWNER TO postgres;

--
-- TOC entry 163 (class 1259 OID 89723)
-- Dependencies: 1862 1863 3
-- Name: detalle_requerimiento; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE detalle_requerimiento (
    id_detalle_requerimiento integer DEFAULT nextval(('Detalle_Requerimiento_Id_detalle_requerimiento_seq'::text)::regclass) NOT NULL,
    id_requerimiento integer NOT NULL,
    id_clasificacion_repuesto integer,
    nombre character varying(50) NOT NULL,
    codigo_oem character varying(50) NOT NULL,
    cantidad bigint DEFAULT 0,
    foto bytea,
    estatus character varying(50) NOT NULL
);


ALTER TABLE public.detalle_requerimiento OWNER TO postgres;

--
-- TOC entry 162 (class 1259 OID 89721)
-- Dependencies: 3
-- Name: detalle_requerimiento_id_detalle_requerimiento_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE detalle_requerimiento_id_detalle_requerimiento_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.detalle_requerimiento_id_detalle_requerimiento_seq OWNER TO postgres;

--
-- TOC entry 1935 (class 0 OID 0)
-- Dependencies: 162
-- Name: detalle_requerimiento_id_detalle_requerimiento_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('detalle_requerimiento_id_detalle_requerimiento_seq', 1, false);


--
-- TOC entry 151 (class 1259 OID 89504)
-- Dependencies: 3
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

--
-- TOC entry 1936 (class 0 OID 0)
-- Dependencies: 151
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('hibernate_sequence', 1, true);


--
-- TOC entry 142 (class 1259 OID 89402)
-- Dependencies: 3
-- Name: history_logins; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE history_logins (
    id integer NOT NULL,
    date_login timestamp without time zone,
    date_logout timestamp without time zone,
    username character varying(20) NOT NULL
);


ALTER TABLE public.history_logins OWNER TO postgres;

--
-- TOC entry 153 (class 1259 OID 89663)
-- Dependencies: 1857 3
-- Name: marca_vehiculo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE marca_vehiculo (
    id_marca_vehiculo integer DEFAULT nextval(('Marca_Vehiculo_id_marca_vehiculo_seq'::text)::regclass) NOT NULL,
    nombre character varying(50)
);


ALTER TABLE public.marca_vehiculo OWNER TO postgres;

--
-- TOC entry 152 (class 1259 OID 89661)
-- Dependencies: 3
-- Name: marca_vehiculo_id_marca_vehiculo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE marca_vehiculo_id_marca_vehiculo_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.marca_vehiculo_id_marca_vehiculo_seq OWNER TO postgres;

--
-- TOC entry 1937 (class 0 OID 0)
-- Dependencies: 152
-- Name: marca_vehiculo_id_marca_vehiculo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('marca_vehiculo_id_marca_vehiculo_seq', 1, false);


--
-- TOC entry 165 (class 1259 OID 89778)
-- Dependencies: 3
-- Name: menu; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE menu (
    id_menu integer NOT NULL,
    actividad character varying(255),
    icono character varying(255),
    nombre character varying(255),
    ruta character varying(255),
    id_padre integer
);


ALTER TABLE public.menu OWNER TO postgres;

--
-- TOC entry 164 (class 1259 OID 89776)
-- Dependencies: 165 3
-- Name: menu_id_menu_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE menu_id_menu_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.menu_id_menu_seq OWNER TO postgres;

--
-- TOC entry 1938 (class 0 OID 0)
-- Dependencies: 164
-- Name: menu_id_menu_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE menu_id_menu_seq OWNED BY menu.id_menu;


--
-- TOC entry 1939 (class 0 OID 0)
-- Dependencies: 164
-- Name: menu_id_menu_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('menu_id_menu_seq', 4, true);


--
-- TOC entry 157 (class 1259 OID 89675)
-- Dependencies: 1859 3
-- Name: motor; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE motor (
    id_motor integer DEFAULT nextval(('Motor_id_motor_seq'::text)::regclass) NOT NULL,
    nombre character varying(50) NOT NULL
);


ALTER TABLE public.motor OWNER TO postgres;

--
-- TOC entry 156 (class 1259 OID 89673)
-- Dependencies: 3
-- Name: motor_id_motor_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE motor_id_motor_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.motor_id_motor_seq OWNER TO postgres;

--
-- TOC entry 1940 (class 0 OID 0)
-- Dependencies: 156
-- Name: motor_id_motor_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('motor_id_motor_seq', 1, false);


--
-- TOC entry 143 (class 1259 OID 89423)
-- Dependencies: 3
-- Name: persistent_logins; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE persistent_logins (
    series character varying(64) NOT NULL,
    last_used timestamp without time zone NOT NULL,
    token character varying(64) NOT NULL,
    username character varying(20) NOT NULL
);


ALTER TABLE public.persistent_logins OWNER TO postgres;

--
-- TOC entry 145 (class 1259 OID 89430)
-- Dependencies: 3
-- Name: persona; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE persona (
    id integer NOT NULL,
    apellido character varying(50),
    cedula character varying(255) NOT NULL,
    correo character varying(20),
    direccion character varying(255),
    nombre character varying(50),
    telefono character varying(20)
);


ALTER TABLE public.persona OWNER TO postgres;

--
-- TOC entry 144 (class 1259 OID 89428)
-- Dependencies: 145 3
-- Name: persona_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE persona_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.persona_id_seq OWNER TO postgres;

--
-- TOC entry 1941 (class 0 OID 0)
-- Dependencies: 144
-- Name: persona_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE persona_id_seq OWNED BY persona.id;


--
-- TOC entry 1942 (class 0 OID 0)
-- Dependencies: 144
-- Name: persona_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('persona_id_seq', 1, true);


--
-- TOC entry 146 (class 1259 OID 89439)
-- Dependencies: 3
-- Name: proveedor; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE proveedor (
    estatus character varying(255),
    rif character varying(255),
    id_proveedor integer NOT NULL
);


ALTER TABLE public.proveedor OWNER TO postgres;

--
-- TOC entry 148 (class 1259 OID 89449)
-- Dependencies: 3
-- Name: requerimiento; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE requerimiento (
    id_requerimiento integer NOT NULL,
    anno_v timestamp without time zone,
    estatus character varying(255),
    fecha_cierre timestamp without time zone,
    fecha_creacion timestamp without time zone,
    fecha_vencimiento timestamp without time zone,
    id_marca_v integer,
    id_motor_v integer,
    id_traccion_v integer,
    modelo_v character varying(255),
    serial_carroceria_v character varying(255),
    transmision_v boolean,
    id_analista integer,
    id_cliente integer
);


ALTER TABLE public.requerimiento OWNER TO postgres;

--
-- TOC entry 147 (class 1259 OID 89447)
-- Dependencies: 3 148
-- Name: requerimiento_id_requerimiento_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE requerimiento_id_requerimiento_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.requerimiento_id_requerimiento_seq OWNER TO postgres;

--
-- TOC entry 1943 (class 0 OID 0)
-- Dependencies: 147
-- Name: requerimiento_id_requerimiento_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE requerimiento_id_requerimiento_seq OWNED BY requerimiento.id_requerimiento;


--
-- TOC entry 1944 (class 0 OID 0)
-- Dependencies: 147
-- Name: requerimiento_id_requerimiento_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('requerimiento_id_requerimiento_seq', 1, false);


--
-- TOC entry 159 (class 1259 OID 89702)
-- Dependencies: 1860 3
-- Name: tipo_cliente; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tipo_cliente (
    id_tipo_cliente integer DEFAULT nextval(('Tipo_Cliente_id_tipo_cliente_seq'::text)::regclass) NOT NULL,
    nombre character varying(50)
);


ALTER TABLE public.tipo_cliente OWNER TO postgres;

--
-- TOC entry 158 (class 1259 OID 89700)
-- Dependencies: 3
-- Name: tipo_cliente_id_tipo_cliente_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tipo_cliente_id_tipo_cliente_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tipo_cliente_id_tipo_cliente_seq OWNER TO postgres;

--
-- TOC entry 1945 (class 0 OID 0)
-- Dependencies: 158
-- Name: tipo_cliente_id_tipo_cliente_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tipo_cliente_id_tipo_cliente_seq', 1, false);


--
-- TOC entry 155 (class 1259 OID 89669)
-- Dependencies: 1858 3
-- Name: traccion; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE traccion (
    id_traccion integer DEFAULT nextval(('Traccion_id_traccion_seq'::text)::regclass) NOT NULL,
    nombre character varying(50)
);


ALTER TABLE public.traccion OWNER TO postgres;

--
-- TOC entry 154 (class 1259 OID 89667)
-- Dependencies: 3
-- Name: traccion_id_traccion_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE traccion_id_traccion_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.traccion_id_traccion_seq OWNER TO postgres;

--
-- TOC entry 1946 (class 0 OID 0)
-- Dependencies: 154
-- Name: traccion_id_traccion_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('traccion_id_traccion_seq', 1, false);


--
-- TOC entry 150 (class 1259 OID 89460)
-- Dependencies: 3
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE usuario (
    id integer NOT NULL,
    activo boolean NOT NULL,
    foto bytea,
    pasword character varying(100),
    username character varying(20),
    persona_id integer
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 149 (class 1259 OID 89458)
-- Dependencies: 3 150
-- Name: usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.usuario_id_seq OWNER TO postgres;

--
-- TOC entry 1947 (class 0 OID 0)
-- Dependencies: 149
-- Name: usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE usuario_id_seq OWNED BY usuario.id;


--
-- TOC entry 1948 (class 0 OID 0)
-- Dependencies: 149
-- Name: usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('usuario_id_seq', 2, true);


--
-- TOC entry 1864 (class 2604 OID 89781)
-- Dependencies: 165 164 165
-- Name: id_menu; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY menu ALTER COLUMN id_menu SET DEFAULT nextval('menu_id_menu_seq'::regclass);


--
-- TOC entry 1854 (class 2604 OID 89433)
-- Dependencies: 144 145 145
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY persona ALTER COLUMN id SET DEFAULT nextval('persona_id_seq'::regclass);


--
-- TOC entry 1855 (class 2604 OID 89452)
-- Dependencies: 147 148 148
-- Name: id_requerimiento; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY requerimiento ALTER COLUMN id_requerimiento SET DEFAULT nextval('requerimiento_id_requerimiento_seq'::regclass);


--
-- TOC entry 1856 (class 2604 OID 89463)
-- Dependencies: 150 149 150
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuario ALTER COLUMN id SET DEFAULT nextval('usuario_id_seq'::regclass);


--
-- TOC entry 1914 (class 0 OID 89384)
-- Dependencies: 140
-- Data for Name: analista; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO analista (id_analista) VALUES (1);


--
-- TOC entry 1926 (class 0 OID 89715)
-- Dependencies: 161
-- Data for Name: clasificacion_repuesto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1915 (class 0 OID 89397)
-- Dependencies: 141
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO cliente (id_cliente, id_tipo_cliente) VALUES (6, NULL);


--
-- TOC entry 1927 (class 0 OID 89723)
-- Dependencies: 163
-- Data for Name: detalle_requerimiento; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1916 (class 0 OID 89402)
-- Dependencies: 142
-- Data for Name: history_logins; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO history_logins (id, date_login, date_logout, username) VALUES (1, '2015-03-31 00:38:20.021', '2015-03-31 00:38:20.021', 'euge');


--
-- TOC entry 1922 (class 0 OID 89663)
-- Dependencies: 153
-- Data for Name: marca_vehiculo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1928 (class 0 OID 89778)
-- Dependencies: 165
-- Data for Name: menu; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, id_padre) VALUES (1, NULL, 'z-icon-book', 'Paso 1', '/WEB-INF/views/sistema/funcionalidades/new_file.zul', NULL);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, id_padre) VALUES (2, NULL, 'z-icon-cog', 'Configuracion', '/WEB-INF/views/sistema/funcionalidades/new_file.zul', NULL);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, id_padre) VALUES (3, NULL, 'z-icon-lock', 'Seguridad', NULL, 2);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, id_padre) VALUES (4, NULL, NULL, 'Usuarios', '/WEB-INF/views/sistema/seguridad/configuracion/usuarios/listaUsuarios.zul', 3);


--
-- TOC entry 1924 (class 0 OID 89675)
-- Dependencies: 157
-- Data for Name: motor; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1917 (class 0 OID 89423)
-- Dependencies: 143
-- Data for Name: persistent_logins; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1918 (class 0 OID 89430)
-- Dependencies: 145
-- Data for Name: persona; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO persona (id, apellido, cedula, correo, direccion, nombre, telefono) VALUES (6, NULL, '20186243', 'euge@gmail.com', NULL, 'euge', '123');
INSERT INTO persona (id, apellido, cedula, correo, direccion, nombre, telefono) VALUES (1, 'Caicedo', '20186243', 'euge17@gmail.com', NULL, 'Eugenio', NULL);


--
-- TOC entry 1919 (class 0 OID 89439)
-- Dependencies: 146
-- Data for Name: proveedor; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1920 (class 0 OID 89449)
-- Dependencies: 148
-- Data for Name: requerimiento; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO requerimiento (id_requerimiento, anno_v, estatus, fecha_cierre, fecha_creacion, fecha_vencimiento, id_marca_v, id_motor_v, id_traccion_v, modelo_v, serial_carroceria_v, transmision_v, id_analista, id_cliente) VALUES (7, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'wwww', '123456', NULL, NULL, 6);


--
-- TOC entry 1925 (class 0 OID 89702)
-- Dependencies: 159
-- Data for Name: tipo_cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1923 (class 0 OID 89669)
-- Dependencies: 155
-- Data for Name: traccion; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1921 (class 0 OID 89460)
-- Dependencies: 150
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO usuario (id, activo, foto, pasword, username, persona_id) VALUES (2, true, NULL, '123', 'admin', NULL);
INSERT INTO usuario (id, activo, foto, pasword, username, persona_id) VALUES (1, true, NULL, '147', 'euge', 1);


--
-- TOC entry 1866 (class 2606 OID 89388)
-- Dependencies: 140 140
-- Name: analista_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY analista
    ADD CONSTRAINT analista_pkey PRIMARY KEY (id_analista);


--
-- TOC entry 1868 (class 2606 OID 89401)
-- Dependencies: 141 141
-- Name: cliente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (id_cliente);


--
-- TOC entry 1870 (class 2606 OID 89406)
-- Dependencies: 142 142
-- Name: history_logins_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY history_logins
    ADD CONSTRAINT history_logins_pkey PRIMARY KEY (id);


--
-- TOC entry 1898 (class 2606 OID 89786)
-- Dependencies: 165 165
-- Name: menu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY menu
    ADD CONSTRAINT menu_pkey PRIMARY KEY (id_menu);


--
-- TOC entry 1872 (class 2606 OID 89427)
-- Dependencies: 143 143
-- Name: persistent_logins_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY persistent_logins
    ADD CONSTRAINT persistent_logins_pkey PRIMARY KEY (series);


--
-- TOC entry 1874 (class 2606 OID 89438)
-- Dependencies: 145 145
-- Name: persona_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY persona
    ADD CONSTRAINT persona_pkey PRIMARY KEY (id);


--
-- TOC entry 1892 (class 2606 OID 89720)
-- Dependencies: 161 161
-- Name: pk_clasificacion_repuesto; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY clasificacion_repuesto
    ADD CONSTRAINT pk_clasificacion_repuesto PRIMARY KEY (id_clasificacion_repuesto);


--
-- TOC entry 1896 (class 2606 OID 89734)
-- Dependencies: 163 163
-- Name: pk_detalle_requerimiento; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY detalle_requerimiento
    ADD CONSTRAINT pk_detalle_requerimiento PRIMARY KEY (id_detalle_requerimiento);


--
-- TOC entry 1884 (class 2606 OID 89680)
-- Dependencies: 153 153
-- Name: pk_marca_vehiculo; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY marca_vehiculo
    ADD CONSTRAINT pk_marca_vehiculo PRIMARY KEY (id_marca_vehiculo);


--
-- TOC entry 1888 (class 2606 OID 89684)
-- Dependencies: 157 157
-- Name: pk_motor; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY motor
    ADD CONSTRAINT pk_motor PRIMARY KEY (id_motor);


--
-- TOC entry 1890 (class 2606 OID 89707)
-- Dependencies: 159 159
-- Name: pk_tipo_cliente; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tipo_cliente
    ADD CONSTRAINT pk_tipo_cliente PRIMARY KEY (id_tipo_cliente);


--
-- TOC entry 1886 (class 2606 OID 89682)
-- Dependencies: 155 155
-- Name: pk_traccion; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY traccion
    ADD CONSTRAINT pk_traccion PRIMARY KEY (id_traccion);


--
-- TOC entry 1876 (class 2606 OID 89446)
-- Dependencies: 146 146
-- Name: proveedor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY proveedor
    ADD CONSTRAINT proveedor_pkey PRIMARY KEY (id_proveedor);


--
-- TOC entry 1878 (class 2606 OID 89457)
-- Dependencies: 148 148
-- Name: requerimiento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY requerimiento
    ADD CONSTRAINT requerimiento_pkey PRIMARY KEY (id_requerimiento);


--
-- TOC entry 1880 (class 2606 OID 89468)
-- Dependencies: 150 150
-- Name: usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 1882 (class 2606 OID 89650)
-- Dependencies: 150 150
-- Name: usuario_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_username_key UNIQUE (username);


--
-- TOC entry 1893 (class 1259 OID 89731)
-- Dependencies: 163
-- Name: ixfk_detalle_requerimiento_clasificacion_repuesto; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX ixfk_detalle_requerimiento_clasificacion_repuesto ON detalle_requerimiento USING btree (id_clasificacion_repuesto);


--
-- TOC entry 1894 (class 1259 OID 89732)
-- Dependencies: 163
-- Name: ixfk_detalle_requerimiento_requerimiento; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX ixfk_detalle_requerimiento_requerimiento ON detalle_requerimiento USING btree (id_requerimiento);


--
-- TOC entry 1913 (class 2606 OID 89787)
-- Dependencies: 165 1897 165
-- Name: fk33155fb000c573; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY menu
    ADD CONSTRAINT fk33155fb000c573 FOREIGN KEY (id_padre) REFERENCES menu(id_menu);


--
-- TOC entry 1900 (class 2606 OID 89474)
-- Dependencies: 145 141 1873
-- Name: fk334b85fa72a75f10; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cliente
    ADD CONSTRAINT fk334b85fa72a75f10 FOREIGN KEY (id_cliente) REFERENCES persona(id);


--
-- TOC entry 1901 (class 2606 OID 89708)
-- Dependencies: 159 1889 141
-- Name: fk334b85fab56ffe47; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cliente
    ADD CONSTRAINT fk334b85fab56ffe47 FOREIGN KEY (id_tipo_cliente) REFERENCES tipo_cliente(id_tipo_cliente);


--
-- TOC entry 1912 (class 2606 OID 89740)
-- Dependencies: 1877 148 163
-- Name: fk42c4ba5d1726034; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY detalle_requerimiento
    ADD CONSTRAINT fk42c4ba5d1726034 FOREIGN KEY (id_requerimiento) REFERENCES requerimiento(id_requerimiento);


--
-- TOC entry 1911 (class 2606 OID 89735)
-- Dependencies: 161 1891 163
-- Name: fk_detalle_requerimiento_clasificacion_repuesto; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY detalle_requerimiento
    ADD CONSTRAINT fk_detalle_requerimiento_clasificacion_repuesto FOREIGN KEY (id_clasificacion_repuesto) REFERENCES clasificacion_repuesto(id_clasificacion_repuesto);


--
-- TOC entry 1903 (class 2606 OID 89656)
-- Dependencies: 1881 150 143
-- Name: fkbd224d2a6a01c92; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY persistent_logins
    ADD CONSTRAINT fkbd224d2a6a01c92 FOREIGN KEY (username) REFERENCES usuario(username);


--
-- TOC entry 1899 (class 2606 OID 89469)
-- Dependencies: 145 1873 140
-- Name: fkc2e8ee2fdcbd110d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY analista
    ADD CONSTRAINT fkc2e8ee2fdcbd110d FOREIGN KEY (id_analista) REFERENCES persona(id);


--
-- TOC entry 1905 (class 2606 OID 89489)
-- Dependencies: 148 1865 140
-- Name: fkd19e472517870034; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY requerimiento
    ADD CONSTRAINT fkd19e472517870034 FOREIGN KEY (id_analista) REFERENCES analista(id_analista);


--
-- TOC entry 1908 (class 2606 OID 89690)
-- Dependencies: 148 157 1887
-- Name: fkd19e47257973088b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY requerimiento
    ADD CONSTRAINT fkd19e47257973088b FOREIGN KEY (id_motor_v) REFERENCES motor(id_motor);


--
-- TOC entry 1909 (class 2606 OID 89695)
-- Dependencies: 155 148 1885
-- Name: fkd19e4725a790a957; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY requerimiento
    ADD CONSTRAINT fkd19e4725a790a957 FOREIGN KEY (id_traccion_v) REFERENCES traccion(id_traccion);


--
-- TOC entry 1907 (class 2606 OID 89685)
-- Dependencies: 1883 148 153
-- Name: fkd19e4725b1f9d9de; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY requerimiento
    ADD CONSTRAINT fkd19e4725b1f9d9de FOREIGN KEY (id_marca_v) REFERENCES marca_vehiculo(id_marca_vehiculo);


--
-- TOC entry 1906 (class 2606 OID 89494)
-- Dependencies: 1867 148 141
-- Name: fkd19e4725ce63155e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY requerimiento
    ADD CONSTRAINT fkd19e4725ce63155e FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente);


--
-- TOC entry 1904 (class 2606 OID 89484)
-- Dependencies: 1873 145 146
-- Name: fkdf24cade6d89dcf4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY proveedor
    ADD CONSTRAINT fkdf24cade6d89dcf4 FOREIGN KEY (id_proveedor) REFERENCES persona(id);


--
-- TOC entry 1902 (class 2606 OID 89651)
-- Dependencies: 142 1881 150
-- Name: fkee0d8835a6a01c92; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY history_logins
    ADD CONSTRAINT fkee0d8835a6a01c92 FOREIGN KEY (username) REFERENCES usuario(username);


--
-- TOC entry 1910 (class 2606 OID 89644)
-- Dependencies: 1873 150 145
-- Name: fkf814f32ebe6ae2c8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT fkf814f32ebe6ae2c8 FOREIGN KEY (persona_id) REFERENCES persona(id);


--
-- TOC entry 1933 (class 0 OID 0)
-- Dependencies: 3
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2015-03-31 00:39:51

--
-- PostgreSQL database dump complete
--

