-- 01_ia_schema.sql
CREATE SCHEMA IF NOT EXISTS ia AUTHORIZATION ciudadania;

-- ----------------------------
-- Tabla: IaConversation
-- ----------------------------
CREATE TABLE IF NOT EXISTS ia.conversation (
    conversation_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    created_at timestamptz DEFAULT now()
);

-- ----------------------------
-- Tabla: IaChatMessage
-- ----------------------------
CREATE TABLE IF NOT EXISTS ia.chat_message (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    conversation_id UUID NOT NULL REFERENCES ia.conversation(conversation_id) ON DELETE CASCADE,
    user_message TEXT NOT NULL,
    response TEXT,
    raw_response JSONB,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- ----------------------------
-- Tabla: IaTrainingExample
-- ----------------------------
CREATE TABLE IF NOT EXISTS ia.training_example (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_message TEXT NOT NULL,
    response TEXT NOT NULL,
    used_for_training BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- ----------------------------
-- Datos iniciales para pruebas
-- ----------------------------

-- Conversaciones de ejemplo
INSERT INTO ia.conversation (conversation_id) VALUES
(gen_random_uuid()),
(gen_random_uuid()),
(gen_random_uuid()),
(gen_random_uuid()),
(gen_random_uuid());

-- Insertar mensajes asociando cada uno a una conversación
WITH conv AS (
    SELECT conversation_id FROM ia.conversation ORDER BY created_at
)
INSERT INTO ia.chat_message (conversation_id, user_message, response, raw_response)
SELECT conv.conversation_id, '¿Cuáles son los requisitos para renovar el DNI?',
       'Para renovar el DNI necesitas: 1) Una foto reciente, 2) El pago de la tasa, 3) El DNI anterior.',
       '{"confidence":0.95}' FROM conv LIMIT 1
UNION ALL
SELECT conv.conversation_id, '¿Puedo renovar si estoy en el extranjero?',
       'Sí, pero deberás acudir al consulado correspondiente.',
       '{"confidence":0.9}' FROM conv LIMIT 1
UNION ALL
SELECT conv.conversation_id, '¿Cuál es el horario de atención?',
       'El horario de atención es de lunes a viernes de 09:00 a 14:00.',
       '{"confidence":0.9}' FROM conv LIMIT 1
UNION ALL
SELECT conv.conversation_id, '¿Cómo solicito cita previa?',
       'Puedes solicitarla online o por teléfono.',
       '{"confidence":0.9}' FROM conv OFFSET 1 LIMIT 1
UNION ALL
SELECT conv.conversation_id, '¿Qué documentos necesito para empadronarme?',
       'Necesitas DNI/NIE y un justificante de domicilio.',
       '{"confidence":0.9}' FROM conv OFFSET 1 LIMIT 1
UNION ALL
SELECT conv.conversation_id, '¿Cuánto tarda en llegar un certificado?',
       'Generalmente 3-5 días hábiles.',
       '{"confidence":0.9}' FROM conv OFFSET 1 LIMIT 1;

-- Ejemplos de entrenamiento completos
INSERT INTO ia.training_example (id, created_at, user_message, response, used_for_training) VALUES
(gen_random_uuid(), NOW(), '¿Cuáles son los requisitos para renovar el DNI?', 'Para renovar el DNI necesitas: 1) Una foto reciente, 2) El pago de la tasa, 3) El DNI anterior.', FALSE),
(gen_random_uuid(), NOW(), '¿Puedo renovar si estoy en el extranjero?', 'Sí, pero deberás acudir al consulado correspondiente.', FALSE),
(gen_random_uuid(), NOW(), '¿Cómo solicito cita previa?', 'Puedes solicitarla online o por teléfono.', FALSE),
(gen_random_uuid(), NOW(), '¿Cuál es el horario de atención?', 'El horario de atención es de lunes a viernes de 09:00 a 14:00.', FALSE),
(gen_random_uuid(), NOW(), '¿Dónde puedo obtener el certificado de empadronamiento?', 'Puedes solicitarlo en el ayuntamiento de tu localidad o mediante la sede electrónica.', FALSE),
(gen_random_uuid(), NOW(), '¿Qué documentos necesito para empadronarme?', 'Necesitas DNI/NIE y un justificante de domicilio.', FALSE),
(gen_random_uuid(), NOW(), '¿Cuánto tarda en llegar un certificado?', 'Generalmente 3-5 días hábiles.', FALSE),
(gen_random_uuid(), NOW(), '¿Cómo cambio mi domicilio en el registro?', 'Debes actualizar tus datos online o presencialmente en el ayuntamiento.', FALSE),
(gen_random_uuid(), NOW(), '¿Se puede hacer por teléfono?', 'No, debe hacerse presencialmente o mediante la sede electrónica.', FALSE),
(gen_random_uuid(), NOW(), '¿Cómo solicito un duplicado de DNI?', 'Debes acudir presencialmente con una foto reciente y abonar la tasa correspondiente.', FALSE),
(gen_random_uuid(), NOW(), '¿Puedo renovar mi pasaporte con cita previa online?', 'Sí, puedes solicitar la cita a través de la página web oficial.', FALSE),
(gen_random_uuid(), NOW(), '¿Dónde puedo encontrar el formulario de solicitud?', 'El formulario está disponible en la sede electrónica o en las oficinas del registro.', FALSE),
(gen_random_uuid(), NOW(), '¿Cuál es el coste de una renovación de DNI?', 'La tasa actual es de 12 euros.', FALSE),
(gen_random_uuid(), NOW(), '¿Cuánto tiempo tarda la renovación?', 'Generalmente entre 10 y 15 días hábiles.', FALSE),
(gen_random_uuid(), NOW(), '¿Puedo pagar la tasa online?', 'Sí, algunas sedes electrónicas permiten pago online.', FALSE),
(gen_random_uuid(), NOW(), '¿Qué hacer si pierdo mi DNI?', 'Debes presentar denuncia y solicitar un duplicado.', FALSE),
(gen_random_uuid(), NOW(), '¿Cómo cambio mi nombre legalmente?', 'Debes presentar una solicitud al registro civil con la documentación necesaria.', FALSE),
(gen_random_uuid(), NOW(), '¿Qué documentos se necesitan para matrimonio?', 'Acta de nacimiento, DNI/NIE y certificado de soltería.', FALSE),
(gen_random_uuid(), NOW(), '¿Dónde puedo solicitar la tarjeta sanitaria?', 'En el centro de salud de tu localidad con tu DNI/NIE.', FALSE),
(gen_random_uuid(), NOW(), '¿Qué debo llevar para solicitar la pensión?', 'Documentación de tu historial laboral, DNI/NIE y formularios oficiales.', FALSE),
(gen_random_uuid(), NOW(), '¿Cómo renovar el carnet de conducir?', 'Acudiendo a la Jefatura de Tráfico con foto reciente y pago de tasas.', FALSE),
(gen_random_uuid(), NOW(), '¿Qué hacer si caduca mi pasaporte en el extranjero?', 'Acude al consulado más cercano para renovación.', FALSE),
(gen_random_uuid(), NOW(), '¿Se puede cambiar la dirección del domicilio online?', 'Sí, mediante la sede electrónica si estás registrado.', FALSE),
(gen_random_uuid(), NOW(), '¿Cómo obtengo certificado de nacimiento?', 'Debes solicitarlo en el registro civil de tu localidad.', FALSE),
(gen_random_uuid(), NOW(), '¿Qué hacer si se extravía un certificado?', 'Solicita un duplicado en el mismo registro donde se emitió.', FALSE),
(gen_random_uuid(), NOW(), '¿Cómo solicitar la tarjeta de familia numerosa?', 'Acude al ayuntamiento con la documentación requerida.', FALSE),
(gen_random_uuid(), NOW(), '¿Cuánto tarda en llegar la tarjeta sanitaria?', 'Entre 7 y 15 días hábiles según la localidad.', FALSE),
(gen_random_uuid(), NOW(), '¿Puedo solicitar el certificado de empadronamiento online?', 'Sí, mediante la sede electrónica con autenticación.', FALSE),
(gen_random_uuid(), NOW(), '¿Qué hacer si cambio de municipio?', 'Debes actualizar tu domicilio en el registro civil y en el empadronamiento.', FALSE),
(gen_random_uuid(), NOW(), '¿Se puede cancelar una cita previa?', 'Sí, con al menos 24 horas de antelación.', FALSE),
(gen_random_uuid(), NOW(), '¿Qué documentos necesito para la nacionalidad?', 'Acta de nacimiento, antecedentes penales y certificado de residencia.', FALSE),
(gen_random_uuid(), NOW(), '¿Cómo solicitar duplicado de tarjeta sanitaria?', 'Acudiendo al centro de salud con tu DNI/NIE.', FALSE),
(gen_random_uuid(), NOW(), '¿Cuánto tiempo tarda la inscripción escolar?', 'Entre 2 y 5 días hábiles.', FALSE),
(gen_random_uuid(), NOW(), '¿Dónde puedo encontrar información sobre ayudas sociales?', 'En la sede electrónica del Ministerio o en el ayuntamiento.', FALSE),
(gen_random_uuid(), NOW(), '¿Qué hacer si pierdo la tarjeta de residencia?', 'Presentar denuncia y solicitar duplicado en extranjería.', FALSE),
(gen_random_uuid(), NOW(), '¿Se puede renovar el carnet de conducir online?', 'No, debe hacerse presencialmente en la Jefatura de Tráfico.', FALSE),
(gen_random_uuid(), NOW(), '¿Cómo obtener certificado de empadronamiento histórico?', 'Solicitarlo en el ayuntamiento con la fecha requerida.', FALSE),
(gen_random_uuid(), NOW(), '¿Qué hacer si cambio de apellido?', 'Presentar solicitud en el registro civil con documentación justificativa.', FALSE),
(gen_random_uuid(), NOW(), '¿Cuándo caduca el pasaporte?', '5 años si eres menor de 30 años, 10 años si eres mayor.', FALSE),
(gen_random_uuid(), NOW(), '¿Cómo solicitar cita para DNI por primera vez?', 'Acudiendo al registro con documentación y fotografía reciente.', FALSE),
(gen_random_uuid(), NOW(), '¿Qué hacer si olvido mi contraseña de la sede electrónica?', 'Usar la opción de recuperación de contraseña en la web oficial.', FALSE),
(gen_random_uuid(), NOW(), '¿Se puede renovar el pasaporte online?', 'No, solo presencialmente en oficinas habilitadas.', FALSE),
(gen_random_uuid(), NOW(), '¿Cómo solicitar el certificado de antecedentes penales?', 'Acudir al registro central con identificación oficial.', FALSE),
(gen_random_uuid(), NOW(), '¿Qué documentos necesito para la licencia de actividad?', 'Formulario oficial, DNI/NIE y justificantes técnicos.', FALSE),
(gen_random_uuid(), NOW(), '¿Cómo consultar el estado de un expediente?', 'Mediante la sede electrónica introduciendo el número de expediente.', FALSE),
(gen_random_uuid(), NOW(), '¿Dónde puedo presentar reclamaciones?', 'En la oficina correspondiente o mediante la sede electrónica.', FALSE),
(gen_random_uuid(), NOW(), '¿Se puede cambiar el nombre de usuario en la sede electrónica?', 'Sí, en la configuración de tu cuenta.', FALSE),
(gen_random_uuid(), NOW(), '¿Cómo obtener información sobre plazas escolares?', 'Consultar el portal de educación de tu comunidad autónoma.', FALSE),
(gen_random_uuid(), NOW(), '¿Qué hacer si no recibo notificaciones?', 'Verificar la configuración de contacto en la sede electrónica.', FALSE),
(gen_random_uuid(), NOW(), '¿Se puede solicitar duplicado de DNI online?', 'No, solo presencialmente.', FALSE),
(gen_random_uuid(), NOW(), '¿Cómo consultar historial de pagos?', 'Mediante la sede electrónica con tu identificación.', FALSE),
(gen_random_uuid(), NOW(), '¿Qué hacer si pierdo la tarjeta de transporte?', 'Solicitar duplicado en la oficina de transporte correspondiente.', FALSE);
