function(doc){
  //if(doc.tweettime > 0 && doc.tweettime < 1426936843000  && doc.tweetlocation = "Arizona" ){
    for (var key in doc){
      emit(key, doc[key]);
    }
  //}
} 