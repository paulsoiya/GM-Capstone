<?php
    require_once('adminDao.php'); 
	$dao = new adminDao('filterquery');

	$currentMake = $_GET['currentMake'];
	$newModel = '';
	if(isset($_POST['newModel'])) {
		$newModel = $_POST['newModel'];
		$currentModels = $dao->getModelString($currentMake);
		if(strlen($currentModels) > 0 && strlen($newModel) > 0) {
			$currentModels .= ",";
		}
		$currentModels .= $newModel;
		$dao->setFilterQueryModels($currentMake, $currentModels);
	}

	if(isset($_POST['newAlternate'])) {
		$newAlternate = $_POST['newAlternate'];
		$currentAlternates = $dao->getAlternateString($currentMake);
		if(strlen($currentAlternates) > 0  && strlen($newAlternate) > 0) {
			$currentAlternates .= ",";
		}
		$currentAlternates .= $newAlternate;
		$dao->setFilterQueryAlternates($currentMake, $currentAlternates);
	}
?>
	