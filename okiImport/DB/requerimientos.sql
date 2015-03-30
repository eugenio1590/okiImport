--
-- PostgreSQL database dump
--

-- Started on 2015-03-29 20:26:07

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 497 (class 2612 OID 16386)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--

CREATE PROCEDURAL LANGUAGE plpgsql;


ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 141 (class 1259 OID 88711)
-- Dependencies: 3
-- Name: analista; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE analista (
    id_analista integer NOT NULL,
    apellido character varying(50),
    cedula character varying(255) NOT NULL,
    correo character varying(20),
    direccion character varying(255),
    nombre character varying(50),
    sexo boolean,
    telefono character varying(20)
);


ALTER TABLE public.analista OWNER TO postgres;

--
-- TOC entry 140 (class 1259 OID 88709)
-- Dependencies: 141 3
-- Name: analista_id_analista_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE analista_id_analista_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.analista_id_analista_seq OWNER TO postgres;

--
-- TOC entry 1879 (class 0 OID 0)
-- Dependencies: 140
-- Name: analista_id_analista_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE analista_id_analista_seq OWNED BY analista.id_analista;


--
-- TOC entry 1880 (class 0 OID 0)
-- Dependencies: 140
-- Name: analista_id_analista_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('analista_id_analista_seq', 1, true);


--
-- TOC entry 143 (class 1259 OID 88722)
-- Dependencies: 3
-- Name: clasificacion_repuesto; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE clasificacion_repuesto (
    id integer NOT NULL,
    nombre character varying(255)
);


ALTER TABLE public.clasificacion_repuesto OWNER TO postgres;

--
-- TOC entry 142 (class 1259 OID 88720)
-- Dependencies: 3 143
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
-- TOC entry 1881 (class 0 OID 0)
-- Dependencies: 142
-- Name: clasificacion_repuesto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE clasificacion_repuesto_id_seq OWNED BY clasificacion_repuesto.id;


--
-- TOC entry 1882 (class 0 OID 0)
-- Dependencies: 142
-- Name: clasificacion_repuesto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('clasificacion_repuesto_id_seq', 1, false);


--
-- TOC entry 145 (class 1259 OID 88730)
-- Dependencies: 3
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cliente (
    id_cliente integer NOT NULL,
    apellido character varying(50),
    cedula character varying(255) NOT NULL,
    correo character varying(20),
    direccion character varying(255),
    nombre character varying(50),
    sexo boolean,
    telefono character varying(20)
);


ALTER TABLE public.cliente OWNER TO postgres;

--
-- TOC entry 144 (class 1259 OID 88728)
-- Dependencies: 3 145
-- Name: cliente_id_cliente_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE cliente_id_cliente_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.cliente_id_cliente_seq OWNER TO postgres;

--
-- TOC entry 1883 (class 0 OID 0)
-- Dependencies: 144
-- Name: cliente_id_cliente_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE cliente_id_cliente_seq OWNED BY cliente.id_cliente;


--
-- TOC entry 1884 (class 0 OID 0)
-- Dependencies: 144
-- Name: cliente_id_cliente_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('cliente_id_cliente_seq', 1, false);


--
-- TOC entry 157 (class 1259 OID 88823)
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
-- TOC entry 1885 (class 0 OID 0)
-- Dependencies: 157
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('hibernate_sequence', 1, false);


--
-- TOC entry 146 (class 1259 OID 88739)
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
-- TOC entry 148 (class 1259 OID 88746)
-- Dependencies: 3
-- Name: marca_vehiculo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE marca_vehiculo (
    id_marca_vehiculo integer NOT NULL,
    nombre character varying(255)
);


ALTER TABLE public.marca_vehiculo OWNER TO postgres;

--
-- TOC entry 147 (class 1259 OID 88744)
-- Dependencies: 148 3
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
-- TOC entry 1886 (class 0 OID 0)
-- Dependencies: 147
-- Name: marca_vehiculo_id_marca_vehiculo_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE marca_vehiculo_id_marca_vehiculo_seq OWNED BY marca_vehiculo.id_marca_vehiculo;


--
-- TOC entry 1887 (class 0 OID 0)
-- Dependencies: 147
-- Name: marca_vehiculo_id_marca_vehiculo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('marca_vehiculo_id_marca_vehiculo_seq', 1, false);


--
-- TOC entry 149 (class 1259 OID 88752)
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
-- TOC entry 150 (class 1259 OID 88760)
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
-- TOC entry 152 (class 1259 OID 88767)
-- Dependencies: 3
-- Name: proveedor; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE proveedor (
    id_proveedor integer NOT NULL,
    apellido character varying(50),
    cedula character varying(255) NOT NULL,
    correo character varying(20),
    direccion character varying(255),
    nombre character varying(50),
    sexo boolean,
    telefono character varying(20),
    estatus character varying(255),
    rif character varying(255)
);


ALTER TABLE public.proveedor OWNER TO postgres;

--
-- TOC entry 151 (class 1259 OID 88765)
-- Dependencies: 152 3
-- Name: proveedor_id_proveedor_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE proveedor_id_proveedor_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.proveedor_id_proveedor_seq OWNER TO postgres;

--
-- TOC entry 1888 (class 0 OID 0)
-- Dependencies: 151
-- Name: proveedor_id_proveedor_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE proveedor_id_proveedor_seq OWNED BY proveedor.id_proveedor;


--
-- TOC entry 1889 (class 0 OID 0)
-- Dependencies: 151
-- Name: proveedor_id_proveedor_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('proveedor_id_proveedor_seq', 1, false);


--
-- TOC entry 154 (class 1259 OID 88778)
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
-- TOC entry 153 (class 1259 OID 88776)
-- Dependencies: 154 3
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
-- TOC entry 1890 (class 0 OID 0)
-- Dependencies: 153
-- Name: requerimiento_id_requerimiento_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE requerimiento_id_requerimiento_seq OWNED BY requerimiento.id_requerimiento;


--
-- TOC entry 1891 (class 0 OID 0)
-- Dependencies: 153
-- Name: requerimiento_id_requerimiento_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('requerimiento_id_requerimiento_seq', 1, false);


--
-- TOC entry 156 (class 1259 OID 88789)
-- Dependencies: 3
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE usuario (
    id integer NOT NULL,
    activo boolean NOT NULL,
    foto bytea,
    pasword character varying(100),
    username character varying(20),
    id_analista integer,
    id_proveedor integer
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 155 (class 1259 OID 88787)
-- Dependencies: 156 3
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
-- TOC entry 1892 (class 0 OID 0)
-- Dependencies: 155
-- Name: usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE usuario_id_seq OWNED BY usuario.id;


--
-- TOC entry 1893 (class 0 OID 0)
-- Dependencies: 155
-- Name: usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('usuario_id_seq', 1, false);


--
-- TOC entry 1828 (class 2604 OID 88714)
-- Dependencies: 140 141 141
-- Name: id_analista; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY analista ALTER COLUMN id_analista SET DEFAULT nextval('analista_id_analista_seq'::regclass);


--
-- TOC entry 1829 (class 2604 OID 88725)
-- Dependencies: 142 143 143
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY clasificacion_repuesto ALTER COLUMN id SET DEFAULT nextval('clasificacion_repuesto_id_seq'::regclass);


--
-- TOC entry 1830 (class 2604 OID 88733)
-- Dependencies: 144 145 145
-- Name: id_cliente; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cliente ALTER COLUMN id_cliente SET DEFAULT nextval('cliente_id_cliente_seq'::regclass);


--
-- TOC entry 1831 (class 2604 OID 88749)
-- Dependencies: 148 147 148
-- Name: id_marca_vehiculo; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY marca_vehiculo ALTER COLUMN id_marca_vehiculo SET DEFAULT nextval('marca_vehiculo_id_marca_vehiculo_seq'::regclass);


--
-- TOC entry 1832 (class 2604 OID 88770)
-- Dependencies: 152 151 152
-- Name: id_proveedor; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY proveedor ALTER COLUMN id_proveedor SET DEFAULT nextval('proveedor_id_proveedor_seq'::regclass);


--
-- TOC entry 1833 (class 2604 OID 88781)
-- Dependencies: 154 153 154
-- Name: id_requerimiento; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY requerimiento ALTER COLUMN id_requerimiento SET DEFAULT nextval('requerimiento_id_requerimiento_seq'::regclass);


--
-- TOC entry 1834 (class 2604 OID 88792)
-- Dependencies: 156 155 156
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuario ALTER COLUMN id SET DEFAULT nextval('usuario_id_seq'::regclass);


--
-- TOC entry 1864 (class 0 OID 88711)
-- Dependencies: 141
-- Data for Name: analista; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO analista (id_analista, apellido, cedula, correo, direccion, nombre, sexo, telefono) VALUES (1, NULL, '20186243', 'euge17@gmail.com', NULL, 'Eugenio', NULL, '11111111111');


--
-- TOC entry 1865 (class 0 OID 88722)
-- Dependencies: 143
-- Data for Name: clasificacion_repuesto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1866 (class 0 OID 88730)
-- Dependencies: 145
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1867 (class 0 OID 88739)
-- Dependencies: 146
-- Data for Name: history_logins; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1868 (class 0 OID 88746)
-- Dependencies: 148
-- Data for Name: marca_vehiculo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1869 (class 0 OID 88752)
-- Dependencies: 149
-- Data for Name: menu; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, id_padre) VALUES (1, NULL, 'z-icon-book', 'Paso 1', '/WEB-INF/views/sistema/funcionalidades/new_file.zul', NULL);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, id_padre) VALUES (2, NULL, 'z-icon-cog', 'Configuracion', '/WEB-INF/views/sistema/funcionalidades/new_file.zul', NULL);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, id_padre) VALUES (3, NULL, 'z-icon-lock', 'Seguridad', NULL, 2);
INSERT INTO menu (id_menu, actividad, icono, nombre, ruta, id_padre) VALUES (4, NULL, NULL, 'Usuarios', '/WEB-INF/views/sistema/seguridad/configuracion/usuarios/listaUsuarios.zul', 3);


--
-- TOC entry 1870 (class 0 OID 88760)
-- Dependencies: 150
-- Data for Name: persistent_logins; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1871 (class 0 OID 88767)
-- Dependencies: 152
-- Data for Name: proveedor; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1872 (class 0 OID 88778)
-- Dependencies: 154
-- Data for Name: requerimiento; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1873 (class 0 OID 88789)
-- Dependencies: 156
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO usuario (id, activo, foto, pasword, username, id_analista, id_proveedor) VALUES (2, true, '\\377\\330\\377\\340\\000\\020JFIF\\000\\001\\001\\000\\000\\001\\000\\001\\000\\000\\377\\333\\000\\204\\000\\011\\006\\007\\024\\020\\022\\024\\021\\023\\021\\022\\024\\022\\020\\031\\020\\026\\024\\017\\025\\026\\030\\024\\030\\032\\024\\027\\027\\027\\026\\026\\027\\025\\024$\\0354 \\030\\036%\\035\\026\\024!2#%)02..\\032\\0373D4,7(-.+\\001\\012\\012\\012\\016\\015\\016\\033\\020\\020\\033,& !5,.47-7/,04,,,.,,.4/-44,4,74./.,,,,,,,,,0,,4,,,,,,\\377\\300\\000\\021\\010\\000\\240\\000\\240\\003\\001\\021\\000\\002\\021\\001\\003\\021\\001\\377\\304\\000\\034\\000\\001\\001\\000\\002\\003\\001\\001\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\005\\001\\004\\002\\003\\006\\007\\010\\377\\304\\000B\\020\\000\\001\\003\\002\\002\\006\\007\\006\\003\\004\\012\\003\\000\\000\\000\\000\\001\\000\\002\\003\\004\\021\\005\\022\\006\\023!1QR\\007\\024"Aa\\221\\26134qrs\\3012\\201\\241Bb\\223\\321$5CSTc\\222\\262\\302\\322\\025\\026#\\377\\304\\000\\032\\001\\001\\000\\003\\001\\001\\001\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\002\\003\\004\\001\\005\\006\\377\\304\\0006\\021\\001\\000\\002\\001\\002\\003\\006\\002\\010\\005\\005\\000\\000\\000\\000\\000\\000\\001\\002\\003\\004\\021\\022!1\\005\\023AQaq2\\201\\024\\025BR\\221\\241\\261\\301"4\\321\\341\\360#$Sb\\361\\377\\332\\000\\014\\003\\001\\000\\002\\021\\003\\021\\000?\\000\\373\\212\\002\\002\\002\\002\\002\\002\\002\\002\\002\\002\\002\\002\\002\\002\\002\\002\\002\\002\\002\\002\\014\\023d\\023\\016(\\351\\011\\020F^\\006\\371\\011\\312\\337\\313\\212\\016Y\\252\\271`\\363r\\006j\\256X<\\334\\201\\232\\253\\226\\0177 f\\252\\345\\203\\315\\310\\031\\252\\271`\\363r\\006j\\256X<\\334\\201\\232\\253\\226\\0177 f\\252\\345\\203\\315\\310\\031\\252\\271`\\363r\\006j\\256X<\\334\\201\\232\\253\\226\\0177 f\\252\\345\\203\\315\\310\\031\\252\\271`\\363r\\014\\027\\325\\017\\330\\204\\370\\002\\344\\034\\251\\261K\\270G#\\014o;\\201\\332\\017\\301\\310(\\240  \\221\\214<\\275\\314\\200\\033k\\011\\314\\177toAR(\\303@kE\\200\\330\\002\\016h\\010\\010\\010\\010\\010\\010\\010\\010\\010\\010\\010\\010\\0105\\261\\012A3\\013N\\375\\355<\\017q\\010:0j\\263$c7\\342m\\332\\357\\210AA\\006\\034\\202+\\035z\\266x6O\\262\\013h\\010\\010\\010\\010\\010\\010\\010\\010\\010\\010\\010\\010\\010\\010\\010!\\340\\316\\355\\312?\\314\\223\\325\\005\\304\\030v\\344\\020\\341\\367\\266\\374\\262}\\220]@@@@@@@@@@@@@@@A\\007\\010\\366\\222\\375I=P^A\\207nA\\016\\037{o\\313''\\331\\005\\324\\004\\004\\004\\004\\004\\004\\004\\004\\004\\004\\004\\004\\004\\004\\004\\020p\\217i/\\324\\223\\325\\005\\344\\030v\\344\\020\\341\\367\\266\\374\\262}\\220]@@@@@@@@@@@@@@@A\\007\\010\\366\\222\\375I=P^A\\207nA\\016\\037{o\\313''\\331\\005\\251\\246k\\032\\\\\\367\\006\\265\\273K\\234l\\000\\361+\\261Y\\264\\355\\016L\\304s\\226\\217\\376v\\233\\374L\\037\\304o\\363V\\375\\037/\\335\\237\\301\\036\\362\\236p\\240\\012\\2456\\255V%\\014G,\\223F\\307Z\\371\\\\\\366\\264\\333\\215\\211VW\\025\\355\\033\\326&Q\\233\\326:\\313\\266z\\266F\\334\\357{\\032\\315\\235\\2678\\001\\267v\\325\\032\\322\\326\\235\\2429\\2736\\210\\215\\345\\242t\\216\\223\\374]?\\361Y\\374\\325\\277E\\315\\367''\\360\\2278\\353\\346\\345\\036?J\\342\\003j\\240$\\354\\000H\\323\\267\\315rt\\331\\243\\255g\\360s\\216\\276m\\272\\252\\310\\342\\000\\311#\\030\\016\\300\\\\\\340\\333\\237\\013\\252\\353K[\\341\\215\\322\\233Du\\227*j\\226J\\334\\321\\275\\257n\\354\\315!\\302\\377\\000\\020\\226\\255\\253;Z6"bz:\\352\\353\\342\\206\\332\\311cfk\\333;\\203om\\366\\276\\375\\341v\\230\\357\\177\\206&\\\\\\233Du\\227\\010\\361X\\034\\327<O\\021k-\\231\\301\\355!\\267\\335s}\\213\\263\\207$LD\\326y\\234u\\353\\273\\262\\222\\2729\\257\\253\\221\\217\\313k\\344pu\\257\\272\\366\\\\\\266;S\\342\\215\\210\\264OIv\\313 h.q\\015h\\332\\\\M\\200\\036%F"fv\\207fv\\352\\325\\203\\026\\202G\\006\\262x\\234\\347nk^\\322O\\300]N\\330rV7\\232\\313\\221z\\317(\\226\\352\\255!\\004\\034#\\332K\\365$\\365Ay\\006\\035\\271\\0048}\\355\\277,\\237d\\035\\035#\\377\\000VU\\3753\\352\\026\\336\\315\\376j\\236\\352\\363|\\022\\374\\363G\\205\\3114SJ\\306\\346m0\\214\\310;\\303^\\\\\\003\\255\\300e7_g|\\325\\245\\353[}\\255\\366\\371<\\230\\244\\314M\\274\\237U\\350\\227M\\263\\206\\320T\\036\\323vA)?\\210\\177v|Gw\\021\\360\\333\\363\\375\\257\\331\\37439\\361\\364\\361\\217\\335\\267M\\233\\354K\\317\\364\\336?\\2473\\350\\263\\375\\317[;\\017\\371y\\367W\\253\\370\\241\\243\\322eC\\337=4E\\307#)\\351r6\\373\\001{Fco\\033\\017%ge\\326\\265\\307{m\\316m?\\222\\274\\32331\\036\\217H\\336\\205M\\207\\364\\361\\335\\375\\205\\377\\000]b\\307\\365\\374\\177\\307\\371\\377\\000e\\361\\243\\365B\\323N\\216\\016\\033O\\326:\\320\\224fk\\0135Z\\277\\305\\337|\\345k\\321v\\254jrw|\\033|\\367\\375\\225\\345\\323\\360W}\\332\\330\\335S\\345\\301\\350\\263\\270\\270\\3075C\\032O(\\002\\303\\362R\\301J\\323]\\223\\207\\306"U\\336fq\\327\\346\\364Z\\023\\245\\321a\\230Ys\\200|\\322K.\\256\\000lI\\263v\\273\\203|V=n\\212\\372\\255^\\321\\312"#y[\\2074c\\307\\352\\211\\203\\340U\\232AP\\352\\211_\\2260l\\351\\210\\354\\264rD\\333\\355\\262\\325\\233S\\203\\263\\361\\306:G?/\\336Q\\255/\\236w\\227\\277\\323\\274\\022\\032,\\036h`\\2145\\203W~.9\\207i\\307\\274\\257\\037A\\250\\311\\233[[\\336w\\236m9\\351\\024\\305\\264z t\\012;U\\177\\010\\177\\344\\266v\\377\\000\\330\\371\\252\\321\\365\\226\\317MZM\\221\\255\\240\\215\\333^3\\316Gs?e\\277\\236\\377\\000\\200P\\354M''\\024\\316{xr\\217t\\265Y>\\314>m[\\204\\324P\\212Z\\223\\3303\\215l/\\035\\305\\244\\020\\017\\216\\343n\\005{t\\317\\213Q7\\307\\327nR\\3115\\2656\\263\\364>\\212c\\215\\257\\245\\216\\241\\273\\013\\205\\236\\336W\\215\\216\\036k\\3435zy\\323\\345\\234s\\341\\372=Lw\\343\\256\\352\\353:\\304\\034#\\332K\\365$\\365Ay\\006\\035\\271\\0048}\\355\\277,\\237d\\035\\035#\\377\\000VU\\3753\\352\\026\\336\\315\\376j\\236\\352\\363|\\022\\371\\367A\\014\\016\\226\\264\\020\\0101\\323\\002\\016\\320At\\267\\004/g\\267\\247jc\\230\\363\\237\\331\\227G\\322Q:E\\321\\027a\\223\\211\\241\\314)\\344u\\343{ox\\3366\\345\\277w\\020\\177\\222\\325\\331\\272\\350\\324\\343\\340\\277\\305\\035}c\\374\\352\\247>)\\245\\267\\216\\210zU\\244.\\304\\035\\024\\262\\013I\\034m\\215\\356\\346-$\\346\\267u\\356\\265\\3514\\261\\247\\213V\\275&wW\\223/\\036\\333\\252\\364\\217\\262\\252\\002A\\267W\\242?\\020\\006\\333,\\335\\231\\317\\015\\243\\376\\326K7+D\\372C\\350C\\246\\012?\\356\\252?\\322\\337\\373/\\037\\352=G\\2345}2\\276R\\363=!t\\201O\\210Rux\\2310~v:\\357\\015\\002\\315\\337\\336\\267vwfe\\323\\346\\343\\274\\306\\333J\\254\\332\\232\\336\\273D bM#\\006\\243\\331\\370\\252*\\210\\361\\026\\002\\377\\000\\242\\327\\212\\177\\337d\\366\\205V\\217\\364\\343\\346\\267\\242\\232\\016\\314K\\015\\316\\322#\\251d\\222\\006\\311\\334\\340-\\330\\177\\207\\217r\\313\\253\\355\\033i\\265[O:\\314G%\\230\\260w\\230\\367\\216\\255M\\027\\323*\\234\\032SIS\\033\\235\\023\\015\\235\\011\\374L\\375\\350\\317x;\\355\\270\\370+uZ\\014:\\332w\\270\\247\\234\\370\\371\\373\\230\\363[\\024\\360\\331\\364\\016\\220\\2618\\252\\260\\211\\245\\206F\\275\\216\\325\\331\\303\\346\\033\\010\\356>\\013\\306\\354\\374W\\305\\254\\255o\\033KN{E\\261L\\303\\306\\364C\\211\\262\\222*\\372\\211\\017f&\\304~''\\265`>''b\\364\\373c\\025\\262\\337\\025+\\326wf\\323Z+\\3052\\361\\201\\2658\\215D\\2236\\027M+\\235\\254{\\032\\334\\300\\002v\\003\\341\\262\\3366^\\246\\370\\264\\330\\242\\223m\\243\\244)\\332\\327\\266\\360\\364\\330\\373\\361z\\350\\3043P\\274\\261\\244\\026e\\207)i\\033;&\\373\\005\\226\\015<hp[\\216\\2319\\373\\256\\274e\\274m0\\354\\350\\207H\\272\\255Q\\245\\220\\221\\035I\\000\\003\\263,\\303`\\370_q\\370\\005\\316\\330\\322\\367\\270\\243-z\\327\\3644\\2718m\\303>/\\272\\257\\223zH8G\\264\\227\\352I\\352\\202\\362\\014;r\\010p\\373\\333~Y>\\310*\\327\\3212x\\335\\014\\255\\017\\216Ag0\\356#\\202\\236<\\226\\307h\\265gi\\207&7\\215\\245\\347ha\\242\\303\\346|4\\324\\356\\3279\\254t\\254\\205\\245\\3442\\347)~\\333\\015\\245\\326\\343\\265l\\311mF\\242\\221l\\226\\345\\341\\277\\232\\250\\232\\322v\\254)u\\252\\\\B\\235\\271\\362\\272\\032\\215\\201\\222v\\013\\254ml\\247m\\301T\\360f\\323\\344\\345\\326\\276IoK\\302a\\320\\\\2\\304\\365hl\\016Rs\\035\\207\\201\\333\\277\\301_\\365\\216\\257\\357J=\\326?%\\034O\\004\\243\\251dm\\2328^\\310\\306X\\256F\\300\\005\\254\\327_\\303\\364Tb\\324g\\305i\\232L\\304\\317T\\246\\224\\230\\215\\322\\033\\240\\330S\\257ha9E\\315\\237{\\016''n\\305\\247\\353\\015du\\264\\241\\334\\342\\033\\2418P\\261\\324\\300A\\335w\\354?\\015\\251\\365\\206\\262~\\324\\221\\213\\024+b\\030-\\025K#\\212H\\341{"\\260\\216;\\200\\033q`\\000\\007\\276\\313><\\371\\361ZmY\\230\\231\\352\\234\\326\\223\\26460\\272*j(\\314p\\210\\342\\214:\\345\\271\\200\\001\\307\\215\\316\\303\\261C.L\\271\\255\\305}\\346]\\254V\\261\\3117K\\350\\350_\\027Z\\253\\205\\262\\262\\021\\262@\\013\\210k\\210\\341\\274]]\\243\\311\\250\\255\\373\\2743\\264\\312986\\342\\262~\\027\\204\\341g_N\\330\\004^\\317_\\004\\231\\243\\270\\276f8\\264\\233;h6#\\305]\\2276\\2628rM\\267\\353\\264\\307?t"1N\\361\\263w\\377\\000S\\303Y\\021f\\246\\026\\305)i#=\\232\\3477v\\333\\355\\262\\257\\351\\272\\271\\266\\374S\\274%\\335\\343\\205\\014\\033\\010\\244\\242.d\\014\\212''Ib\\346\\203\\264\\330l\\276\\333\\356\\272\\2476|\\331\\366\\234\\2233\\262U\\255+\\321M\\325\\014\\015\\016/hi\\334\\353\\213y\\2528m\\276\\333''\\274 M\\241\\270}C\\3351\\247\\211\\356\\221\\305\\316\\220\\023\\265\\335\\346\\340\\357Z\\353\\256\\324\\343\\254R-1\\020\\257\\272\\245\\271\\354\\364\\213\\022\\324\\034#\\332K\\365$\\365Ay\\006\\035\\271\\0048}\\355\\277,\\237d\\027Py*:Yh\\353\\252\\2450\\276hk5ol\\261\\200\\\\\\3270\\020cp''v\\333\\213x\\257B\\367\\246l\\024\\257\\026\\323]\\343i\\365\\361\\376\\254\\365\\254\\322\\363;o\\272~!\\205\\313#\\252\\344\\222\\221\\317\\353PF\\332vv\\\\ax\\317\\231\\244\\356a%\\314va\\303\\301[\\2175+\\024\\255o\\267\\014\\316\\376\\261\\313\\361\\362\\331\\011\\244\\316\\3635\\353\\016\\021h\\344\\355x~\\\\\\305\\322\\3236\\251\\216\\003,\\2421\\035\\252\\030;\\234\\327\\007_\\230\\037\\000\\245:\\254s^\\035\\374''oM\\367\\376\\037o\\322N\\356\\323\\370\\301E\\243\\2632F8Du\\016\\222\\262WB\\353^9\\\\%\\015tc\\271\\257\\017\\027\\035\\304\\004\\276\\246\\226\\254\\304\\3178\\212\\306\\376q\\313\\257\\254lF;o\\023\\341\\3157\\007\\300''\\216\\226\\030\\337J\\367\\030\\3059\\236,\\221\\307\\2326\\023xZ\\340\\177\\372\\233\\220\\356\\326\\374\\266\\357Wf\\324\\343\\266kZ-\\327}\\247\\234\\363\\237\\037O.NF9\\362Y\\322<\\011\\263C\\232\\012\\022\\327\\272\\242\\225\\316ikA,c\\332\\347\\273-\\354\\321`~+6\\233Q4\\276\\327\\277-\\247\\363\\216I\\333\\034O8\\257\\213\\243\\020\\321\\234\\363\\3250S9\\260\\312p\\366\\261\\354\\015n]Y~g\\260\\336\\343)p*x\\365\\\\8\\351<\\\\\\343\\213}\\375v\\345>\\350[\\024\\361LDr\\345\\375\\334h0J\\226T6Z\\250\\033;[$\\341\\331\\000!\\3161\\302\\330\\352Lgy!\\217i\\033\\301''\\212d\\317\\212q\\315q[nQ\\327\\336w\\256\\377\\000?\\233\\265\\307h\\266\\366\\215\\377\\000\\363\\253\\266\\273\\014\\250v\\012\\350:\\270\\023\\274\\020)\\342\\015h\\027}\\306\\313\\330l\\361\\\\\\307\\227\\024kb\\374_\\303\\0363\\354\\354\\322\\323\\207\\207nn\\355!\\321\\316\\314r\\304\\311e\\226I\\251\\337,\\222\\006\\312\\366\\261\\200\\333\\262\\343k\\013\\376\\021\\336T4\\372\\256s[LDDLF\\334\\243y\\366\\375K\\342\\3511\\033\\313\\242,.F\\273<\\224\\257\\232#\\004\\320\\2622\\306\\002\\3313\\227\\027j\\357\\225\\202@F\\355\\331T\\3475f6\\255\\266\\235\\342|zm\\347\\350\\344Rz\\314xm\\376{\\247\\322hl\\317\\316\\331\\203\\257\\0355 \\022\\013\\027I4m\\222\\354l\\267\\314\\007i\\240\\361W_]\\2166\\232x\\332\\337(\\235\\274:\\177D{\\231\\236\\276M\\251(g~\\035MI\\325%\\017\\203\\251\\271\\344\\265\\204\\002\\311\\001pksY\\326\\000\\236\\012\\270\\311\\21657\\313\\307\\033O\\027\\237\\214%5\\264\\322+\\267M\\236\\267Fa1\\304\\346\\2269\\275\\271\\015\\313\\033\\036k\\233\\346\\310\\323f\\357\\267\\344\\274\\375M\\242\\326\\336''\\303\\337\\363_\\2126\\216\\212\\353:\\304\\034#\\332K\\365$\\365Ay\\006\\035\\271\\0048}\\355\\277,\\237d\\027P\\020\\020\\020\\020\\020\\020\\020\\020\\020\\020\\020\\020\\020\\020\\020A\\302=\\244\\277ROT\\027\\220a\\333\\220C\\207\\336\\333\\362\\311\\366Au\\001\\001\\001\\001\\001\\001\\001\\001\\001\\001\\001\\001\\001\\001\\001\\004\\034#\\332K\\365$\\365Ay\\006\\035\\271\\0048}\\355\\277,\\237d\\027P\\020\\020\\020\\020\\020\\020\\020\\020\\020\\020\\020\\020\\020\\020\\020A\\302=\\244\\277ROT\\027\\220a\\333\\220C\\207\\336\\333\\362\\311\\366Au\\001\\001\\001\\001\\001\\001\\001\\001\\001\\001\\001\\001\\001\\001\\001\\004\\034#\\332K\\365$\\365Ay\\006\\035\\271\\004(\\335j\\266x\\265\\343\\363Ay\\001\\001\\001\\001\\001\\001\\001\\001\\001\\001\\001\\001\\001\\001\\001\\004\\014\\025\\327|\\204n/y\\036h/\\240\\301A\\346q\\266\\2269\\257n\\366\\233\\204\\0260\\354U\\223\\001b\\003\\273\\330x\\370qA\\276\\200\\200\\200\\200\\200\\200\\200\\200\\200\\200\\200\\200\\200J\\010\\330\\3262\\3264\\261\\204\\027\\273e\\306\\341\\343t\\035z?\\005\\232\\020]@A\\243\\210Rg\\010<\\245f\\024\\346\\233\\265\\006\\266\\242ng~\\250\\032\\231\\271\\235\\346P53s;\\314\\240jf\\346w\\231@\\324\\315\\314\\3572\\201\\251\\233\\231\\336e\\003S73\\274\\312\\006\\246ngy\\224\\015L\\334\\316\\363(\\032\\231\\271\\235\\346P53s;\\314\\240jf\\346w\\231@\\324\\315\\314\\357\\325\\000S\\312\\177i\\337\\252\\012\\030v\\020I\\271A\\352\\251`\\312\\020l   \\353| \\367 \\352\\352m\\340\\201\\324\\333\\301\\003\\251\\267\\202\\007So\\004\\016\\246\\336\\010\\035M\\274\\020:\\233x u6\\360@\\352m\\340\\201\\324\\333\\301\\003\\251\\267\\202\\007So\\004\\016\\246\\336\\0102)\\033\\301\\007k"\\001\\0074\\004\\037\\377\\331', '147', 'euge', NULL, NULL);
INSERT INTO usuario (id, activo, foto, pasword, username, id_analista, id_proveedor) VALUES (1, false, NULL, '123', '123', NULL, NULL);
INSERT INTO usuario (id, activo, foto, pasword, username, id_analista, id_proveedor) VALUES (3, true, NULL, '123', 'admin', NULL, NULL);


--
-- TOC entry 1836 (class 2606 OID 88719)
-- Dependencies: 141 141
-- Name: analista_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY analista
    ADD CONSTRAINT analista_pkey PRIMARY KEY (id_analista);


--
-- TOC entry 1838 (class 2606 OID 88727)
-- Dependencies: 143 143
-- Name: clasificacion_repuesto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY clasificacion_repuesto
    ADD CONSTRAINT clasificacion_repuesto_pkey PRIMARY KEY (id);


--
-- TOC entry 1840 (class 2606 OID 88738)
-- Dependencies: 145 145
-- Name: cliente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (id_cliente);


--
-- TOC entry 1842 (class 2606 OID 88743)
-- Dependencies: 146 146
-- Name: history_logins_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY history_logins
    ADD CONSTRAINT history_logins_pkey PRIMARY KEY (id);


--
-- TOC entry 1844 (class 2606 OID 88751)
-- Dependencies: 148 148
-- Name: marca_vehiculo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY marca_vehiculo
    ADD CONSTRAINT marca_vehiculo_pkey PRIMARY KEY (id_marca_vehiculo);


--
-- TOC entry 1846 (class 2606 OID 88759)
-- Dependencies: 149 149
-- Name: menu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY menu
    ADD CONSTRAINT menu_pkey PRIMARY KEY (id_menu);


--
-- TOC entry 1848 (class 2606 OID 88764)
-- Dependencies: 150 150
-- Name: persistent_logins_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY persistent_logins
    ADD CONSTRAINT persistent_logins_pkey PRIMARY KEY (series);


--
-- TOC entry 1850 (class 2606 OID 88775)
-- Dependencies: 152 152
-- Name: proveedor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY proveedor
    ADD CONSTRAINT proveedor_pkey PRIMARY KEY (id_proveedor);


--
-- TOC entry 1852 (class 2606 OID 88786)
-- Dependencies: 154 154
-- Name: requerimiento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY requerimiento
    ADD CONSTRAINT requerimiento_pkey PRIMARY KEY (id_requerimiento);


--
-- TOC entry 1854 (class 2606 OID 88797)
-- Dependencies: 156 156
-- Name: usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 1856 (class 2606 OID 88826)
-- Dependencies: 156 156
-- Name: usuario_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_username_key UNIQUE (username);


--
-- TOC entry 1858 (class 2606 OID 88798)
-- Dependencies: 1845 149 149
-- Name: fk33155fb000c573; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY menu
    ADD CONSTRAINT fk33155fb000c573 FOREIGN KEY (id_padre) REFERENCES menu(id_menu);


--
-- TOC entry 1859 (class 2606 OID 88833)
-- Dependencies: 1855 150 156
-- Name: fkbd224d2a6a01c92; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY persistent_logins
    ADD CONSTRAINT fkbd224d2a6a01c92 FOREIGN KEY (username) REFERENCES usuario(username);


--
-- TOC entry 1860 (class 2606 OID 88803)
-- Dependencies: 141 154 1835
-- Name: fkd19e472517870034; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY requerimiento
    ADD CONSTRAINT fkd19e472517870034 FOREIGN KEY (id_analista) REFERENCES analista(id_analista);


--
-- TOC entry 1861 (class 2606 OID 88808)
-- Dependencies: 1839 145 154
-- Name: fkd19e4725ce63155e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY requerimiento
    ADD CONSTRAINT fkd19e4725ce63155e FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente);


--
-- TOC entry 1857 (class 2606 OID 88828)
-- Dependencies: 156 1855 146
-- Name: fkee0d8835a6a01c92; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY history_logins
    ADD CONSTRAINT fkee0d8835a6a01c92 FOREIGN KEY (username) REFERENCES usuario(username);


--
-- TOC entry 1862 (class 2606 OID 88813)
-- Dependencies: 141 156 1835
-- Name: fkf814f32e17870034; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT fkf814f32e17870034 FOREIGN KEY (id_analista) REFERENCES analista(id_analista);


--
-- TOC entry 1863 (class 2606 OID 88818)
-- Dependencies: 152 156 1849
-- Name: fkf814f32e6338eca6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT fkf814f32e6338eca6 FOREIGN KEY (id_proveedor) REFERENCES proveedor(id_proveedor);


--
-- TOC entry 1878 (class 0 OID 0)
-- Dependencies: 3
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2015-03-29 20:26:08

--
-- PostgreSQL database dump complete
--

