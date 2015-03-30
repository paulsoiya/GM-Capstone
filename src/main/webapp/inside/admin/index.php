
<!DOCTYPE html>
<html lang="en">
<?php
    require_once('php/adminDao.php'); 
    require_once('php/update.php'); 
    $newMake = "";
    $dao = new adminDao('filterquery');

    if(isset($_GET['newMake'])) {
    	$newMake = $_GET['newMake'];
    }
    else {
    	$newMake = "chevrolet";
    }

    $allMakes = $dao->getMakeFilterQuery();

    $modelString = $dao->getModelString($newMake);
    $alternateString = $dao->getAlternateString($newMake);
?>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Admin- Manage Data</title>


        <link rel="stylesheet" href="../../plugins/bootstrap/dist/css/bootstrap.min.css">

        <script src="../../plugins/jquery/dist/jquery.min.js"></script>
        <script src="../../plugins/bootstrap/dist/js/bootstrap.min.js"></script>

        <link rel="stylesheet" type="text/css" href="../../css/inside.css">
        <!-- 
        <script type="text/javascript" src="scripts/manage-data.js"></script>

        -->
        <script defer>
            function removeModel() {
                console.log("adfsf");
                element = document.getElementById("removeModelSelector").value;
                
                if(element !== undefined){
                    console.log(element);
                }
            }
            function removeAlternate() {
                console.log("adfsf");
                element = document.getElementById("removeAlternateSelector").value;
                
                if(element !== undefined){
                    console.log(element);
                }
            }
        
        </script>
    </head>
   
    <body>
    <!-- Fixed navbar -->
    <nav class="navbar navbar-inverse navbar-fixed-top white">
      <div class="container">
        <div class="navbar-header">
        
          <a class="navbar-brand" href="#">  
              <img src="../../images/primaryNavigationGMLogo.png" 
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
                    $row = mysql_fetch_assoc($allMakes);
                   	$makes = preg_split('/[,]/', $row['makes']);
                    foreach($makes as $make) {
                    	echo "<option value=" . $make . " " . ($make == $newMake ? "selected>" : ">") . $make . "</option>";  
                    }
                ?>
                </select>
                
                <h3>Models   <form method="post" name="update" action="php/update.php?currentMake=<?php echo $newMake ?>"><input type="text" name="newModel" placeholder="New Model"></input>
                	<input class="btn btn-primary" type="submit" value="Add Model" action=""></input>
                </form></h3>
                <div id="models"></div>
                <select id="removeModelSelector" multiple class="form-control">
                <?php
                    $models = preg_split('/[,]/', $modelString);
                    foreach($models as $model) {
                        echo "<option>" . $model . "</option>";
                    }
                ?>
                </select>
                <button class="btn btn-primary" onclick="removeModel()">Remove</button>           

               	<h3>Alternates <form method="post" name="update" action="php/update.php?currentMake=<?php echo $newMake ?>"><input type="text" name="newAlternate" placeholder="New Alternate"></input>
                	<input class="btn btn-primary" type="submit" value="Add Alternate" action=""></input>
                </form></h3>
          
                <div id="alt"></div>
                <select id="removeAlternateSelector" multiple class="form-control">
                <?php
                    $alternates = preg_split('/[,]/', $alternateString);
                    foreach($alternates as $alternate) {
                        echo "<option>" . $alternate . "</option>";
                    } 
                ?>
                </select>
                <button class="btn btn-primary" onclick="removeAlternate()">Remove</button> 
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
    	function newModelSelected(val) {
    		window.location.href='index.php?newModel=' + val;
    	}
    	function newAlternateSelected(val) {
    		window.location.href='index.php?newAlternate=' + val;
    	}
    </script>
</html>
