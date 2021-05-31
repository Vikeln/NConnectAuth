--
-- PostgreSQL database dump
--

-- Dumped from database version 11.2
-- Dumped by pg_dump version 12.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

--
-- Name: tbl_groups; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tbl_groups (
    id integer NOT NULL,
    created_by character varying(255),
    created_ip_address character varying(255),
    date_time_created timestamp without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp without time zone,
    last_modified_ip character varying(255),
    name character varying(255) NOT NULL,
    parent integer,
    tenant integer NOT NULL,
    ip_address character varying(255)
);


ALTER TABLE public.tbl_groups OWNER TO postgres;

--
-- Name: tbl_groups_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tbl_groups_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tbl_groups_id_seq OWNER TO postgres;

--
-- Name: tbl_groups_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tbl_groups_id_seq OWNED BY public.tbl_groups.id;


--
-- Name: tbl_permissions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tbl_permissions (
    id integer NOT NULL,
    name character varying(255),
    created_by character varying(255),
    created_ip_address character varying(255),
    date_time_created timestamp without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp without time zone,
    last_modified_ip character varying(255),
    ip_address character varying(255)
);


ALTER TABLE public.tbl_permissions OWNER TO postgres;

--
-- Name: tbl_permissions_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tbl_permissions_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tbl_permissions_id_seq OWNER TO postgres;

--
-- Name: tbl_permissions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tbl_permissions_id_seq OWNED BY public.tbl_permissions.id;


--
-- Name: tbl_roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tbl_roles (
    id integer NOT NULL,
    created_by character varying(255),
    created_ip_address character varying(255),
    date_time_created timestamp without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp without time zone,
    last_modified_ip character varying(255),
    role_name character varying(255),
    tenant integer NOT NULL,
    ip_address character varying(255)
);


ALTER TABLE public.tbl_roles OWNER TO postgres;

--
-- Name: tbl_roles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tbl_roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tbl_roles_id_seq OWNER TO postgres;

--
-- Name: tbl_roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tbl_roles_id_seq OWNED BY public.tbl_roles.id;


--
-- Name: tbl_roles_permissions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tbl_roles_permissions (
    role_id integer NOT NULL,
    permission_id integer NOT NULL
);


ALTER TABLE public.tbl_roles_permissions OWNER TO postgres;

--
-- Name: tbl_tenants; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tbl_tenants (
    id integer NOT NULL,
    cust_id character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    active_status boolean DEFAULT true
);


ALTER TABLE public.tbl_tenants OWNER TO postgres;

--
-- Name: tbl_tenants_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tbl_tenants_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tbl_tenants_id_seq OWNER TO postgres;

--
-- Name: tbl_tenants_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tbl_tenants_id_seq OWNED BY public.tbl_tenants.id;


--
-- Name: tbl_user_groups; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tbl_user_groups (
    user_id integer NOT NULL,
    user_group_id integer NOT NULL
);


ALTER TABLE public.tbl_user_groups OWNER TO postgres;

--
-- Name: tbl_users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tbl_users (
    id integer NOT NULL,
    created_by character varying(255),
    created_ip_address character varying(255),
    date_time_created timestamp without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp without time zone,
    last_modified_ip character varying(255),
    correlator character varying(255),
    password_expiry_date timestamp without time zone NOT NULL,
    email character varying(255),
    enabled boolean NOT NULL,
    first_name character varying(50),
    last_login timestamp without time zone,
    last_name character varying(255),
    other_names character varying(255),
    phone_number character varying(255),
    status integer NOT NULL,
    is_super_user boolean,
    user_name character varying(255),
    role_id integer NOT NULL,
    tenant integer,
    ip_address character varying(255)
);


ALTER TABLE public.tbl_users OWNER TO postgres;

--
-- Name: tbl_users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tbl_users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tbl_users_id_seq OWNER TO postgres;

--
-- Name: tbl_users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tbl_users_id_seq OWNED BY public.tbl_users.id;


--
-- Name: tbl_groups id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_groups ALTER COLUMN id SET DEFAULT nextval('public.tbl_groups_id_seq'::regclass);


--
-- Name: tbl_permissions id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_permissions ALTER COLUMN id SET DEFAULT nextval('public.tbl_permissions_id_seq'::regclass);


--
-- Name: tbl_roles id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_roles ALTER COLUMN id SET DEFAULT nextval('public.tbl_roles_id_seq'::regclass);


--
-- Name: tbl_tenants id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_tenants ALTER COLUMN id SET DEFAULT nextval('public.tbl_tenants_id_seq'::regclass);


--
-- Name: tbl_users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_users ALTER COLUMN id SET DEFAULT nextval('public.tbl_users_id_seq'::regclass);


--
-- Data for Name: tbl_groups; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tbl_groups (id, created_by, created_ip_address, date_time_created, last_modified_by, last_modified_date, last_modified_ip, name, parent, tenant, ip_address) FROM stdin;
1	anonymousUser	\N	2020-09-10 00:55:27.4	anonymousUser	2020-09-10 00:55:27.4	\N	Ground Men	\N	1	0:0:0:0:0:0:0:1
\.


--
-- Data for Name: tbl_permissions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tbl_permissions (id, name, created_by, created_ip_address, date_time_created, last_modified_by, last_modified_date, last_modified_ip, ip_address) FROM stdin;
1	CAN_VIEW_ROLES	\N	\N	\N	\N	\N	\N	\N
2	CAN_CREATE_ROLES	\N	\N	\N	\N	\N	\N	\N
3	CAN_EDIT_ROLES	\N	\N	\N	\N	\N	\N	\N
4	CAN_DELETE_ROLES	\N	\N	\N	\N	\N	\N	\N
5	CAN_VIEW_USERS	\N	\N	\N	\N	\N	\N	\N
6	CAN_VIEW_USER_DETAILS	\N	\N	\N	\N	\N	\N	\N
7	CAN_CREATE_USERS	\N	\N	\N	\N	\N	\N	\N
8	CAN_EDIT_USERS	\N	\N	\N	\N	\N	\N	\N
9	CAN_UNLOCK_USER_ACCOUNT	\N	\N	\N	\N	\N	\N	\N
10	CAN_RESET_USER_PASSWORD	\N	\N	\N	\N	\N	\N	\N
11	CAN_DELETE_USERS	\N	\N	\N	\N	\N	\N	\N
12	CAN_DEACTIVATE_USER	\N	\N	\N	\N	\N	\N	\N
13	CAN_VIEW_PERMISSIONS	\N	\N	\N	\N	\N	\N	\N
14	CAN_CREATE_PERMISSIONS	\N	\N	\N	\N	\N	\N	\N
15	CAN_EDIT_PERMISSIONS	\N	\N	\N	\N	\N	\N	\N
16	CAN_DELETE_PERMISSIONS	\N	\N	\N	\N	\N	\N	\N
17	CAN_VIEW_CM_CHANNELS	\N	\N	\N	\N	\N	\N	\N
18	CAN_CREATE_CM_CHANNELS	\N	\N	\N	\N	\N	\N	\N
19	CAN_EDIT_CM_CHANNELS	\N	\N	\N	\N	\N	\N	\N
20	CAN_DELETE_CM_CHANNELS	\N	\N	\N	\N	\N	\N	\N
21	CAN_VIEW_CUSTOMERS	\N	\N	\N	\N	\N	\N	\N
22	CAN_CREATE_CUSTOMERS	\N	\N	\N	\N	\N	\N	\N
23	CAN_EDIT_CUSTOMERS	\N	\N	\N	\N	\N	\N	\N
24	CAN_RESET_CUSTOMER_PIN	\N	\N	\N	\N	\N	\N	\N
25	CAN_DELETE_CUSTOMERS	\N	\N	\N	\N	\N	\N	\N
26	CAN_DEACTIVATE_CUSTOMERS	\N	\N	\N	\N	\N	\N	\N
27	CAN_ACTIVATE_CUSTOMERS	\N	\N	\N	\N	\N	\N	\N
28	CAN_VIEW_CUSTOMER_DETAILS	\N	\N	\N	\N	\N	\N	\N
29	CAN_VIEW_CUSTOMER_STATUS	\N	\N	\N	\N	\N	\N	\N
30	CAN_CREATE_CUSTOMER_STATUS	\N	\N	\N	\N	\N	\N	\N
31	CAN_EDIT_CUSTOMER_STATUS	\N	\N	\N	\N	\N	\N	\N
32	CAN_DELETE_CUSTOMER_STATUS	\N	\N	\N	\N	\N	\N	\N
33	CAN_VIEW_CUSTOMER_TYPE	\N	\N	\N	\N	\N	\N	\N
34	CAN_CREATE_CUSTOMER_TYPE	\N	\N	\N	\N	\N	\N	\N
35	CAN_EDIT_CUSTOMER_TYPE	\N	\N	\N	\N	\N	\N	\N
36	CAN_DELETE_CUSTOMER_TYPE	\N	\N	\N	\N	\N	\N	\N
37	CAN_VIEW_DOC_TYPE	\N	\N	\N	\N	\N	\N	\N
38	CAN_CREATE_DOC_TYPE	\N	\N	\N	\N	\N	\N	\N
39	CAN_EDIT_DOC_TYPE	\N	\N	\N	\N	\N	\N	\N
40	CAN_DELETE_DOC_TYPE	\N	\N	\N	\N	\N	\N	\N
41	CAN_VIEW_KYC_TYPE	\N	\N	\N	\N	\N	\N	\N
42	CAN_CREATE_KYC_TYPE	\N	\N	\N	\N	\N	\N	\N
43	CAN_EDIT_KYC_TYPE	\N	\N	\N	\N	\N	\N	\N
44	CAN_DELETE_KYC_TYPE	\N	\N	\N	\N	\N	\N	\N
45	CAN_VIEW_KYC	\N	\N	\N	\N	\N	\N	\N
46	CAN_CREATE_KYC	\N	\N	\N	\N	\N	\N	\N
47	CAN_EDIT_KYC	\N	\N	\N	\N	\N	\N	\N
48	CAN_DELETE_KYC	\N	\N	\N	\N	\N	\N	\N
49	CAN_VIEW_FEE_AND_CHARGES	\N	\N	\N	\N	\N	\N	\N
50	CAN_CREATE_FEE_AND_CHARGES	\N	\N	\N	\N	\N	\N	\N
51	CAN_EDIT_FEE_AND_CHARGES	\N	\N	\N	\N	\N	\N	\N
52	CAN_DELETE_FEE_AND_CHARGES	\N	\N	\N	\N	\N	\N	\N
53	CAN_VIEW_SERVICE_PROVIDER	\N	\N	\N	\N	\N	\N	\N
54	CAN_CREATE_SERVICE_PROVIDER	\N	\N	\N	\N	\N	\N	\N
55	CAN_EDIT_SERVICE_PROVIDER	\N	\N	\N	\N	\N	\N	\N
56	CAN_DELETE_SERVICE_PROVIDER	\N	\N	\N	\N	\N	\N	\N
57	CAN_VIEW_LOAN_PRODUCT	\N	\N	\N	\N	\N	\N	\N
58	CAN_CREATE_LOAN_PRODUCT	\N	\N	\N	\N	\N	\N	\N
59	CAN_EDIT_LOAN_PRODUCT	\N	\N	\N	\N	\N	\N	\N
60	CAN_DELETE_LOAN_PRODUCT	\N	\N	\N	\N	\N	\N	\N
61	CAN_VIEW_LOAN_REPAYMENTS	\N	\N	\N	\N	\N	\N	\N
62	CAN_CREATE_LOAN_REPAYMENTS	\N	\N	\N	\N	\N	\N	\N
63	CAN_EDIT_LOAN_REPAYMENTS	\N	\N	\N	\N	\N	\N	\N
64	CAN_DELETE_LOAN_REPAYMENTS	\N	\N	\N	\N	\N	\N	\N
65	CAN_VIEW_LOAN_PRODUCT_ACOUNT	\N	\N	\N	\N	\N	\N	\N
66	CAN_CREATE_LOAN_PRODUCT_ACOUNT	\N	\N	\N	\N	\N	\N	\N
67	CAN_EDIT_LOAN_PRODUCT_ACOUNT	\N	\N	\N	\N	\N	\N	\N
68	CAN_DELETE_LOAN_PRODUCT_ACOUNT	\N	\N	\N	\N	\N	\N	\N
69	CAN_VIEW_LOAN_INSTALLMENTS	\N	\N	\N	\N	\N	\N	\N
70	CAN_CREATE_LOAN_INSTALLMENTS	\N	\N	\N	\N	\N	\N	\N
71	CAN_EDIT_LOAN_INSTALLMENTS	\N	\N	\N	\N	\N	\N	\N
72	CAN_DELETE_LOAN_INSTALLMENTS	\N	\N	\N	\N	\N	\N	\N
73	CAN_VIEW_LOAN_CHECKS	\N	\N	\N	\N	\N	\N	\N
74	CAN_CREATE_LOAN_CHECKS	\N	\N	\N	\N	\N	\N	\N
75	CAN_EDIT_LOAN_CHECKS	\N	\N	\N	\N	\N	\N	\N
76	CAN_DELETE_LOAN_CHECKS	\N	\N	\N	\N	\N	\N	\N
77	CAN_VIEW_LOAN_APPLICATIONS	\N	\N	\N	\N	\N	\N	\N
78	CAN_CREATE_LOAN_APPLICATIONS	\N	\N	\N	\N	\N	\N	\N
79	CAN_EDIT_LOAN_APPLICATIONS	\N	\N	\N	\N	\N	\N	\N
80	CAN_DELETE_LOAN_APPLICATIONS	\N	\N	\N	\N	\N	\N	\N
81	CAN_VIEW_LOAN_ACCOUNTS	\N	\N	\N	\N	\N	\N	\N
82	CAN_CREATE_LOAN_ACCOUNTS	\N	\N	\N	\N	\N	\N	\N
83	CAN_EDIT_LOAN_ACCOUNTS	\N	\N	\N	\N	\N	\N	\N
84	CAN_DELETE_LOAN_ACCOUNTS	\N	\N	\N	\N	\N	\N	\N
85	CAN_VIEW_LOAN_ACCOUNT_STATUS	\N	\N	\N	\N	\N	\N	\N
86	CAN_CREATE_LOAN_ACCOUNT_STATUS	\N	\N	\N	\N	\N	\N	\N
87	CAN_EDIT_LOAN_ACCOUNT_STATUS	\N	\N	\N	\N	\N	\N	\N
88	CAN_DELETE_LOAN_ACCOUNT_STATUS	\N	\N	\N	\N	\N	\N	\N
89	CAN_VIEW_DISBURSEMENT_TRANSACTIONS	\N	\N	\N	\N	\N	\N	\N
90	CAN_CREATE_DISBURSEMENT_TRANSACTIONS	\N	\N	\N	\N	\N	\N	\N
91	CAN_EDIT_DISBURSEMENT_TRANSACTIONS	\N	\N	\N	\N	\N	\N	\N
92	CAN_DELETE_DISBURSEMENT_TRANSACTIONS	\N	\N	\N	\N	\N	\N	\N
93	CAN_VIEW_CUSTOMER_PROFILES	\N	\N	\N	\N	\N	\N	\N
94	CAN_CREATE_CUSTOMER_PROFILES	\N	\N	\N	\N	\N	\N	\N
95	CAN_EDIT_CUSTOMER_PROFILES	\N	\N	\N	\N	\N	\N	\N
96	CAN_DELETE_CUSTOMER_PROFILES	\N	\N	\N	\N	\N	\N	\N
97	CAN_VIEW_CUSTOMER_SUBSCRIPTIONS	\N	\N	\N	\N	\N	\N	\N
98	CAN_CREATE_CUSTOMER_SUBSCRIPTIONS	\N	\N	\N	\N	\N	\N	\N
99	CAN_EDIT_CUSTOMER_SUBSCRIPTIONS	\N	\N	\N	\N	\N	\N	\N
100	CAN_DELETE_CUSTOMER_SUBSCRIPTIONS	\N	\N	\N	\N	\N	\N	\N
101	CAN_ACCCESS_COMMUNICATIONS	\N	\N	\N	\N	\N	\N	\N
102	CAN_UPLOAD_CUSTOMERS	\N	\N	\N	\N	\N	\N	\N
103	CAN_VIEW_ORGANIZATIONS	\N	\N	\N	\N	\N	\N	\N
104	CAN_CREATE_ORGANIZATIONS	\N	\N	\N	\N	\N	\N	\N
105	CAN_EDIT_ORGANIZATION_ADMIN	\N	\N	\N	\N	\N	\N	\N
106	CAN_EDIT_ORGANIZATIONS	\N	\N	\N	\N	\N	\N	\N
107	CAN_DELETE_ORGANIZATIONS	\N	\N	\N	\N	\N	\N	\N
108	CAN_ACTIVATE_ORGANIZATION	\N	\N	\N	\N	\N	\N	\N
109	CAN_DEACTIVATE_ORGANIZATION	\N	\N	\N	\N	\N	\N	\N
110	CAN_VIEW_APPROVALS	\N	\N	\N	\N	\N	\N	\N
111	CAN_APPROVE	\N	\N	\N	\N	\N	\N	\N
112	CAN_VIEW_MESSAGE_TYPES	\N	\N	\N	\N	\N	\N	\N
113	CAN_VIEW_MESSAGE_TEMPLATES	\N	\N	\N	\N	\N	\N	\N
114	CAN_VIEW_OUTBOX	\N	\N	\N	\N	\N	\N	\N
115	CAN_VIEW_SETUP	\N	\N	\N	\N	\N	\N	\N
116	CAN_VIEW_CUSTOMER_EXCEPTIONS	\N	\N	\N	\N	\N	\N	\N
117	CAN_UPDATE_CUSTOMER_SALARY	\N	\N	\N	\N	\N	\N	\N
118	CAN_APPROVE_UPDATE_CUSTOMER_SALARY	\N	\N	\N	\N	\N	\N	\N
119	CAN_INITIATE_LOAN_REPAYMENT	\N	\N	\N	\N	\N	\N	\N
120	CAN_VIEW_EVENT_LOGS	\N	\N	\N	\N	\N	\N	\N
121	CAN_VIEW_USER_EVENTS_LOGS	\N	\N	\N	\N	\N	\N	\N
\.


--
-- Data for Name: tbl_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tbl_roles (id, created_by, created_ip_address, date_time_created, last_modified_by, last_modified_date, last_modified_ip, role_name, tenant, ip_address) FROM stdin;
2	\N	\N	\N	\N	\N	\N	Super Admin	1	\N
7	anonymousUser	\N	2020-09-10 00:13:30.748	anonymousUser	2020-09-10 00:13:30.748	\N	MFS ADMIN ROLE	1	0:0:0:0:0:0:0:1
\.


--
-- Data for Name: tbl_roles_permissions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tbl_roles_permissions (role_id, permission_id) FROM stdin;
2	1
2	2
2	3
2	4
2	5
2	6
2	7
2	8
2	9
2	10
2	11
2	12
2	13
2	14
2	15
2	16
2	17
2	18
2	19
2	20
2	21
2	22
2	23
2	24
2	25
2	26
2	27
2	28
2	29
2	30
2	31
2	32
2	33
2	34
2	35
2	36
2	37
2	38
2	39
2	40
2	41
2	42
2	43
2	44
2	45
2	46
2	47
2	48
2	49
2	50
2	51
2	52
2	53
2	54
2	55
2	56
2	57
2	58
2	59
2	60
2	61
2	62
2	63
2	64
2	65
2	66
2	67
2	68
2	69
2	70
2	71
2	72
2	73
2	74
2	75
2	76
2	77
2	78
2	79
2	80
2	81
2	82
2	83
2	84
2	85
2	86
2	87
2	88
2	89
2	90
2	91
2	92
2	93
2	94
2	95
2	96
2	97
2	98
2	99
2	100
2	101
2	102
2	103
2	104
2	105
2	106
2	107
2	108
2	109
2	110
2	111
2	112
2	113
2	114
2	115
2	116
2	117
2	118
2	119
2	120
2	121
7	1
7	2
7	3
7	4
7	5
\.


--
-- Data for Name: tbl_tenants; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tbl_tenants (id, cust_id, name, active_status) FROM stdin;
1	MFS00002	MFS Salary Advance	t
2	MFS0001	MFS Super	t
3	MFS0001	MFS Super	t
4	MFS0001	MFS Super	t
5	MFS0001	MFS Super	t
6	MFS0001	MFS Super	t
7	MFS0001	MFS Super	t
8	MFS0001	MFS Super	t
9	MFS0001	MFS Super	t
10	MFS0001	MFS Super	t
11	MFS0001	MFS Super	t
12	MFS0001	MFS Super	t
13	MFS0001	MFS Super	t
14	MFS0001	MFS Super	t
\.


--
-- Data for Name: tbl_user_groups; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tbl_user_groups (user_id, user_group_id) FROM stdin;
\.


--
-- Data for Name: tbl_users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tbl_users (id, created_by, created_ip_address, date_time_created, last_modified_by, last_modified_date, last_modified_ip, correlator, password_expiry_date, email, enabled, first_name, last_login, last_name, other_names, phone_number, status, is_super_user, user_name, role_id, tenant, ip_address) FROM stdin;
4	\N	\N	\N	\N	\N	\N	\N	2020-10-09 23:17:21.609	rmunge@mfs.co.ke	t	Super	\N	Admin	\N	0709184000	-1	t	admin	2	\N	\N
5	anonymousUser	\N	2020-09-10 01:05:08.2	anonymousUser	2020-09-10 13:33:48.844	\N	\N	2020-10-25 01:05:07.991	jakello@mfs.co.ke	t	John	\N	Akello	\N	\N	-1	f	jakello	7	\N	0:0:0:0:0:0:0:1
6	anonymousUser	\N	2020-09-10 13:35:15.655	anonymousUser	2020-09-10 13:35:15.655	\N	\N	2020-10-25 13:35:15.653	kthuku@mfs.co.ke	f	Kelvin	\N	Thuku	\N	\N	-1	f	developer	7	\N	0:0:0:0:0:0:0:1
7	anonymousUser	\N	2020-09-10 13:40:09.825	anonymousUser	2020-09-10 13:40:46.695	\N	\N	2020-10-25 13:40:09.786	pmutwiri@mfs.co.ke	t	Patrick	\N	Mutwiri	\N	\N	-1	f	pmutwiri	7	1	0:0:0:0:0:0:0:1
9	anonymousUser	\N	2020-09-10 13:49:48.015	anonymousUser	2020-09-10 13:49:48.015	\N	\N	2020-10-25 13:49:48.014	emutanda@mfs.co.ke	f	Extus	\N	Mutanda	\N	\N	-1	f	emutanda	7	1	0:0:0:0:0:0:0:1
10	anonymousUser	\N	2020-09-10 13:54:33.445	Application Root	2020-09-10 14:07:19.865	\N	c1067b43-7523-4bd9-b018-5a1e5fb202d8	2020-10-25 13:54:33.398	tmwaura@mfs.co.ke	t	Timothy	\N	Mwaura	\N	\N	-1	f	tmwaura	7	1	\N
\.


--
-- Name: tbl_groups_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tbl_groups_id_seq', 2, true);


--
-- Name: tbl_permissions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tbl_permissions_id_seq', 121, true);


--
-- Name: tbl_roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tbl_roles_id_seq', 7, true);


--
-- Name: tbl_tenants_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tbl_tenants_id_seq', 14, true);


--
-- Name: tbl_users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tbl_users_id_seq', 10, true);


--
-- Name: tbl_groups tbl_groups_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_groups
    ADD CONSTRAINT tbl_groups_pkey PRIMARY KEY (id);


--
-- Name: tbl_permissions tbl_permissions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_permissions
    ADD CONSTRAINT tbl_permissions_pkey PRIMARY KEY (id);


--
-- Name: tbl_roles tbl_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_roles
    ADD CONSTRAINT tbl_roles_pkey PRIMARY KEY (id);


--
-- Name: tbl_tenants tbl_tenants_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_tenants
    ADD CONSTRAINT tbl_tenants_pkey PRIMARY KEY (id);


--
-- Name: tbl_users tbl_users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_users
    ADD CONSTRAINT tbl_users_pkey PRIMARY KEY (id);


--
-- Name: tbl_permissions uk_gvbdxaa7ayfls2dhf5boc3pqh; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_permissions
    ADD CONSTRAINT uk_gvbdxaa7ayfls2dhf5boc3pqh UNIQUE (name);


--
-- Name: tbl_users uk_j562wwmipqt96rkoqbo0jc34; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_users
    ADD CONSTRAINT uk_j562wwmipqt96rkoqbo0jc34 UNIQUE (email);


--
-- Name: tbl_roles uk_pqdas30v3akex6bi6i9f9v5k6; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_roles
    ADD CONSTRAINT uk_pqdas30v3akex6bi6i9f9v5k6 UNIQUE (role_name);


--
-- Name: tbl_users uk_q830f72qe6mhpkt2aec8p0byg; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_users
    ADD CONSTRAINT uk_q830f72qe6mhpkt2aec8p0byg UNIQUE (user_name);


--
-- Name: tbl_users uniqueemailconstraint; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_users
    ADD CONSTRAINT uniqueemailconstraint UNIQUE (email);


--
-- Name: tbl_permissions uniquepermissionname; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_permissions
    ADD CONSTRAINT uniquepermissionname UNIQUE (name);


--
-- Name: tbl_roles uniquerolenameconstraint; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_roles
    ADD CONSTRAINT uniquerolenameconstraint UNIQUE (role_name);


--
-- Name: tbl_users uniqueusernameconstraint; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_users
    ADD CONSTRAINT uniqueusernameconstraint UNIQUE (user_name);


--
-- Name: tbl_user_groups fk2tyqa1rtn4pjqgpteu0fci9go; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_user_groups
    ADD CONSTRAINT fk2tyqa1rtn4pjqgpteu0fci9go FOREIGN KEY (user_group_id) REFERENCES public.tbl_groups(id);


--
-- Name: tbl_roles_permissions fk5bq23g4ja5motg71vpdqi0xg; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_roles_permissions
    ADD CONSTRAINT fk5bq23g4ja5motg71vpdqi0xg FOREIGN KEY (role_id) REFERENCES public.tbl_roles(id);


--
-- Name: tbl_groups fk5ro74wrxamjvv99ckikn8vk2g; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_groups
    ADD CONSTRAINT fk5ro74wrxamjvv99ckikn8vk2g FOREIGN KEY (tenant) REFERENCES public.tbl_tenants(id);


--
-- Name: tbl_roles fk8i6am9c4orsppy1h3nxas05hu; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_roles
    ADD CONSTRAINT fk8i6am9c4orsppy1h3nxas05hu FOREIGN KEY (tenant) REFERENCES public.tbl_tenants(id);


--
-- Name: tbl_groups fkagh3e5nv2p34wjrou67pir1s9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_groups
    ADD CONSTRAINT fkagh3e5nv2p34wjrou67pir1s9 FOREIGN KEY (parent) REFERENCES public.tbl_groups(id);


--
-- Name: tbl_users fkalwd0ykxm005ql77cdp6l64dk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_users
    ADD CONSTRAINT fkalwd0ykxm005ql77cdp6l64dk FOREIGN KEY (tenant) REFERENCES public.tbl_tenants(id);


--
-- Name: tbl_users fkdfa9voks1x35h59d82jwqumxt; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_users
    ADD CONSTRAINT fkdfa9voks1x35h59d82jwqumxt FOREIGN KEY (role_id) REFERENCES public.tbl_roles(id);


--
-- Name: tbl_user_groups fkoaj42o39kviaok2xk2e10q0o3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_user_groups
    ADD CONSTRAINT fkoaj42o39kviaok2xk2e10q0o3 FOREIGN KEY (user_id) REFERENCES public.tbl_users(id);


--
-- Name: tbl_roles_permissions fkrttsvutjm7vd6uyy0jw0yq45w; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_roles_permissions
    ADD CONSTRAINT fkrttsvutjm7vd6uyy0jw0yq45w FOREIGN KEY (permission_id) REFERENCES public.tbl_permissions(id);


--
-- PostgreSQL database dump complete
--

