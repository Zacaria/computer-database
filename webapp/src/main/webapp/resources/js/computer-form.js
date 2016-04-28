//On load
$(function() {
	var pattern = getLang() == 'fr' ? 'dd-mm-yyyy' : 'yyyy-mm-dd';

	$('.form_datetime').datetimepicker({
		format : pattern,
		minView : 'month',
		autoclose : true
	});

	$('.selectpicker').selectpicker({
		size : 7,
		witdth : 'auto',
		liveSearch : true
	});

	$(".form-message-error").parent().addClass('has-error');

});