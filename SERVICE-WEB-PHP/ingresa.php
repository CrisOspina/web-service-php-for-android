<?php 
	if(isset($_REQUEST['usr']) && isset($_REQUEST['clave']) && isset($_REQUEST['nombre']) &&
	isset($_REQUEST['correo'])) {
		$usr=$_REQUEST['usr'];
		$clave=$_REQUEST['clave'];
		$nombre=$_REQUEST['nombre'];
		$correo=$_REQUEST['correo'];

		$cnx =  mysqli_connect("localhost","root","","bdusuarios") or die("Ha sucedido un error inexperado en la conexion de la base de datos");

		$result = mysqli_query($cnx,"select usr from usuario where usr = '$usr'");

		if (mysqli_num_rows($result) == 0) {
			mysqli_query($cnx,"INSERT INTO usuario (usr,nombre,correo,clave) VALUES ('$usr','$nombre','$correo','$clave')");	
		}
		else {
			echo "Usuario ya existe....";
		}
		mysqli_close($cnx);
	} else {
		echo "Ingrese todos los datos....";
	}
?>
