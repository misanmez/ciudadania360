# Subsistema de Inteligencia Artificial (IA)

## 📌 Descripción General
El **Subsistema de IA** de Ciudadanía 360 es un módulo transversal que proporciona capacidades de **procesamiento de lenguaje natural (PLN), análisis de texto, clasificación de solicitudes y predicción**.  
Este subsistema funciona como **servicio centralizado**, utilizado por otros subsistemas y no directamente por los ciudadanos.

Actualmente implementa:
- **Chatbot ciudadano** con almacenamiento de conversaciones (`IAConversation`) y mensajes (`IAChatMessage`).
- **Base de datos de entrenamiento** (`IATrainingExample`) para mejorar las respuestas.
- Integración con **Subsistema SHARE** para obtener datos maestros y normalizados.
- Pruebas unitarias y MockMvc para controladores REST.

El modelo es **híbrido**, permitiendo IA local y futura integración con proveedores externos (Azure OpenAI, Google Vertex AI).

---

## 👥 Usuarios y Clientes del Subsistema
El subsistema de IA sirve como backend para:
- **Chatbot ciudadano** → respuestas automáticas y predicciones.
- **Tramitación** → clasificación de solicitudes y priorización de incidencias.
- **Comunicaciones** → sugerencias automáticas de mensajes.
- **Información** → búsqueda semántica y recomendaciones.
- **SHARE** → datos maestros para entrenamiento y normalización.

---

## 📌 Casos de Uso Implementados
- Registro y almacenamiento de conversaciones y mensajes.
- Base de datos de ejemplos de entrenamiento.
- Tests unitarios y de integración con MockMvc.
- Preparado para IA predictiva y análisis semántico.
- Integración con IAClient para centralizar llamadas a modelos.

Próximas funcionalidades:
- Integración con modelos externos de IA.
- Clasificación automática avanzada de documentos y expedientes.
- Análisis de sentimientos y motor de recomendaciones.
- Transcripción y subtitulado de videoconferencias.

---

## 📡 Integración con la Plataforma
- Cada subsistema cliente utiliza **IAClient** para acceder al servicio de IA.
- API REST expuesta para solicitudes sincrónicas.
- Preparado para integración con colas/eventos para procesos asíncronos.
- Acceso a datos normalizados de SHARE para entrenamiento y consultas.

---

## 🧪 Ejemplo de Uso (Postman / Swagger)

### 1️⃣ Inicio de conversación (primera pregunta)
**POST** `/api/ia/chat`

**Request Body:**
```json
{
  "message": "¿Cuáles son los requisitos para renovar el DNI?"
}
```

**Response Body:**
```json
{
  "conversationId": "c0a80123-4567-89ab-cdef-1234567890ab",
  "response": "Para renovar el DNI necesitas: 1) Una foto reciente, 2) Pago de la tasa, 3) DNI anterior."
}
```

### 2️⃣ Segundo mensaje (misma conversación)
**POST** `/api/ia/chat`

**Request Body:**
```json
{
  "conversationId": "c0a80123-4567-89ab-cdef-1234567890ab",
  "message": "¿Puedo renovar si estoy en el extranjero?"
}
```

**Response Body:**
```json
{
  "conversationId": "c0a80123-4567-89ab-cdef-1234567890ab",
  "response": "Sí, pero deberás acudir al consulado correspondiente."
}
```

> ✅ Nota: `conversationId` permite mantener el historial de la conversación y asociar todos los mensajes al mismo registro en la BBDD (`IAConversation` y `IAChatMessage`).

---

## ✅ Beneficios
- Reutilización de modelos y servicios IA en toda la plataforma.
- Centralización y mantenimiento sencillo.
- Base sólida para incorporar IA avanzada en todos los subsistemas.
- Entrenamiento continuo mediante `IATrainingExample` y datos de SHARE.

---

## 🧪 Pruebas y Validación
- Tests unitarios con Mockito para `IAService` y `ChatbotService`.
- MockMvc para `IAController` y `ChatbotController`.
- Preparado para tests de integración con proveedor de IA real.
- Cobertura de flujos críticos de comunicación y almacenamiento en BBDD.

---

## 🚀 Próximos Pasos
1. Definir APIs completas expuestas por IA.
2. Configurar conexiones a BBDD y colas en `application-docker.yml`.
3. Integrar soporte para modelos externos de IA.
4. Añadir análisis de sentimientos y motor de recomendaciones.
5. Monitorizar métricas de uso y rendimiento en producción.
