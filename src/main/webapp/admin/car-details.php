<?php
    require_once('adminDao.php');
    
    $newMake = "";
    $dao = new adminDao('filterquery');
    if(isset($_GET['newMake'])) {
    	$newMake = $_GET['newMake'];
    }
    else {
    	$newMake = "chevrolet";
    }
    $result = $dao->getMakeFilterQuery();
	$models = $dao->getModelsFilterQuery($newMake);
    $alternates = $dao->getAlternatesFilterQuery($newMake);
    $modelRow = mysql_fetch_assoc($models);
    $alternateRow = mysql_fetch_assoc($alternates);
    $modelString = '';
    $alternateString = '' ;
    $modelString .= $modelRow['models'];
    if(isset($_GET['newModel'])) {
    	$modelString .= ",";
    	$modelString .= $_GET['newModel'];
    }
    
    $alternateString .= $alternateRow['alternates'];
    if(isset($_GET['newAlternate'])) {
		$alternateString .= ",";
    	$alternateString .= $_GET['newAlternate'];
    }
   $row = mysql_fetch_assoc($result);
                   	$makes = preg_split('/[,]/', $row['makes']);
                    foreach($makes as $make) {
                    	echo "<option value=" . $make . " " . ($make == $newMake ? "selected>" : ">") . $make . "</option>";  
                    }
$arr = array("models" => $modelString,
             "alt" => $alternateString);
echo json_encode($array);
?>