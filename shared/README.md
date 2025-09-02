# Subsistema SHARE

## 📌 Descripción General
El **Subsistema SHARE** de Ciudadanía 360 gestiona la **información compartida y reutilizable** entre todos los subsistemas.  
Incluye entidades comunes, catálogos, usuarios y roles, y sirve como **capa de datos centralizada**.

Actualmente implementa:
- Entidades compartidas (direcciones, municipios, tipos de solicitudes).
- Usuarios y roles (con control GCA).
- Catálogos reutilizables en varios subsistemas.
- Repositorios Spring Data JPA y servicios REST.
- Tests unitarios para asegurar consistencia.

---

## 📡 Integración con la Plataforma
- APIs REST para acceder a datos maestros y catálogos.
- Integración con GCA para roles y permisos.
- Base de datos PostgreSQL compartida (`share` schema).

---

## ✅ Beneficios
- Centralización de datos comunes.
- Consistencia entre subsistemas.
- Facilita auditoría y control de accesos mediante roles.

---

## 🧪 Pruebas y Validación
- Repositorios JPA con tests unitarios.
- Validación de integridad de datos y relaciones.

---

## 🚀 Próximos Pasos
1. Completar integración con todos los subsistemas dependientes.
2. Añadir más catálogos y datos maestros según pliego.
3. Mejorar cobertura de pruebas unitarias.
4. Documentar endpoints y contratos de API.
