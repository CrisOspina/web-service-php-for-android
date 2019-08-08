<?php 
	if(isset($_REQUEST['usr']) && isset($_REQUEST['clave']) && isset($_REQUEST['nombre']) &&
	isset($_REQUEST['correo'])) {

		$usr    = $_REQUEST['usr'];
		$clave  = $_REQUEST['clave'];
		$nombre = $_REQUEST['nombre'];
		$correo = $_REQUEST['correo'];
		$cnx    =  mysqli_connect("localhost","root","","bdusuarios") or die("Ha sucedido un error inexperado en la conexion de la base de datos");

		$result = mysqli_query($cnx,"select usr from usuario where usr = '$usr'");

		if (mysqli_num_rows($result) > 0) {
			mysqli_query($cnx,"UPDATE usuario SET nombre='$nombre', correo='$correo', clave='$clave' where usr='$usr'");	
		}	else {
			echo "Usuario no existe....";
		}
		mysqli_close($cnx);
	}
	else {
		echo "No se puede actualizar, verifica...";
	}
?>
