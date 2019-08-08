<?php 

	if(isset($_REQUEST['usr'])) {

		$usr=$_REQUEST['usr'];

		$cnx =  mysqli_connect("localhost","root","","bdusuarios") or die("Ha sucedido un error inexperado en la conexion de la base de datos");
		
		$result = mysqli_query($cnx,"select usr from usuario where usr = '$usr'");

		if (mysqli_num_rows($result) > 0) {
			mysqli_query($cnx,"DELETE from usuario where usr='$usr'");	
		} else {
			echo "Usuario no existe....";
		}
		mysqli_close($cnx);

	} else {
		echo "No se puede eliminar, verifica...";
	}
?>
