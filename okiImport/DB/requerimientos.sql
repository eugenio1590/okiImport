--
-- PostgreSQL database dump
--

-- Started on 2015-04-09 22:56:12

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 521 (class 2612 OID 16386)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--

CREATE PROCEDURAL LANGUAGE plpgsql;


ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 140 (class 1259 OID 92542)
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
-- TOC entry 141 (class 1259 OID 92547)
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
-- TOC entry 155 (class 1259 OID 92698)
-- Dependencies: 3
-- Name: clasificacion_repuesto_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE clasificacion_repuesto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.clasificacion_repuesto_id_seq OWNER TO postgres;

--
-- TOC entry 1917 (class 0 OID 0)
-- Dependencies: 155
-- Name: clasificacion_repuesto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('clasificacion_repuesto_id_seq', 1, false);


--
-- TOC entry 142 (class 1259 OID 92555)
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
-- TOC entry 143 (class 1259 OID 92560)
-- Dependencies: 3
-- Name: detalle_requerimiento; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE detalle_requerimiento (
    id_detalle_requerimiento integer NOT NULL,
    cantidad bigint,
    codigo_oem character varying(255),
    descripcion character varying(255),
    estatus character varying(255),
    foto bytea,
    nombre character varying(255),
    id_clasificacion_repuesto integer,
    id_requerimiento integer
);


ALTER TABLE public.detalle_requerimiento OWNER TO postgres;

--
-- TOC entry 156 (class 1259 OID 92700)
-- Dependencies: 3
-- Name: detalle_requerimiento_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE detalle_requerimiento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.detalle_requerimiento_id_seq OWNER TO postgres;

--
-- TOC entry 1918 (class 0 OID 0)
-- Dependencies: 156
-- Name: detalle_requerimiento_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('detalle_requerimiento_id_seq', 1, false);


--
-- TOC entry 144 (class 1259 OID 92568)
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
-- TOC entry 157 (class 1259 OID 92702)
-- Dependencies: 3
-- Name: history_logins_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE history_logins_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.history_logins_id_seq OWNER TO postgres;

--
-- TOC entry 1919 (class 0 OID 0)
-- Dependencies: 157
-- Name: history_logins_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('history_logins_id_seq', 2, true);


--
-- TOC entry 145 (class 1259 OID 92573)
-- Dependencies: 3
-- Name: marca_repuesto; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE marca_repuesto (
    id_marca_repuesto integer NOT NULL,
    nombre character varying(255)
);


ALTER TABLE public.marca_repuesto OWNER TO postgres;

--
-- TOC entry 158 (class 1259 OID 92704)
-- Dependencies: 3
-- Name: marca_repuesto_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE marca_repuesto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.marca_repuesto_id_seq OWNER TO postgres;

--
-- TOC entry 1920 (class 0 OID 0)
-- Dependencies: 158
-- Name: marca_repuesto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('marca_repuesto_id_seq', 1, false);


--
-- TOC entry 146 (class 1259 OID 92578)
-- Dependencies: 3
-- Name: marca_vehiculo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE marca_vehiculo (
    id_marca_vehiculo integer NOT NULL,
    nombre character varying(255)
);


ALTER TABLE public.marca_vehiculo OWNER TO postgres;

--
-- TOC entry 159 (class 1259 OID 92706)
-- Dependencies: 3
-- Name: marca_vehiculo_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE marca_vehiculo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.marca_vehiculo_id_seq OWNER TO postgres;

--
-- TOC entry 1921 (class 0 OID 0)
-- Dependencies: 159
-- Name: marca_vehiculo_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('marca_vehiculo_id_seq', 1, false);


--
-- TOC entry 147 (class 1259 OID 92583)
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
-- TOC entry 160 (class 1259 OID 92708)
-- Dependencies: 3
-- Name: menu_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE menu_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.menu_id_seq OWNER TO postgres;

--
-- TOC entry 1922 (class 0 OID 0)
-- Dependencies: 160
-- Name: menu_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('menu_id_seq', 1, false);


--
-- TOC entry 148 (class 1259 OID 92591)
-- Dependencies: 3
-- Name: motor; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE motor (
    id_motor integer NOT NULL,
    nombre character varying(255)
);


ALTER TABLE public.motor OWNER TO postgres;

--
-- TOC entry 161 (class 1259 OID 92710)
-- Dependencies: 3
-- Name: motor_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE motor_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.motor_id_seq OWNER TO postgres;

--
-- TOC entry 1923 (class 0 OID 0)
-- Dependencies: 161
-- Name: motor_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('motor_id_seq', 1, false);


--
-- TOC entry 149 (class 1259 OID 92596)
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
-- TOC entry 150 (class 1259 OID 92601)
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
-- TOC entry 162 (class 1259 OID 92712)
-- Dependencies: 3
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
-- TOC entry 1924 (class 0 OID 0)
-- Dependencies: 162
-- Name: persona_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('persona_id_seq', 4, false);


--
-- TOC entry 151 (class 1259 OID 92609)
-- Dependencies: 3
-- Name: proveedor; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE proveedor (
    estatus character varying(255),
    id_proveedor integer NOT NULL
);


ALTER TABLE public.proveedor OWNER TO postgres;

--
-- TOC entry 152 (class 1259 OID 92614)
-- Dependencies: 3
-- Name: proveedor_marca_repuesto; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE proveedor_marca_repuesto (
    id_proveedor integer NOT NULL,
    id_marca_repuesto integer NOT NULL
);


ALTER TABLE public.proveedor_marca_repuesto OWNER TO postgres;

--
-- TOC entry 153 (class 1259 OID 92617)
-- Dependencies: 3
-- Name: requerimiento; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE requerimiento (
    id_requerimiento integer NOT NULL,
    anno_v timestamp without time zone,
    estatus character varying(255),
    fecha_cierre date,
    fecha_creacion date,
    fecha_vencimiento date,
    modelo_v character varying(255),
    serial_carroceria_v character varying(255),
    traccion_v boolean,
    transmision_v boolean,
    id_analista integer,
    id_cliente integer,
    id_marca_v integer,
    id_motor_v integer
);


ALTER TABLE public.requerimiento OWNER TO postgres;

--
-- TOC entry 163 (class 1259 OID 92714)
-- Dependencies: 3
-- Name: requerimiento_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE requerimiento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.requerimiento_id_seq OWNER TO postgres;

--
-- TOC entry 1925 (class 0 OID 0)
-- Dependencies: 163
-- Name: requerimiento_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('requerimiento_id_seq', 1, false);


--
-- TOC entry 154 (class 1259 OID 92625)
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
-- TOC entry 164 (class 1259 OID 92716)
-- Dependencies: 3
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
-- TOC entry 1926 (class 0 OID 0)
-- Dependencies: 164
-- Name: usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('usuario_id_seq', 2, false);


--
-- TOC entry 1897 (class 0 OID 92542)
-- Dependencies: 140
-- Data for Name: analista; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO analista (administrador, estatus, id_analista) VALUES (NULL, NULL, 2);
INSERT INTO analista (administrador, estatus, id_analista) VALUES (NULL, NULL, 4);


--
-- TOC entry 1898 (class 0 OID 92547)
-- Dependencies: 141
-- Data for Name: clasificacion_repuesto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1899 (class 0 OID 92555)
-- Dependencies: 142
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO cliente (estatus, juridico, id_cliente) VALUES (NULL, NULL, 1);
INSERT INTO cliente (estatus, juridico, id_cliente) VALUES (NULL, NULL, 3);


--
-- TOC entry 1900 (class 0 OID 92560)
-- Dependencies: 143
-- Data for Name: detalle_requerimiento; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1901 (class 0 OID 92568)
-- Dependencies: 144
-- Data for Name: history_logins; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO history_logins (id, date_login, date_logout, username) VALUES (1, '2015-04-09 22:35:16.51', '2015-04-09 22:35:16.51', 'euge');
INSERT INTO history_logins (id, date_login, date_logout, username) VALUES (2, '2015-04-09 22:35:16.51', '2015-04-09 22:35:16.51', 'euge');


--
-- TOC entry 1902 (class 0 OID 92573)
-- Dependencies: 145
-- Data for Name: marca_repuesto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1903 (class 0 OID 92578)
-- Dependencies: 146
-- Data for Name: marca_vehiculo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1904 (class 0 OID 92583)
-- Dependencies: 147
-- Data for Name: menu; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, id_padre) VALUES (3, NULL, 'z-icon-lock', 'Seguridad', NULL, 2);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, id_padre) VALUES (4, NULL, NULL, 'Usuarios', '/WEB-INF/views/sistema/seguridad/configuracion/usuarios/listaUsuarios.zul', 3);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, id_padre) VALUES (1, NULL, 'z-icon-book', 'Paso 1', '/WEB-INF/views/sistema/funcionalidades/listaMisRequerimientos.zul', NULL);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, id_padre) VALUES (2, NULL, 'z-icon-cog', 'Configuracion', NULL, NULL);


--
-- TOC entry 1905 (class 0 OID 92591)
-- Dependencies: 148
-- Data for Name: motor; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1906 (class 0 OID 92596)
-- Dependencies: 149
-- Data for Name: persistent_logins; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1907 (class 0 OID 92601)
-- Dependencies: 150
-- Data for Name: persona; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO persona (id, apellido, cedula, correo, direccion, nombre, telefono) VALUES (1, NULL, '20186243', 'euge@gmail.com', NULL, 'EUGENIO CAICEDO', '2528291');
INSERT INTO persona (id, apellido, cedula, correo, direccion, nombre, telefono) VALUES (2, NULL, '4578124', 'maria@gmail.com', NULL, 'MARIA', '784512');
INSERT INTO persona (id, apellido, cedula, correo, direccion, nombre, telefono) VALUES (3, NULL, '123456', 'luis@gmail.com', NULL, 'LUIS', '457896');
INSERT INTO persona (id, apellido, cedula, correo, direccion, nombre, telefono) VALUES (4, NULL, '19547896', 'p.fer@gmail.com', NULL, 'FERNANDO', '457896');


--
-- TOC entry 1908 (class 0 OID 92609)
-- Dependencies: 151
-- Data for Name: proveedor; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1909 (class 0 OID 92614)
-- Dependencies: 152
-- Data for Name: proveedor_marca_repuesto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1910 (class 0 OID 92617)
-- Dependencies: 153
-- Data for Name: requerimiento; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1911 (class 0 OID 92625)
-- Dependencies: 154
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO usuario (id, activo, foto, pasword, username, persona_id) VALUES (1, true, NULL, '147', 'euge', 2);
INSERT INTO usuario (id, activo, foto, pasword, username, persona_id) VALUES (2, true, NULL, '123', 'admin', 4);


--
-- TOC entry 1853 (class 2606 OID 92546)
-- Dependencies: 140 140
-- Name: analista_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY analista
    ADD CONSTRAINT analista_pkey PRIMARY KEY (id_analista);


--
-- TOC entry 1855 (class 2606 OID 92554)
-- Dependencies: 141 141
-- Name: clasificacion_repuesto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY clasificacion_repuesto
    ADD CONSTRAINT clasificacion_repuesto_pkey PRIMARY KEY (id_clasificacion_repuesto);


--
-- TOC entry 1857 (class 2606 OID 92559)
-- Dependencies: 142 142
-- Name: cliente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (id_cliente);


--
-- TOC entry 1859 (class 2606 OID 92567)
-- Dependencies: 143 143
-- Name: detalle_requerimiento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY detalle_requerimiento
    ADD CONSTRAINT detalle_requerimiento_pkey PRIMARY KEY (id_detalle_requerimiento);


--
-- TOC entry 1861 (class 2606 OID 92572)
-- Dependencies: 144 144
-- Name: history_logins_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY history_logins
    ADD CONSTRAINT history_logins_pkey PRIMARY KEY (id);


--
-- TOC entry 1863 (class 2606 OID 92577)
-- Dependencies: 145 145
-- Name: marca_repuesto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY marca_repuesto
    ADD CONSTRAINT marca_repuesto_pkey PRIMARY KEY (id_marca_repuesto);


--
-- TOC entry 1865 (class 2606 OID 92582)
-- Dependencies: 146 146
-- Name: marca_vehiculo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY marca_vehiculo
    ADD CONSTRAINT marca_vehiculo_pkey PRIMARY KEY (id_marca_vehiculo);


--
-- TOC entry 1867 (class 2606 OID 92590)
-- Dependencies: 147 147
-- Name: menu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY menu
    ADD CONSTRAINT menu_pkey PRIMARY KEY (id_menu);


--
-- TOC entry 1869 (class 2606 OID 92595)
-- Dependencies: 148 148
-- Name: motor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY motor
    ADD CONSTRAINT motor_pkey PRIMARY KEY (id_motor);


--
-- TOC entry 1871 (class 2606 OID 92600)
-- Dependencies: 149 149
-- Name: persistent_logins_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY persistent_logins
    ADD CONSTRAINT persistent_logins_pkey PRIMARY KEY (series);


--
-- TOC entry 1873 (class 2606 OID 92608)
-- Dependencies: 150 150
-- Name: persona_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY persona
    ADD CONSTRAINT persona_pkey PRIMARY KEY (id);


--
-- TOC entry 1875 (class 2606 OID 92613)
-- Dependencies: 151 151
-- Name: proveedor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY proveedor
    ADD CONSTRAINT proveedor_pkey PRIMARY KEY (id_proveedor);


--
-- TOC entry 1877 (class 2606 OID 92624)
-- Dependencies: 153 153
-- Name: requerimiento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY requerimiento
    ADD CONSTRAINT requerimiento_pkey PRIMARY KEY (id_requerimiento);


--
-- TOC entry 1879 (class 2606 OID 92632)
-- Dependencies: 154 154
-- Name: usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 1881 (class 2606 OID 92720)
-- Dependencies: 154 154
-- Name: usuario_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_username_key UNIQUE (username);


--
-- TOC entry 1887 (class 2606 OID 92653)
-- Dependencies: 1866 147 147
-- Name: fk33155fb000c573; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY menu
    ADD CONSTRAINT fk33155fb000c573 FOREIGN KEY (id_padre) REFERENCES menu(id_menu);


--
-- TOC entry 1883 (class 2606 OID 92638)
-- Dependencies: 142 150 1872
-- Name: fk334b85fa72a75f10; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cliente
    ADD CONSTRAINT fk334b85fa72a75f10 FOREIGN KEY (id_cliente) REFERENCES persona(id);


--
-- TOC entry 1885 (class 2606 OID 92648)
-- Dependencies: 1876 153 143
-- Name: fk42c4ba5d1726034; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY detalle_requerimiento
    ADD CONSTRAINT fk42c4ba5d1726034 FOREIGN KEY (id_requerimiento) REFERENCES requerimiento(id_requerimiento);


--
-- TOC entry 1884 (class 2606 OID 92643)
-- Dependencies: 141 1854 143
-- Name: fk42c4ba5d9195f9f3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY detalle_requerimiento
    ADD CONSTRAINT fk42c4ba5d9195f9f3 FOREIGN KEY (id_clasificacion_repuesto) REFERENCES clasificacion_repuesto(id_clasificacion_repuesto);


--
-- TOC entry 1891 (class 2606 OID 92668)
-- Dependencies: 1874 152 151
-- Name: fk8b0d75456338eca6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY proveedor_marca_repuesto
    ADD CONSTRAINT fk8b0d75456338eca6 FOREIGN KEY (id_proveedor) REFERENCES proveedor(id_proveedor);


--
-- TOC entry 1890 (class 2606 OID 92663)
-- Dependencies: 152 145 1862
-- Name: fk8b0d7545d92110f3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY proveedor_marca_repuesto
    ADD CONSTRAINT fk8b0d7545d92110f3 FOREIGN KEY (id_marca_repuesto) REFERENCES marca_repuesto(id_marca_repuesto);


--
-- TOC entry 1888 (class 2606 OID 92726)
-- Dependencies: 1880 154 149
-- Name: fkbd224d2a6a01c92; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY persistent_logins
    ADD CONSTRAINT fkbd224d2a6a01c92 FOREIGN KEY (username) REFERENCES usuario(username);


--
-- TOC entry 1882 (class 2606 OID 92633)
-- Dependencies: 140 1872 150
-- Name: fkc2e8ee2fdcbd110d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY analista
    ADD CONSTRAINT fkc2e8ee2fdcbd110d FOREIGN KEY (id_analista) REFERENCES persona(id);


--
-- TOC entry 1892 (class 2606 OID 92673)
-- Dependencies: 1852 140 153
-- Name: fkd19e472517870034; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY requerimiento
    ADD CONSTRAINT fkd19e472517870034 FOREIGN KEY (id_analista) REFERENCES analista(id_analista);


--
-- TOC entry 1895 (class 2606 OID 92688)
-- Dependencies: 1868 148 153
-- Name: fkd19e47257973088b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY requerimiento
    ADD CONSTRAINT fkd19e47257973088b FOREIGN KEY (id_motor_v) REFERENCES motor(id_motor);


--
-- TOC entry 1894 (class 2606 OID 92683)
-- Dependencies: 153 1864 146
-- Name: fkd19e4725b1f9d9de; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY requerimiento
    ADD CONSTRAINT fkd19e4725b1f9d9de FOREIGN KEY (id_marca_v) REFERENCES marca_vehiculo(id_marca_vehiculo);


--
-- TOC entry 1893 (class 2606 OID 92678)
-- Dependencies: 153 1856 142
-- Name: fkd19e4725ce63155e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY requerimiento
    ADD CONSTRAINT fkd19e4725ce63155e FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente);


--
-- TOC entry 1889 (class 2606 OID 92658)
-- Dependencies: 151 150 1872
-- Name: fkdf24cade6d89dcf4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY proveedor
    ADD CONSTRAINT fkdf24cade6d89dcf4 FOREIGN KEY (id_proveedor) REFERENCES persona(id);


--
-- TOC entry 1886 (class 2606 OID 92721)
-- Dependencies: 144 154 1880
-- Name: fkee0d8835a6a01c92; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY history_logins
    ADD CONSTRAINT fkee0d8835a6a01c92 FOREIGN KEY (username) REFERENCES usuario(username);


--
-- TOC entry 1896 (class 2606 OID 92693)
-- Dependencies: 150 1872 154
-- Name: fkf814f32ebe6ae2c8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT fkf814f32ebe6ae2c8 FOREIGN KEY (persona_id) REFERENCES persona(id);


--
-- TOC entry 1916 (class 0 OID 0)
-- Dependencies: 3
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2015-04-09 22:56:12

--
-- PostgreSQL database dump complete
--

