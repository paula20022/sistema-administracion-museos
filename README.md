# Sistema de Administración de Museos — Patrimonio & Curaduría

![Java](https://img.shields.io/badge/Java-21%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Quarkus](https://img.shields.io/badge/Quarkus-3.40-4695EB?style=for-the-badge&logo=quarkus&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate_JPA-ORM-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL%20%2F%20MariaDB-XAMPP-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Frontend](https://img.shields.io/badge/HTML5%20%7C%20CSS3%20%7C%20JS-Editorial_UI-532024?style=for-the-badge)

Plataforma web **Full-Stack** desarrollada bajo el paradigma de **Programación Orientada a Objetos (POO)** para centralizar la gestión integral del patrimonio cultural: administración de sedes museísticas, salas de exposición, inventario de obras (artefactos), control del estado de conservación, registro biográfico de artistas, clasificación por categorías, programación de recorridos guiados y trazabilidad automática del historial de exhibición.

---

## Tabla de Contenidos
1. [Descripción y Funcionalidades Principales](#-descripción-y-funcionalidades-principales)
2. [Arquitectura del Sistema y Estructura de Carpetas](#-arquitectura-del-sistema-y-estructura-de-carpetas)
3. [Modelo Relacional de Base de Datos (`museo_db`)](#-modelo-relacional-de-base-de-datos-museo_db)
4. [Aplicación de Principios POO](#-aplicación-de-principios-poo)
5. [Tecnologías Utilizadas](#-tecnologías-utilizadas)
6. [Guía de Instalación y Ejecución Local](#-guía-de-instalación-y-ejecución-local)
7. [Capturas de Pantalla del Sistema](#-capturas-de-pantalla-del-sistema)
8. [Autora](#-autora)

---

##  Descripción y Funcionalidades Principales

El sistema resuelve la dispersión de información museográfica mediante cuatro módulos curatoriales conectados en tiempo real a una base de datos relacional:

* **I. Galería de Obras y Conservación:** Registro de piezas con título, reseña histórica, fotografía, porcentaje de conservación (alertando automáticamente cuando es inferior al `60%`), buscador en vivo por obra/artista/sala y modal de **Ficha Técnica Museográfica**.
* **II. Sedes y Salas de Exposición:** Gestión de instituciones museísticas (coordenadas geográficas y portal web) y creación de salas físicas vinculadas a cada museo con control de aforo máximo.
* **III. Maestros Artistas y Categorías:** Inscripción biográfica de autores y definición de categorías temáticas para clasificar el archivo patrimonial.
* **IV. Recorridos Guiados e Historial Automático:** Publicación de rutas temáticas y bitácora automatizada que registra cada ingreso de una obra a una sala con fecha y hora exacta.

---

##  Arquitectura del Sistema y Estructura de Carpetas

El backend implementa una **arquitectura por capas** (`Model` – `Repository` – `Controller`) expuesta mediante servicios **API REST (JSON)**, mientras que el frontend estático es servido directamente por Quarkus desde `META-INF/resources`:

```text
museos/
├── docs/
│   └── screenshots/                  # Evidencias gráficas del sistema para el README
│       ├── 01-portada-galeria.png
│       ├── 02-catalogo-obras.png
│       ├── 03-ficha-tecnica.png
│       ├── 04-sedes-salas.png
│       ├── 05-base-datos-phpmyadmin.png
│       ├── 06-artistas-categorias.png
│       └── 07-recorridos-historial.png
├── src/
│   └── main/
│       ├── java/org/acme/
│       │   ├── controller/           # Capa REST API (Endpoints HTTP GET, POST, DELETE)
│       │   │   ├── ArtifactController.java
│       │   │   ├── CatalogoController.java
│       │   │   └── MuseumController.java
│       │   ├── model/                # Capa de Dominio (Entidades POO + Mapeo JPA)
│       │   │   ├── Artifact.java
│       │   │   ├── Artist.java
│       │   │   ├── Category.java
│       │   │   ├── ExhibitionHistory.java
│       │   │   ├── ExhibitionRoom.java
│       │   │   ├── Museum.java
│       │   │   ├── Tour.java
│       │   │   └── TourRoom.java
│       │   └── repository/           # Capa de Persistencia (Hibernate ORM Panache)
│       │       ├── ArtifactRepository.java
│       │       ├── ArtistRepository.java
│       │       ├── CategoryRepository.java
│       │       ├── ExhibitionHistoryRepository.java
│       │       ├── ExhibitionRoomRepository.java
│       │       ├── MuseumRepository.java
│       │       ├── TourRepository.java
│       │       └── TourRoomRepository.java
│       └── resources/
│           ├── META-INF/resources/   # Interfaz Web Editorial (Frontend)
│           │   ├── index.html
│           │   ├── styles.css
│           │   ├── app.js
│           │   └── portada.jpg
│           ├── application.properties # Configuración MySQL y Hibernate
│           └── import.sql
├── .gitignore
├── mvnw / mvnw.cmd
├── pom.xml                           # Dependencias Maven (Quarkus, Panache, MySQL, Jackson)
└── README.md