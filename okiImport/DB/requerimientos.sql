--
-- PostgreSQL database dump
--

-- Started on 2015-04-07 15:29:43

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 510 (class 2612 OID 16386)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--

CREATE PROCEDURAL LANGUAGE plpgsql;


ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 140 (class 1259 OID 90778)
-- Dependencies: 3
-- Name: analista; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE analista (
    administrador boolean,
    estatus character varying(50),
    id_analista integer NOT NULL
);


ALTER TABLE public.analista OWNER TO postgres;

--
-- TOC entry 142 (class 1259 OID 90785)
-- Dependencies: 3
-- Name: clasificacion_repuesto; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE clasificacion_repuesto (
    id_clasificacion_repuesto integer NOT NULL,
    descripcion character varying(255),
    estatus character varying(255)
);


ALTER TABLE public.clasificacion_repuesto OWNER TO postgres;

--
-- TOC entry 141 (class 1259 OID 90783)
-- Dependencies: 142 3
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
-- TOC entry 1905 (class 0 OID 0)
-- Dependencies: 141
-- Name: clasificacion_repuesto_id_clasificacion_repuesto_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE clasificacion_repuesto_id_clasificacion_repuesto_seq OWNED BY clasificacion_repuesto.id_clasificacion_repuesto;


--
-- TOC entry 1906 (class 0 OID 0)
-- Dependencies: 141
-- Name: clasificacion_repuesto_id_clasificacion_repuesto_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('clasificacion_repuesto_id_clasificacion_repuesto_seq', 1, false);


--
-- TOC entry 143 (class 1259 OID 90794)
-- Dependencies: 3
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cliente (
    estatus character varying(255),
    juridico boolean,
    id_cliente integer NOT NULL
);


ALTER TABLE public.cliente OWNER TO postgres;

--
-- TOC entry 144 (class 1259 OID 90799)
-- Dependencies: 3
-- Name: detalle_requerimiento; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE detalle_requerimiento (
    id_detalle_requerimiento integer NOT NULL,
    cantidad bigint,
    codigo_oem character varying(255),
    estatus character varying(255),
    foto bytea,
    nombre character varying(255),
    id_clasificacion_repuesto integer,
    id_requerimiento integer
);


ALTER TABLE public.detalle_requerimiento OWNER TO postgres;

--
-- TOC entry 159 (class 1259 OID 90944)
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
-- TOC entry 1907 (class 0 OID 0)
-- Dependencies: 159
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('hibernate_sequence', 1, false);


--
-- TOC entry 145 (class 1259 OID 90807)
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
-- TOC entry 146 (class 1259 OID 90812)
-- Dependencies: 3
-- Name: marca_vehiculo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE marca_vehiculo (
    id_marca_vehiculo integer NOT NULL,
    nombre character varying(255)
);


ALTER TABLE public.marca_vehiculo OWNER TO postgres;

--
-- TOC entry 147 (class 1259 OID 90817)
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
-- TOC entry 148 (class 1259 OID 90825)
-- Dependencies: 3
-- Name: motor; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE motor (
    id_motor integer NOT NULL,
    nombre character varying(255)
);


ALTER TABLE public.motor OWNER TO postgres;

--
-- TOC entry 149 (class 1259 OID 90830)
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
-- TOC entry 151 (class 1259 OID 90837)
-- Dependencies: 3
-- Name: persona; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE persona (
    id integer NOT NULL,
    apellido character varying(255),
    cedula character varying(255) NOT NULL,
    correo character varying(255),
    direccion character varying(255),
    nombre character varying(255),
    telefono character varying(255)
);


ALTER TABLE public.persona OWNER TO postgres;

--
-- TOC entry 150 (class 1259 OID 90835)
-- Dependencies: 3 151
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
-- TOC entry 1908 (class 0 OID 0)
-- Dependencies: 150
-- Name: persona_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE persona_id_seq OWNED BY persona.id;


--
-- TOC entry 1909 (class 0 OID 0)
-- Dependencies: 150
-- Name: persona_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('persona_id_seq', 14, true);


--
-- TOC entry 152 (class 1259 OID 90846)
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
-- TOC entry 155 (class 1259 OID 90858)
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
    modelo_v character varying(255),
    serial_carroceria_v character varying(255),
    transmision_v boolean,
    id_analista integer NOT NULL,
    id_cliente integer NOT NULL,
    id_marca_v integer,
    id_motor_v integer,
    id_traccion_v integer
);


ALTER TABLE public.requerimiento OWNER TO postgres;

--
-- TOC entry 153 (class 1259 OID 90854)
-- Dependencies: 3 155
-- Name: requerimiento_id_analista_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE requerimiento_id_analista_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.requerimiento_id_analista_seq OWNER TO postgres;

--
-- TOC entry 1910 (class 0 OID 0)
-- Dependencies: 153
-- Name: requerimiento_id_analista_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE requerimiento_id_analista_seq OWNED BY requerimiento.id_analista;


--
-- TOC entry 1911 (class 0 OID 0)
-- Dependencies: 153
-- Name: requerimiento_id_analista_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('requerimiento_id_analista_seq', 1, false);


--
-- TOC entry 154 (class 1259 OID 90856)
-- Dependencies: 155 3
-- Name: requerimiento_id_cliente_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE requerimiento_id_cliente_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.requerimiento_id_cliente_seq OWNER TO postgres;

--
-- TOC entry 1912 (class 0 OID 0)
-- Dependencies: 154
-- Name: requerimiento_id_cliente_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE requerimiento_id_cliente_seq OWNED BY requerimiento.id_cliente;


--
-- TOC entry 1913 (class 0 OID 0)
-- Dependencies: 154
-- Name: requerimiento_id_cliente_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('requerimiento_id_cliente_seq', 1, false);


--
-- TOC entry 156 (class 1259 OID 90868)
-- Dependencies: 3
-- Name: traccion; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE traccion (
    id_traccion integer NOT NULL,
    nombre character varying(255)
);


ALTER TABLE public.traccion OWNER TO postgres;

--
-- TOC entry 158 (class 1259 OID 90875)
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
-- TOC entry 157 (class 1259 OID 90873)
-- Dependencies: 3 158
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
-- TOC entry 1914 (class 0 OID 0)
-- Dependencies: 157
-- Name: usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE usuario_id_seq OWNED BY usuario.id;


--
-- TOC entry 1915 (class 0 OID 0)
-- Dependencies: 157
-- Name: usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('usuario_id_seq', 1, false);


--
-- TOC entry 1841 (class 2604 OID 90788)
-- Dependencies: 142 141 142
-- Name: id_clasificacion_repuesto; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY clasificacion_repuesto ALTER COLUMN id_clasificacion_repuesto SET DEFAULT nextval('clasificacion_repuesto_id_clasificacion_repuesto_seq'::regclass);


--
-- TOC entry 1842 (class 2604 OID 90840)
-- Dependencies: 151 150 151
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY persona ALTER COLUMN id SET DEFAULT nextval('persona_id_seq'::regclass);


--
-- TOC entry 1843 (class 2604 OID 90861)
-- Dependencies: 153 155 155
-- Name: id_analista; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY requerimiento ALTER COLUMN id_analista SET DEFAULT nextval('requerimiento_id_analista_seq'::regclass);


--
-- TOC entry 1844 (class 2604 OID 90862)
-- Dependencies: 154 155 155
-- Name: id_cliente; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY requerimiento ALTER COLUMN id_cliente SET DEFAULT nextval('requerimiento_id_cliente_seq'::regclass);


--
-- TOC entry 1845 (class 2604 OID 90878)
-- Dependencies: 157 158 158
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuario ALTER COLUMN id SET DEFAULT nextval('usuario_id_seq'::regclass);


--
-- TOC entry 1886 (class 0 OID 90778)
-- Dependencies: 140
-- Data for Name: analista; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO analista (administrador, estatus, id_analista) VALUES (NULL, NULL, 1);
INSERT INTO analista (administrador, estatus, id_analista) VALUES (NULL, NULL, 9);


--
-- TOC entry 1887 (class 0 OID 90785)
-- Dependencies: 142
-- Data for Name: clasificacion_repuesto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1888 (class 0 OID 90794)
-- Dependencies: 143
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO cliente (estatus, juridico, id_cliente) VALUES (NULL, NULL, 9);
INSERT INTO cliente (estatus, juridico, id_cliente) VALUES (NULL, NULL, 10);
INSERT INTO cliente (estatus, juridico, id_cliente) VALUES (NULL, NULL, 12);
INSERT INTO cliente (estatus, juridico, id_cliente) VALUES (NULL, NULL, 14);


--
-- TOC entry 1889 (class 0 OID 90799)
-- Dependencies: 144
-- Data for Name: detalle_requerimiento; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1890 (class 0 OID 90807)
-- Dependencies: 145
-- Data for Name: history_logins; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO history_logins (id, date_login, date_logout, username) VALUES (1, '2015-03-31 00:38:20.021', '2015-03-31 00:38:20.021', 'euge');
INSERT INTO history_logins (id, date_login, date_logout, username) VALUES (17, '2015-03-31 20:18:48.427', '2015-04-07 15:28:17.832', 'euge');


--
-- TOC entry 1891 (class 0 OID 90812)
-- Dependencies: 146
-- Data for Name: marca_vehiculo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1892 (class 0 OID 90817)
-- Dependencies: 147
-- Data for Name: menu; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, id_padre) VALUES (1, NULL, 'z-icon-book', 'Paso 1', '/WEB-INF/views/sistema/funcionalidades/new_file.zul', NULL);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, id_padre) VALUES (2, NULL, 'z-icon-cog', 'Configuracion', '/WEB-INF/views/sistema/funcionalidades/new_file.zul', NULL);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, id_padre) VALUES (3, NULL, 'z-icon-lock', 'Seguridad', NULL, 2);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, id_padre) VALUES (4, NULL, NULL, 'Usuarios', '/WEB-INF/views/sistema/seguridad/configuracion/usuarios/listaUsuarios.zul', 3);


--
-- TOC entry 1893 (class 0 OID 90825)
-- Dependencies: 148
-- Data for Name: motor; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1894 (class 0 OID 90830)
-- Dependencies: 149
-- Data for Name: persistent_logins; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1895 (class 0 OID 90837)
-- Dependencies: 151
-- Data for Name: persona; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO persona (id, apellido, cedula, correo, direccion, nombre, telefono) VALUES (1, 'Caicedo', '20186243', 'euge17@gmail.com', NULL, 'Eugenio', NULL);
INSERT INTO persona (id, apellido, cedula, correo, direccion, nombre, telefono) VALUES (10, NULL, '201869243', 'd@gm.com', NULL, 'dddd', '47852');
INSERT INTO persona (id, apellido, cedula, correo, direccion, nombre, telefono) VALUES (12, NULL, '18934677', 'J@gmail.com', NULL, 'JOSE CAICEDO', '0251252227');
INSERT INTO persona (id, apellido, cedula, correo, direccion, nombre, telefono) VALUES (14, NULL, '7412354', 'fer@hot.com', NULL, 'fernando', '78541');
INSERT INTO persona (id, apellido, cedula, correo, direccion, nombre, telefono) VALUES (9, NULL, '20186243', 'euge@g.com', NULL, 'euge', '47854');


--
-- TOC entry 1896 (class 0 OID 90846)
-- Dependencies: 152
-- Data for Name: proveedor; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1897 (class 0 OID 90858)
-- Dependencies: 155
-- Data for Name: requerimiento; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO requerimiento (id_requerimiento, anno_v, estatus, fecha_cierre, fecha_creacion, fecha_vencimiento, modelo_v, serial_carroceria_v, transmision_v, id_analista, id_cliente, id_marca_v, id_motor_v, id_traccion_v) VALUES (11, NULL, 'CR', NULL, NULL, NULL, 'asfa', 'asf', NULL, 1, 9, NULL, NULL, NULL);
INSERT INTO requerimiento (id_requerimiento, anno_v, estatus, fecha_cierre, fecha_creacion, fecha_vencimiento, modelo_v, serial_carroceria_v, transmision_v, id_analista, id_cliente, id_marca_v, id_motor_v, id_traccion_v) VALUES (13, NULL, 'CR', NULL, NULL, NULL, 'ford', '212121', NULL, 1, 12, NULL, NULL, NULL);
INSERT INTO requerimiento (id_requerimiento, anno_v, estatus, fecha_cierre, fecha_creacion, fecha_vencimiento, modelo_v, serial_carroceria_v, transmision_v, id_analista, id_cliente, id_marca_v, id_motor_v, id_traccion_v) VALUES (15, NULL, 'CR', NULL, NULL, NULL, 'FORD', '7854', NULL, 9, 14, NULL, NULL, NULL);
INSERT INTO requerimiento (id_requerimiento, anno_v, estatus, fecha_cierre, fecha_creacion, fecha_vencimiento, modelo_v, serial_carroceria_v, transmision_v, id_analista, id_cliente, id_marca_v, id_motor_v, id_traccion_v) VALUES (16, NULL, 'CR', NULL, '2015-03-31 19:55:18.87', NULL, 'FORD', '1234', NULL, 1, 9, NULL, NULL, NULL);


--
-- TOC entry 1898 (class 0 OID 90868)
-- Dependencies: 156
-- Data for Name: traccion; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1899 (class 0 OID 90875)
-- Dependencies: 158
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO usuario (id, activo, foto, pasword, username, persona_id) VALUES (2, true, NULL, '123', 'admin', NULL);
INSERT INTO usuario (id, activo, foto, pasword, username, persona_id) VALUES (1, true, NULL, '147', 'euge', 1);


--
-- TOC entry 1847 (class 2606 OID 90782)
-- Dependencies: 140 140
-- Name: analista_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY analista
    ADD CONSTRAINT analista_pkey PRIMARY KEY (id_analista);


--
-- TOC entry 1849 (class 2606 OID 90793)
-- Dependencies: 142 142
-- Name: clasificacion_repuesto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY clasificacion_repuesto
    ADD CONSTRAINT clasificacion_repuesto_pkey PRIMARY KEY (id_clasificacion_repuesto);


--
-- TOC entry 1851 (class 2606 OID 90798)
-- Dependencies: 143 143
-- Name: cliente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (id_cliente);


--
-- TOC entry 1853 (class 2606 OID 90806)
-- Dependencies: 144 144
-- Name: detalle_requerimiento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY detalle_requerimiento
    ADD CONSTRAINT detalle_requerimiento_pkey PRIMARY KEY (id_detalle_requerimiento);


--
-- TOC entry 1855 (class 2606 OID 90811)
-- Dependencies: 145 145
-- Name: history_logins_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY history_logins
    ADD CONSTRAINT history_logins_pkey PRIMARY KEY (id);


--
-- TOC entry 1857 (class 2606 OID 90816)
-- Dependencies: 146 146
-- Name: marca_vehiculo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY marca_vehiculo
    ADD CONSTRAINT marca_vehiculo_pkey PRIMARY KEY (id_marca_vehiculo);


--
-- TOC entry 1859 (class 2606 OID 90824)
-- Dependencies: 147 147
-- Name: menu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY menu
    ADD CONSTRAINT menu_pkey PRIMARY KEY (id_menu);


--
-- TOC entry 1861 (class 2606 OID 90829)
-- Dependencies: 148 148
-- Name: motor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY motor
    ADD CONSTRAINT motor_pkey PRIMARY KEY (id_motor);


--
-- TOC entry 1863 (class 2606 OID 90834)
-- Dependencies: 149 149
-- Name: persistent_logins_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY persistent_logins
    ADD CONSTRAINT persistent_logins_pkey PRIMARY KEY (series);


--
-- TOC entry 1865 (class 2606 OID 90845)
-- Dependencies: 151 151
-- Name: persona_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY persona
    ADD CONSTRAINT persona_pkey PRIMARY KEY (id);


--
-- TOC entry 1867 (class 2606 OID 90853)
-- Dependencies: 152 152
-- Name: proveedor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY proveedor
    ADD CONSTRAINT proveedor_pkey PRIMARY KEY (id_proveedor);


--
-- TOC entry 1869 (class 2606 OID 90867)
-- Dependencies: 155 155
-- Name: requerimiento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY requerimiento
    ADD CONSTRAINT requerimiento_pkey PRIMARY KEY (id_requerimiento);


--
-- TOC entry 1871 (class 2606 OID 90872)
-- Dependencies: 156 156
-- Name: traccion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY traccion
    ADD CONSTRAINT traccion_pkey PRIMARY KEY (id_traccion);


--
-- TOC entry 1873 (class 2606 OID 90883)
-- Dependencies: 158 158
-- Name: usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 1878 (class 2606 OID 90904)
-- Dependencies: 1858 147 147
-- Name: fk33155fb000c573; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY menu
    ADD CONSTRAINT fk33155fb000c573 FOREIGN KEY (id_padre) REFERENCES menu(id_menu);


--
-- TOC entry 1875 (class 2606 OID 90889)
-- Dependencies: 151 143 1864
-- Name: fk334b85fa72a75f10; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cliente
    ADD CONSTRAINT fk334b85fa72a75f10 FOREIGN KEY (id_cliente) REFERENCES persona(id);


--
-- TOC entry 1877 (class 2606 OID 90899)
-- Dependencies: 1868 144 155
-- Name: fk42c4ba5d1726034; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY detalle_requerimiento
    ADD CONSTRAINT fk42c4ba5d1726034 FOREIGN KEY (id_requerimiento) REFERENCES requerimiento(id_requerimiento);


--
-- TOC entry 1876 (class 2606 OID 90894)
-- Dependencies: 1848 142 144
-- Name: fk42c4ba5d9195f9f3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY detalle_requerimiento
    ADD CONSTRAINT fk42c4ba5d9195f9f3 FOREIGN KEY (id_clasificacion_repuesto) REFERENCES clasificacion_repuesto(id_clasificacion_repuesto);


--
-- TOC entry 1874 (class 2606 OID 90884)
-- Dependencies: 151 140 1864
-- Name: fkc2e8ee2fdcbd110d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY analista
    ADD CONSTRAINT fkc2e8ee2fdcbd110d FOREIGN KEY (id_analista) REFERENCES persona(id);


--
-- TOC entry 1880 (class 2606 OID 90914)
-- Dependencies: 1846 155 140
-- Name: fkd19e472517870034; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY requerimiento
    ADD CONSTRAINT fkd19e472517870034 FOREIGN KEY (id_analista) REFERENCES analista(id_analista);


--
-- TOC entry 1883 (class 2606 OID 90929)
-- Dependencies: 1860 148 155
-- Name: fkd19e47257973088b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY requerimiento
    ADD CONSTRAINT fkd19e47257973088b FOREIGN KEY (id_motor_v) REFERENCES motor(id_motor);


--
-- TOC entry 1884 (class 2606 OID 90934)
-- Dependencies: 1870 155 156
-- Name: fkd19e4725a790a957; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY requerimiento
    ADD CONSTRAINT fkd19e4725a790a957 FOREIGN KEY (id_traccion_v) REFERENCES traccion(id_traccion);


--
-- TOC entry 1882 (class 2606 OID 90924)
-- Dependencies: 1856 155 146
-- Name: fkd19e4725b1f9d9de; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY requerimiento
    ADD CONSTRAINT fkd19e4725b1f9d9de FOREIGN KEY (id_marca_v) REFERENCES marca_vehiculo(id_marca_vehiculo);


--
-- TOC entry 1881 (class 2606 OID 90919)
-- Dependencies: 155 143 1850
-- Name: fkd19e4725ce63155e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY requerimiento
    ADD CONSTRAINT fkd19e4725ce63155e FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente);


--
-- TOC entry 1879 (class 2606 OID 90909)
-- Dependencies: 1864 151 152
-- Name: fkdf24cade6d89dcf4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY proveedor
    ADD CONSTRAINT fkdf24cade6d89dcf4 FOREIGN KEY (id_proveedor) REFERENCES persona(id);


--
-- TOC entry 1885 (class 2606 OID 90939)
-- Dependencies: 151 158 1864
-- Name: fkf814f32ebe6ae2c8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT fkf814f32ebe6ae2c8 FOREIGN KEY (persona_id) REFERENCES persona(id);


--
-- TOC entry 1904 (class 0 OID 0)
-- Dependencies: 3
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2015-04-07 15:29:43

--
-- PostgreSQL database dump complete
--

