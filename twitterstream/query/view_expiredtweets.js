function(doc) {
  if((Date.now() - doc.timeLong) > 1000*60*60*hours*days*months){
    emit(null, doc.timeLong);
  }
}