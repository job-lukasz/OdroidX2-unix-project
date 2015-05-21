function togglePin(_pinID) {
    $.ajax({
      url: 'togglePin',
      type: 'POST',
      data: ({pinID : _pinID}),
      success: function(data) {
      	$("td#value_"+_pinID).text(data);
        if(data == true){
        	$("button#"+_pinID).attr("class", "btn btn-danger").text("Wyłącz");
        }
        else {
        	$("button#"+_pinID).attr("class", "btn btn-success").text("Włącz");
        }
      }
    });
  }

function generateInput(id, labelText, value){
	var div = document.createElement('div');
	div.setAttribute('class','form-group extra-input');
	var label = document.createElement('label');
	label.setAttribute('for', id);
	label.setAttribute('class', 'control-label');
	var text = document.createTextNode(labelText);
	label.appendChild(text);
	var input = document.createElement('input');
	input.setAttribute('type','text');
	input.setAttribute('class','form-control');
	input.setAttribute('id',id);
	  if(typeof value === 'undefined'){
		   	value = "";
	  };
	input.setAttribute('value',value);
	div.appendChild(label);
	div.appendChild(input);
	return div;
}

function generateTextArea(id, labelText, value){
	var div = document.createElement('div');
	div.setAttribute('class','form-group extra-input');
	var label = document.createElement('label');
	label.setAttribute('for', id);
	label.setAttribute('class', 'control-label');
	var text = document.createTextNode(labelText);
	label.appendChild(text);
	var textArea = document.createElement('textarea');
	textArea.setAttribute('type','text');
	textArea.setAttribute('class','form-control');
	textArea.setAttribute('id',id);
	textArea.innerHTML = value;
	  if(typeof value === 'undefined'){
		   	value = "";
	  }
	div.appendChild(label);
	div.appendChild(textArea);
	return div;
}
$(document).ready(
	function(){
		$("a[href='"+window.location.pathname+"']").parent().attr("class","active");
		$('#editModal').on('show.bs.modal', function (event) {
			  var button = $(event.relatedTarget);
			  var id = button.data('id');
			  var action = button.data('action');
			  var name = button.data('name');
			  if(typeof name === 'undefined'){
				   	name = "Nowy wpis";
			  };
			  var description = button.data('description');
			  var type = button.data('type');
			  var modal = $(this);
			  modal.find('.modal-title').text('Edytujesz - ' + name);
			  modal.find('.modal-body input#id').val(id);
			  modal.find('.modal-body input#action').val(action);
			  modal.find('.modal-body input#type').val(type); 
			  if(typeof description === 'undefined'){
				   	description = "";
			  };
			  //extra Values
			  switch(type){
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
				case "addmalt":
					var quantity = button.data('quantity');
					var maltId = button.data('maltid');
					modal.find('form')[0].appendChild(generateInput('quantity', 'Ilość słodu: ', quantity));
					modal.find('form')[0].appendChild(generateInput('maltId', 'Słód: ', maltId));
					break;
				}
			});
		$('#editModal').on('hide.bs.modal', function (event) {
			  var modal = $(this);
			  var extraInputs = modal.find('.extra-input');
			  for (var i = 0; i < extraInputs.length; i++) { 
				    modal.find('form')[0].removeChild(extraInputs[i]);
				}
			});
		$('#deleteModal').on('show.bs.modal', function (event) {
			  var button = $(event.relatedTarget);
			  var id = button.data('id');
			  var name = button.data('name');
			  var action = button.data('action');
			  var modal = $(this);
			  modal.find('.modal-title').text('Usuń -' + name);
			  modal.find('.modal-body input#id').val(id);
			  modal.find('.modal-body input#action').val(action);
			  modal.find('span#name').text(name);
			});
	}
);

function saveSources(){
		var _id = $('.edit-modal-body input#id').val();
		var _url= $('.edit-modal-body input#action').val();
		var _data = {id : _id};
		var extraInputs = $('.extra-input');
		for (var i = 0; i < extraInputs.length; i++) {
			var input = extraInputs.eq(i).find('input');
			if(input.length==0){
				input = extraInputs.eq(i).find('textarea');
			}
			_data[input.attr("id")]=input.val();
		}
		$.ajax({
		      url: _url,
		      type: 'POST',
		      data: _data,
		      success: function(data) {
		    	  $('#editModal').modal('hide');
		    	  location.reload();
		      }
		    });
	}

	function deleteSources(){
		var _id = $('.delete-modal-body input#id').val();
		var _url= $('.delete-modal-body input#action').val();;
		$.ajax({
		      url: _url,
		      type: 'POST',
		      data: ({id : _id}),
		      success: function(data) {
		    	  $('#deleteModal').modal('hide');
		    	  location.reload();
		      }
		    });
	}