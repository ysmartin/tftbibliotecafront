# AGENTS.md

## Proposito
Este proyecto es un frontend docente Spring Boot MVC con Thymeleaf que consume el backend `tftbiblioteca`. Esta pensado para que el alumnado practique controladores MVC, formularios, plantillas, consumo HTTP sencillo y tratamiento basico de errores.

Las decisiones actuales son intencionadamente simples. Un agente de IA debe mantener el codigo dentro de un nivel tecnico que el alumnado pueda seguir y explicar.

## Reglas para agentes de IA
- Antes de proponer o modificar codigo, revisa el patron actual del proyecto y aplica la solucion mas pequena que mantenga la navegacion funcionando.
- Este repositorio es el frontend del sistema. Un cambio funcional en frontend puede requerir cambios coordinados en el proyecto hermano de backend, aunque su ruta concreta varie segun el entorno del alumno.
- No introduzcas dependencias, frameworks frontend, JavaScript innecesario ni patrones avanzados sin peticion explicita.
- Si una mejora requiere un salto tecnico importante, preguntalo o presentalo como alternativa, no lo apliques por defecto.
- Manten el codigo didactico: controladores legibles, formularios sencillos, modelos locales claros, mensajes comprensibles y pruebas enfocadas en el flujo de usuario.
- Usa la pantalla de `Obra` como ejemplo del estado actual, no como limite. El frontend puede crecer con nuevas pantallas y formularios para futuras entidades del dominio.
- Si implementas una historia de usuario o una feature descrita a nivel funcional, antes de escribir codigo cierra el comportamiento observable que falta por concretar: pantallas implicadas, rutas MVC, formularios, mensajes, redirecciones, contexto de navegacion y verificaciones visibles para el usuario.
- Cuando el enunciado de una historia de usuario no fije esos detalles, deducelos a partir del codigo existente, las pruebas, los contratos vigentes del backend y el estilo del proyecto, eligiendo siempre la solucion mas pequena y coherente.
- Si una feature deja de ser coherente sin cambios en el backend, no des por terminada la tarea en frontend sin senalar esa dependencia y sin contemplar tambien los ajustes necesarios en el proyecto hermano.

## Capacidades esperadas
- Java 17 y Spring Boot MVC.
- Thymeleaf para vistas renderizadas en servidor.
- Formularios MVC con Bean Validation para entradas de usuario.
- `RestClient` para consumir el backend de forma manual y legible.
- Una fachada fina que oculte URLs, detalles HTTP y traduccion de errores del backend.
- Modelos locales sencillos cuando ayuden a desacoplar la vista del JSON recibido.
- Un unico CSS sencillo en `static/css` para evitar apariencia de HTML plano.
- Spring Security puede estar presente, pero con `SecurityConfig` permisivo mientras no exista autenticacion real.

## Arquitectura docente
- Manten el enfoque actual: controladores MVC, formularios en `web/form`, modelos simples, servicios/fachadas finas y plantillas Thymeleaf.
- No introduzcas cliente OpenAPI generado en este proyecto salvo peticion explicita. El consumo manual con `RestClient` es parte del objetivo docente.
- No sustituyas `RestClient` por `WebClient` ni introduzcas programacion reactiva.
- No anadas capas adicionales si solo trasladan codigo sin simplificar el aprendizaje.
- Puedes anadir nuevas pantallas, rutas MVC, formularios y metodos de fachada cuando aparezcan nuevas entidades o relaciones en el backend.

## Navegacion y contratos
- Disena rutas MVC orientadas a acciones humanas y formularios, no como copia automatica de las rutas REST del backend.
- Usa patron PRG en operaciones de escritura cuando evite reenvios de formulario.
- Manten mensajes flash para exitos y errores recuperables.
- Conserva el contexto de navegacion en parametros o formularios ocultos cuando ayude a no perder filtros o seleccion.
- Crea formularios locales cuando la vista necesite campos distintos del payload REST exacto, como valores CSV, ficheros subidos o contexto de pantalla.
- Traduce errores HTTP del backend a mensajes comprensibles para la UI sin exponer detalles tecnicos innecesarios.

## Interfaz
- Manten Thymeleaf y CSS sencillo. No anadas frameworks frontend, bundlers, componentes web ni SPA.
- Usa fragmentos comunes solo cuando reduzcan duplicacion visible y no dificulten entender una pagina.
- Manten las plantillas claras: formularios, tablas, detalle seleccionado y mensajes de estado deben ser faciles de localizar.
- Evita JavaScript salvo que el ejercicio lo pida expresamente o mejore una interaccion sin cambiar el modelo docente.

## Pruebas
- Cubrir controladores con `@WebMvcTest`, incluyendo validacion de formularios, redirecciones, mensajes flash y errores del backend.
- Cubrir la fachada con `MockRestServiceServer` para verificar URLs, metodos HTTP, cuerpos JSON o binarios y tratamiento de respuestas vacias.
- Mantener un smoke test `@SpringBootTest` para comprobar arranque, configuracion permisiva de seguridad y beans principales.
- Cuando se anadan nuevas pantallas o entidades, probar al menos el flujo feliz y un caso de validacion o error significativo.
