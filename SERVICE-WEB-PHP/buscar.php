<?PHP 
  $usr=$_REQUEST['usr'];

  $cnx = mysqli_connect("localhost","root","","bdusuarios") or die ("ha sucedido un error inesperado en la conexion a la base de datos");

  $result = mysqli_query($cnx,"select * from usuario where usr= '$usr'");

  while($fila=$result->fetch_array()){
      $usuario[]=array_map('utf8_encode', $fila);
  }
  echo json_encode($usuario);

  mysqli_close($cnx);
?>