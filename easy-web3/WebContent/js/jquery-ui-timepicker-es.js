/* Inicialización en español para el 'UI time picker' para jQuery. */
jQuery(function($){
	$.timepicker.regional['es'] = {
		currentText: 'Ahora',
		closeText: 'Aceptar',
		ampm: false,
		amNames: ['AM', 'A'],
		pmNames: ['PM', 'P'],
		timeFormat: 'hh:mm tt',
		timeSuffix: '',
		timeOnlyTitle: 'Elegir Tiempo',
		timeText: 'Tiempo',
		hourText: 'Hora',
		minuteText: 'Minuto',
		secondText: 'Segundo',
		millisecText: 'Milisegundo',
		timezoneText: 'Time Zone'
		};
	$.timepicker.setDefaults($.timepicker.regional['es']);
});