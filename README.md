# polaflix_CarlosAlarcon
Repositorio para prácticas personales de 332 - Tecnologías para el Desarrollo de Aplicaciones Empresariales sobre Internet - Curso 2025-2026

//Página para pasar a png el .puml
# https://www.planttext.com/ 

### FASE 1.1 : Modelo de dominio

png's visibles en el repo, carpeta "DiagramaDeClasesUML".

### FASE 1.2 : Domain-Driven Design

1. Clasificación de Elementos
Entities (Entidades): Tienen identidad propia y un ciclo de vida que debe ser monitorizado.

Usuario: Identificado unívocamente por su username.

Serie: Identificada por un id o titulo.

Temporada: Identidad local dentro de la Serie (número de temporada).

Capitulo: Identidad local dentro de la Temporada (número de capítulo).

Factura: Identificada por un id único o la tupla (Usuario, Mes, Año).

Value Objects (Objetos de Valor): No tienen identidad conceptual; importan por los valores de sus atributos. Son inmutables.

IBAN: Representa la cuenta bancaria.

CategoriaSerie: Enum o clase inmutable (Estándar, Silver, Gold) que encapsula el coste por visionado.

PlanSuscripcion: Define si el usuario paga por visión o tiene tarifa plana (20€).

LineaFactura: Registro inmutable del visionado de un capítulo para el cobro.

RegistroVisualizacion: Guarda el momento exacto y el identificador del capítulo que un usuario ha visto.

Services (Servicios de Dominio): Operaciones que involucran múltiples Aggregates y no pertenecen naturalmente a una sola entidad.

VisualizacionService: Orquesta la acción de "Anotar un capítulo como reproducido", ya que requiere modificar el historial del Usuario y, potencialmente, generar un cargo para la Factura.

2. Aggregates y Aggregate Roots
Siguiendo la regla de que los elementos externos solo pueden referenciar al Aggregate Root y que al eliminar el Root se elimina el resto en cascada, hemos identificado los siguientes clústeres:

Aggregate: Usuario

Aggregate Root: Usuario.

Elementos internos: RegistroVisualizacion (Value Object), IBAN (Value Object).

Justificación: El usuario controla su propio ciclo de vida, su plan de suscripción y su historial de visualizaciones. Las listas de series (Empezadas, Pendientes, Terminadas) se gestionan guardando referencias (IDs) a las Series, nunca los objetos Serie completos, así podemos evitar cruzar fronteras de agregados indebidamente.

Aggregate: Serie

Aggregate Root: Serie.

Elementos internos: Temporada (Entity), Capitulo (Entity), CategoriaSerie (Value Object).

Justificación: Una temporada o un capítulo no tienen sentido conceptual ni existencia sin la serie a la que pertenecen. Si se elimina "Juego de Tronos", se eliminan todos sus capítulos.

Aggregate: Facturación

Aggregate Root: Factura.

Elementos internos: LineaFactura (Value Object).

Justificación: La factura mensual protege la invariante de los cobros de un usuario en un mes concreto. Contiene el desglose de los cargos inmutables de ese periodo.

3. Refactorización DDD (Operaciones de Negocio)
Para cumplir con DDD, el modelo inicial ha sido modificado de la siguiente forma:

Evitar referencias directas entre objetos de distintos Aggregates: El Usuario no tiene una lista de objetos Serie para sus "Pendientes". Tiene una lista de SerieID (Strings). Esto reduce el acoplamiento y facilita la carga desde los Repositorios.

Operación "Agregar Serie al espacio personal": Es un método del Aggregate Root Usuario (ej. usuario.agregarSeriePendiente(serieId)).

Operación "Anotar capítulo como reproducido": Dado que afecta al estado del Usuario (pasa la serie a Empezada/Terminada) y a la Facturación (genera un cargo), se gestiona a través de un VisualizacionService. Este servicio recupera el Usuario y la Serie de sus respectivos repositorios, anota la visualización en el Usuario y registra el cargo en la Factura del mes en curso.

