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

function clickBtnTraidoCliente(curr){
	var obj = $(PrimeFaces.escapeClientId(curr.source));
	
	var i = 9;
	while(i > 0 && !obj.is('td')){
		obj = obj.parent();
		i--;
	}
	
	obj.find('.btnProdCliente').click();
}

function buscarNuevoTrabajoOT(codigo){
	$('.iptCodigoTrabajo').val(codigo);
	$('.btnBuscarTrabajo').get(0).click();
}

function buscarNuevoProductoOT(codigo){
	$('.iptCodigoProducto').val(codigo);
	$('.btnBuscarProducto').get(0).click();
}