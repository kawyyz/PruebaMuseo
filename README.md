PruebaMuseo - Arquitectura de Microservicios

Descripción del Proyecto

PruebaMuseo es una plataforma de gestión de museos desarrollada bajo una arquitectura de microservicios utilizando Spring Boot. El sistema permite administrar clientes, museos, reservas, pagos, tickets, eventos, exposiciones, salas y empleados, garantizando una separación clara de responsabilidades y una comunicación distribuida entre servicios.

La solución utiliza Eureka Server para el descubrimiento de servicios, Spring Cloud Gateway para el enrutamiento centralizado, WebClient para la comunicación entre microservicios, JWT para autenticación y Docker para el despliegue de la aplicación.



Integrantes

Arion Ruiz-Tagle Zepeda



Arquitectura

Componentes Principales

* Eureka Server (Service Discovery)
* API Gateway
* Base de datos MySQL
* 10 Microservicios independientes

Microservicios Implementados

Microservicio     | Puerto 
----------------- | ------ 
ms-clientes       | 8081   
ms-museos         | 8082   
ms-reservas       | 8083   
ms-pagos          | 8084   
ms-notificaciones | 8085   
ms-eventos        | 8086   
ms-exposiciones   | 8087   
ms-tickets        | 8088   
ms-empleados      | 8089   
ms-salas          | 8090   
API Gateway       | 8091   
Eureka Server     | 8761   



Tecnologías Utilizadas

* Java 21
* Spring Boot
* Spring Data JPA
* Spring Security
* JWT
* Spring Cloud Gateway
* Eureka Server
* WebClient
* MySQL
* Docker
* Swagger/OpenAPI
* Maven
* GitHub



Comunicación entre Microservicios

La comunicación entre servicios se realiza mediante WebClient utilizando Eureka Server para la resolución dinámica de servicios.

Ejemplos:

* Reservas consulta información de clientes.
* Reservas consulta información de museos.
* Pagos procesa reservas.
* Tickets se generan a partir de reservas confirmadas.
* Notificaciones informa eventos relevantes del sistema.



API Gateway

Todas las solicitudes externas ingresan a través del API Gateway.

Rutas Principales

Ruta                  

/api/clientes       
/api/museos         
/api/reservas       
/api/pagos       
/api/notificaciones 
/api/eventos        
/api/exposiciones   
/api/tickets        
/api/empleados      
/api/salas         
/auth/login               



Documentación Swagger

Una vez ejecutados los servicios, la documentación puede consultarse en:

Clientes

http://localhost:8081/swagger-ui.html

Museos

http://localhost:8082/swagger-ui.html

Reservas

http://localhost:8083/swagger-ui.html

Pagos

http://localhost:8084/swagger-ui.html

Notificaciones

http://localhost:8085/swagger-ui.html

Eventos

http://localhost:8086/swagger-ui.html

Exposiciones

http://localhost:8087/swagger-ui.html

Tickets

http://localhost:8088/swagger-ui.html

Empleados

http://localhost:8089/swagger-ui.html

Salas

http://localhost:8090/swagger-ui.html



Despliegue Local

Requisitos

* Java 21
* Maven
* Docker Desktop
* MySQL
* Git

Clonar repositorio


git clone https://github.com/kawyyz/PruebaMuseo.git


Compilar microservicios


mvn clean package


Construir imágenes Docker


docker build -t prueba3-clientes ./clientes
docker build -t prueba3-museos ./museos
docker build -t prueba3-reservas ./reservas
docker build -t prueba3-pagos ./pagos
docker build -t prueba3-notificaciones ./notificaciones
docker build -t prueba3-eventos ./eventos
docker build -t prueba3-exposiciones ./exposiciones
docker build -t prueba3-tickets ./tickets
docker build -t prueba3-empleados ./empleados
docker build -t prueba3-salas ./salas
docker build -t prueba3-gateway ./gateway
docker build -t prueba3-eurekaserver ./eurekaserver


Ejecutar contenedores

Iniciar Eureka Server, microservicios y Gateway mediante Docker.



Seguridad

La autenticación se implementa mediante JWT.

Flujo:

1. Usuario realiza login.
2. Se genera token JWT.
3. El token es enviado en cada petición protegida.
4. Gateway valida y reenvía la solicitud al microservicio correspondiente.



Repositorio

https://github.com/kawyyz/PruebaMuseo
