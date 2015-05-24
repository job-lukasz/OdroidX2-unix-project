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