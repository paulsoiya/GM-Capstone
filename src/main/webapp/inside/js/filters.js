'use strict';

/* Filters */

angular.module('filters', []).filter('modelFilter',function () {
    return function (items, value) {
        var out = [{}];
        if(value === -1){
            return items;
        }
        else if(value){
            for(var i = 0; i < items.length; i++){
                if(items[i].makeId == value || items[i].makeId == -1 )
                    out.push(items[i]);
            }
            return out;
        }
        else if(!value){
            return items
        }
    };
}).filter('yearFilter',function () {
    return function (items, value) {
        var out = [{}];
        
        if(value){
            for( var i = 0; i < items.length; i++){
                if(items[i].modelId == value || items[i].modelId == -1 )
                    out.push(items[i]);
            }
            return out;
        }
        else if(!value){
            return items
        }
    };
});