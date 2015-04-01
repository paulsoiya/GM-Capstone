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

	if(isset($_POST['removeModelSelector'])) {
		$removeModel = $_POST['removeModelSelector'];
		$currentModels = $dao->getModelString($currentMake);

		// do stuff here
		$temp = explode(",", $currentModels);
		$newModelString = "";
		foreach($temp as $item) {
			if($item != $removeModel && strlen($item) > 0) {
				$newModelString .= $item . ",";
			}
		}
		//
		$dao->setFilterQueryModels($currentMake, $newModelString);
	}

	if(isset($_POST['removeAlternateSelector'])) {
		$removeAlternate = $_POST['removeAlternateSelector'];
		$currentAlternates = $dao->getAlternateString($currentMake);
		// do stuff here
		$temp = explode(",", $currentAlternates);
		$newAlternateString = "";
		foreach($temp as $item) {
			if($item != $removeAlternate && strlen($item) > 0) {
				$newAlternateString .= $item . ",";
			}
		}
		//
		$dao->setFilterQueryAlternates($currentMake, $newAlternateString);
		// die($newAlternateString);
	}
?>
	