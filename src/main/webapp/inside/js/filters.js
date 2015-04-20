'use strict';

/* Filters */

angular.module('filters', []).filter('modelFilter',function () {
        return function (items, value) {
            var out = [{}];
            console.log(items);
            console.log(value);
            if(value === -1){
        	    return items;
            }
            else if(value){
                for(var x=0; x<items.length; x++){
                    if(items[x].makeId == value || items[x].makeId == -1 )
                        out.push(items[x]);
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
            console.log(items);
            console.log(value);
            if(value === -1){
        	    return items;
            }
            else if(value){
                for(var x=0; x<items.length; x++){
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