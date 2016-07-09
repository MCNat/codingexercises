var http = require("http");

function getWebpageContent(url, callback)
{
	http.get(
		url, 
		function(response) {
		        
	        var resultText = "";

	        response.on("data", function(data) {
	            resultText += data;
	        });

	        response.on("end", function() {

	        	if (callback)
	        		callback(resultText);
	        });
		});
}

function processContent(text)
{
	if (!text)
	{
		console.log("no content to process");
		return;
	}

	var regex = /\<a.*href\s?=\s?[\'\"]([^\"\']*)[\'\"][^>]*\>(.*)<\/a>/ig;
	
	while ((match = regex.exec(text)) != null)
	{
	    console.log(match[2] + " -> " + match[1]);
	}
}

getWebpageContent(
	"http://www.spacex.com/", 
	processContent);
