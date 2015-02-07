<?php

class adminDao
{
	protected $objectName;
	protected $database;

	public function __construct($objName)
	{
		$this->objectName = strtolower($objName);
		// Connect to MySQL 
		$properties = parse_ini_file("properties.ini");

		//Do not instantiate multiple connections to database
		if ( $this->database == null)
		{
			if ( !( $this->database = mysql_connect( $properties["dbUrl"], $properties["username"], $properties["password"] ) ) ) 
			{
				echo "<p> Be sure to fill out information in the properties.ini file </p>";
				die( "Could not connect to database </body></html>" ); 
			} 

			// open our database 
			if ( !mysql_select_db( $properties["dbName"], $this->database ) ) 
				die( "Could not open the database </body></html>" ); 
		}
	}

	// public function getMakeFilterQuery()
	// {
	// 	$query = "select * from filterquerymakes";

	// 	if(!($result = mysql_query( $query, $this->database))) 
	// 	{ 
	// 		print( "Could not execute query! <br />" ); 
	// 		die( mysql_error() . "</body></html>" ); 
	// 	} 
	// 	return $result;
	// }

	// public function getModelsFilterQuery($make) {
	// 	$query = "select * from filterquerymodels where make=\"". $make ."\"";
	// 	if ( !( $result = mysql_query( $query, $this->database ) ) ) 
	// 	{ 
	// 		print( "Could not execute query! <br />" ); 
	// 		die( mysql_error() . "</body></html>" ); 
	// 	} 
	// 	return $result;
	// }

	// public function getAlternatesFilterQuery($make) {
	// 	$query = "select * from filterqueryalternates where make=\"" . $make "\"";
	// 	if ( !( $result = mysql_query( $query, $this->database ) ) ) 
	// 	{ 
	// 		print( "Could not execute query! <br />" ); 
	// 		die( mysql_error() . "</body></html>" ); 
	// 	} 
	// 	return $result;
	// }

	// // assumes that the paramaters passed are well formed csv
	// public function setFilterQuery($makes, $models, $years, $alternates) {
	// 	$query = "insert into filterquery values(0, '" . $makes . "', '" . $models . "', '" . $years . "', '" . $alternates . "')";
	// 	if ( !( $result = mysql_query( $query, $this->database ) ) ) 
	// 	{
	// 		$query = "UPDATE filterquery SET makes='" . $makes ."', models='" . $models . "', years='" . $years . "', alternates='" . $alternates . "' WHERE filterid=0"; 
	// 		if ( !( $result = mysql_query( $query, $this->database ) ) ) 
	// 		{
	// 			print( "Could not execute query! <br />" ); 
	// 			die( mysql_error() . "</body></html>" ); 
	// 		}
	// 	} 
	// }

	// public function display($result, $displayHeader=true)
	// {
	// 	if ($displayHeader)
	// 	{
	// 		print("<tr>");
	// 	    for ( $index = 0; $index < mysql_num_fields($result); $index++ )
	// 	    {
	// 	        $header = mysql_field_name($result, $index);
	// 	        print("<th>$header</td>");
	// 	    }
	// 	    print("</tr>");
	// 	}
	    
	//     // fetch each record in result set 
	//     for ( $counter = 0; $row = mysql_fetch_row( $result ); $counter++ ) 
	//     { 
	//         // build table to display results 
	//         print( "<tr>" ); 
	//         foreach ( $row as $key => $value ) 
	//             print( "<td>$value</td>" ); 

	//         print( "</tr>" ); 
	//     } // end for 
	// }

	// //This is mostly for test functions that need to do things like execute delete queries etc. Try not to use this method in non-test code
	// public function runQuery($query)
	// {
	// 	if ( !( $result = mysql_query( $query, $this->database ) ) ) 
	// 	{ 
	// 		print( "Could not execute query! <br />" ); 
	// 		die( mysql_error() . "</body></html>" ); 
	// 	} 
	// 	return $result;
	// }


}
	
?>