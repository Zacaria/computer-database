window.getLang = function (){
	return document.querySelector('html').lang;
}

/**
 * transforms dd-mm-yyyy to yyyy-mm-dd
 */
window.frToEnDate = function (str) {
	if(str.match(/^\d{2}-\d{2}-\d{4}$/)){
		return str.substr(6) + '-' + str.substr(0, 5);
	}
	return str;
}

/**
 * transforms yyyy-mm-dd to dd-mm-yyyy
 */
window.enToFrDate = function (str) {
	if(str.match(/^\d{4}-\d{2}-\d{2}$/)){
		return str.substr(8) + '-' + str.substr(5, 2) + '-' + str.substr(0, 4) ;
	}
	return str;		
}

//On load
$(function() {
	var lang = window.getLang();
	$(".navbar-lang[data-lang='"+ lang +"']").addClass("lang-selected");
	$('.navbar-lang').click(function(e) {
		e.preventDefault();
		
		var that = $(this);

		$.ajax({
			type : "POST",
			url : "/computer-database/lang?lang=" + $(this).data().lang,
			success : function(data) {
				console.log("switched language to " + that.data().lang);				
				that.empty();
				that.append($('<i>').addClass('fa fa-circle-o-notch fa-spin fa-fw margin-bottom'))
				
				window.location.href = document.location
			},
			error: function(err){
				console.log(err);
			},
			dataType : "json"
		});
	});
});

function getURLParameter(sParam) {
	// Remove the question mark from the search string.
	var sPageURL = window.location.search.substring(1);

	// Separate the parameters
	var sURLVariables = sPageURL.split('&');

	// Loop over the parameters
	for (var i = 0; i < sURLVariables.length; i++) {
		// Put the parameter in a key-value array.
		var sParameterName = sURLVariables[i].split('=');

		if (sParameterName[0] == sParam) {
			// Return the requested value.
			return sParameterName[1];
		}
	}
}