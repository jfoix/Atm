function validateNumbers(e) {
	var key = window.event ? e.keyCode : e.which;
	var keychar = String.fromCharCode(key);
	reg = /[0-9]/;
	return reg.test(keychar);
}

function validarDecimal(event, obj){
	var key = window.event ? event.keyCode : event.which;
	var keychar = String.fromCharCode(key);
	var regNum = /[0-9]/;
	if(regNum.test(keychar) || keychar == '.'){
		var reg = /^[0-9]{1,12}\.{0,1}([0-9]{0,2})?$/;
		return reg.test(obj.value + keychar);
	} else {
		return false;
	}
}


function validateText(e) {
	var key = window.event ? e.keyCode : e.which;
	var keychar = String.fromCharCode(key);
	reg = /\d/;
	return !reg.test(keychar);
}