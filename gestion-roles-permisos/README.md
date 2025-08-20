# gestion-roles-permisos

## Propósito
Gestión de autorización y seguridad de los portales y APIs. Integra OAuth2 federado/SSO, expone catálogos de roles, permisos y su asignación a usuarios.

## Entidades y atributos

### 1) Rol
Conjunto de permisos.

| Campo | Tipo | Req | Descripción / Por qué |
|-------|------|-----|----------------------|
| id | UUID | ✔️ | Identificador único del rol. |
| codigo | String(50) | ✔️ | Estable para referencias (e.g., AGENTE_TRAMITACION). |
| nombre | String | ✔️ | Nombre legible del rol. |
| descripcion | Text |  | Información adicional. |
| nivel | ENUM(SYSTEM,ADMIN,AGENTE,CIUDADANO) | ✔️ | Jerarquía dentro del sistema. |
| activo | Boolean | ✔️ | Control de disponibilidad en el catálogo. |

### 2) Permiso
Acción sobre un recurso.

| Campo | Tipo | Req | Descripción / Por qué |
|-------|------|-----|----------------------|
| id | UUID | ✔️ | Identificador único del permiso. |
| codigo | String(80) | ✔️ | Código estable (e.g., solicitudes.read). |
| nombre | String | ✔️ | Nombre legible del permiso. |
| descripcion | Text |  | Información adicional sobre el permiso. |
| scope | ENUM(API,PORTAL,ADMIN) | ✔️ | Contexto de uso. |
| recurso | String | ✔️ | Entidad o endpoint sobre el que aplica. |
| accion | ENUM(READ,WRITE,DELETE,EXECUTE) | ✔️ | Semántica de la operación. |
| activo | Boolean | ✔️ | Control de deprecaciones o disponibilidad. |

### 3) UsuarioRol
Asignación de roles a usuarios federados (SSO/OAuth2).

| Campo | Tipo | Req | Descripción / Por qué |
|-------|------|-----|----------------------|
| id | UUID | ✔️ | Identificador único de la asignación. |
| usuarioId | String | ✔️ | Sub/subject del IdP. |
| rolId | UUID | ✔️ | Relación con el rol asignado. |
| asignadoPor | String |  | Auditoría: quién realizó la asignación. |
| fechaAsignacion | Instant | ✔️ | Fecha de asignación. |
| fechaCaducidad | Instant |  | Fecha de caducidad de un acceso temporal. |
| origen | ENUM(SSO,OAUTH2,SYNC) | ✔️ | Procedencia de la asignación. |
| observaciones | Text |  | Información adicional. |

---

## Tests

### RolHandlerTest
Pruebas unitarias del handler de roles, validando las operaciones CRUD básicas:
- **listReturnsAllRoles**: devuelve todos los roles.
- **getReturnsRolById**: obtiene un rol por ID.
- **createSavesRol**: crea un rol nuevo.
- **updateSavesExistingRol**: actualiza un rol existente.
- **deleteRemovesRolById**: elimina un rol por ID.

### PermisoHandlerTest
Pruebas unitarias del handler de permisos:
- **listReturnsAllPermisos**: devuelve todos los permisos.
- **getReturnsPermisoById**: obtiene un permiso por ID.
- **createSavesPermiso**: crea un permiso nuevo.
- **updateSavesExistingPermiso**: actualiza un permiso existente.
- **deleteRemovesPermisoById**: elimina un permiso por ID.

### UsuarioRolHandlerTest
Pruebas unitarias del handler de asignaciones de usuario a rol:
- **listReturnsAllUsuarioRoles**: devuelve todas las asignaciones.
- **getReturnsUsuarioRolById**: obtiene una asignación por ID.
- **createSavesUsuarioRol**: crea una nueva asignación.
- **updateSavesExistingUsuarioRol**: actualiza una asignación existente.
- **deleteRemovesUsuarioRolById**: elimina una asignación por ID.

---

## Ejecución de tests

Para ejecutar todos los tests del proyecto:

### Usando Maven
```bash
mvn test
```
### Ejecutar un test específico:
```bash
mvn -Dtest=RolHandlerTest test
```

