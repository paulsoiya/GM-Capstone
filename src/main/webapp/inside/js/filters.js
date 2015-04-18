'use strict';

/* Filters */

angular.module('filters', []).filter('checkmark', function() {
  return function(input) {
    return input ? '\u2713' : '\u2718';
  };
});

angular.module('filters', []).filter('selectFromSelected', function () {
    return function (items, value) {
        var out = [{}];
        console.log(items);
        console.log(value);
        if(value){
            for(x=0; x<items.length; x++){

                if(items[x].makeId == value || items[x].makeId == -1 )
                    out.push(items[x]);
            }
            return out;
        }
        else if(!value){
            return items
        }
    };
});

angular.module('filters', []).filter('selectFromSelected2', function () {
    return function (items, value) {
        var out = [{}];
        
        console.log(items);
        console.log(value);
        if(value){
            for(x=0; x<items.length; x++){
                if(items[x].modelId == value || items[x].modelId == -1 )
                    out.push(items[x]);
            }
            return out;
        }
        else if(!value){
            return items
        }
    };
});