function(keys, values, rereduce) {
  if (!rereduce){
    var length = values.length
    return length
  }else{
    var length = sum(values.map(function(v){return v}))
    return length
  }
}