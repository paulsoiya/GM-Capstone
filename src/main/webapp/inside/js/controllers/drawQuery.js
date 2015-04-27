function drawQuery(response, wordCloudCanvas, pieParentId, pieId, barParentId, barId){

  var responseJSON = response.data;
  
  // Parsing and prepping wordCount and sentiment
  var wordCount = JSON.parse(responseJSON.wordCount);
  var sentiment = JSON.parse(responseJSON.sentiment);
  var wordCountData = [];
  for(var i = 0; i < wordCount.rows.length; ++i){
    if(wordCount.rows[i].key !== "_id" &&
       wordCount.rows[i].key !== "_rev" &&
       wordCount.rows[i].key !== "tweetsentiment" &&
       wordCount.rows[i].key !== "tweettext" &&
       wordCount.rows[i].key !== "tweettime") {
         wordCountData.push([wordCount.rows[i].key, wordCount.rows[i].value]);
       }
  }
  wordCountData.sort(function(current, next) {
        return ((current[1] > next[1]) ? -1 : ((current[1] === next[1]) ? 0 : 1));
  });
  
  
  // Grab the top thirty most frequent words and displays them in a word cloud based on their frequency
  if(wordCountData.length > 30) {
    var tempData = [];
    for(var i = 0; i < 30; ++i) {
      tempData[i] = wordCountData[i];
    }
    wordCountData = tempData;
  }
  WordCloud(wordCloudCanvas, { 
    list: wordCountData, 
    color: 'random-dark',
    shape: 'square',
    rotateRatio: 0.0,
    weightFactor: 2
  });
  
  console.log("wordCountData: "+wordCountData);
  
  // Grabs the top four most frequent words and displays them and their frequency in a bar graph
  var barData = {
    labels: [wordCountData[0][0], 
             wordCountData[1][0], 
             wordCountData[2][0], 
             wordCountData[3][0]
    ],
    datasets: [
      {
        label: "Top four words",
        fillColor: "rgba(220,220,220,0.5)",
        strokeColor: "rgba(220,220,220,0.8)",
        highlightFill: "rgba(220,220,220,0.75)",
        highlightStroke: "rgba(220,220,220,1)",
        data: [wordCountData[0][1], 
               wordCountData[1][1], 
               wordCountData[2][1], 
               wordCountData[3][1] 
        ]
      }
    ]
  };
  
  $("#"+barId).remove();//remove the canvas from the dom
  $("#"+barParentId).append('<canvas id="'+barId+'"><canvas>');//append a new canvas to the dom
  
  var barCanvas = document.querySelector("#"+barId);//get the canvas
  
  var barCtx = barCanvas.getContext('2d');
  barCtx.canvas.width = document.getElementById(barId).width;
  barCtx.canvas.height = document.getElementById(barId).height;
  
  /*
  var barCtx = barGraphCanvas.getContext("2d");
  barCtx.clearRect(0, 0, barGraphCanvas.width, barGraphCanvas.height);
  */
  var barChart = new Chart(barCtx).Bar(barData);

  
  // Turn the sentiment into an arbitrary, normalized percentage and display in a pie chart
  var pieData = [
    {
      value: (Math.round(Math.abs(sentiment.rows[0].value[0] + 1) * 100) / 100) / 2 * 100,
      color:"#000080",
      highlight: "#00004c",
      label: "Positive %"
    },
    {
      value: (Math.round(Math.abs(sentiment.rows[0].value[0] - 1) * 100) / 100) / 2 * 100,
      color: "#7f7fff",
      highlight: "#4c4cff",
      label: "Negative %"
    }
  ]


  
  //$("#pieGraph_canvas").remove(); // remove the canvas from the dom
  //$("#pieCanvasArea").append('<canvas id="pieGraph_canvas"><canvas>');//append a new canvas to the dom
  
  $("#"+pieId).remove();
  $("#"+pieParentId).append('<canvas id="'+pieId+'"><canvas>')
  
  
  var canvas = document.querySelector("#"+pieId);//get the canvas
  
  var pieCtx = canvas.getContext('2d');
  pieCtx.canvas.width = document.getElementById(pieId).width;
  pieCtx.canvas.height = document.getElementById(pieId).height;
  var pieChart = new Chart(pieCtx).Pie(pieData);

  
  // Determines the grade range of the sentiment score and returns relevant grade image
  var positive = Math.round(Math.abs(sentiment.rows[0].value[0] + 1) * 100) / 100 / 2 * 100;
  var grade;  
  if      (positive >= 60)                  grade = "a.png";
  else if (positive < 60 && positive >=50)  grade = "b.png";
  else if (positive < 50 && positive >=40)  grade = "c.png";
  else if (positive < 40 && positive >=30)  grade = "d.png";
  else                                      grade = "f.png";
  return grade;
  
}
