# 📋 RUTA DE TRABAJO - PARCIAL PRIMER CORTE

## 🎯 Sistema de Reservas TransCesar

**Asignatura:** Programación III  
**Docente:** Ing. Esp. Alfredo Bautista  
**Modalidad:** Equipos de 3 personas  
**Tiempo:** 6 Horas  
**Fecha:** Parcial Primer Corte  

---

## 📊 TABLA DE CONTENIDOS

1. [Contexto y Problema](#contexto)
2. [Requerimiento Funcional](#requerimiento)
3. [Análisis Técnico](#análisis)
4. [Ruta de Trabajo](#ruta)
5. [Detalles de Implementación](#detalles)
6. [Distribución del Trabajo](#distribución)
7. [Validaciones Críticas](#validaciones)
8. [Timeline](#timeline)

---

## 🔍 CONTEXTO Y PROBLEMA {#contexto}

### La Situación Actual

El sistema TransCesar ha evolucionado correctamente en:
- ✅ Gestión de vehículos (Bus, MicroBus, Buseta)
- ✅ Gestión de personas (Conductores, Pasajeros)
- ✅ Venta de tickets con descuentos automáticos
- ✅ Gestión de rutas formales

### El Problema Identificado

```
Pasajero quiere viajar el fin de semana
        │
        ├─ NO hay reserva anticipada
        │
        ├─ Debe ir presencialmente el mismo día
        │
        └─ Sin garantía de cupo disponible
            │
            ├─ Filas largas
            ├─ Inconformidad
            └─ Pérdida de clientes a competencia ❌
```

### La Solución: Sistema de Reservas

Un pasajero puede:
1. **Reservar** un cupo con anticipación
2. **Garantizar** su viaje sin ir a terminal
3. **Cancelar** si sus planes cambian
4. **Convertir** la reserva en ticket al momento del viaje

---

## 📌 REQUERIMIENTO FUNCIONAL {#requerimiento}

### Clase Reserva - Nueva Entidad

La clase `Reserva` representa el apartado temporal de un cupo.

#### Atributos Requeridos

```java
private String codigo;              // Código único
private Pasajero pasajero;          // Quién reservó
private Vehiculo vehiculo;          // Dónde reservó
private LocalDate fechaCreacion;    // Cuándo se hizo
private LocalDate fechaViaje;       // Fecha del viaje
private String estado;              // Estado actual
```

#### Estados Posibles (3 total)

| Estado | Descripción | Cupos Contados | Siguiente Transición |
|--------|-------------|---|---|
| **ACTIVA** | Cupo apartado, cuenta en capacidad | ✅ Sí | Convertida o Cancelada |
| **CONVERTIDA** | Ya pagó su ticket, liberó cupo | ❌ No | Ninguna (final) |
| **CANCELADA** | Pasajero no fue, liberó cupo | ❌ No | Ninguna (final) |

#### Transiciones de Estado

```
        ┌─────────────────────────────────┐
        │   CREACIÓN DE RESERVA           │
        │   estado = ACTIVA               │
        │   Cupo cuenta en capacidad      │
        └─────────┬───────────────────────┘
                  │
         ┌────────┴────────┐
         │                 │
         ▼                 ▼
    ┌─────────┐       ┌──────────┐
    │CONVERTIDA   │       │CANCELADA │
    │(Compró)     │       │(No fue)  │
    │Libera cupo  │       │Libera cupo
    └─────────┘       └──────────┘
         │                 │
         └────────┬────────┘
                  │
                  ▼
           [FINAL] - No cambia
```

### Reglas de Negocio

#### 1️⃣ Límite por Capacidad
```
Reservas Activas + Tickets Vendidos ≤ Capacidad Máxima del Vehículo

Ejemplo Bus (capacidad 45):
- 35 tickets vendidos
- 8 reservas activas
- Total: 43 (OK, hay 2 cupos libres)
- NO se puede hacer más reservas
```

#### 2️⃣ Expiración por Tiempo
```
Reserva EXPIRADA si:
    Fecha Actual - Fecha Creación > 24 horas
    ↓
    Cambiar a CANCELADA automáticamente
    ↓
    Liberar cupo
```

#### 3️⃣ No Duplicados por Pasajero
```
Un pasajero NO puede tener:
    ✗ 2 reservas ACTIVAS
    ✗ Para el mismo vehículo
    ✗ En la misma fecha de viaje
    
Pero SÍ puede tener:
    ✓ Reservas para diferentes vehículos
    ✓ Reservas para diferentes fechas
    ✓ Reservas convertidas o canceladas
```

#### 4️⃣ Conversión a Ticket con Reglas Completas
```
Cuando convierte reserva → ticket:
    ├─ Aplicar descuento por tipo de pasajero (15%, 30%)
    ├─ Aplicar incremento de festivo (+20% si es festivo)
    ├─ Cambiar estado a CONVERTIDA
    ├─ Crear Ticket correspondiente
    └─ Liberar cupo de reserva
```

---

## 🔧 ANÁLISIS TÉCNICO {#análisis}

### Impacto en Capas Existentes

#### CAPA MODEL ✅ Nueva Clase
```
model/
├── Reserva.java (NUEVA)
│   ├── Atributos: código, pasajero, vehiculo, fechas, estado
│   ├── Métodos: crear, cancelar, convertir, verificarVencimiento
│   └── Estados: ACTIVA, CONVERTIDA, CANCELADA
│
├── [Clases existentes sin cambios]
│   ├── Vehiculo.java
│   ├── Persona.java
│   ├── Ticket.java
│   └── Ruta.java
```

#### CAPA DAO ✅ Nueva Clase
```
dao/
├── ReservaDAO.java (NUEVA)
│   ├── guardarReserva(r: Reserva)
│   ├── cargarReservas(): List<Reserva>
│   ├── obtenerPorCodigo(codigo: String): Reserva
│   ├── actualizar(r: Reserva)
│   ├── reescribir(lista: List<Reserva>)
│   └─ archivo: "reservas.txt"
```

#### CAPA SERVICE ✅ Nueva Clase
```
service/
├── ReservaService.java (NUEVA)
│   ├── crearReserva(pasajero, vehiculo, fechaViaje)
│   ├── cancelarReserva(codigo)
│   ├── convertirEnTicket(codigo)
│   ├── listarActivas(): List<Reserva>
│   ├── historialPasajero(cedulaPasajero): List<Reserva>
│   ├── verificarVencidas()
│   ├── contarReservasActivas(vehiculo)
│   └── buscarPorCodigo(codigo)
```

#### CAPA VIEW ✅ Modificación Menor
```
view/
├── Menu.java (MODIFICADO)
│   ├── menuPrincipal() - Agregar opción "Reservas"
│   └── menuReservas() (NUEVA)
│       ├── Crear reserva
│       ├── Cancelar reserva
│       ├── Listar activas
│       ├── Historial pasajero
│       ├── Convertir en ticket
│       └── Verificar vencidas
```

---

## 🛣️ RUTA DE TRABAJO {#ruta}

### Flujo Completo del Parcial

```
INICIO DEL PARCIAL (Hora 0)
        │
        ├─► [FASE 1: Análisis] (15 min)
        │   ├─ Leer requerimiento
        │   ├─ Identificar cambios
        │   └─ Dividir trabajo por rol
        │
        ├─► [FASE 2: Modelo] (45 min) 
        │   ├─ Crear clase Reserva
        │   ├─ Implementar estados
        │   ├─ Métodos esenciales
        │   └─ Git: feature/reserva-model
        │
        ├─► [FASE 3: Persistencia] (45 min)
        │   ├─ ReservaDAO.java
        │   ├─ Formato reservas.txt
        │   ├─ Carga en startup
        │   └─ Git: feature/reserva-dao
        │
        ├─► [FASE 4: Lógica de Negocio] (60 min)
        │   ├─ ReservaService.java
        │   ├─ Validaciones
        │   ├─ Estados y transiciones
        │   ├─ Expiración automática
        │   └─ Git: feature/reserva-service
        │
        ├─► [FASE 5: Interfaz] (45 min)
        │   ├─ MenuReservas.java
        │   ├─ Integración en Menu.java
        │   ├─ Entrada/salida usuario
        │   └─ Git: feature/reserva-view
        │
        ├─► [FASE 6: Pruebas] (30 min)
        │   ├─ Crear reserva
        │   ├─ Convertir a ticket
        │   ├─ Cancelar
        │   ├─ Verificar vencidas
        │   └─ Casos límite
        │
        └─► FIN (Documentación y Entrega)

TOTAL: 6 HORAS ✅
```

---

## 🔨 DETALLES DE IMPLEMENTACIÓN {#detalles}

### 1. Clase Reserva (Estructura Completa)

```java
public class Reserva implements Imprimible {
    
    // Constantes de estado
    public static final String ESTADO_ACTIVA = "ACTIVA";
    public static final String ESTADO_CONVERTIDA = "CONVERTIDA";
    public static final String ESTADO_CANCELADA = "CANCELADA";
    
    // Atributos
    private String codigo;
    private Pasajero pasajero;
    private Vehiculo vehiculo;
    private LocalDate fechaCreacion;
    private LocalDate fechaViaje;
    private String estado;
    
    // Constructor
    public Reserva(String codigo, Pasajero pasajero, 
                   Vehiculo vehiculo, LocalDate fechaViaje) {
        this.codigo = codigo;
        this.pasajero = pasajero;
        this.vehiculo = vehiculo;
        this.fechaCreacion = LocalDate.now();
        this.fechaViaje = fechaViaje;
        this.estado = ESTADO_ACTIVA;
    }
    
    // Métodos esenciales
    public boolean estaVencida() {
        // Retorna true si > 24 horas desde creación
        return ChronoUnit.HOURS.between(
            fechaCreacion.atStartOfDay(),
            LocalDateTime.now()
        ) > 24;
    }
    
    public void cancelar() {
        if (this.estado.equals(ESTADO_ACTIVA)) {
            this.estado = ESTADO_CANCELADA;
        }
    }
    
    public void convertir() {
        if (this.estado.equals(ESTADO_ACTIVA)) {
            this.estado = ESTADO_CONVERTIDA;
        }
    }
    
    @Override
    public void imprimirDetalle() {
        System.out.println("=== RESERVA ===");
        System.out.println("Código: " + codigo);
        System.out.println("Pasajero: " + pasajero.getNombre());
        System.out.println("Vehículo: " + vehiculo.getPlaca());
        System.out.println("Fecha Viaje: " + fechaViaje);
        System.out.println("Estado: " + estado);
        System.out.println("Vencida: " + estaVencida());
    }
}
```

### 2. Formato de Persistencia (reservas.txt)

```
codigo;cedula_pasajero;placa_vehiculo;fecha_creacion;fecha_viaje;estado
RES001;1234567890;ABC-1234;2025-03-20;2025-03-25;ACTIVA
RES002;0987654321;XYZ-5678;2025-03-18;2025-03-28;CONVERTIDA
RES003;1111111111;ABC-1234;2025-03-10;2025-03-15;CANCELADA
```

### 3. Validaciones en ReservaService

```java
// Validación 1: Capacidad disponible
private boolean hayCapacidadDisponible(Vehiculo v) {
    int ticketActuales = contarTicketsVendidos(v);
    int reservasActivas = contarReservasActivas(v);
    int total = ticketActuales + reservasActivas;
    return total < v.getCapacidad();
}

// Validación 2: No duplicado
private boolean tieneDuplicado(Pasajero p, Vehiculo v, LocalDate fecha) {
    for (Reserva r : reservas) {
        if (r.getPasajero().equals(p) &&
            r.getVehiculo().equals(v) &&
            r.getFechaViaje().equals(fecha) &&
            r.getEstado().equals(Reserva.ESTADO_ACTIVA)) {
            return true;
        }
    }
    return false;
}

// Validación 3: Expiración automática
public void verificarVencidas() {
    int canceladas = 0;
    for (Reserva r : reservas) {
        if (r.estaVencida() && 
            r.getEstado().equals(Reserva.ESTADO_ACTIVA)) {
            r.cancelar();
            canceladas++;
        }
    }
    dao.reescribirReservas(reservas);
    System.out.println("✅ " + canceladas + " reservas canceladas");
}
```

---

## 👥 DISTRIBUCIÓN DEL TRABAJO {#distribución}

### Estructura de Equipo (3 personas)

#### 👤 LÍDER (Mario Nicolás Poveda)
**Tiempo: 360 minutos**

| Tarea | Tiempo | Status |
|-------|--------|--------|
| Coordinar equipo | 30 min | |
| Revisar requerimientos | 15 min | |
| Integrar MenuReservas en Menu | 30 min | |
| Pruebas finales | 30 min | |
| Merge a main | 15 min | |
| **DISPONIBLE PARA AYUDAR** | **240 min** | |

#### 👤 DESARROLLADOR 1 (Modelo + DAO)
**Tiempo: 360 minutos**

| Tarea | Tiempo | Entregable |
|-------|--------|-----------|
| Crear Reserva.java | 45 min | Clase con 3 estados |
| Estados y validación vencimiento | 30 min | método estaVencida() |
| Métodos: cancelar, convertir | 15 min | Transiciones correctas |
| Crear ReservaDAO.java | 45 min | CRUD completo |
| Formato de archivo | 30 min | reservas.txt con delimitador `;` |
| Cargar en startup | 30 min | Constructor con carga automática |
| Pruebas unitarias | 45 min | Validar persistencia |
| Documentación | 30 min | Javadoc |
| Git commits | 15 min | feature/reserva-model, feature/reserva-dao |
| **BUFFER** | **15 min** | |

#### 👤 DESARROLLADOR 2 (Service + View)
**Tiempo: 360 minutos**

| Tarea | Tiempo | Entregable |
|-------|--------|-----------|
| Crear ReservaService.java | 45 min | 6 métodos principales |
| Validación de capacidad | 30 min | hayCapacidadDisponible() |
| Validación de duplicado | 30 min | tieneDuplicado() |
| Método verificarVencidas | 30 min | Cancelación automática |
| Método convertirEnTicket | 45 min | Con descuentos y festivos |
| Crear MenuReservas | 45 min | 6 opciones del submenú |
| Entrada de datos | 30 min | Métodos validación View |
| Pruebas integración | 30 min | Flujo completo |
| Git commits | 15 min | feature/reserva-service, feature/reserva-view |
| **BUFFER** | **15 min** | |

---

## ✅ VALIDACIONES CRÍTICAS {#validaciones}

### Checklist de Implementación

- [ ] **MODELO**
  - [ ] Clase Reserva con todos los atributos
  - [ ] Estados: ACTIVA, CONVERTIDA, CANCELADA
  - [ ] Método estaVencida() correcto
  - [ ] Método cancelar() cambia estado
  - [ ] Método convertir() cambia estado
  - [ ] Implementa Imprimible

- [ ] **DAO**
  - [ ] ReservaDAO.java creado
  - [ ] Archivo reservas.txt con delimitador `;`
  - [ ] guardarReserva() funciona
  - [ ] cargarReservas() al iniciar
  - [ ] obtenerPorCodigo() busca correctamente
  - [ ] actualizar() modifica estado
  - [ ] reescribirReservas() escribe todo el archivo

- [ ] **SERVICE**
  - [ ] ReservaService.java creado
  - [ ] crearReserva() valida:
    - [ ] Capacidad disponible
    - [ ] No duplicado
    - [ ] Pasajero existe
    - [ ] Vehículo existe
  - [ ] cancelarReserva() funciona
  - [ ] convertirEnTicket() aplica:
    - [ ] Descuento por tipo pasajero
    - [ ] Incremento festivo
    - [ ] Crea Ticket nuevo
  - [ ] listarActivas() retorna solo ACTIVAS
  - [ ] historialPasajero() filtra por cedula
  - [ ] verificarVencidas() cancela automáticamente
  - [ ] buscarPorCodigo() encuentra la reserva

- [ ] **VIEW**
  - [ ] MenuReservas integrado en Menu.java
  - [ ] 6 opciones del submenú:
    - [ ] Crear reserva
    - [ ] Cancelar reserva
    - [ ] Listar activas
    - [ ] Historial pasajero
    - [ ] Convertir en ticket
    - [ ] Verificar vencidas
  - [ ] Validación de entrada de usuario
  - [ ] Mensajes claros y útiles

- [ ] **PERSISTENCIA**
  - [ ] Archivo reservas.txt existe
  - [ ] Formato correcto con `;`
  - [ ] Carga al iniciar aplicación
  - [ ] Guarda cambios en disco
  - [ ] Recupera estados correctamente

- [ ] **REGLAS DE NEGOCIO**
  - [ ] No más reservas que capacidad
  - [ ] Reservas activas > 24h se cancelan automáticamente
  - [ ] No duplicados: mismo pasajero, vehículo, fecha
  - [ ] Conversión aplica todas las reglas de venta

---

## ⏱️ TIMELINE {#timeline}

### Distribución de Tiempo (6 Horas = 360 minutos)

```
MINUTO 0-15: ANÁLISIS Y DISTRIBUCIÓN
└─ ✓ Leer requerimiento completo
└─ ✓ Crear ramas en Git
└─ ✓ Distribuir responsabilidades

MINUTO 15-60: FASE 1 - MODELO Y DAO (45 min)
├─ Dev1: Reserva.java (estructura, estados, métodos)
└─ Dev1: ReservaDAO.java (CRUD, archivo)

MINUTO 60-120: FASE 2 - PERSISTENCIA (60 min)
├─ Dev1: Carga en memoria en startup
├─ Dev1: Verificación de vencidas al cargar
└─ Líder: Revisar estructura

MINUTO 120-180: FASE 3 - SERVICE (60 min)
├─ Dev2: ReservaService.java (métodos principales)
├─ Dev2: Validaciones (capacidad, duplicado)
└─ Dev2: Método verificarVencidas()

MINUTO 180-240: FASE 4 - VIEW (60 min)
├─ Dev2: MenuReservas con 6 opciones
├─ Dev2: Integración en Menu.java
└─ Líder: Ayuda con menú

MINUTO 240-330: FASE 5 - PRUEBAS (90 min)
├─ Todo el equipo: Pruebas funcionales
├─ Dev1: Crear reserva, cancelar
├─ Dev2: Convertir, vencidas
└─ Líder: Pruebas extremas

MINUTO 330-360: FASE 6 - FINALES (30 min)
├─ Documentar código
├─ Git commits finales
├─ Revisar checklist
└─ Preparar entrega
```

---

## 📦 ENTREGA FINAL

### Archivos a Entregar

- [ ] **Código fuente Java**
  - [ ] `src/main/java/model/Reserva.java`
  - [ ] `src/main/java/dao/ReservaDAO.java`
  - [ ] `src/main/java/service/ReservaService.java`
  - [ ] `src/main/java/view/Menu.java` (modificado)

- [ ] **Archivos de datos**
  - [ ] `data/reservas.txt` (con al menos 3 reservas de ejemplo)

- [ ] **Control de versiones**
  - [ ] Git log limpio con commits ordenados
  - [ ] Ramas: feature/reserva-model, feature/reserva-dao, etc.
  - [ ] Merge a main completado

- [ ] **Documentación**
  - [ ] README.md actualizado
  - [ ] Diagrama de clases actualizado (UML con Reserva)
  - [ ] Esta ruta de trabajo completada

---

## 🎯 CRITERIOS DE ÉXITO

✅ **Funcional**: Sistema de reservas completo funcionando  
✅ **Validaciones**: Todas las 4 reglas implementadas  
✅ **Persistencia**: Datos se guardan y cargan correctamente  
✅ **Interface**: Menú intuitivo y amigable  
✅ **Código limpio**: Bien estructurado, comentado, profesional  
✅ **Git**: Commits ordenados, ramas limpias, merges exitosos  
✅ **Documentación**: Completa y actualizada  

---

**¡A por el parcial! 💪**

---

*Creado por: Equipo ArquitecturaEnCapas2*  
*Universidad Popular del Cesar - Programación III*  
*Docente: Ing. Esp. Alfredo Bautista*