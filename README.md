## InAdvance  JAVA 

# Entorno
- java 17
- h2
- gradle
- springboot
- JWToken
- MokitoServer
- Swagger

# Ejecutar UserApiApplication

# Curl: Crear usuario

curl --location 'http://localhost:8080/users/create' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "Juan Rodriguez",
"email": "juan2@domain.org",
"password": "passW@rd1",
"phones": [
{
"number": "1234567",
"citycode": "1",
"contrycode": "57"
},
{
"number": "789101112",
"citycode": "2",
"contrycode": "58"
}
]
}'

- Tener en cuenta que el request tiene validaciones de correo y password asi como la obligatoriedad de los campos.

# Curl: Login usuario

curl --location 'http://localhost:8080/users/login' \
--header 'Content-Type: application/json' \
--data-raw '{
"email": "juan23@domain.org",
"password": "passW@rd1"
}'

- Si el usuario a loguear no existe en BD: respuesta "Email o contraseña incorrectos!!"
- Si por el contrario el usuario se encuentra en BD entonces se actualiza los campos:
  "LAST_LOGIN_DATE" ---
  "MODIFIED_DATE" --- 
  "TOKEN" ---  y se responde con el token nuevo.


# Acceso a H2: http://localhost:8080/console-db

- La aplicación java debe estar corriendo.

# Documentación Swagger URL

- http://localhost:8080/swagger-ui/index.html