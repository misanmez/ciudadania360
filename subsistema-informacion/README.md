# subsistema-informacion

## Propósito
Gestiona el conocimiento que se ofrece al ciudadano y al agente: contenidos, guías operativas, FAQ, sugerencias, recomendaciones y datasets e indicadores que alimentan el portal de datos abiertos y la analítica (ETL/BI del esquema).

## Entidades y atributos

### 1) Informacion
Artículo o ficha informativa (pública o interna).

| Campo               | Tipo                  | Req | Descripción / Por qué |
|--------------------|---------------------|-----|----------------------|
| id                 | UUID                | ✔️  | — |
| titulo             | String(150)         | ✔️  | SEO/UX |
| contenido          | Text(Markdown/HTML) | ✔️  | Cuerpo del artículo |
| etiquetas          | String[]            |     | Búsqueda facetada |
| audiencia          | ENUM(PUBLICA,INTERNA) | ✔️ | Control de acceso |
| estadoPublicacion  | ENUM(BORRADOR,PUBLICADA,ARCHIVADA) | ✔️ | Flujo editorial |
| propietario        | String              |     | Unidad responsable |
| version            | Integer             | ✔️  | Historial de cambios |
| fechaPublicacion/Arch | Instant           |     | SLAs informativos |

---

### 2) Instruccion
Procedimiento paso a paso (para agentes o ciudadanía).

| Campo               | Tipo                 | Req | Descripción / Por qué |
|--------------------|--------------------|-----|----------------------|
| id                 | UUID               | ✔️  | — |
| titulo             | String             | ✔️  | — |
| descripcion        | Text               | ✔️  | Contexto |
| pasos              | Text(Markdown)     | ✔️  | Checklists |
| documentosRequeridos | JSONB             |     | Validación previa de trámites |
| tiempoEstimado     | Integer(min)       |     | Gestión de expectativas |
| aplicabilidad      | String             |     | Casuística (quién/cuándo) |
| canal              | ENUM(ONLINE,PRESENCIAL,TELEFONO) | | Diferencia circuitos |
| etiquetas          | String[]           |     | — |

---

### 3) Duda (FAQ)

| Campo   | Tipo               | Req | Descripción / Por qué |
|--------|------------------|-----|----------------------|
| id     | UUID             | ✔️  | — |
| pregunta | String(200)     | ✔️  | — |
| respuesta | Text           | ✔️  | — |
| categoria | String         |     | Agrupación |
| vistas  | Long             |     | Medición de utilidad |
| util    | Integer          |     | Votos útiles |
| estado  | ENUM(BORRADOR,VALIDADA,PUBLICADA) | ✔️ | Control editorial |

---

### 4) Sugerencia
Propuestas de mejora recibidas (pueden venir del subsistema ciudadano).

| Campo          | Tipo                  | Req | Descripción / Por qué |
|----------------|---------------------|-----|----------------------|
| id             | UUID                | ✔️  | — |
| titulo         | String              | ✔️  | — |
| descripcion    | Text                | ✔️  | — |
| ciudadanoId    | UUID                |     | Anónimas permitidas |
| estado         | ENUM(NUEVA,EN_ESTUDIO,ACEPTADA,RECHAZADA,IMPLEMENTADA) | ✔️ | Ciclo de evaluación |
| prioridad      | ENUM(BAJA,MEDIA,ALTA) |     | Roadmap |
| areaResponsable | String             |     | Derivación |
| fecha          | Instant             | ✔️  | — |

---

### 5) Recomendacion
Contenidos o acciones sugeridas a segmentos de usuarios.

| Campo             | Tipo                  | Req | Descripción / Por qué |
|------------------|---------------------|-----|----------------------|
| id                | UUID                | ✔️  | — |
| titulo            | String              | ✔️  | — |
| descripcion       | Text                | ✔️  | — |
| criterio          | JSONB               | ✔️  | Reglas de segmentación |
| destino           | ENUM(CIUDADANOS,AGENTES,AMBOS) | ✔️ | Difusión adecuada |
| vigenciaDesde/Hasta | Instant           |     | Campañas temporales |

---

### 6) Indicador
Métrica operativa para cuadros de mando (Power BI/BI).

| Campo             | Tipo                     | Req | Descripción / Por qué |
|------------------|------------------------|-----|----------------------|
| id                | UUID                   | ✔️  | — |
| codigo            | String(50)             | ✔️  | Estable para ETL |
| nombre            | String                 | ✔️  | KPI legible |
| descripcion       | Text                   |     | Definición |
| definicionCalculo | Text                   | ✔️  | Transparencia del KPI |
| frecuencia        | ENUM(DIARIA,SEM, MENSUAL,TRIMESTRAL) | ✔️ | Planificación ETL |
| unidad            | String                 | ✔️  | (% , días, nº) |
| responsable       | String                 |     | Gobierno de dato |
| kpi               | Boolean                |     | Señala KPI prioritario |
| origenDatos       | String                 | ✔️  | Linaje de datos |
| datasetId         | UUID                   |     | Trazabilidad a dataset |

---

### 7) Dataset
Activo de datos (para portal de datos abiertos y analítica).

| Campo                 | Tipo                     | Req | Descripción / Por qué |
|----------------------|------------------------|-----|----------------------|
| id                   | UUID                   | ✔️  | — |
| nombre               | String                 | ✔️  | Catálogo |
| descripcion          | Text                   | ✔️  | Contexto del conjunto de datos |
| fuente               | String                 | ✔️  | Sistema origen |
| esquema              | JSONB                  | ✔️  | Estructura/columnas |
| periodicidad         | ENUM(ADHOC,DIARIA,SEM,MENSUAL) | ✔️ | ETL programado |
| licencia             | String                 | ✔️  | Reutilización (Open Data) |
| urlPortalDatos       | String                 |     | Publicación externa |
| formato              | ENUM(CSV,JSON,PARQUET,API) | ✔️ | Consumo |
| responsable          | String                 |     | DPO/Gobierno dato |
| coberturaTemporal    | String                 |     | Metadato DCAT |
| coberturaGeografica  | String                 |     | Metadato DCAT |
| ultimaActualizacion  | Instant                | ✔️  | Data freshness |
| palabrasClave        | String[]               |     | Descubribilidad |
