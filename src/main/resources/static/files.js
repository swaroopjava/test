angular.module('file', [])
  .controller('FilesListController', function($http) {
    var fileObj = this;
 

    fileObj.upload = function() {
    	var f = document.getElementById('fileObj').files[0];    	 	
    	var file = {name: f.name};
    	    	$http.post('/files/', file).
    		then(function(response) {
	    		var fd = new FormData();
	            fd.append('file', f);
	            return $http.put(response.headers("Location"), fd, {
	                transformRequest: angular.identity,
	                headers: {'Content-Type': undefined}
	            });
	        })
	     );
    }
  });