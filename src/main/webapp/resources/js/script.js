function togglePin(_pinID) {
	$.ajax({
		url : 'togglePin',
		type : 'POST',
		data : ({
			pinID : _pinID
		}),
		success : function(data) {
			$("td#value_" + _pinID).text(data);
			if (data == true) {
				$("button#" + _pinID).attr("class", "btn btn-danger").text("Wyłącz");
			} else {
				$("button#" + _pinID).attr("class", "btn btn-success").text("Włącz");
			}
		}
	});
}

function generateInput(id, labelText, value) {
	var div = document.createElement('div');
	div.setAttribute('class', 'form-group extra-input');
	var label = document.createElement('label');
	label.setAttribute('for', id);
	label.setAttribute('class', 'control-label');
	var text = document.createTextNode(labelText);
	label.appendChild(text);
	var input = document.createElement('input');
	input.setAttribute('type', 'text');
	input.setAttribute('class', 'form-control');
	input.setAttribute('id', id);
	if (typeof value === 'undefined') {
		value = "";
	}
	;
	input.setAttribute('value', value);
	div.appendChild(label);
	div.appendChild(input);
	return div;
}

function generateSelectBox(id, labelText, actionToGetValues, value) {
	var div = document.createElement('div');
	div.setAttribute('class', 'form-group extra-input');
	var label = document.createElement('label');
	label.setAttribute('for', id);
	label.setAttribute('class', 'control-label');
	var text = document.createTextNode(labelText);
	label.appendChild(text);
	var input = document.createElement('select');
	input.setAttribute('class', 'form-control');
	input.setAttribute('id', id);
	var options = getSources(actionToGetValues, "");
	for (var i = 0; i < options.length; i++) {
		var option = document.createElement('option');
		option.setAttribute('id', options[i].id);
		option.setAttribute('value', options[i].name);
		option.innerHTML = options[i].name;
		if (options[i].id == value) {
			option.setAttribute('selected', 'true');
		}
		input.appendChild(option);
	}
	if (typeof value === 'undefined') {
		value = "";
	}
	;
	input.setAttribute('value', value);
	div.appendChild(label);
	div.appendChild(input);
	return div;
}

function generateTextArea(id, labelText, value) {
	var div = document.createElement('div');
	div.setAttribute('class', 'form-group extra-input');
	var label = document.createElement('label');
	label.setAttribute('for', id);
	label.setAttribute('class', 'control-label');
	var text = document.createTextNode(labelText);
	label.appendChild(text);
	var textArea = document.createElement('textarea');
	textArea.setAttribute('type', 'text');
	textArea.setAttribute('class', 'form-control');
	textArea.setAttribute('id', id);
	textArea.innerHTML = value;
	if (typeof value === 'undefined') {
		value = "";
	}
	div.appendChild(label);
	div.appendChild(textArea);
	return div;
}

function showPropertyPanel(inputID, propertyActionArray) {
	var modal = $('#editModal');
	var inputs = modal.find('.inputs');
	inputs.attr('class', 'inputs col-md-6');
	var properties = modal.find('.properties');
	properties.attr('class', 'properties col-md-6');
	input = modal.find('#' + inputID);
	input.attr("onChange", "fillProperties(this)");
	for (var i = 0; i < propertyActionArray.length; i++) {
		var div = document.createElement('div');
		div.setAttribute('class', 'form-group extra-output');
		var label = document.createElement('label');
		label.setAttribute('class', 'control-label');
		var text = document.createTextNode(propertyActionArray[i].property);
		label.appendChild(text);
		properties[0].appendChild(label);
		var value = document.createElement('p');
		value.setAttribute('class', 'text propertyValue');
		value.setAttribute("data-action", propertyActionArray[i].action);
		div.appendChild(label);
		div.appendChild(value);
		properties[0].appendChild(div);
	}
	fillProperties(input[0]);
}

function fillProperties(input) {
	var properties = $('#editModal').find('.properties').find('.propertyValue');
	for (var i = 0; i < properties.length; i++) {
		var action = properties.eq(i).data('action');
		properties[i].innerHTML = getSources(action, ({
			id : $(input).children(":selected").attr("id")
		}));
	}
}

$(document).ready(function() {
	$("a[href='" + window.location.pathname + "']").parent().attr("class", "active");
	$('#editModal').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget);
		var id = button.data('id');
		var action = button.data('action');
		var name = button.data('name');
		if (typeof name === 'undefined') {
			name = "Nowy wpis";
		}
		;
		var description = button.data('description');
		var type = button.data('type');
		var modal = $(this);
		modal.find('.modal-title').text('Edytujesz - ' + name);
		modal.find('.modal-body input#id').val(id);
		modal.find('.modal-body input#action').val(action);
		modal.find('.modal-body input#type').val(type);
		if (typeof description === 'undefined') {
			description = "";
		}
		;
		// extra Values
		switch (type) {
		case "malt":
			var info = button.data('info');
			modal.find('form')[0].appendChild(generateInput('name', 'Nazwa: ', name));
			modal.find('form')[0].appendChild(generateInput('info', 'Podaj barwe: ', info));
			modal.find('form')[0].appendChild(generateTextArea('description', 'Opis: ', description));
			break;
		case "hop":
			var info = button.data('info');
			modal.find('form')[0].appendChild(generateInput('name', 'Nazwa: ', name));
			modal.find('form')[0].appendChild(generateInput('info', 'Podaj ilość alfa-kwasów: ', info));
			modal.find('form')[0].appendChild(generateTextArea('description', 'Opis: ', description));
			break;
		case "addon":
			var info = button.data('info');
			modal.find('form')[0].appendChild(generateInput('name', 'Nazwa: ', name));
			modal.find('form')[0].appendChild(generateInput('info', 'Cecha: ', info));
			modal.find('form')[0].appendChild(generateTextArea('description', 'Opis: ', description));
			break;
		case "break":
			var tempLow = button.data('templow');
			var tempHigh = button.data('temphigh');
			modal.find('form')[0].appendChild(generateInput('name', 'Nazwa: ', name));
			modal.find('form')[0].appendChild(generateInput('temp_low', 'Podaj dolną temperaturę: ', tempLow));
			modal.find('form')[0].appendChild(generateInput('temp_high', 'Podaj gorną temperaturę: ', tempHigh));
			modal.find('form')[0].appendChild(generateTextArea('description', 'Opis: ', description));
			break;
		case "yeast":
			modal.find('form')[0].appendChild(generateInput('name', 'Nazwa: ', name));
			modal.find('form')[0].appendChild(generateTextArea('description', 'Opis: ', description));
			break;
		case "brew":
			var brewtype = button.data('brewtype');
			modal.find('form')[0].appendChild(generateInput('name', 'Nazwa: ', name));
			modal.find('form')[0].appendChild(generateInput('type', 'Podaj typ warki: ', brewtype));
			modal.find('form')[0].appendChild(generateTextArea('description', 'Opis: ', description));
			break;
		case "brewmalt":
			var quantity = button.data('quantity');
			var maltId = button.data('maltid');
			modal.find('form')[0].appendChild(generateSelectBox('maltId', 'Słód: ', '../sources/getMalts', maltId));
			modal.find('form')[0].appendChild(generateInput('quantity', 'Ilość słodu: ', quantity));
			showPropertyPanel("maltId", [ {
				'property' : 'Color',
				'action' : '../sources/getMaltColor'
			}, {
				'property' : 'Opis',
				'action' : '../sources/getMaltDescription'
			} ]);
			break;
		case "brewbreak":
			var startMinute = button.data('startminute');
			var duration = button.data('duration');
			var breakId = button.data('breakid');
			modal.find('form')[0].appendChild(generateSelectBox('breakId', 'Przerwa: ', '../sources/getBreaks', breakId));
			modal.find('form')[0].appendChild(generateInput('startMinute', 'Start: ', startMinute));
			modal.find('form')[0].appendChild(generateInput('duration', 'Czas trwania: ', duration));
			showPropertyPanel("breakId", [ {
				'property' : 'Temperatura dolna:',
				'action' : '../sources/getBreakTemp_Low'
			},{
				'property' : 'Temperatura gorna:',
				'action' : '../sources/getBreakTemp_High'
			},{
				'property' : 'Opis',
				'action' : '../sources/getMaltDescription'
			} ]);
			break;
	case "brewhop":
		var startMinute = button.data('startminute');
		var quantity = button.data('quantity');
		var breakId = button.data('breakid');
		modal.find('form')[0].appendChild(generateSelectBox('hopId', 'Chmiel: ', '../sources/getHops', breakId));
		modal.find('form')[0].appendChild(generateInput('startMinute', 'Start: ', startMinute));
		modal.find('form')[0].appendChild(generateInput('quantity', 'Ilość: ', quantity));
		showPropertyPanel("hopId", [ {
			'property' : 'Ilość alfa-kwasów',
			'action' : '../sources/getHopAcid'
		},{
			'property' : 'Opis',
			'action' : '../sources/getHopDescription'
		} ]);
		break;
	case "brewaddon":
		var startMinute = button.data('startminute');
		var quantity = button.data('quantity');
		var addonId = button.data('addonid');
		var addonUsingTimeId = button.data('addonusingtimeid');
		modal.find('form')[0].appendChild(generateSelectBox('addonId', 'Dodatek: ', '../sources/getAddons', addonId));
		modal.find('form')[0].appendChild(generateSelectBox('addonUsingTimeId', 'Podczas: ', '../sources/getAddonsUsingTime', addonUsingTimeId));
		modal.find('form')[0].appendChild(generateInput('startMinute', 'Start: ', startMinute));
		modal.find('form')[0].appendChild(generateInput('quantity', 'Ilość: ', quantity));
		showPropertyPanel("addonId", [ {
			'property' : 'Opis',
			'action' : '../sources/getAddonDescription'
		} ]);
		break;
	case "editbrew":
		var name = button.data('name');
		var date = button.data('brewdate');
		var type = button.data('brewtype');
		var startDensity = button.data('startdensity');
		var endDensity = button.data('enddensity');
		var description = button.data('description');
		var hopDuration = button.data('hopduration');
		modal.find('form')[0].appendChild(generateInput('name', 'Nazwa: ', name));
		modal.find('form')[0].appendChild(generateInput('date', 'Data warzenia: ',date));
		modal.find('form')[0].appendChild(generateInput('type', 'Typ: ', type));
		modal.find('form')[0].appendChild(generateInput('hopDuration', 'Czas chmielenia: ', hopDuration));
		modal.find('form')[0].appendChild(generateInput('startDensity', 'Gęstość początkowa: ', startDensity));
		modal.find('form')[0].appendChild(generateInput('endDensity', 'Gęstość końcowa: ', endDensity));
		modal.find('form')[0].appendChild(generateTextArea('description', 'Opis: ', description));
		break;
	case "brewfermentation":
		var yeastId = button.data('yeastid');
		var yeastAddDate = button.data('yeastadddate');
		var yeastOrigin = button.data('yeastorigin');
		var fermentationstartvolume = button.data('fermentationstartvolume');
		var fermantationTemperature = button.data('fermantationtemperature');
		var silentFermentationDate = button.data('silentfermentationdate');
		var silentFemrantationTemperature = button.data('silentfemrantationtemperature');
		modal.find('form')[0].appendChild(generateSelectBox('yeastId', 'Drożdże:', '../sources/getYeasts', yeastId));
		modal.find('form')[0].appendChild(generateInput('yeastAddDate', 'Data dodania drożdży ',yeastAddDate));
		modal.find('form')[0].appendChild(generateInput('yeastOrigin', 'Pochodzenie drożdży: ', yeastOrigin));
		modal.find('form')[0].appendChild(generateInput('fermentationStartVolume', 'Początkowa objętość: ', fermentationstartvolume));
		modal.find('form')[0].appendChild(generateInput('fermantationTemperature', 'Temperatura fermentacji burzliwej: ', fermantationTemperature));
		modal.find('form')[0].appendChild(generateInput('silentFermentationDate', 'Data rozpoczęcia fermentacji cichej: ', silentFermentationDate));
		modal.find('form')[0].appendChild(generateInput('silentFemrantationTemperature', 'Temperatura fermentacji cichej: ', silentFemrantationTemperature));
		showPropertyPanel("yeastId", [ {
			'property' : 'Opis',
			'action' : '../sources/getYeastDescription'
		} ]);
		break;
	case "brewbottling":
		var endVolume = button.data('endvolume');
		var bottlingDate = button.data('bottling');
		var refermentationSource = button.data('refermentationsource');
		var referemntationSourceVolume = button.data('referemntationsourcevolume');
		modal.find('form')[0].appendChild(generateInput('endVolume', 'Końcowa objętość: ', endVolume));
		modal.find('form')[0].appendChild(generateInput('bottlingDate', 'Data rozlewu: ',bottlingDate));
		modal.find('form')[0].appendChild(generateInput('refermentationSource', 'Surowiec refermentacji: ',refermentationSource));
		modal.find('form')[0].appendChild(generateInput('refermentationSourceVolume', 'Ilość surowca refermentacji: ', referemntationSourceVolume ));
		break;
	}
	});
	$('#editModal').on('hide.bs.modal', function(event) {
		var modal = $(this);
		var extraInputs = modal.find('.extra-input');
		var extraOutputs = modal.find('.extra-output');
		for (var i = 0; i < extraInputs.length; i++) {
			modal.find('form')[0].removeChild(extraInputs[i]);
		}
		for (var i = 0; i < extraOutputs.length; i++) {
			modal.find('.properties')[0].removeChild(extraOutputs[i]);
		}
		var inputs = modal.find('.inputs');
		inputs.attr('class', 'inputs col-md-12');
		var properties = modal.find('.properties');
		properties.attr('class', 'properties col-md-0');
	});
	$('#deleteModal').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget);
		var id = button.data('id');
		var type = button.data('type');
		var name = button.data('name');
		var action = button.data('action');
		var modal = $(this);
		switch (type) {
		case "brewmalt":
			var maltid = button.data('maltid');
			var input = document.createElement('input');
			var div = document.createElement('div');
			div.setAttribute('class', 'form-group extra-input');
			input.setAttribute('type', 'hidden');
			input.setAttribute('id', "maltId");
			input.setAttribute('value', maltid);
			div.appendChild(input);
			modal.find(".delete-modal-body").append(div);
			break;
		case "brewbreak":
			var breakid = button.data('breakid');
			var input = document.createElement('input');
			var div = document.createElement('div');
			div.setAttribute('class', 'form-group extra-input');
			input.setAttribute('type', 'hidden');
			input.setAttribute('id', "breakId");
			input.setAttribute('value', breakid);
			div.appendChild(input);
			modal.find(".delete-modal-body").append(div);
			break;
		case "brewhop":
			var hopid = button.data('hopid');
			var input = document.createElement('input');
			var div = document.createElement('div');
			div.setAttribute('class', 'form-group extra-input');
			input.setAttribute('type', 'hidden');
			input.setAttribute('id', "hopId");
			input.setAttribute('value', hopid);
			div.appendChild(input);
			modal.find(".delete-modal-body").append(div);
			break;
		case "brewaddon":
			var addonid = button.data('addonid');
			var input = document.createElement('input');
			var div = document.createElement('div');
			div.setAttribute('class', 'form-group extra-input');
			input.setAttribute('type', 'hidden');
			input.setAttribute('id', "addonId");
			input.setAttribute('value', addonid);
			div.appendChild(input);
			modal.find(".delete-modal-body").append(div);
			break;
		default:
			break;
		}
		modal.find('.modal-title').text('Usuń -' + name);
		modal.find('.modal-body input#id').val(id);
		modal.find('.modal-body input#action').val(action);
		modal.find('span#name').text(name);
	});
	$('#deleteModal').on('hide.bs.modal', function(event) {
		var modal = $(this);
		var extraInputs = modal.find('.extra-input');
		for (var i = 0; i < extraInputs.length; i++) {
			modal.find('.delete-modal-body')[0].removeChild(extraInputs[i]);
		}
	});
});

function getDataFromInputs(){
	var _data = {};
		var extraInputs = $('.extra-input');
		for (var i = 0; i < extraInputs.length; i++) {
			var controlsName = [ 'input', 'textarea', 'select' ];
			var input;
			for (var j = 0; j < controlsName.length; j++) {
				input = extraInputs.eq(i).find(controlsName[j]);
				if (input.length > 0) {
					if (controlsName[j] == 'select') {
						_data[input.attr("id")] = input.children(":selected").attr("id");
					} else {
						_data[input.attr("id")] = input.val();
					}
					break;
				}
			}
		}
		return _data;
}

function saveSources() {
	var _id = $('.edit-modal-body input#id').val();
	var _url = $('.edit-modal-body input#action').val();
	var _data = getDataFromInputs();
	_data['id'] = _id;		
	$.ajax({
		url : _url,
		type : 'POST',
		data : _data,
		success : function(data) {
			$('#editModal').modal('hide');
			location.reload();
		}
	});
}
function deleteSources() {
	var _id = $('.delete-modal-body input#id').val();
	var _url = $('.delete-modal-body input#action').val();
	var _data = getDataFromInputs();
	_data['id'] = _id;		
	$.ajax({
		url : _url,
		type : 'POST',
		data : _data,
		success : function(data) {
			$('#deleteModal').modal('hide');
			location.reload();
		}
	});
}

function getSources(action, _data) {
	return JSON.parse($.ajax({
		url : action,
		data : _data,
		type : 'GET',
		async : false,
	}).responseText);
}