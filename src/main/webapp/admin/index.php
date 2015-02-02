
<!DOCTYPE html>
<html lang="en">
<?php
    require_once('adminDao.php');
    if (isset($_GET['sethashtags'])) {
        $dao = new adminDao('filterquery');
        // test data
        // $makes = 'chevy';
        // $models = 'cruze';
        // $years = '2001';
        // $alternates= 'caddy';
        // //
        // $dao->setFilterQuery($makes, $models, $years, $alternates);
    }
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
                        <button type="submit" class="btn btn-primary">Add</button>
                    </div>
                    <!-- right here will need to be display and collection of hashtags to be pushed into db -->
                    <button class="btn btn-primary" onclick><a href="index.php?sethashtags=true">Apply Hashtags</a></button>
                </form>
                <?php
                    $result = $dao->getFilterQuery();
                    $row = mysql_fetch_assoc($result);
                   	$makes = preg_split('/[,]/', $row['makes']);
                    echo "<select>";
                    foreach($makes as $make) {
                    	echo "<option value=" . $make . ">" . $make . "</option>";  
                    }
                    echo "</select>";
                    echo "<table>";
                    mysql_data_seek($result, 0);
                    while($row = mysql_fetch_assoc($result)) {
                        echo "<h3>Models</h3>"
                        echo "<tr>";
                        echo "<td>" . $row['models'] . "</td>";
                        echo "</tr>";
                    	echo "<h3>Alternates</h3>"
                        echo "<tr>";
                        echo "<td>" . $row['alternates'] . "</td>";
                        echo "</tr>";
                    }
                    echo "</table>";
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
</html>
