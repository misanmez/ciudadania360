# Subsistema de Inteligencia Artificial (IA)

## 📌 Descripción General
El **Subsistema de IA** de Ciudadanía 360 es un módulo transversal que proporciona capacidades de **análisis avanzado, predicción, asistencia inteligente y procesamiento de lenguaje natural (PLN)**.  
Este subsistema no está pensado como una aplicación aislada para usuarios finales, sino como un **servicio centralizado** al que acceden otros subsistemas de la plataforma.

Puede funcionar en dos modos:
- **Local (on-premise):** modelos entrenados y desplegados en nuestros servidores.
- **Externo (SaaS/licencias):** integración con proveedores de IA (ej. Azure OpenAI, Google Vertex AI).

El modelo es **mixto** (híbrido), para aprovechar lo mejor de ambos mundos.

---

## 👥 Usuarios y Clientes del Subsistema
El subsistema de IA no lo usa directamente el ciudadano, sino que actúa como **backend de soporte** para:
- **Ciudadano App** → asistentes virtuales, ayuda en formularios, lenguaje natural.
- **Tramitación** → clasificación automática de expedientes, priorización de tareas, predicción de resoluciones.
- **Comunicaciones** → análisis de mensajes, detección de spam, sugerencias automáticas de respuesta.
- **Videoconferencia** → transcripción automática, resúmenes de reuniones, subtitulado en tiempo real.
- **Información** → buscadores semánticos, recomendaciones personalizadas de contenido.
- **Roles y Permisos** → detección de anomalías, patrones de acceso sospechosos.
- **Subsistema Documental** → análisis de documentos, extracción de entidades.
- **Subsistema de Analítica** → generación de insights avanzados con modelos predictivos.

---

## 📌 Casos de Uso según el Pliego
- **Chatbot ciudadano multicanal** con lenguaje natural.
- **Asistente inteligente para tramitadores** (sugerencias en tiempo real).
- **Clasificación automática de documentos y expedientes**.
- **Análisis de sentimientos y opiniones** en encuestas y comunicaciones.
- **Motor de recomendaciones** (información, trámites, contenido relevante).
- **Predicción de cargas de trabajo** para optimizar recursos.
- **Transcripción y subtitulado en tiempo real** de videoconferencias.
- **Generación automática de resúmenes** de expedientes y actas.
- **Soporte a accesibilidad** mediante lectura automática y comprensión simplificada de textos.

---

## 📡 Integración con la Plataforma
- Cada subsistema se conecta al de IA mediante un **IAClient**.
- El IAClient estandariza las peticiones y evita que cada módulo tenga que implementar su propia lógica de conexión.
- El subsistema IA expone un **API REST** y eventualmente colas/eventos para tareas asíncronas.

---

## 🗂️ Diagrama de Casos de Uso

![imagen2.png](imagen2.png)

---

## ✅ Beneficios de este enfoque
- Reutilización de modelos y servicios IA en toda la plataforma.
- Reducción de costes (evita duplicar integraciones en cada subsistema).
- Flexibilidad para usar IA local o externa.
- Mejor mantenimiento y seguridad centralizada.

---

## 🧪 Pruebas y Validación
Para garantizar la calidad y estabilidad del subsistema, se han implementado **tests unitarios y de controlador** siguiendo la misma filosofía que los subsistemas existentes:

- **IAService:** tests unitarios con Mockito que verifican la delegación hacia `IAClient` para predicción, clasificación de documentos, procesamiento de texto y transcripción.
- **ChatbotService:** tests unitarios que aseguran que el envío de mensajes se delega correctamente al `IAClient`.
- **IAController:** tests con MockMvc para comprobar que las rutas REST `/api/ia/generate` funcionan y devuelven los resultados esperados.
- **ChatbotController:** tests con MockMvc para `/api/chatbot/message`, validando la respuesta y la asociación de conversación.

**Buenas prácticas aplicadas:**
- Uso de **DTOs** para separar capa de transporte de la lógica de negocio.
- Mocking de servicios externos (`IAClient`) para tests unitarios.
- Cobertura de métodos principales y de flujos críticos de comunicación.
- Base lista para **tests de integración con mock server o proveedor de IA real**.

---

## 🚀 Próximos Pasos
1. Definir APIs expuestas por el subsistema IA.
2. Implementar el **IAClient** en cada microservicio.
3. Configurar `application-docker.yml` para conexión a BD y colas.
4. Decidir el proveedor externo de IA (si aplica).
5. Añadir **tests de integración** para el Chatbot y los servicios de IA.
6. Monitorizar métricas de uso y rendimiento del Chatbot y servicios de IA en producción.