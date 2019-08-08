<?php 
	$correo=$_REQUEST['correo'];
	$clave=$_REQUEST['clave'];
	//Conexion a la base de datos con PDO
	//en vez de localhost se pone el servidor de la nube o el ip servidor
	//$cnx = new PDO("mysql:host=localhost;dbname=serviciosandroidphp","root","");
	$cnx =  mysqli_connect("localhost","root","","bdusuarios");

	//Ejecutar una sentencia SELECT y recibir una respuesta
	$res=$cnx->query("select * from usuario where correo = '$correo' and clave = '$clave'");

	//si existe el usuario la variable res queda en 1 y sino en 0
	//En este arreglo se guardará la informacion para pasarla a JSON
	$datos = array();
	foreach ($res as $row) {
		$json['datos'][]=$row;
	}

	//pasar los datos del array a JSON con informacion o vacío
	echo json_encode($datos);
 ?>