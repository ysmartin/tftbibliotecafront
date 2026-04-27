# AGENTS.md

## Propósito
Este proyecto es un frontend docente Spring Boot MVC con Thymeleaf que consume el backend `tftbiblioteca`. Está pensado para que el alumnado practique controladores MVC, formularios, plantillas, consumo HTTP sencillo y tratamiento básico de errores.

Las decisiones actuales son intencionadamente simples. Un agente de IA debe mantener el código dentro de un nivel técnico que el alumnado pueda seguir y explicar.

## Reglas para agentes de IA
- Antes de proponer o modificar código, revisa el patrón actual del proyecto y aplica la solución más pequeña que mantenga la navegación funcionando.
- Este repositorio es el frontend del sistema. Un cambio funcional en frontend puede requerir cambios coordinados en el proyecto hermano de backend, aunque su ruta concreta varíe según el entorno del alumno.
- No introduzcas dependencias, frameworks frontend, JavaScript innecesario ni patrones avanzados sin petición explícita.
- Si una mejora requiere un salto técnico importante, pregúntalo o preséntalo como alternativa, no lo apliques por defecto.
- Mantén el código didáctico: controladores legibles, formularios sencillos, modelos locales claros, mensajes comprensibles y pruebas enfocadas en el flujo de usuario.
- No elimines anotaciones, funciones o clases ya existentes solo porque no encajen del todo con estas pautas. Conserva lo preexistente salvo que sea incorrecto, rompa el comportamiento o impida implementar bien la característica pedida.
- Usa la pantalla de `Obra` como ejemplo del estado actual, no como límite. El frontend puede crecer con nuevas pantallas y formularios para futuras entidades del dominio.
- Si implementas una historia de usuario o una feature descrita a nivel funcional, antes de escribir código cierra el comportamiento observable que falta por concretar: pantallas implicadas, rutas MVC, formularios, mensajes, redirecciones, contexto de navegación y verificaciones visibles para el usuario.
- Cuando el enunciado de una historia de usuario no fije esos detalles, dedúcelos a partir del código existente, las pruebas, los contratos vigentes del backend y el estilo del proyecto, eligiendo siempre la solución más pequeña y coherente.
- Si una feature deja de ser coherente sin cambios en el backend, no des por terminada la tarea en frontend sin señalar esa dependencia y sin contemplar también los ajustes necesarios en el proyecto hermano.

## Capacidades esperadas
- Java 17 y Spring Boot MVC.
- Thymeleaf para vistas renderizadas en servidor.
- Formularios MVC con Bean Validation para entradas de usuario.
- `RestClient` para consumir el backend de forma manual y legible.
- Una fachada fina que oculte URLs, detalles HTTP y traducción de errores del backend.
- Modelos locales sencillos cuando ayuden a desacoplar la vista del JSON recibido.
- Un único CSS sencillo en `static/css` para evitar apariencia de HTML plano.
- Spring Security puede estar presente, pero con `SecurityConfig` permisivo mientras no exista autenticación real.

## Arquitectura docente
- Mantén el enfoque actual: controladores MVC, formularios en `web/form`, modelos simples, servicios/fachadas finas y plantillas Thymeleaf.
- No introduzcas cliente OpenAPI generado en este proyecto salvo petición explícita. El consumo manual con `RestClient` es parte del objetivo docente.
- No sustituyas `RestClient` por `WebClient` ni introduzcas programación reactiva.
- No añadas capas adicionales si solo trasladan código sin simplificar el aprendizaje.
- Puedes añadir nuevas pantallas, rutas MVC, formularios y métodos de fachada cuando aparezcan nuevas entidades o relaciones en el backend.

## Navegación y contratos
- Diseña rutas MVC orientadas a acciones humanas y formularios, no como copia automática de las rutas REST del backend.
- Usa patrón PRG en operaciones de escritura cuando evite reenvíos de formulario.
- Mantén mensajes flash para éxitos y errores recuperables.
- Conserva el contexto de navegación en parámetros o formularios ocultos cuando ayude a no perder filtros o selección.
- Crea formularios locales cuando la vista necesite campos distintos del payload REST exacto, como valores CSV, ficheros subidos o contexto de pantalla.
- Traduce errores HTTP del backend a mensajes comprensibles para la UI sin exponer detalles técnicos innecesarios.

## Interfaz
- Mantén Thymeleaf y CSS sencillo. No añadas frameworks frontend, bundlers, componentes web ni SPA.
- Usa fragmentos comunes solo cuando reduzcan duplicación visible y no dificulten entender una página.
- Mantén las plantillas claras: formularios, tablas, detalle seleccionado y mensajes de estado deben ser fáciles de localizar.
- Evita JavaScript salvo que el ejercicio lo pida expresamente o mejore una interacción sin cambiar el modelo docente.

## Pruebas
- Cubrir controladores con `@WebMvcTest`, incluyendo validación de formularios, redirecciones, mensajes flash y errores del backend.
- Cubrir la fachada con `MockRestServiceServer` para verificar URLs, métodos HTTP, cuerpos JSON o binarios y tratamiento de respuestas vacías.
- Mantener un smoke test `@SpringBootTest` para comprobar arranque, configuración permisiva de seguridad y beans principales.
- Cuando se añadan nuevas pantallas o entidades, probar al menos el flujo feliz y un caso de validación o error significativo.
