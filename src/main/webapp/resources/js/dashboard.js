//On load
$(function() {
	// Default: hide edit mode
	$(".editMode").hide();

	// Click on "selectall" box
	$("#selectall").click(function() {
		$('.cb').prop('checked', this.checked);
	});

	// Click on a checkbox
	$(".cb").click(function() {
		if ($(".cb").length == $(".cb:checked").length) {
			$("#selectall").prop("checked", true);
		} else {
			$("#selectall").prop("checked", false);
		}
		if ($(".cb:checked").length != 0) {
			$("#deleteSelected").enable();
		} else {
			$("#deleteSelected").disable();
		}
	});

	$('.glyphicon-sort').click(function() {
		var link = 'dashboard?p=1';

		// get range
		link += getURLParameter('r') ? '&r=' + getURLParameter('r') : '';
		// get search
		link += getURLParameter('s') ? '&s=' + getURLParameter('s') : '';
		// set order by
		link += '&col=' + $(this).data().column;

		// switch asc and desc
		link += '&asc=' + (getURLParameter('asc') == '0' ? '1' : '0');

		window.location.href = link;
	});

	$('.form_datetime').datetimepicker({
		format : 'yyyy-mm-dd',
		minView : 'month',
		autoclose : true
	});

	$('.selectpicker').selectpicker({
		size : 7,
		witdth : 'auto',
		liveSearch : true
	});

});

// Function setCheckboxValues
(function($) {

	$.fn.setCheckboxValues = function(formFieldName, checkboxFieldName) {

		var str = $('.' + checkboxFieldName + ':checked').map(function() {
			return this.value;
		}).get().join();

		$(this).attr('value', str);

		return this;
	};

}(jQuery));

// Function toggleEditMode
(function($) {

	$.fn.toggleEditMode = function() {
		if ($(".editMode").is(":visible")) {
			$(".editMode").hide();
			$("#editComputer").text("Edit");
		} else {
			$(".editMode").show();
			$("#editComputer").text("View");
		}
		return this;
	};

}(jQuery));

// Function delete selected: Asks for confirmation to delete selected computers,
// then submits it to the deleteForm
(function($) {
	$.fn.deleteSelected = function() {
		if (confirm("Are you sure you want to delete the selected computers?")) {
			$('#deleteForm input[name=selection]').setCheckboxValues(
					'selection', 'cb');
			$('#deleteForm').submit();
		}
	};
}(jQuery));

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

// Event handling
// Onkeydown
$(document).keydown(function(e) {

	switch (e.keyCode) {
	// DEL key
	case 46:
		if ($(".editMode").is(":visible") && $(".cb:checked").length != 0) {
			$.fn.deleteSelected();
		}
		break;
	// E key (CTRL+E will switch to edit mode)
	case 69:
		if (e.ctrlKey) {
			$.fn.toggleEditMode();
		}
		break;
	}
});