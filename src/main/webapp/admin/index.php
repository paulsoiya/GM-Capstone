
<!DOCTYPE html>
<html lang="en">
<?php
    require_once('adminDao.php');
    
    $newMake = "";

    $dao = new adminDao('filterquery');
    if(isset($_GET['newMake'])) {
    	$newMake = $_GET['newMake'];
    }
   //  else {
   //  	$newMake = "chevrolet";
   //  }

   //  $result = $dao->getMakeFilterQuery();
  	// $models = $dao->getModelsFilterQuery($newMake);
  	// $alternates = $dao->getAlternatesFilterQuery($newMake);

?>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Admin- Manage Data</title>


        <link rel="stylesheet" href="../plugins/bootstrap/css/bootstrap.min.css">

        <script src="../plugins/jquery/jquery-1.11.2.min.js"></script>
        <script src="../plugins/bootstrap/js/bootstrap.min.js"></script>

        <link rel="stylesheet" type="text/css" href="../css/inside.css">

       
    </head>
   
    <body>

    <!-- Fixed navbar -->
    <nav class="navbar navbar-inverse navbar-fixed-top white">
      <div class="container">
        <div class="navbar-header">
        
          <a class="navbar-brand" href="#">  
              <img src="../images/primaryNavigationGMLogo.png" 
                   style="margin-top:-10px;" alt="General Motors">
          </a>
        </div>
          <div class="paddingTop alignRight">
              Welcome, Admin | <a href="">Logout</a>
          </div>
      </div>
    </nav>
    <div class="secondNavBar">
        
        <div class="row">
            <div class="col-md-8"><h1>SocialGM: Admin</h1></div>
            <div class="col-md-4"> 
                <ul class="nav nav-pills pillMarigin">
                    <li role="presentation" class="active">
                        <a href="index.html">Manage Data</a>
                    </li>
                    <li role="presentation">
                        <a href="manage-analytics.html">Manage Analytics</a>
                    </li>
                    <li role="presentation">
                        <a href="manage-users.html">Manage Users</a>
                    </li>
                </ul>
            </div>
        </div>  
    </div>

    <!-- Begin page content -->
    <div class="container">
        <div class="row">
            <div class="col-lg-5 grayBoxArea">
                <h3>Data Pull Frequency</h3>
                <form class="form-inline" id="dataAccessForm">
                <div class="form-group">
              
                    <select class="form-control"
                            name="accessFrequency" 
                            style="width:200px">
                        <option>24 Hours</option>
                    </select>
                    
                    <button type="submit"
                            class="btn btn-primary">
                        Update
                    </button>
                </div>
                </form>
                <h3>Data Retention</h3>
                <form class="form-inline" id="dataRetentionForm">
                <div class="form-group">
              
                    <select class="form-control"
                            name="top-words" 
                            style="width:200px">
                        <option value="6">6 Months</option>
                    </select>
                    
                    <button type="submit"
                            class="btn btn-primary">
                        Update
                    </button>
                </div>
                </form>
                <h3>Data Statistics</h3>
                
                <table class="table-condensed">
                    <tr>
                        <td><b>Total Number of Tweets:</b></td>
                        <td><span id="totalNumTweets">243500</span></td>
                    </tr>
                    <tr>
                        <td><b>Total Data Storage:</b></td>
                        <td><span id="totalDataStorage">29.3 TB</span></td>
                    </tr>
                </table>
               
            </div>
            <div class="col-lg-1"></div>
            <div class="col-lg-5 grayBoxArea">
                <h3>Hash Tags</h3>
                <form class="form-inline" id="hashtagForm">
                    <div class="form-group">
                        <input type="text"
                               name="hashtag" 
                               class="form-control"
                               placeholder="Enter hashtag">
                        <button type="submit" class="btn btn-primary">Add Make</button>
                    </div>
                </form>
                <select class="form-control" onchange="newMakeSelected(this.value)">
                <?php
                    $row = mysql_fetch_assoc($result);
                   	$makes = preg_split('/[,]/', $row['makes']);
                    foreach($makes as $make) {
                    	echo "<option value=" . $make . " " . ($make == $newMake ? "selected>" : ">") . $make . "</option>";  
                    }
                ?>
                </select>
                <h3>Models<input type="button" value="Add Model"></input></h3>
                <?php
                    while($row = mysql_fetch_assoc($models)) {
                        echo $row['models'];
                    }
                ?>
                <h3>Alternates<input type="button" value="Add Alternate"></input></h3>
                <?php
                	while($row = mysql_fetch_assoc($alternates)) {
                        echo $row['alternates'];
                    }
                ?>
            </div>
        </div>
    </div>

    <footer class="footer">
      <div class="container">
          <p>Last Data Update: XXXX</p>
      </div>
    </footer>

       
    </body>
    <script>
    	function newMakeSelected(val) {
    		window.location.href='index.php?newMake=' + val;
    	}
    </script>
</html>
