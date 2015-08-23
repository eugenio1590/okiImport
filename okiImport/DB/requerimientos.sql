--
-- PostgreSQL database dump
--

-- Dumped from database version 8.4.12
-- Dumped by pg_dump version 9.4.0
-- Started on 2015-08-22 20:47:06

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 603 (class 2612 OID 16386)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--

CREATE OR REPLACE PROCEDURAL LANGUAGE plpgsql;


ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 140 (class 1259 OID 108446)
-- Name: analista; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE analista (
    administrador boolean,
    id_analista integer NOT NULL
);


ALTER TABLE analista OWNER TO postgres;

--
-- TOC entry 141 (class 1259 OID 108451)
-- Name: banco; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE banco (
    id_banco integer NOT NULL,
    estatus character varying(255),
    nombre character varying(255)
);


ALTER TABLE banco OWNER TO postgres;

--
-- TOC entry 169 (class 1259 OID 108783)
-- Name: banco_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE banco_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE banco_id_seq OWNER TO postgres;

--
-- TOC entry 142 (class 1259 OID 108459)
-- Name: ciudad; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ciudad (
    id_ciudad integer NOT NULL,
    nombre character varying(255),
    id_estado integer
);


ALTER TABLE ciudad OWNER TO postgres;

--
-- TOC entry 143 (class 1259 OID 108464)
-- Name: clasificacion_repuesto; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE clasificacion_repuesto (
    id_clasificacion_repuesto integer NOT NULL,
    descripcion character varying(255),
    estatus character varying(255)
);


ALTER TABLE clasificacion_repuesto OWNER TO postgres;

--
-- TOC entry 170 (class 1259 OID 108785)
-- Name: clasificacion_repuesto_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE clasificacion_repuesto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE clasificacion_repuesto_id_seq OWNER TO postgres;

--
-- TOC entry 144 (class 1259 OID 108472)
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cliente (
    juridico boolean,
    id_cliente integer NOT NULL
);


ALTER TABLE cliente OWNER TO postgres;

--
-- TOC entry 145 (class 1259 OID 108477)
-- Name: compra; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE compra (
    id_compra integer NOT NULL,
    fecha_creacion timestamp without time zone,
    observacion character varying(255),
    precio_flete real,
    precio_venta real,
    id_historico_moneda integer,
    id_requerimiento integer
);


ALTER TABLE compra OWNER TO postgres;

--
-- TOC entry 171 (class 1259 OID 108787)
-- Name: compra_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE compra_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE compra_id_seq OWNER TO postgres;

--
-- TOC entry 146 (class 1259 OID 108482)
-- Name: configuracion; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE configuracion (
    id_configuracion integer NOT NULL,
    porct_ganancia real,
    porct_iva real,
    valor_libra real
);


ALTER TABLE configuracion OWNER TO postgres;

--
-- TOC entry 147 (class 1259 OID 108487)
-- Name: cotizacion; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cotizacion (
    id_cotizacion integer NOT NULL,
    condiciones character varying(255),
    estatus character varying(255),
    fecha_creacion timestamp without time zone,
    fecha_vencimiento timestamp without time zone,
    mensaje character varying(255),
    precio_flete real,
    id_historico_moneda integer,
    id_proveedor integer
);


ALTER TABLE cotizacion OWNER TO postgres;

--
-- TOC entry 172 (class 1259 OID 108789)
-- Name: cotizacion_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE cotizacion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE cotizacion_id_seq OWNER TO postgres;

--
-- TOC entry 148 (class 1259 OID 108495)
-- Name: detalle_cotizacion; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE detalle_cotizacion (
    id_detalle_cotizacion integer NOT NULL,
    cantidad bigint,
    estatus character varying(255),
    marca_repuesto character varying(255),
    precio_flete real,
    precio_venta real,
    id_cotizacion integer,
    id_detalle_requerimiento integer
);


ALTER TABLE detalle_cotizacion OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 108791)
-- Name: detalle_cotizacion_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE detalle_cotizacion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE detalle_cotizacion_id_seq OWNER TO postgres;

--
-- TOC entry 149 (class 1259 OID 108503)
-- Name: detalle_cotizacion_internacional; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE detalle_cotizacion_internacional (
    alto real,
    ancho real,
    forma_envio boolean,
    largo real,
    peso real,
    tipo_flete boolean,
    valor_libra real,
    id_detalle_cotizacion_internacional integer NOT NULL
);


ALTER TABLE detalle_cotizacion_internacional OWNER TO postgres;

--
-- TOC entry 150 (class 1259 OID 108508)
-- Name: detalle_oferta; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE detalle_oferta (
    id_detalle_oferta integer NOT NULL,
    estatus character varying(255),
    id_compra integer,
    id_detalle_cotizacion integer,
    id_oferta integer
);


ALTER TABLE detalle_oferta OWNER TO postgres;

--
-- TOC entry 174 (class 1259 OID 108793)
-- Name: detalle_oferta_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE detalle_oferta_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE detalle_oferta_id_seq OWNER TO postgres;

--
-- TOC entry 151 (class 1259 OID 108513)
-- Name: detalle_requerimiento; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE detalle_requerimiento (
    id_detalle_requerimiento integer NOT NULL,
    cantidad bigint,
    codigo_oem character varying(255),
    descripcion character varying(255),
    estatus character varying(255),
    foto bytea,
    id_clasificacion_repuesto integer,
    id_requerimiento integer
);


ALTER TABLE detalle_requerimiento OWNER TO postgres;

--
-- TOC entry 175 (class 1259 OID 108795)
-- Name: detalle_requerimiento_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE detalle_requerimiento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE detalle_requerimiento_id_seq OWNER TO postgres;

--
-- TOC entry 152 (class 1259 OID 108521)
-- Name: estado; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE estado (
    id_estado integer NOT NULL,
    nombre character varying(255)
);


ALTER TABLE estado OWNER TO postgres;

--
-- TOC entry 153 (class 1259 OID 108526)
-- Name: forma_pago; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE forma_pago (
    id_forma_pago integer NOT NULL,
    nombre character varying(255)
);


ALTER TABLE forma_pago OWNER TO postgres;

--
-- TOC entry 176 (class 1259 OID 108797)
-- Name: forma_pago_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE forma_pago_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE forma_pago_id_seq OWNER TO postgres;

--
-- TOC entry 177 (class 1259 OID 108799)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hibernate_sequence OWNER TO postgres;

--
-- TOC entry 154 (class 1259 OID 108531)
-- Name: historico_moneda; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE historico_moneda (
    id_historia integer NOT NULL,
    fecha_creacion timestamp without time zone,
    monto_conversion real,
    id_moneda integer
);


ALTER TABLE historico_moneda OWNER TO postgres;

--
-- TOC entry 178 (class 1259 OID 108801)
-- Name: historico_moneda_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE historico_moneda_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE historico_moneda_id_seq OWNER TO postgres;

--
-- TOC entry 155 (class 1259 OID 108536)
-- Name: history_logins; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE history_logins (
    id integer NOT NULL,
    date_login timestamp without time zone,
    date_logout timestamp without time zone,
    username character varying(20) NOT NULL
);


ALTER TABLE history_logins OWNER TO postgres;

--
-- TOC entry 179 (class 1259 OID 108803)
-- Name: history_logins_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE history_logins_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE history_logins_id_seq OWNER TO postgres;

--
-- TOC entry 156 (class 1259 OID 108541)
-- Name: marca_vehiculo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE marca_vehiculo (
    id_marca_vehiculo integer NOT NULL,
    estatus character varying(255),
    nombre character varying(255)
);


ALTER TABLE marca_vehiculo OWNER TO postgres;

--
-- TOC entry 180 (class 1259 OID 108805)
-- Name: marca_vehiculo_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE marca_vehiculo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE marca_vehiculo_id_seq OWNER TO postgres;

--
-- TOC entry 157 (class 1259 OID 108549)
-- Name: menu; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE menu (
    id_menu integer NOT NULL,
    actividad character varying(255),
    icono character varying(255),
    nombre character varying(255),
    ruta character varying(255),
    tipo integer,
    id_padre integer
);


ALTER TABLE menu OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 108807)
-- Name: menu_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE menu_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE menu_id_seq OWNER TO postgres;

--
-- TOC entry 158 (class 1259 OID 108557)
-- Name: moneda; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE moneda (
    id_moneda integer NOT NULL,
    estatus character varying(255),
    nombre character varying(255),
    pais boolean,
    simbolo character varying(255)
);


ALTER TABLE moneda OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 108809)
-- Name: moneda_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE moneda_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE moneda_id_seq OWNER TO postgres;

--
-- TOC entry 159 (class 1259 OID 108565)
-- Name: motor; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE motor (
    id_motor integer NOT NULL,
    nombre character varying(255)
);


ALTER TABLE motor OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 108811)
-- Name: motor_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE motor_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE motor_id_seq OWNER TO postgres;

--
-- TOC entry 160 (class 1259 OID 108570)
-- Name: oferta; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE oferta (
    id_oferta integer NOT NULL,
    estatus character varying(255),
    fecha_creacion timestamp without time zone,
    porct_ganancia real,
    porct_iva real
);


ALTER TABLE oferta OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 108813)
-- Name: oferta_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE oferta_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE oferta_id_seq OWNER TO postgres;

--
-- TOC entry 161 (class 1259 OID 108575)
-- Name: pago_compra; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE pago_compra (
    id_pago_compra integer NOT NULL,
    estatus character varying(255),
    fecha_creacion timestamp without time zone,
    fecha_pago timestamp without time zone,
    monto real,
    nro_deposito character varying(255),
    id_banco integer,
    id_compra integer,
    id_forma_pago integer,
    id_persona integer
);


ALTER TABLE pago_compra OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 108815)
-- Name: pago_compra_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE pago_compra_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pago_compra_id_seq OWNER TO postgres;

--
-- TOC entry 162 (class 1259 OID 108583)
-- Name: persistent_logins; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE persistent_logins (
    series character varying(64) NOT NULL,
    last_used timestamp without time zone NOT NULL,
    token character varying(64) NOT NULL,
    username character varying(20) NOT NULL
);


ALTER TABLE persistent_logins OWNER TO postgres;

--
-- TOC entry 163 (class 1259 OID 108588)
-- Name: persona; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE persona (
    id integer NOT NULL,
    apellido character varying(255),
    cedula character varying(255) NOT NULL,
    correo character varying(255),
    direccion character varying(255),
    estatus character varying(50),
    nombre character varying(255),
    telefono character varying(255),
    tipo_menu integer,
    id_ciudad integer
);


ALTER TABLE persona OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 108817)
-- Name: persona_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE persona_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE persona_id_seq OWNER TO postgres;

--
-- TOC entry 164 (class 1259 OID 108596)
-- Name: proveedor; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE proveedor (
    tipo_proveedor boolean,
    id_proveedor integer NOT NULL
);


ALTER TABLE proveedor OWNER TO postgres;

--
-- TOC entry 165 (class 1259 OID 108601)
-- Name: proveedor_clasificacion_repuesto; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE proveedor_clasificacion_repuesto (
    id_proveedor integer NOT NULL,
    id_clasificacion_repuesto integer NOT NULL
);


ALTER TABLE proveedor_clasificacion_repuesto OWNER TO postgres;

--
-- TOC entry 166 (class 1259 OID 108604)
-- Name: proveedor_marca_vehiculo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE proveedor_marca_vehiculo (
    id_proveedor integer NOT NULL,
    id_marca_vehiculo integer NOT NULL
);


ALTER TABLE proveedor_marca_vehiculo OWNER TO postgres;

--
-- TOC entry 167 (class 1259 OID 108607)
-- Name: requerimiento; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE requerimiento (
    id_requerimiento integer NOT NULL,
    anno_v integer,
    estatus character varying(255),
    fecha_cierre date,
    fecha_creacion date,
    fecha_solicitud timestamp without time zone,
    fecha_vencimiento date,
    modelo_v character varying(255),
    serial_carroceria_v character varying(255),
    tipo_repuesto boolean,
    traccion_v boolean,
    transmision_v boolean,
    id_analista integer,
    id_cliente integer,
    id_marca_v integer,
    id_motor_v integer
);


ALTER TABLE requerimiento OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 108819)
-- Name: requerimiento_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE requerimiento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE requerimiento_id_seq OWNER TO postgres;

--
-- TOC entry 168 (class 1259 OID 108615)
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


ALTER TABLE usuario OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 108821)
-- Name: usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE usuario_id_seq OWNER TO postgres;

--
-- TOC entry 2024 (class 0 OID 108446)
-- Dependencies: 140
-- Data for Name: analista; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO analista (administrador, id_analista) VALUES (true, 1);
INSERT INTO analista (administrador, id_analista) VALUES (false, 2);


--
-- TOC entry 2025 (class 0 OID 108451)
-- Dependencies: 141
-- Data for Name: banco; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2080 (class 0 OID 0)
-- Dependencies: 169
-- Name: banco_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('banco_id_seq', 1, false);


--
-- TOC entry 2026 (class 0 OID 108459)
-- Dependencies: 142
-- Data for Name: ciudad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (1, 'Maroa', 1);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (2, 'Puerto Ayacucho', 1);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (3, 'San Fernando De Atabapo', 1);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (4, 'Anaco', 2);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (5, 'Aragua De Barcelona', 2);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (6, 'Barcelona', 2);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (7, 'Boca De Uchire', 2);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (8, 'Cantaura', 2);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (9, 'Clarines', 2);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (10, 'El Chaparro', 2);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (11, 'El Pao Anzoategui', 2);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (12, 'El Tigre', 2);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (13, 'El Tigrito', 2);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (14, 'Guanape', 2);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (15, 'Guanta', 2);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (16, 'Lecherias', 2);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (17, 'Onoto', 2);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (18, 'Pariaguan', 2);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (19, 'PÃ­ritu', 2);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (20, 'Puerto La Cruz', 2);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (21, 'Puerto PÃ­ritu', 2);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (22, 'Sabana De Uchire', 2);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (23, 'San Mateo Anzoategui', 2);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (24, 'San Pablo Anzoategui', 2);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (25, 'San Tome', 2);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (26, 'Santa Ana De Anzoategui', 2);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (27, 'Santa Fe Anzoategui', 2);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (28, 'Santa Rosa', 2);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (29, 'Soledad', 2);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (30, 'Urica', 2);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (31, 'Valle De Guanape', 2);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (43, 'Achaguas', 3);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (44, 'Biruaca', 3);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (45, 'Bruzual', 3);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (46, 'El Amparo', 3);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (47, 'El Nula', 3);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (48, 'Elorza', 3);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (49, 'Guasdualito', 3);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (50, 'Mantecal', 3);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (51, 'Puerto Paez', 3);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (52, 'San Fernando De Apure', 3);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (53, 'San Juan De Payara', 3);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (54, 'Barbacoas', 4);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (55, 'Cagua', 4);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (56, 'Camatagua', 4);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (58, 'Choroni', 4);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (59, 'Colonia Tovar', 4);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (60, 'El Consejo', 4);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (61, 'La Victoria', 4);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (62, 'Las Tejerias', 4);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (63, 'Magdaleno', 4);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (64, 'Maracay', 4);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (65, 'Ocumare De La Costa', 4);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (66, 'Palo Negro', 4);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (67, 'San Casimiro', 4);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (68, 'San Mateo', 4);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (69, 'San Sebastian', 4);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (70, 'Santa Cruz De Aragua', 4);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (71, 'Tocoron', 4);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (72, 'Turmero', 4);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (73, 'Villa De Cura', 4);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (74, 'Zuata', 5);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (75, 'Barinas', 5);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (76, 'Barinitas', 5);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (77, 'Barrancas', 5);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (78, 'Calderas', 5);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (79, 'Capitanejo', 5);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (80, 'Ciudad Bolivia', 5);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (81, 'El Canton', 5);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (82, 'Las Veguitas', 5);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (83, 'Libertad Barinas', 5);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (84, 'Sabaneta', 5);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (85, 'Santa Barbara De Barinas', 5);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (86, 'Socopo', 5);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (87, 'Caicara Del Orinoco', 6);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (88, 'Canaima', 6);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (89, 'Ciudad Bolivar', 6);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (90, 'Ciudad Piar', 6);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (91, 'El Callao', 6);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (92, 'El Dorado', 6);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (93, 'El Manteco', 6);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (94, 'El Palmar Bolivar', 6);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (95, 'El Pao Bolivar', 6);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (96, 'Guasipati', 6);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (97, 'Guri', 6);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (98, 'La Paragua', 6);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (99, 'Matanzas', 6);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (100, 'Puerto Ordaz', 6);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (101, 'San Felix', 6);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (102, 'Santa Elena De Uairen', 6);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (103, 'Tumeremo', 6);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (104, 'Unare', 6);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (105, 'Upata', 6);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (106, 'Bejuma', 7);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (107, 'Belen', 7);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (108, 'Campo De Carabobo', 7);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (109, 'Canoabo', 7);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (110, 'Central Tacarigua', 7);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (111, 'Chirgua', 7);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (112, 'Ciudad Alianza', 7);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (113, 'El Palito', 7);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (114, 'Guacara', 7);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (115, 'Guigue', 7);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (116, 'Las Trincheras', 7);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (117, 'Los Guayos', 7);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (118, 'Mariara', 7);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (119, 'Miranda', 7);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (120, 'Montalban', 7);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (121, 'Moron', 7);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (122, 'Naguanagua', 7);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (123, 'Puerto Cabello', 7);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (124, 'San Joaquin', 7);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (125, 'Tocuyito', 7);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (126, 'Urama', 7);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (127, 'Valencia', 7);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (128, 'Vigirimita', 7);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (129, 'Aguirre', 8);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (130, 'Apartaderos Cojedes', 8);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (131, 'Arismendi', 8);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (132, 'Camuriquito', 8);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (133, 'El Baul', 8);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (134, 'El Limon', 8);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (135, 'El Pao Cojedes', 8);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (136, 'Hato El Socorro', 8);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (137, 'La Aguadita', 8);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (138, 'Las Vegas', 8);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (139, 'Libertad Cojedes', 8);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (140, 'Mapuey', 8);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (141, 'PiÃ±edo', 8);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (142, 'Samancito', 8);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (143, 'San Carlos', 8);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (144, 'Sucre', 8);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (145, 'Tinaco', 8);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (146, 'Tinaquillo', 8);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (147, 'Vallecito', 8);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (148, 'Tucupita', 9);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (149, 'Caracas', 24);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (150, 'El Junquito', 24);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (151, 'Adicora', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (152, 'Boca De Aroa', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (153, 'Cabure', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (154, 'Capadare', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (155, 'Capatarida', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (156, 'Chichiriviche', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (157, 'Churuguara', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (158, 'Coro', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (159, 'Cumarebo', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (160, 'Dabajuro', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (161, 'Judibana', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (162, 'La Cruz De Taratara', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (163, 'La Vela De Coro', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (164, 'Los Taques', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (165, 'Maparari', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (166, 'Mene De Mauroa', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (167, 'Mirimire', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (168, 'Pedregal', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (169, 'PÃ­ritu Falcon', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (170, 'Pueblo Nuevo Falcon', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (171, 'Puerto Cumarebo', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (172, 'Punta Cardon', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (173, 'Punto Fijo', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (174, 'San Juan De Los Cayos', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (175, 'San Luis', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (176, 'Santa Ana Falcon', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (177, 'Santa Cruz De Bucaral', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (178, 'Tocopero', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (179, 'Tocuyo De La Costa', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (180, 'Tucacas', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (181, 'Yaracal', 10);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (182, 'Altagracia De Orituco', 11);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (183, 'Cabruta', 11);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (184, 'Calabozo', 11);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (185, 'Camaguan', 11);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (196, 'Chaguaramas Guarico', 11);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (197, 'El Socorro', 11);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (198, 'El Sombrero', 11);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (199, 'Las Mercedes De Los Llanos', 11);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (200, 'Lezama', 11);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (201, 'Onoto', 11);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (202, 'Ortiz', 11);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (203, 'San Jose De Guaribe', 11);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (204, 'San Juan De Los Morros', 11);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (205, 'San Rafael De Laya', 11);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (206, 'Santa Maria De Ipire', 11);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (207, 'Tucupido', 11);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (208, 'Valle De La Pascua', 11);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (209, 'Zaraza', 11);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (210, 'Aguada Grande', 12);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (211, 'Atarigua', 12);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (212, 'Barquisimeto', 12);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (213, 'Bobare', 12);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (214, 'Cabudare', 12);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (215, 'Carora', 12);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (216, 'Cubiro', 12);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (217, 'Cuji', 12);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (218, 'Duaca', 12);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (219, 'El Manzano', 12);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (220, 'El Tocuyo', 12);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (221, 'Guarico', 12);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (222, 'Humocaro Alto', 12);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (223, 'Humocaro Bajo', 12);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (224, 'La Miel', 12);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (225, 'Moroturo', 12);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (226, 'Quibor', 12);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (227, 'Rio Claro', 12);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (228, 'Sanare', 12);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (229, 'Santa Ines', 12);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (230, 'Sarare', 12);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (231, 'Siquisique', 12);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (232, 'Tintorero', 12);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (233, 'Apartaderos Merida', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (234, 'Arapuey', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (235, 'Bailadores', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (236, 'Caja Seca', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (237, 'CanaguÃ¡', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (238, 'Chachopo', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (239, 'Chiguara', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (240, 'Ejido', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (241, 'El Vigia', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (242, 'La Azulita', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (243, 'La Playa', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (244, 'Lagunillas Merida', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (245, 'Merida', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (246, 'Mesa De Bolivar', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (247, 'MucuchÃ­es', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (248, 'Mucujepe', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (249, 'Mucuruba', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (250, 'Nueva Bolivia', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (251, 'Palmarito', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (252, 'Pueblo Llano', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (253, 'Santa Cruz De Mora', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (254, 'Santa Elena De Arenales', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (255, 'Santo Domingo', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (256, 'Tabay', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (257, 'Timotes', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (258, 'Torondoy', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (259, 'Tovar', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (260, 'TucanÃ­', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (261, 'Zea', 13);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (262, 'Araguita', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (263, 'Carrizal', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (264, 'Caucagua', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (265, 'Chaguaramas Miranda', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (266, 'Charallave', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (267, 'Chirimena', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (268, 'Chuspa', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (269, 'CÃºa', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (270, 'CÃºpira', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (271, 'Curiepe', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (272, 'El Guapo', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (273, 'El Jarillo', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (274, 'Filas De Mariche', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (275, 'Guarenas', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (276, 'Guatire', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (277, 'Higuerote', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (278, 'Los Anaucos', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (279, 'Los Teques', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (280, 'Ocumare Del Tuy', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (281, 'Panaquire', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (282, 'Paracotos', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (283, 'Rio Chico', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (284, 'San Antonio De Los Altos', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (285, 'San Diego De Los Altos', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (286, 'San Fernando Del Guapo', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (287, 'San Francisco De Yare', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (288, 'San Jose De Los Altos', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (289, 'San Jose De Rio Chico', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (290, 'San Pedro De Los Altos', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (291, 'Santa Lucia', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (292, 'Santa Teresa', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (293, 'Tacarigua De La Laguna', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (295, 'Tacata', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (296, 'Turumo', 14);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (297, 'Aguasay', 15);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (298, 'Aragua De Maturin', 15);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (299, 'Barrancas Del Orinoco', 15);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (300, 'Caicara De Maturin', 15);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (301, 'Caripe', 15);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (302, 'Caripito', 15);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (303, 'Chaguaramal', 15);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (304, 'Chaguaramal', 15);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (305, 'Chaguaramas Monagas', 15);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (306, 'El Furial', 15);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (307, 'El Furrial', 15);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (308, 'El Tejero', 15);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (309, 'Jusepin', 15);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (310, 'La Toscana', 15);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (311, 'Maturin', 15);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (312, 'Miraflores', 15);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (313, 'Punta De Mata', 15);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (314, 'Quiriquire', 15);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (315, 'San Antonio De Maturin', 15);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (316, 'San Vicente Monagas', 15);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (317, 'Santa Barbara', 15);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (318, 'Temblador', 15);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (319, 'TeresÃ©n', 15);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (320, 'Uracoa', 15);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (321, 'Altagracia', 16);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (322, 'Boca De Pozo', 16);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (323, 'Boca De Rio', 16);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (324, 'El Espinal', 16);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (325, 'El Valle Del Espiritu Santo', 16);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (326, 'El Yaque', 16);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (327, 'Juangriego', 16);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (328, 'La Asuncion', 16);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (329, 'La Guardia', 16);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (330, 'Pampatar', 16);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (331, 'Porlamar', 16);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (332, 'Puerto Fermin', 16);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (333, 'Punta De Piedras', 16);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (334, 'San Francisco De Macanao', 16);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (335, 'San Juan Bautista', 16);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (336, 'San Pedro De Coche', 16);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (337, 'Santa Ana De Nueva Esparta', 16);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (338, 'Villa Rosa', 16);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (339, 'Acarigua', 17);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (340, 'Agua Blanca', 17);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (341, 'Araure', 17);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (342, 'Biscucuy', 17);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (343, 'Boconoito', 17);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (344, 'Campo ElÃ­as', 17);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (345, 'Chabasquen', 17);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (346, 'Guanare', 17);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (347, 'Guanarito', 17);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (348, 'La Aparicion', 17);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (349, 'La Mision', 17);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (350, 'Mesa De Cavaca', 17);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (351, 'Ospino', 17);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (352, 'Papelon', 17);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (353, 'Payara', 17);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (354, 'Pimpinela', 17);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (355, 'PÃ­ritu Portuguesa', 17);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (356, 'San Rafael De Onoto', 17);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (357, 'Santa Rosalia', 17);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (358, 'Turen', 17);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (359, 'Altos De Sucre', 18);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (360, 'Araya', 18);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (361, 'Cariaco', 18);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (362, 'Carupano', 18);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (363, 'Casanay', 18);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (364, 'Cumana', 18);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (365, 'Cumanacoa', 18);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (366, 'El Morro Puerto Santo', 18);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (367, 'El Pilar', 18);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (368, 'El Poblado', 18);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (369, 'Guaca', 18);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (370, 'Guiria', 18);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (371, 'Irapa', 18);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (372, 'Manicuare', 18);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (373, 'Mariguitar', 18);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (374, 'Rio Caribe', 18);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (375, 'San Antonio Del Golfo', 18);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (376, 'San Jose De Aerocuar', 18);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (377, 'San Vicente Sucre', 18);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (378, 'Santa Fe Sucre', 18);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (379, 'Tunapuy', 18);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (380, 'Yaguaraparo', 18);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (381, 'Yoco', 18);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (382, 'Abejales', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (383, 'Borota', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (384, 'Bramon', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (385, 'Capacho', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (386, 'Colon', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (387, 'Coloncito', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (388, 'Cordero', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (389, 'El Cobre', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (390, 'El Pinal', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (391, 'Independencia', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (392, 'La FrÃ­a', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (393, 'La Grita', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (394, 'La Pedrera', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (395, 'La Tendida', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (396, 'Las Delicias', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (397, 'Las Hernandez', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (398, 'Lobatera', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (399, 'Michelena', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (400, 'Palmira', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (401, 'Pregonero', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (402, 'Queniquea', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (403, 'Rubio', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (404, 'San Antonio Del Tachira', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (405, 'San CristÃ³bal', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (406, 'San Jose De Bolivar', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (407, 'San Josecito', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (408, 'San Pedro Del Rio', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (409, 'Santa Ana TÃ¡chira', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (410, 'Seboruco', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (411, 'Tariba', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (412, 'Umuquena', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (413, 'UreÃ±a', 19);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (414, 'Batatal', 20);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (415, 'Betijoque', 20);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (416, 'BoconÃ³', 20);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (417, 'Carache', 20);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (418, 'ChejendÃ©', 20);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (419, 'Cuicas', 20);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (420, 'El Dividive', 20);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (421, 'El Jaguito', 20);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (422, 'Escuque', 20);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (423, 'Isnotu', 20);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (424, 'Jajo', 20);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (425, 'La Ceiba', 20);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (426, 'La Concepcion Trujllo', 20);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (427, 'La Mesa De Esnujaque', 20);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (428, 'La Puerta', 20);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (429, 'La Quebrada', 20);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (430, 'Mendoza Fria', 20);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (431, 'Meseta De Chimpire', 20);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (432, 'Monay', 20);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (433, 'Motatan', 20);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (434, 'PampÃ¡n', 20);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (435, 'Pampanito', 20);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (436, 'Sabana De Mendoza', 20);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (437, 'San Lazaro', 20);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (438, 'Santa Ana Trujillo', 20);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (439, 'Tostos', 20);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (440, 'Trujillo', 20);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (441, 'Valera', 20);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (442, 'Carayaca', 21);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (443, 'Litoral', 21);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (444, 'ArchipiÃ©lago Los Roques', 25);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (445, 'Aroa', 22);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (446, 'Boraure', 22);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (447, 'Campo ElÃ­as Yaracuy', 22);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (448, 'Chivacoa', 22);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (449, 'Cocorote', 22);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (450, 'Farriar', 22);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (451, 'Guama', 22);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (452, 'Marin', 22);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (453, 'Nirgua', 22);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (454, 'Sabana De Parra', 22);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (455, 'Salom', 22);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (456, 'San Felipe', 22);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (457, 'San Pablo Yaracuy', 22);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (458, 'Urachiche', 22);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (459, 'Yaritagua', 22);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (460, 'Yumare', 22);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (461, 'Bachaquero', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (462, 'Bobures', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (463, 'Cabimas', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (464, 'Campo Concepcion', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (465, 'Campo Mara', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (466, 'Campo Rojo', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (467, 'Carrasquero', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (468, 'Casigua', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (469, 'Chiquinquira', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (470, 'Ciudad Ojeda', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (471, 'El Batey', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (472, 'El Carmelo', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (473, 'El Chivo', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (474, 'El Guayabo', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (475, 'El Mene', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (476, 'El Venado', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (477, 'Encontrados', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (478, 'Gibraltar', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (479, 'Isla De Toas', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (480, 'La Concepcion Zulia', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (481, 'La Paz', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (482, 'La Sierrita', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (483, 'Lagunillas Zulia', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (484, 'Las Piedras De Perija', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (485, 'Los Cortijos', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (486, 'Machiques', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (487, 'Maracaibo', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (488, 'Mene Grande', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (489, 'Palmarejo', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (490, 'Paraguaipoa', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (491, 'Potrerito', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (492, 'Pueblo Nuevo Zulia', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (493, 'Los Puertos De Altagracia', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (494, 'Punta Gorda', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (495, 'Sabaneta De Palma', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (496, 'San Francisco', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (497, 'San Jose De Perija', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (498, 'San Rafael Del MojÃ¡n', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (499, 'San Timoteo', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (500, 'Santa Barbara Del Zulia', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (501, 'Santa Cruz De Mara', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (502, 'Santa Cruz Del Zulia', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (503, 'Santa Rita', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (504, 'Sinamaica', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (505, 'Tamare', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (506, 'Tia Juana', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (507, 'Villa Del Rosario', 23);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (508, 'La Guaira', 21);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (509, 'Catia La Mar', 21);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (510, 'Macuto', 21);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (511, 'Naiguata', 21);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (512, 'Archipielago Los Monjes', 25);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (513, 'Isla La Tortuga y Cayos adyacentes', 25);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (514, 'Isla La Sola', 25);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (515, 'Islas Los Testigos', 25);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (516, 'Islas Los Frailes', 25);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (517, 'Isla La Orchila', 25);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (518, 'ArchipiÃ©lago Las Aves', 25);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (519, 'Isla de Aves', 25);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (520, 'Isla La Blanquilla', 25);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (521, 'Isla de Patos', 25);
INSERT INTO ciudad (id_ciudad, nombre, id_estado) VALUES (522, 'Islas Los Hermanos', 25);


--
-- TOC entry 2027 (class 0 OID 108464)
-- Dependencies: 143
-- Data for Name: clasificacion_repuesto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO clasificacion_repuesto (id_clasificacion_repuesto, descripcion, estatus) VALUES (1, 'Motor', 'activo');
INSERT INTO clasificacion_repuesto (id_clasificacion_repuesto, descripcion, estatus) VALUES (4, 'Sistema de Dirección', 'activo');
INSERT INTO clasificacion_repuesto (id_clasificacion_repuesto, descripcion, estatus) VALUES (3, 'Suspensión y Chasis', 'activo');
INSERT INTO clasificacion_repuesto (id_clasificacion_repuesto, descripcion, estatus) VALUES (2, 'Caja y Tracción', 'activo');
INSERT INTO clasificacion_repuesto (id_clasificacion_repuesto, descripcion, estatus) VALUES (5, 'Sistema de Rodamiento', 'activo');
INSERT INTO clasificacion_repuesto (id_clasificacion_repuesto, descripcion, estatus) VALUES (6, 'Sistema de Frenos', 'activo');
INSERT INTO clasificacion_repuesto (id_clasificacion_repuesto, descripcion, estatus) VALUES (7, 'Sistema de Refrigeración', 'activo');
INSERT INTO clasificacion_repuesto (id_clasificacion_repuesto, descripcion, estatus) VALUES (8, 'Sistema A/A', 'activo');
INSERT INTO clasificacion_repuesto (id_clasificacion_repuesto, descripcion, estatus) VALUES (9, 'Carroceria Iny y Ext', 'activo');
INSERT INTO clasificacion_repuesto (id_clasificacion_repuesto, descripcion, estatus) VALUES (10, 'Lubricantes,Grasas y Silicones', 'activo');
INSERT INTO clasificacion_repuesto (id_clasificacion_repuesto, descripcion, estatus) VALUES (11, 'Accesorios y Boutique', 'activo');
INSERT INTO clasificacion_repuesto (id_clasificacion_repuesto, descripcion, estatus) VALUES (12, 'Sistema Electrico', 'activo');
INSERT INTO clasificacion_repuesto (id_clasificacion_repuesto, descripcion, estatus) VALUES (13, 'Sistema de Combustible', 'activo');


--
-- TOC entry 2081 (class 0 OID 0)
-- Dependencies: 170
-- Name: clasificacion_repuesto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('clasificacion_repuesto_id_seq', 13, true);


--
-- TOC entry 2028 (class 0 OID 108472)
-- Dependencies: 144
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO cliente (juridico, id_cliente) VALUES (NULL, 3);


--
-- TOC entry 2029 (class 0 OID 108477)
-- Dependencies: 145
-- Data for Name: compra; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2082 (class 0 OID 0)
-- Dependencies: 171
-- Name: compra_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('compra_id_seq', 1, false);


--
-- TOC entry 2030 (class 0 OID 108482)
-- Dependencies: 146
-- Data for Name: configuracion; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO configuracion (id_configuracion, porct_ganancia, porct_iva, valor_libra) VALUES (1, 0.69999999, 0.12, 5);


--
-- TOC entry 2031 (class 0 OID 108487)
-- Dependencies: 147
-- Data for Name: cotizacion; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO cotizacion (id_cotizacion, condiciones, estatus, fecha_creacion, fecha_vencimiento, mensaje, precio_flete, id_historico_moneda, id_proveedor) VALUES (1, NULL, 'C', '2015-08-22 19:56:40.746', '2015-08-23 20:24:00.292', 'Estimado Proveedor le hacemos el envio de un nuevo requerimiento para su posterior revisión ', NULL, 1, 4);


--
-- TOC entry 2083 (class 0 OID 0)
-- Dependencies: 172
-- Name: cotizacion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('cotizacion_id_seq', 1, true);


--
-- TOC entry 2032 (class 0 OID 108495)
-- Dependencies: 148
-- Data for Name: detalle_cotizacion; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO detalle_cotizacion (id_detalle_cotizacion, cantidad, estatus, marca_repuesto, precio_flete, precio_venta, id_cotizacion, id_detalle_requerimiento) VALUES (1, 1, NULL, 'Ford', NULL, 100, 1, 1);


--
-- TOC entry 2084 (class 0 OID 0)
-- Dependencies: 173
-- Name: detalle_cotizacion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('detalle_cotizacion_id_seq', 1, true);


--
-- TOC entry 2033 (class 0 OID 108503)
-- Dependencies: 149
-- Data for Name: detalle_cotizacion_internacional; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO detalle_cotizacion_internacional (alto, ancho, forma_envio, largo, peso, tipo_flete, valor_libra, id_detalle_cotizacion_internacional) VALUES (3, 1, false, 7, 8, false, 5, 1);


--
-- TOC entry 2034 (class 0 OID 108508)
-- Dependencies: 150
-- Data for Name: detalle_oferta; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2085 (class 0 OID 0)
-- Dependencies: 174
-- Name: detalle_oferta_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('detalle_oferta_id_seq', 1, false);


--
-- TOC entry 2035 (class 0 OID 108513)
-- Dependencies: 151
-- Data for Name: detalle_requerimiento; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO detalle_requerimiento (id_detalle_requerimiento, cantidad, codigo_oem, descripcion, estatus, foto, id_clasificacion_repuesto, id_requerimiento) VALUES (1, 2, '784512963', 'Motor', 'CT', NULL, 1, 1);


--
-- TOC entry 2086 (class 0 OID 0)
-- Dependencies: 175
-- Name: detalle_requerimiento_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('detalle_requerimiento_id_seq', 1, true);


--
-- TOC entry 2036 (class 0 OID 108521)
-- Dependencies: 152
-- Data for Name: estado; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO estado (id_estado, nombre) VALUES (1, 'Amazonas');
INSERT INTO estado (id_estado, nombre) VALUES (2, 'Anzoategui');
INSERT INTO estado (id_estado, nombre) VALUES (3, 'Apure');
INSERT INTO estado (id_estado, nombre) VALUES (4, 'Aragua');
INSERT INTO estado (id_estado, nombre) VALUES (5, 'Barinas');
INSERT INTO estado (id_estado, nombre) VALUES (6, 'Bolivar');
INSERT INTO estado (id_estado, nombre) VALUES (7, 'Carabobo');
INSERT INTO estado (id_estado, nombre) VALUES (8, 'Cojedes');
INSERT INTO estado (id_estado, nombre) VALUES (9, 'Delta Amacuro');
INSERT INTO estado (id_estado, nombre) VALUES (10, 'Falcon');
INSERT INTO estado (id_estado, nombre) VALUES (11, 'Guarico');
INSERT INTO estado (id_estado, nombre) VALUES (12, 'Lara');
INSERT INTO estado (id_estado, nombre) VALUES (13, 'Merida');
INSERT INTO estado (id_estado, nombre) VALUES (14, 'Miranda');
INSERT INTO estado (id_estado, nombre) VALUES (15, 'Monagas');
INSERT INTO estado (id_estado, nombre) VALUES (16, 'Nueva Esparta');
INSERT INTO estado (id_estado, nombre) VALUES (17, 'Portuguesa');
INSERT INTO estado (id_estado, nombre) VALUES (18, 'Sucre');
INSERT INTO estado (id_estado, nombre) VALUES (19, 'Tachira');
INSERT INTO estado (id_estado, nombre) VALUES (20, 'Trujillo');
INSERT INTO estado (id_estado, nombre) VALUES (21, 'Vargas');
INSERT INTO estado (id_estado, nombre) VALUES (22, 'Yaracuy');
INSERT INTO estado (id_estado, nombre) VALUES (23, 'Zulia');
INSERT INTO estado (id_estado, nombre) VALUES (24, 'Distrito Capital');
INSERT INTO estado (id_estado, nombre) VALUES (25, 'Dependencias Federales');


--
-- TOC entry 2037 (class 0 OID 108526)
-- Dependencies: 153
-- Data for Name: forma_pago; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2087 (class 0 OID 0)
-- Dependencies: 176
-- Name: forma_pago_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('forma_pago_id_seq', 1, false);


--
-- TOC entry 2088 (class 0 OID 0)
-- Dependencies: 177
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('hibernate_sequence', 1, false);


--
-- TOC entry 2038 (class 0 OID 108531)
-- Dependencies: 154
-- Data for Name: historico_moneda; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO historico_moneda (id_historia, fecha_creacion, monto_conversion, id_moneda) VALUES (1, '2015-05-24 00:00:00', 1, 1);
INSERT INTO historico_moneda (id_historia, fecha_creacion, monto_conversion, id_moneda) VALUES (2, '2015-06-01 00:00:00', 25, 2);


--
-- TOC entry 2089 (class 0 OID 0)
-- Dependencies: 178
-- Name: historico_moneda_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('historico_moneda_id_seq', 2, true);


--
-- TOC entry 2039 (class 0 OID 108536)
-- Dependencies: 155
-- Data for Name: history_logins; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO history_logins (id, date_login, date_logout, username) VALUES (1, '2015-08-22 19:49:16.274', NULL, 'maria');


--
-- TOC entry 2090 (class 0 OID 0)
-- Dependencies: 179
-- Name: history_logins_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('history_logins_id_seq', 1, true);


--
-- TOC entry 2040 (class 0 OID 108541)
-- Dependencies: 156
-- Data for Name: marca_vehiculo; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO marca_vehiculo (id_marca_vehiculo, estatus, nombre) VALUES (1, 'activo', 'Chana');
INSERT INTO marca_vehiculo (id_marca_vehiculo, estatus, nombre) VALUES (2, 'activo', 'Chery');
INSERT INTO marca_vehiculo (id_marca_vehiculo, estatus, nombre) VALUES (3, 'activo', 'Chevrolet');
INSERT INTO marca_vehiculo (id_marca_vehiculo, estatus, nombre) VALUES (4, 'activo', 'Daewoo');
INSERT INTO marca_vehiculo (id_marca_vehiculo, estatus, nombre) VALUES (5, 'activo', 'Fiat');
INSERT INTO marca_vehiculo (id_marca_vehiculo, estatus, nombre) VALUES (6, 'activo', 'Ford');
INSERT INTO marca_vehiculo (id_marca_vehiculo, estatus, nombre) VALUES (7, 'activo', 'Honda');
INSERT INTO marca_vehiculo (id_marca_vehiculo, estatus, nombre) VALUES (8, 'activo', 'Hyundai');
INSERT INTO marca_vehiculo (id_marca_vehiculo, estatus, nombre) VALUES (9, 'activo', 'Kia');
INSERT INTO marca_vehiculo (id_marca_vehiculo, estatus, nombre) VALUES (10, 'activo', 'Mazda');
INSERT INTO marca_vehiculo (id_marca_vehiculo, estatus, nombre) VALUES (11, 'activo', 'Mitsubishi');
INSERT INTO marca_vehiculo (id_marca_vehiculo, estatus, nombre) VALUES (12, 'activo', 'Peugeot');
INSERT INTO marca_vehiculo (id_marca_vehiculo, estatus, nombre) VALUES (13, 'activo', 'Renault');
INSERT INTO marca_vehiculo (id_marca_vehiculo, estatus, nombre) VALUES (14, 'activo', 'Tata');
INSERT INTO marca_vehiculo (id_marca_vehiculo, estatus, nombre) VALUES (15, 'activo', 'Toyota');
INSERT INTO marca_vehiculo (id_marca_vehiculo, estatus, nombre) VALUES (16, 'activo', 'Volkswagen');


--
-- TOC entry 2091 (class 0 OID 0)
-- Dependencies: 180
-- Name: marca_vehiculo_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('marca_vehiculo_id_seq', 16, true);


--
-- TOC entry 2041 (class 0 OID 108549)
-- Dependencies: 157
-- Data for Name: menu; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, tipo, id_padre) VALUES (1, NULL, 'z-icon-shopping-cart', 'Cotizar', '/WEB-INF/views/sistema/funcionalidades/listaRequerimientosProveedor.zul', 3, NULL);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, tipo, id_padre) VALUES (2, NULL, 'z-icon-user', 'Perfil', '/WEB-INF/views/sistema/configuracion/editarPerfil.zul', 3, NULL);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, tipo, id_padre) VALUES (10, NULL, 'z-icon-book', 'Mis Requerimientos', NULL, 1, NULL);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, tipo, id_padre) VALUES (11, NULL, NULL, 'En Emision', '/WEB-INF/views/sistema/funcionalidades/listaMisRequerimientosEmitidos.zul', 1, 10);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, tipo, id_padre) VALUES (12, NULL, NULL, 'En Proceso', '/WEB-INF/views/sistema/funcionalidades/listaMisRequerimientosProcesados.zul', 1, 10);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, tipo, id_padre) VALUES (13, NULL, NULL, 'Todos', '/WEB-INF/views/sistema/funcionalidades/listaRequerimientosGeneral.zul', 1, 10);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, tipo, id_padre) VALUES (14, NULL, 'z-icon-bold', 'Datos Básicos', NULL, 1, NULL);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, tipo, id_padre) VALUES (15, NULL, NULL, 'Proveedor', '/WEB-INF/views/sistema/maestros/listaProveedores.zul', 1, 14);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, tipo, id_padre) VALUES (16, NULL, NULL, 'Analista', '/WEB-INF/views/sistema/maestros/listaAnalistas.zul', 1, 14);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, tipo, id_padre) VALUES (17, NULL, NULL, 'Marcas de Vehiculo', '/WEB-INF/views/sistema/maestros/listaMarcas.zul', 1, 14);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, tipo, id_padre) VALUES (18, NULL, 'z-icon-cog', 'Configuracion', NULL, 1, NULL);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, tipo, id_padre) VALUES (19, NULL, 'z-icon-lock', 'Seguridad', NULL, 1, 18);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, tipo, id_padre) VALUES (20, NULL, NULL, 'Usuarios', '/WEB-INF/views/sistema/seguridad/configuracion/usuarios/listaUsuarios.zul', 1, 19);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, tipo, id_padre) VALUES (21, NULL, 'z-icon-user', 'Perfil', '/WEB-INF/views/sistema/configuracion/editarPerfil.zul', 1, NULL);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, tipo, id_padre) VALUES (100, NULL, 'z-icon-book', 'Mis Requerimientos', NULL, 2, NULL);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, tipo, id_padre) VALUES (101, NULL, NULL, 'En Emision', '/WEB-INF/views/sistema/funcionalidades/listaMisRequerimientosEmitidos.zul', 2, 100);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, tipo, id_padre) VALUES (102, NULL, NULL, 'En Proceso', '/WEB-INF/views/sistema/funcionalidades/listaMisRequerimientosProcesados.zul', 2, 100);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, tipo, id_padre) VALUES (103, NULL, 'z-icon-cog', 'Configuracion', NULL, 2, NULL);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, tipo, id_padre) VALUES (104, NULL, 'z-icon-lock', 'Seguridad', NULL, 2, 103);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, tipo, id_padre) VALUES (105, NULL, NULL, 'Usuarios', '/WEB-INF/views/sistema/seguridad/configuracion/usuarios/listaUsuarios.zul', 2, 104);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, tipo, id_padre) VALUES (106, NULL, 'z-icon-user', 'Perfil', '/WEB-INF/views/sistema/configuracion/editarPerfil.zul', 2, NULL);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, tipo, id_padre) VALUES (107, NULL, NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 2092 (class 0 OID 0)
-- Dependencies: 181
-- Name: menu_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('menu_id_seq', 1, false);


--
-- TOC entry 2042 (class 0 OID 108557)
-- Dependencies: 158
-- Data for Name: moneda; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO moneda (id_moneda, estatus, nombre, pais, simbolo) VALUES (1, 'activo', 'Bolivar', true, 'BsF');
INSERT INTO moneda (id_moneda, estatus, nombre, pais, simbolo) VALUES (2, 'activo', 'Dolar', false, 'fa fa-usd');
INSERT INTO moneda (id_moneda, estatus, nombre, pais, simbolo) VALUES (3, 'activo', 'Euro', false, 'fa fa-eur');


--
-- TOC entry 2093 (class 0 OID 0)
-- Dependencies: 182
-- Name: moneda_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('moneda_id_seq', 3, true);


--
-- TOC entry 2043 (class 0 OID 108565)
-- Dependencies: 159
-- Data for Name: motor; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO motor (id_motor, nombre) VALUES (1, '1.0');
INSERT INTO motor (id_motor, nombre) VALUES (2, '1.1');
INSERT INTO motor (id_motor, nombre) VALUES (3, '1.2');
INSERT INTO motor (id_motor, nombre) VALUES (4, '1.3');
INSERT INTO motor (id_motor, nombre) VALUES (5, '1.4');
INSERT INTO motor (id_motor, nombre) VALUES (6, '1.5');
INSERT INTO motor (id_motor, nombre) VALUES (7, '1.6');
INSERT INTO motor (id_motor, nombre) VALUES (8, '1.7');
INSERT INTO motor (id_motor, nombre) VALUES (9, '1.8');
INSERT INTO motor (id_motor, nombre) VALUES (10, '2.0');
INSERT INTO motor (id_motor, nombre) VALUES (11, '2.2');
INSERT INTO motor (id_motor, nombre) VALUES (12, '2.3');
INSERT INTO motor (id_motor, nombre) VALUES (13, '2.4');
INSERT INTO motor (id_motor, nombre) VALUES (14, '4.5');


--
-- TOC entry 2094 (class 0 OID 0)
-- Dependencies: 183
-- Name: motor_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('motor_id_seq', 14, true);


--
-- TOC entry 2044 (class 0 OID 108570)
-- Dependencies: 160
-- Data for Name: oferta; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2095 (class 0 OID 0)
-- Dependencies: 184
-- Name: oferta_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('oferta_id_seq', 1, false);


--
-- TOC entry 2045 (class 0 OID 108575)
-- Dependencies: 161
-- Data for Name: pago_compra; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2096 (class 0 OID 0)
-- Dependencies: 185
-- Name: pago_compra_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('pago_compra_id_seq', 1, false);


--
-- TOC entry 2046 (class 0 OID 108583)
-- Dependencies: 162
-- Data for Name: persistent_logins; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2047 (class 0 OID 108588)
-- Dependencies: 163
-- Data for Name: persona; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO persona (id, apellido, cedula, correo, direccion, estatus, nombre, telefono, tipo_menu, id_ciudad) VALUES (1, NULL, '11111111', NULL, NULL, 'activo', 'Administrador', NULL, 1, NULL);
INSERT INTO persona (id, apellido, cedula, correo, direccion, estatus, nombre, telefono, tipo_menu, id_ciudad) VALUES (2, NULL, '5254874', 'maria@gmail.com', NULL, 'activo', 'Maria', '258963147', 2, 12);
INSERT INTO persona (id, apellido, cedula, correo, direccion, estatus, nombre, telefono, tipo_menu, id_ciudad) VALUES (3, NULL, 'V20186243', 'eugeniohernandez17@gmail.com', NULL, NULL, 'Eugenio', '45798961', NULL, 212);
INSERT INTO persona (id, apellido, cedula, correo, direccion, estatus, nombre, telefono, tipo_menu, id_ciudad) VALUES (4, NULL, 'V20348677', 'jose@gmail.com', 'Zona 2', 'solicitante', 'Jose', '22222222', NULL, 58);


--
-- TOC entry 2097 (class 0 OID 0)
-- Dependencies: 186
-- Name: persona_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('persona_id_seq', 4, true);


--
-- TOC entry 2048 (class 0 OID 108596)
-- Dependencies: 164
-- Data for Name: proveedor; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO proveedor (tipo_proveedor, id_proveedor) VALUES (false, 4);


--
-- TOC entry 2049 (class 0 OID 108601)
-- Dependencies: 165
-- Data for Name: proveedor_clasificacion_repuesto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO proveedor_clasificacion_repuesto (id_proveedor, id_clasificacion_repuesto) VALUES (4, 10);
INSERT INTO proveedor_clasificacion_repuesto (id_proveedor, id_clasificacion_repuesto) VALUES (4, 9);
INSERT INTO proveedor_clasificacion_repuesto (id_proveedor, id_clasificacion_repuesto) VALUES (4, 8);
INSERT INTO proveedor_clasificacion_repuesto (id_proveedor, id_clasificacion_repuesto) VALUES (4, 7);
INSERT INTO proveedor_clasificacion_repuesto (id_proveedor, id_clasificacion_repuesto) VALUES (4, 6);
INSERT INTO proveedor_clasificacion_repuesto (id_proveedor, id_clasificacion_repuesto) VALUES (4, 5);
INSERT INTO proveedor_clasificacion_repuesto (id_proveedor, id_clasificacion_repuesto) VALUES (4, 2);
INSERT INTO proveedor_clasificacion_repuesto (id_proveedor, id_clasificacion_repuesto) VALUES (4, 3);
INSERT INTO proveedor_clasificacion_repuesto (id_proveedor, id_clasificacion_repuesto) VALUES (4, 4);
INSERT INTO proveedor_clasificacion_repuesto (id_proveedor, id_clasificacion_repuesto) VALUES (4, 1);
INSERT INTO proveedor_clasificacion_repuesto (id_proveedor, id_clasificacion_repuesto) VALUES (4, 13);
INSERT INTO proveedor_clasificacion_repuesto (id_proveedor, id_clasificacion_repuesto) VALUES (4, 12);
INSERT INTO proveedor_clasificacion_repuesto (id_proveedor, id_clasificacion_repuesto) VALUES (4, 11);


--
-- TOC entry 2050 (class 0 OID 108604)
-- Dependencies: 166
-- Data for Name: proveedor_marca_vehiculo; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO proveedor_marca_vehiculo (id_proveedor, id_marca_vehiculo) VALUES (4, 10);
INSERT INTO proveedor_marca_vehiculo (id_proveedor, id_marca_vehiculo) VALUES (4, 9);
INSERT INTO proveedor_marca_vehiculo (id_proveedor, id_marca_vehiculo) VALUES (4, 8);
INSERT INTO proveedor_marca_vehiculo (id_proveedor, id_marca_vehiculo) VALUES (4, 7);
INSERT INTO proveedor_marca_vehiculo (id_proveedor, id_marca_vehiculo) VALUES (4, 6);
INSERT INTO proveedor_marca_vehiculo (id_proveedor, id_marca_vehiculo) VALUES (4, 5);
INSERT INTO proveedor_marca_vehiculo (id_proveedor, id_marca_vehiculo) VALUES (4, 4);
INSERT INTO proveedor_marca_vehiculo (id_proveedor, id_marca_vehiculo) VALUES (4, 3);
INSERT INTO proveedor_marca_vehiculo (id_proveedor, id_marca_vehiculo) VALUES (4, 2);
INSERT INTO proveedor_marca_vehiculo (id_proveedor, id_marca_vehiculo) VALUES (4, 1);
INSERT INTO proveedor_marca_vehiculo (id_proveedor, id_marca_vehiculo) VALUES (4, 16);
INSERT INTO proveedor_marca_vehiculo (id_proveedor, id_marca_vehiculo) VALUES (4, 15);
INSERT INTO proveedor_marca_vehiculo (id_proveedor, id_marca_vehiculo) VALUES (4, 14);
INSERT INTO proveedor_marca_vehiculo (id_proveedor, id_marca_vehiculo) VALUES (4, 13);
INSERT INTO proveedor_marca_vehiculo (id_proveedor, id_marca_vehiculo) VALUES (4, 12);
INSERT INTO proveedor_marca_vehiculo (id_proveedor, id_marca_vehiculo) VALUES (4, 11);


--
-- TOC entry 2051 (class 0 OID 108607)
-- Dependencies: 167
-- Data for Name: requerimiento; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO requerimiento (id_requerimiento, anno_v, estatus, fecha_cierre, fecha_creacion, fecha_solicitud, fecha_vencimiento, modelo_v, serial_carroceria_v, tipo_repuesto, traccion_v, transmision_v, id_analista, id_cliente, id_marca_v, id_motor_v) VALUES (1, 2000, 'CT', NULL, '2015-08-22', '2015-08-22 19:57:30.014', '2015-09-06', 'Hilux', '784596324', NULL, true, true, 2, 3, 4, 5);


--
-- TOC entry 2098 (class 0 OID 0)
-- Dependencies: 187
-- Name: requerimiento_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('requerimiento_id_seq', 1, true);


--
-- TOC entry 2052 (class 0 OID 108615)
-- Dependencies: 168
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO usuario (id, activo, foto, pasword, username, persona_id) VALUES (1, true, NULL, '123', 'admin', 1);
INSERT INTO usuario (id, activo, foto, pasword, username, persona_id) VALUES (2, true, NULL, '123', 'maria', 2);


--
-- TOC entry 2099 (class 0 OID 0)
-- Dependencies: 188
-- Name: usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('usuario_id_seq', 1, false);


--
-- TOC entry 1849 (class 2606 OID 108450)
-- Name: analista_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY analista
    ADD CONSTRAINT analista_pkey PRIMARY KEY (id_analista);


--
-- TOC entry 1851 (class 2606 OID 108458)
-- Name: banco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY banco
    ADD CONSTRAINT banco_pkey PRIMARY KEY (id_banco);


--
-- TOC entry 1853 (class 2606 OID 108463)
-- Name: ciudad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ciudad
    ADD CONSTRAINT ciudad_pkey PRIMARY KEY (id_ciudad);


--
-- TOC entry 1855 (class 2606 OID 108471)
-- Name: clasificacion_repuesto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY clasificacion_repuesto
    ADD CONSTRAINT clasificacion_repuesto_pkey PRIMARY KEY (id_clasificacion_repuesto);


--
-- TOC entry 1857 (class 2606 OID 108476)
-- Name: cliente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (id_cliente);


--
-- TOC entry 1859 (class 2606 OID 108481)
-- Name: compra_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY compra
    ADD CONSTRAINT compra_pkey PRIMARY KEY (id_compra);


--
-- TOC entry 1861 (class 2606 OID 108486)
-- Name: configuracion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY configuracion
    ADD CONSTRAINT configuracion_pkey PRIMARY KEY (id_configuracion);


--
-- TOC entry 1863 (class 2606 OID 108494)
-- Name: cotizacion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cotizacion
    ADD CONSTRAINT cotizacion_pkey PRIMARY KEY (id_cotizacion);


--
-- TOC entry 1867 (class 2606 OID 108507)
-- Name: detalle_cotizacion_internacional_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY detalle_cotizacion_internacional
    ADD CONSTRAINT detalle_cotizacion_internacional_pkey PRIMARY KEY (id_detalle_cotizacion_internacional);


--
-- TOC entry 1865 (class 2606 OID 108502)
-- Name: detalle_cotizacion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY detalle_cotizacion
    ADD CONSTRAINT detalle_cotizacion_pkey PRIMARY KEY (id_detalle_cotizacion);


--
-- TOC entry 1869 (class 2606 OID 108512)
-- Name: detalle_oferta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY detalle_oferta
    ADD CONSTRAINT detalle_oferta_pkey PRIMARY KEY (id_detalle_oferta);


--
-- TOC entry 1871 (class 2606 OID 108520)
-- Name: detalle_requerimiento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY detalle_requerimiento
    ADD CONSTRAINT detalle_requerimiento_pkey PRIMARY KEY (id_detalle_requerimiento);


--
-- TOC entry 1873 (class 2606 OID 108525)
-- Name: estado_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY estado
    ADD CONSTRAINT estado_pkey PRIMARY KEY (id_estado);


--
-- TOC entry 1875 (class 2606 OID 108530)
-- Name: forma_pago_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY forma_pago
    ADD CONSTRAINT forma_pago_pkey PRIMARY KEY (id_forma_pago);


--
-- TOC entry 1877 (class 2606 OID 108535)
-- Name: historico_moneda_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY historico_moneda
    ADD CONSTRAINT historico_moneda_pkey PRIMARY KEY (id_historia);


--
-- TOC entry 1879 (class 2606 OID 108540)
-- Name: history_logins_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY history_logins
    ADD CONSTRAINT history_logins_pkey PRIMARY KEY (id);


--
-- TOC entry 1881 (class 2606 OID 108548)
-- Name: marca_vehiculo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY marca_vehiculo
    ADD CONSTRAINT marca_vehiculo_pkey PRIMARY KEY (id_marca_vehiculo);


--
-- TOC entry 1883 (class 2606 OID 108556)
-- Name: menu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY menu
    ADD CONSTRAINT menu_pkey PRIMARY KEY (id_menu);


--
-- TOC entry 1885 (class 2606 OID 108564)
-- Name: moneda_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY moneda
    ADD CONSTRAINT moneda_pkey PRIMARY KEY (id_moneda);


--
-- TOC entry 1887 (class 2606 OID 108569)
-- Name: motor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY motor
    ADD CONSTRAINT motor_pkey PRIMARY KEY (id_motor);


--
-- TOC entry 1889 (class 2606 OID 108574)
-- Name: oferta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY oferta
    ADD CONSTRAINT oferta_pkey PRIMARY KEY (id_oferta);


--
-- TOC entry 1891 (class 2606 OID 108582)
-- Name: pago_compra_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pago_compra
    ADD CONSTRAINT pago_compra_pkey PRIMARY KEY (id_pago_compra);


--
-- TOC entry 1893 (class 2606 OID 108587)
-- Name: persistent_logins_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY persistent_logins
    ADD CONSTRAINT persistent_logins_pkey PRIMARY KEY (series);


--
-- TOC entry 1895 (class 2606 OID 108595)
-- Name: persona_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY persona
    ADD CONSTRAINT persona_pkey PRIMARY KEY (id);


--
-- TOC entry 1897 (class 2606 OID 108600)
-- Name: proveedor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY proveedor
    ADD CONSTRAINT proveedor_pkey PRIMARY KEY (id_proveedor);


--
-- TOC entry 1899 (class 2606 OID 108614)
-- Name: requerimiento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY requerimiento
    ADD CONSTRAINT requerimiento_pkey PRIMARY KEY (id_requerimiento);


--
-- TOC entry 1901 (class 2606 OID 108622)
-- Name: usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 1903 (class 2606 OID 108825)
-- Name: usuario_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_username_key UNIQUE (username);


--
-- TOC entry 1932 (class 2606 OID 108753)
-- Name: fk1c4167896338eca6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY proveedor_marca_vehiculo
    ADD CONSTRAINT fk1c4167896338eca6 FOREIGN KEY (id_proveedor) REFERENCES proveedor(id_proveedor);


--
-- TOC entry 1931 (class 2606 OID 108748)
-- Name: fk1c416789fb88f57b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY proveedor_marca_vehiculo
    ADD CONSTRAINT fk1c416789fb88f57b FOREIGN KEY (id_marca_vehiculo) REFERENCES marca_vehiculo(id_marca_vehiculo);


--
-- TOC entry 1921 (class 2606 OID 108703)
-- Name: fk33155fb000c573; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY menu
    ADD CONSTRAINT fk33155fb000c573 FOREIGN KEY (id_padre) REFERENCES menu(id_menu);


--
-- TOC entry 1906 (class 2606 OID 108633)
-- Name: fk334b85fa72a75f10; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cliente
    ADD CONSTRAINT fk334b85fa72a75f10 FOREIGN KEY (id_cliente) REFERENCES persona(id);


--
-- TOC entry 1915 (class 2606 OID 108678)
-- Name: fk37d329f9220ac46b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY detalle_oferta
    ADD CONSTRAINT fk37d329f9220ac46b FOREIGN KEY (id_detalle_cotizacion) REFERENCES detalle_cotizacion(id_detalle_cotizacion);


--
-- TOC entry 1916 (class 2606 OID 108683)
-- Name: fk37d329f95a5fdb8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY detalle_oferta
    ADD CONSTRAINT fk37d329f95a5fdb8 FOREIGN KEY (id_oferta) REFERENCES oferta(id_oferta);


--
-- TOC entry 1914 (class 2606 OID 108673)
-- Name: fk37d329f9ddb68c52; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY detalle_oferta
    ADD CONSTRAINT fk37d329f9ddb68c52 FOREIGN KEY (id_compra) REFERENCES compra(id_compra);


--
-- TOC entry 1918 (class 2606 OID 108693)
-- Name: fk42c4ba5d1726034; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY detalle_requerimiento
    ADD CONSTRAINT fk42c4ba5d1726034 FOREIGN KEY (id_requerimiento) REFERENCES requerimiento(id_requerimiento);


--
-- TOC entry 1917 (class 2606 OID 108688)
-- Name: fk42c4ba5d9195f9f3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY detalle_requerimiento
    ADD CONSTRAINT fk42c4ba5d9195f9f3 FOREIGN KEY (id_clasificacion_repuesto) REFERENCES clasificacion_repuesto(id_clasificacion_repuesto);


--
-- TOC entry 1909 (class 2606 OID 108648)
-- Name: fk5c3e3f8d18b9529d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cotizacion
    ADD CONSTRAINT fk5c3e3f8d18b9529d FOREIGN KEY (id_historico_moneda) REFERENCES historico_moneda(id_historia);


--
-- TOC entry 1910 (class 2606 OID 108653)
-- Name: fk5c3e3f8d6338eca6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cotizacion
    ADD CONSTRAINT fk5c3e3f8d6338eca6 FOREIGN KEY (id_proveedor) REFERENCES proveedor(id_proveedor);


--
-- TOC entry 1912 (class 2606 OID 108663)
-- Name: fk6d4757554670fb59; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY detalle_cotizacion
    ADD CONSTRAINT fk6d4757554670fb59 FOREIGN KEY (id_detalle_requerimiento) REFERENCES detalle_requerimiento(id_detalle_requerimiento);


--
-- TOC entry 1911 (class 2606 OID 108658)
-- Name: fk6d475755b1780570; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY detalle_cotizacion
    ADD CONSTRAINT fk6d475755b1780570 FOREIGN KEY (id_cotizacion) REFERENCES cotizacion(id_cotizacion);


--
-- TOC entry 1930 (class 2606 OID 108743)
-- Name: fk97b0796d6338eca6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY proveedor_clasificacion_repuesto
    ADD CONSTRAINT fk97b0796d6338eca6 FOREIGN KEY (id_proveedor) REFERENCES proveedor(id_proveedor);


--
-- TOC entry 1929 (class 2606 OID 108738)
-- Name: fk97b0796d9195f9f3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY proveedor_clasificacion_repuesto
    ADD CONSTRAINT fk97b0796d9195f9f3 FOREIGN KEY (id_clasificacion_repuesto) REFERENCES clasificacion_repuesto(id_clasificacion_repuesto);


--
-- TOC entry 1919 (class 2606 OID 108698)
-- Name: fka1184445ffd80b82; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY historico_moneda
    ADD CONSTRAINT fka1184445ffd80b82 FOREIGN KEY (id_moneda) REFERENCES moneda(id_moneda);


--
-- TOC entry 1905 (class 2606 OID 108628)
-- Name: fkaeee1c58e5009422; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ciudad
    ADD CONSTRAINT fkaeee1c58e5009422 FOREIGN KEY (id_estado) REFERENCES estado(id_estado);


--
-- TOC entry 1908 (class 2606 OID 108643)
-- Name: fkaf3f357e1726034; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY compra
    ADD CONSTRAINT fkaf3f357e1726034 FOREIGN KEY (id_requerimiento) REFERENCES requerimiento(id_requerimiento);


--
-- TOC entry 1907 (class 2606 OID 108638)
-- Name: fkaf3f357e18b9529d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY compra
    ADD CONSTRAINT fkaf3f357e18b9529d FOREIGN KEY (id_historico_moneda) REFERENCES historico_moneda(id_historia);


--
-- TOC entry 1925 (class 2606 OID 108723)
-- Name: fkb6bc61e416eba8c2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pago_compra
    ADD CONSTRAINT fkb6bc61e416eba8c2 FOREIGN KEY (id_persona) REFERENCES persona(id);


--
-- TOC entry 1924 (class 2606 OID 108718)
-- Name: fkb6bc61e44097f483; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pago_compra
    ADD CONSTRAINT fkb6bc61e44097f483 FOREIGN KEY (id_forma_pago) REFERENCES forma_pago(id_forma_pago);


--
-- TOC entry 1922 (class 2606 OID 108708)
-- Name: fkb6bc61e47258dd60; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pago_compra
    ADD CONSTRAINT fkb6bc61e47258dd60 FOREIGN KEY (id_banco) REFERENCES banco(id_banco);


--
-- TOC entry 1923 (class 2606 OID 108713)
-- Name: fkb6bc61e4ddb68c52; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pago_compra
    ADD CONSTRAINT fkb6bc61e4ddb68c52 FOREIGN KEY (id_compra) REFERENCES compra(id_compra);


--
-- TOC entry 1913 (class 2606 OID 108668)
-- Name: fkbb5b3c953a4dfaab; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY detalle_cotizacion_internacional
    ADD CONSTRAINT fkbb5b3c953a4dfaab FOREIGN KEY (id_detalle_cotizacion_internacional) REFERENCES detalle_cotizacion(id_detalle_cotizacion);


--
-- TOC entry 1926 (class 2606 OID 108831)
-- Name: fkbd224d2a6a01c92; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY persistent_logins
    ADD CONSTRAINT fkbd224d2a6a01c92 FOREIGN KEY (username) REFERENCES usuario(username);


--
-- TOC entry 1904 (class 2606 OID 108623)
-- Name: fkc2e8ee2fdcbd110d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY analista
    ADD CONSTRAINT fkc2e8ee2fdcbd110d FOREIGN KEY (id_analista) REFERENCES persona(id);


--
-- TOC entry 1933 (class 2606 OID 108758)
-- Name: fkd19e472517870034; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY requerimiento
    ADD CONSTRAINT fkd19e472517870034 FOREIGN KEY (id_analista) REFERENCES analista(id_analista);


--
-- TOC entry 1936 (class 2606 OID 108773)
-- Name: fkd19e47257973088b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY requerimiento
    ADD CONSTRAINT fkd19e47257973088b FOREIGN KEY (id_motor_v) REFERENCES motor(id_motor);


--
-- TOC entry 1935 (class 2606 OID 108768)
-- Name: fkd19e4725b1f9d9de; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY requerimiento
    ADD CONSTRAINT fkd19e4725b1f9d9de FOREIGN KEY (id_marca_v) REFERENCES marca_vehiculo(id_marca_vehiculo);


--
-- TOC entry 1934 (class 2606 OID 108763)
-- Name: fkd19e4725ce63155e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY requerimiento
    ADD CONSTRAINT fkd19e4725ce63155e FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente);


--
-- TOC entry 1927 (class 2606 OID 108728)
-- Name: fkd78fcfacdd145a06; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY persona
    ADD CONSTRAINT fkd78fcfacdd145a06 FOREIGN KEY (id_ciudad) REFERENCES ciudad(id_ciudad);


--
-- TOC entry 1928 (class 2606 OID 108733)
-- Name: fkdf24cade6d89dcf4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY proveedor
    ADD CONSTRAINT fkdf24cade6d89dcf4 FOREIGN KEY (id_proveedor) REFERENCES persona(id);


--
-- TOC entry 1920 (class 2606 OID 108826)
-- Name: fkee0d8835a6a01c92; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY history_logins
    ADD CONSTRAINT fkee0d8835a6a01c92 FOREIGN KEY (username) REFERENCES usuario(username);


--
-- TOC entry 1937 (class 2606 OID 108778)
-- Name: fkf814f32ebe6ae2c8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT fkf814f32ebe6ae2c8 FOREIGN KEY (persona_id) REFERENCES persona(id);


--
-- TOC entry 2079 (class 0 OID 0)
-- Dependencies: 3
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2015-08-22 20:47:06

--
-- PostgreSQL database dump complete
--

