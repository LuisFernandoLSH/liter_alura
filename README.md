LiterAlura es una  aplicación de consulta de libros, esto a tavés de una API y almacenando las busquedas en una base de datos.

-------------------Librerias utilizadas-------------------
El programa fue creado con Spring 3.3.1, utilizando Java17 y Maven.
Se utiliza PostgreSQL 16 para administrar la base de datos.
La API de consulta es: https://gutendex.com/


-------------------Configurando la conexión-------------------
Para mantener los datos sencibles ocultos se utilizaron las variabes de entorno, por lo tanto hay que crearlas poder conectarse a Postgres.
Buscamos la configuración de las variables de entorno y agregamos las siguientes (con los  mismos nombres y su valor correspondiente).
DB_HOST  =>  El host que utiliza Postgres (recuerda añadir el port).
DB_NAME  =>  El nombre de la base de datos donde se almacenará la información.
DB_USER  =>  El nombre de usuario de tu Postgres.
DB_PASSWORD  =>  Tu contraseña de acceso a tu Postgres.
(Recuerda que hay que reiniciar el dispositivo para que este cargue las variables)
Si deseas cambiar el nombre de alguna de las variables puedes ir al archivo "src\main\resources\application.properties".
