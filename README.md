# Sistema de Gestión de Tickets Intermunicipales
# 🚌 ArquitecturaEnCapas2 - TransCesar

> Sistema de Gestión de Tickets Intermunicipales con Arquitectura en Capas

[![Status](https://img.shields.io/badge/status-En%20desarrollo-yellow)](https://github.com/Mario-Nicolas-Poveda/ArquitecturaEnCapas2)
[![Java](https://img.shields.io/badge/java-11%2B-blue)](https://www.oracle.com/java/)
[![Git](https://img.shields.io/badge/git-workflow-orange)](https://git-scm.com/)
[![License](https://img.shields.io/badge/license-Academic-green)](LICENSE)

---

## 📋 Tabla de Contenidos

- [Descripción](#-descripción)
- [Características](#-características)
- [Requisitos](#-requisitos)
- [Instalación](#-instalación)
- [Uso](#-uso)
- [Arquitectura](#-arquitectura)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Equipo de Desarrollo](#-equipo-de-desarrollo)
- [Requerimientos](#-requerimientos)
- [Convenciones de Git](#-convenciones-de-git)
- [Documentación](#-documentación)
- [Contacto](#-contacto)

---

## 📝 Descripción

**TransCesar S.A.S.** es una empresa de transporte intermunicipal que requería modernizar su proceso de venta de tickets. Este proyecto implementa un **sistema de consola** que gestiona de forma integral:

- Venta de tickets de viaje
- Registro de vehículos, conductores y pasajeros
- Gestión de rutas
- Reportes y estadísticas operativas

El sistema fue desarrollado por un equipo de 3 estudiantes de **Programación III** de la Universidad Popular del Cesar, siguiendo principios de **Programación Orientada a Objetos** y **Arquitectura en Capas**.

---

## ✨ Características

### 🚗 Gestión de Vehículos
- **Tres tipos de vehículos**:
  - 🚐 **Buseta**: Capacidad 19 pasajeros, tarifa $8.000
  - 🚌 **Micrrobús**: Capacidad 25 pasajeros, tarifa $10.000
  - 🚎 **Bus**: Capacidad 45 pasajeros, tarifa $15.000
- Registro de placa, ruta y estado
- Validación de capacidad en tiempo real
- Persistencia en archivo `vehiculos.txt`

### 👤 Gestión de Personas
- **Conductores**: Cédula, nombre, número de licencia y categoría (B1, B2, C1, C2)
- **Pasajeros**: Con descuentos automáticos según tipo:
  - 👨 **Regular**: Sin descuento (0%)
  - 🎓 **Estudiante**: 15% descuento (con validación de carnet)
  - 👴 **Adulto Mayor**: 30% descuento (60+ años, cálculo automático)
- Persistencia en `conductores.txt` y `pasajeros.txt`

### 🎫 Sistema de Tickets
- Venta de tickets con cálculo automático de descuentos
- Validación de cupos disponibles
- Registro de pasajero, vehículo, fecha, origen, destino y valor final
- Persistencia en `tickets.txt`

### 📍 Gestión de Rutas *(Requerimiento 1)*
- Registro formal de rutas con:
  - Código de ruta
  - Ciudad de origen y destino
  - Distancia en kilómetros
  - Tiempo estimado de viaje
- Cálculo automático de edad para Adulto Mayor
- Asignación de rutas a vehículos

### 📊 Reglas de Negocio Avanzadas *(Requerimiento 2)*
- **Límite de tickets por día**: Máximo 3 tickets por pasajero/día
- **Incremento en festivos**: +20% en tarifa base cuando es día festivo
- Lista configurable de festivos nacionales

### 📈 Módulo de Reportes *(Requerimiento 3)*
- Consultar tickets por fecha específica
- Filtrar por tipo de vehículo
- Filtrar por tipo de pasajero
- Resumen del día actual (total vendido y recaudado)

### 🔐 Sistema de Reservas *(Requerimiento 4)* 
- **[EN DESARROLLO]** Sistema de reserva anticipada de tickets

---

## 🛠️ Requisitos

- **Java**: 11 o superior
- **Git**: Para control de versiones
- **Sistema Operativo**: Windows, Linux o macOS

Verificar instalación:
```bash
java -version
git --version
```

---

## 📦 Instalación

### 1. Clonar el Repositorio

```bash
git clone https://github.com/Mario-Nicolas-Poveda/ArquitecturaEnCapas2.git
cd ArquitecturaEnCapas2
```

### 2. Compilar el Proyecto

```bash
# En Windows
javac -d bin src/main/java/*/*.java

# En Linux/macOS
javac -d bin src/main/java/*/*.java
```

### 3. Crear Directorio de Datos

```bash
mkdir data
```

Los archivos de persistencia se crearán automáticamente:
- `data/vehiculos.txt`
- `data/conductores.txt`
- `data/pasajeros.txt`
- `data/tickets.txt`
- `data/rutas.txt`

---

## 🚀 Uso

### Ejecutar la Aplicación

```bash
java -cp bin view.Main
```

### Interfaz del Sistema

Al iniciar se presenta un menú principal con opciones:

```
====================================
    SISTEMA TRANSCESAR - MENÚ PRINCIPAL
====================================

1. Gestión de Vehículos
2. Gestión de Personas
3. Venta de Tickets
4. Gestión de Rutas
5. Módulo de Reportes
6. Salir

Seleccione una opción: _
```

### Ejemplos de Uso

#### Registrar un Vehículo

```
Opción: 1
Submenú Vehículos:
  a) Registrar vehículo
  b) Listar vehículos
  c) Volver

Seleccione: a

Tipo de vehículo:
  1) Buseta (19 pasajeros, $8.000)
  2) Micrrobús (25 pasajeros, $10.000)
  3) Bus (45 pasajeros, $15.000)

Seleccione: 2

Ingrese placa: ABC-1234
Ingrese ruta: Valledupar-Barranquilla

✓ Vehículo registrado exitosamente
```

#### Vender un Ticket

```
Opción: 3

Cédula del pasajero: 1234567890
Tipo de vehículo: Micrrobús
Fecha de viaje: 2026-03-25

Procesando...
Descuento aplicado (Estudiante): 15%
Valor final: $8.500

✓ Ticket vendido exitosamente
```

---

## 🏗️ Arquitectura

### Patrón: Arquitectura en Capas (MVC)

```
┌──────────────────────────────────────┐
│     CAPA DE PRESENTACIÓN (View)      │  Menu.java, Main.java
│     Interfaz de consola              │
└──────────────────────────────────────┘
                  ↓
┌──────────────────────────────────────┐
│     CAPA DE NEGOCIO (Service)        │  VehiculoService.java
│     Reglas y validaciones            │  PersonaService.java
│                                      │  TicketService.java
│                                      │  RutaService.java
└──────────────────────────────────────┘
                  ↓
┌──────────────────────────────────────┐
│   CAPA DE PERSISTENCIA (DAO)         │  VehiculoDAO.java
│   Acceso a datos (archivos .txt)     │  PersonaDAO.java
│                                      │  TicketDAO.java
│                                      │  RutaDAO.java
└──────────────────────────────────────┘
                  ↓
┌──────────────────────────────────────┐
│    CAPA DE DOMINIO (Model)           │  Vehiculo.java, Bus.java
│    Entidades del negocio             │  Persona.java, Conductor.java
│                                      │  Pasajero.java, Ticket.java
│                                      │  Ruta.java
└──────────────────────────────────────┘
```

**Principio**: La comunicación fluye en una sola dirección:
```
View → Service → DAO → Model
```

---

## 📂 Estructura del Proyecto

```
ArquitecturaEnCapas2/
├── src/
│   └── main/java/
│       ├── model/                    # Entidades del negocio
│       │   ├── Vehiculo.java        # Clase abstracta
│       │   ├── Bus.java
│       │   ├── MicroBus.java
│       │   ├── Buseta.java
│       │   ├── Persona.java         # Clase abstracta
│       │   ├── Conductor.java
│       │   ├── Pasajero.java        # Clase abstracta
│       │   ├── PasajeroRegular.java
│       │   ├── PasajeroEstudiante.java
│       │   ├── PasajeroAdultoMayor.java
│       │   ├── Ticket.java
│       │   ├── Ruta.java
│       │   ├── Imprimible.java      # Interfaz
│       │   └── Calculable.java      # Interfaz
│       │
│       ├── dao/                     # Acceso a datos
│       │   ├── VehiculoDAO.java
│       │   ├── PersonaDAO.java
│       │   ├── TicketDAO.java
│       │   └── RutaDAO.java
│       │
│       ├── service/                 # Lógica de negocio
│       │   ├── VehiculoService.java
│       │   ├── PersonaService.java
│       │   ├── TicketService.java
│       │   └── RutaService.java
│       │
│       └── view/                    # Interfaz de usuario
│           ├── Main.java            # Punto de entrada
│           └── Menu.java            # Menú principal
│
├── data/                            # Archivos de persistencia
│   ├── vehiculos.txt
│   ├── conductores.txt
│   ├── pasajeros.txt
│   ├── tickets.txt
│   └── rutas.txt
│
├── docs/                            # Documentación
│   ├── DOCUMENTACION_ArquitecturaEnCapas2.docx
│   ├── RESUMEN_EJECUTIVO.txt
│   ├── ANALISIS_GIT_DETALLADO.md
│   ├── ACCIONES_PENDIENTES.md
│   └── GUIA_COMPARTIR_EN_GITHUB.md
│
├── .gitignore
├── README.md                        # Este archivo
└── LICENSE
```

---

## 👥 Equipo de Desarrollo

| Rol | Nombre | Responsabilidad |
|-----|--------|-----------------|
| **Líder de Desarrollo** | Mario Nicolás Poveda | Coordinación, capa View, merge a main |
| **Desarrollador 1** | [Nombre] | Jerarquía Vehiculo + DAO/Service |
| **Desarrollador 2** | [Nombre] | Jerarquía Persona + Ticket + DAO/Service |

### Contribuciones

```bash
# Ver contribuciones por desarrollador
git shortlog -sn

# Ver commits de una rama específica
git log feature/desarrollador1-vehiculos --oneline

# Ver estadísticas de commits
git log --stat
```

---

## 📋 Requerimientos

### ✅ Requerimiento 1: Gestión de Rutas y Validación de Edad
**Capa Principal**: Model

- [x] Clase Ruta con: código, origen, destino, distancia, tiempo estimado
- [x] Integración en registro de vehículos
- [x] Cálculo automático de edad para Adulto Mayor (60+ años)
- [x] Descuento automático del 30% para mayores

**Rama**: `feature/gestion-rutas`  
**Status**: ✅ Completado

---

### ✅ Requerimiento 2: Reglas de Negocio Avanzadas
**Capa Principal**: Service

- [x] Validación: Máximo 3 tickets por pasajero/día
- [x] Incremento de 20% en tarifa base para días festivos
- [x] Lista configurable de festivos
- [x] Notificación al usuario en caso de rechazo

**Rama**: `feature/reglas-negocio` *(Pendiente merge)*  
**Status**: ⚠️ En integración

---

### ✅ Requerimiento 3: Módulo de Reportes
**Capa Principal**: View

- [x] Consultar tickets por fecha específica
- [x] Filtrar por tipo de vehículo
- [x] Filtrar por tipo de pasajero
- [x] Resumen del día actual
- [x] Uso de interfaz Imprimible

**Rama**: `feature/reportes`  
**Status**: ✅ Completado

---

### 🔄 Requerimiento 4: Sistema de Reservas *(SORPRESA)*
**Capas Impactadas**: Model, DAO, Service, View

- [ ] Sistema de reserva anticipada
- [ ] Consulta de disponibilidad
- [ ] Cancelación de reservas
- [ ] Historial de reservas

**Rama**: `feature/sistema-reservas`  
**Status**: 🔧 En desarrollo

---

## 🔀 Convenciones de Git

### Estrategia de Branching

```
main (rama principal, siempre funcional)
├── feature/gestion-rutas ✓ MERGED
├── feature/reglas-negocio (⚠️ Pendiente merge)
├── feature/reportes ✓ MERGED
└── feature/sistema-reservas (En desarrollo)
```

### Convención de Commits (Conventional Commits)

```bash
# Formato general
<type>(<scope>): <subject>

<body>

<footer>
```

### Tipos de Commit

```bash
# Característica nueva
git commit -m "feat(model): agregar clase Ruta con atributos"

# Corrección de bug
git commit -m "fix(service): validar cupos antes de vender ticket"

# Refactorización
git commit -m "refactor(dao): mejorar lectura de archivos"

# Documentación
git commit -m "docs: actualizar README con ejemplos de uso"

# Tests
git commit -m "test(ticket): agregar pruebas de descuento"
```

### Flujo de Trabajo

```bash
# 1. Crear rama de característica
git checkout -b feature/mi-caracteristica

# 2. Hacer cambios y commits
git add .
git commit -m "feat(scope): descripción breve"

# 3. Empujar a remoto
git push origin feature/mi-caracteristica

# 4. El Líder revisa y hace merge a main
git checkout main
git merge feature/mi-caracteristica
git push origin main
```

---

## 📊 Estadísticas del Proyecto

| Métrica | Valor |
|---------|-------|
| Commits | 18+ |
| Ramas de característica | 4 |
| Archivos Java | 24+ |
| Líneas de código | 1,500+ |
| Clases implementadas | 15+ |
| Servicios | 4 |
| DAOs | 4 |
| Merges exitosos | 5/5 |
| Conflictos de merge | 0 |
| Funcionalidad completada | 83% |

---

## 🎨 Patrones de Diseño

### 1. Patrón DAO (Data Access Object)
```java
// Abstracción de acceso a datos
VehiculoDAO dao = new VehiculoDAO();
dao.guardar(bus);
List<Vehiculo> vehiculos = dao.obtenerTodos();
```

### 2. Patrón Service Layer
```java
// Lógica de negocio centralizada
VehiculoService service = new VehiculoService();
service.validarPlaca(placa);  // Validación
service.registrarVehiculo(bus);
```

### 3. Patrón Strategy (Polimorfismo)
```java
// Cálculo de descuentos según tipo de pasajero
double descuento = pasajero.calcularDescuento();
// Sin preguntar qué tipo es, el objeto sabe cómo calcularlo
```

### 4. Arquitectura en Capas
```
Separación clara: View → Service → DAO → Model
Cada capa tiene una responsabilidad específica
```

---

## 🧪 Pruebas y Validaciones

### Validaciones Implementadas

- ✅ Placa única en vehículos
- ✅ Cédula única en personas
- ✅ Cupos disponibles en vehículos
- ✅ Conductor con licencia válida
- ✅ Máximo 3 tickets por pasajero/día
- ✅ Incremento de tarifa en días festivos

### Próximas Mejoras

- [ ] Pruebas unitarias con JUnit 5
- [ ] Migración a Base de Datos (SQLite)
- [ ] Mejora de interfaz gráfica (JavaFX)
- [ ] Documentación JavaDoc
- [ ] Logging completo de operaciones

---

## 📚 Documentación

Este proyecto incluye documentación completa:

| Documento | Descripción |
|-----------|-------------|
| [DOCUMENTACION_ArquitecturaEnCapas2.docx](docs/DOCUMENTACION_ArquitecturaEnCapas2.docx) | Documentación formal completa (40+ páginas) |
| [RESUMEN_EJECUTIVO.txt](docs/RESUMEN_EJECUTIVO.txt) | Resumen ejecutivo del proyecto |
| [ANALISIS_GIT_DETALLADO.md](docs/ANALISIS_GIT_DETALLADO.md) | Análisis técnico de commits y branching |
| [ACCIONES_PENDIENTES.md](docs/ACCIONES_PENDIENTES.md) | Tareas pendientes y recomendaciones |
| [GUIA_COMPARTIR_EN_GITHUB.md](docs/GUIA_COMPARTIR_EN_GITHUB.md) | Instrucciones para actualizar documentación |

---

## 🔧 Troubleshooting

### Error: "No se encuentran las clases"
```bash
# Verificar que la compilación fue correcta
javac -d bin src/main/java/*/*.java
ls -la bin/  # Debe mostrar los directorios: model, dao, service, view
```

### Error: "Archivo no encontrado"
```bash
# Asegurarse de que existe el directorio data
mkdir -p data
```

### Error: "No se puede escribir en el archivo"
```bash
# Verificar permisos
chmod -R 755 data/
```

---

## 📖 Recursos de Aprendizaje

- [Documentación Java Official](https://docs.oracle.com/javase/)
- [Git - The Simple Guide](http://rogerdudley.github.io/git-guide/)
- [Conventional Commits](https://www.conventionalcommits.org/)
- [Arquitectura en Capas](https://en.wikipedia.org/wiki/Multitier_architecture)
- [SOLID Principles](https://en.wikipedia.org/wiki/SOLID)

---

## 📞 Contacto

**Líder del Proyecto**:  
📧 Email: mario.poveda@email.com  
🔗 GitHub: [@Mario-Nicolas-Poveda](https://github.com/Mario-Nicolas-Poveda)

**Universidad**: Universidad Popular del Cesar  
👨‍🏫 **Docente**: Ing. Esp. Alfredo Bautista  
📚 **Asignatura**: Programación III  
📧 Email Docente: adbautista@unicesar.edu.co

---

## 📋 License

Este proyecto es de **propósito académico** para la Universidad Popular del Cesar.

```
Copyright © 2026 - Sistema TransCesar
Proyecto académico - Programación III
```

---

## 🙏 Agradecimientos

- A la empresa **TransCesar S.A.S.** por el contexto y requerimientos
- Al **Ing. Esp. Alfredo Bautista** por la guía y supervisión
- Al equipo de desarrollo por el trabajo colaborativo
- A la comunidad de desarrolladores por las mejores prácticas compartidas

---

## ✨ Estado del Proyecto

```
┌────────────────────────────────────┐
│    FASES DE DESARROLLO             │
├────────────────────────────────────┤
│ ✅ Fase 1: Estructura Base         │ Completado
│ ✅ Fase 2: Módulos Core            │ Completado
│ ✅ Fase 3: Requerimientos 1-3      │ Completado
│ 🔄 Fase 4: Requerimiento 4        │ En desarrollo
│ ⏳ Fase 5: Tests y Mejoras        │ Pendiente
│ ⏳ Fase 6: Producción             │ Futuro
└────────────────────────────────────┘

Versión: 0.9.0 (Pre-release)
Última actualización: Marzo 2026
Funcionalidad: 83% completada
```

---

**🚀 ¡Gracias por revisar este proyecto!**

Para más detalles, consulta la [documentación completa](docs/).

```
Made with ❤️ by the TransCesar Development Team
```

---

*Última actualización: Marzo 20, 2026*  
*Rama: main*  
*Commit: [último commit]*
