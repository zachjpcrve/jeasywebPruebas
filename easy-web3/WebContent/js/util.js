var lNumeros='1234567890';
var lLetras=' ABCÇDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz´';
function ingresoLetrasNumeros(e){
	var key;
	var valid = '' + lLetras + lNumeros ;
	
	if(e.which){
		key = String.fromCharCode(e.which);
		if (valid.indexOf("" + key) == "-1")
			e.preventDefault();
	}
	else if(e.keyCode){
		key = String.fromCharCode(e.keyCode);
		if (valid.indexOf("" + key) == "-1")
			e.keyCode = 0;
	}
} 


function newWindowUploadFile2() {   
    mywindow=open('doverUploadFile.do','myname',"scrollbars=no,scrolling=no,top=" + (screen.height/8 + 80) + ",height=270,width=450,left=" + ((screen.width - 750)/2) + ",resizable=no");
    alert(mywindow);
    mywindow.location.href = 'doverUploadFile.do';
    if (mywindow.opener == null) mywindow.opener = self;
}


function newWindowUploadFile(ptextarea)
{
  var ancho=500;
  var alto=270;
  var vtextarea=ptextarea;

  if (window.showModalDialog)
  {
      var vReturnValue;
      vReturnValue = window.showModalDialog('doverUploadFile.do',"myname","scroll=no;dialogWidth:" + ancho + "px;dialogHeight:" + alto + "px");
    
      if(vReturnValue != null )
      {  
           
        if (vReturnValue.length !=0)
        {	               
        	var vtarea='#'+vtextarea;  
        	$(vtarea).wysiwyg('insertHtml', vReturnValue);        	      
       		 return true
        }
        else
        {
        return false;
        }
        
      }
      else
      {   			       
        return false;
      }    
  }

}


function NumberFormat(num, numDec, decSep, thousandSep){ 
    var arg; 
    var Dec; 
    Dec = Math.pow(10, numDec);  
    if (typeof(num) == 'undefined') return;  
    if (typeof(decSep) == 'undefined') decSep = ','; 
    if (typeof(thousandSep) == 'undefined') thousandSep = '.'; 
    if (thousandSep == '.') 
     arg=/./g; 
    else 
     if (thousandSep == ',') arg=/,/g; 
    if (typeof(arg) != 'undefined') num = num.toString().replace(arg,''); 
    num = num.toString().replace(/,/g, '.');  
    if (isNaN(num)) num = "0"; 
    sign = (num == (num = Math.abs(num))); 
    num = Math.floor(num * Dec + 0.50000000001); 
    cents = num % Dec; 
    num = Math.floor(num/Dec).toString();  
    if (cents < (Dec / 10)) cents = "0" + cents;  
    for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++) 
     num = num.substring(0, num.length - (4 * i + 3)) + thousandSep + num.substring(num.length - (4 * i + 3)); 
    if (Dec == 1) 
     return (((sign)? '': '-') + num); 
    else 
     return (((sign)? '': '-') + num + decSep + cents); 
   }  
   function EvaluateText(cadena, obj){ 
    opc = false;  
    if (cadena == "%d") 
     if (event.keyCode > 47 && event.keyCode < 58) 
      opc = true; 
    if (cadena == "%f"){  
     if (event.keyCode > 47 && event.keyCode < 58) 
      opc = true; 
     if (obj.value.search("[.*]") == -1 && obj.value.length != 0) 
      if (event.keyCode == 46) 
       opc = true; 
    } 
    if(opc == false) 
     event.returnValue = false;  
   } 
   

function check_extension(filename,submitId, ext_valido) {
	var re = /\..+$/;       
	var ext = filename.match(re);   
	var submitEl = document.getElementById(submitId);   
	if( ext_valido.indexOf(ext)>=0){
		submitEl.disabled = false;         
		return true;  
	}
	else {         
		alert("Archivo Invalido, Por favor seleccione otro archivo");
		submitEl.disabled = true;
		return false;      
	}
} 

function check_extension2(filename,submitId, ext_valido,ext_valido2) {
	var re = /\..+$/;       
	var ext = filename.match(re);   
	var submitEl = document.getElementById(submitId);   
	if( ext_valido.indexOf(ext)>=0 || ext_valido2.indexOf(ext)>=0){
		submitEl.disabled = false;         
		return true;  
	}
	else {         
		alert("Archivo Invalido, Por favor seleccione otro archivo");
		submitEl.disabled = true;
		return false;      
	}
} 

function redondeo2decimales(numero)
{
	var original=parseFloat(numero);
	var result=Math.round(original*100)/100 ;
	return result;
}

function confirmDelete()   
{  
     return confirm('Se encuentra seguro de eliminar el registro?');  
}

	    
function SaveScrollXY() {
	
	var scrollX, scrollY;
    
    if (document.all)
    {
       if (!document.documentElement.scrollLeft)
          scrollX = document.body.scrollLeft;
       else
          scrollX = document.documentElement.scrollLeft;
            
       if (!document.documentElement.scrollTop)
          scrollY = document.body.scrollTop;
       else
          scrollY = document.documentElement.scrollTop;
    }  
    else
    {
       scrollX = window.pageXOffset;
       scrollY = window.pageYOffset;
    }

//    document.Form1.scrollLeft.value = scrollX;
//    document.Form1.scrollTop.value = scrollY;
	
	document.forms[0].scrollX.value = scrollX;
	document.forms[0].scrollY.value = scrollY;
}

function ResetScrollPosition(hidx, hidy ) {
//	var hidx, hidy;
//	hidx = document.forms[0].scrollX;
//	hidy = document.forms[0].scrollY;
	if (typeof hidx != 'undefined' && typeof hidy != 'undefined') {
		window.scrollTo(hidx, hidy);
	}
}

function acceptNum(evt){
	var evento = evt || window.event;
	if(window.event)//IE
	{
		key = evento.keyCode;
	}
	else if(evento.which)//Netscape/Firefox/Opera
	{
		key = evento.which;
	}
	if(key == 8){
		return true;
	}
	for (i=57; i>=48; i--){
		if (key==i){
			return true;
		}
	}
	return false;
}

function validarHora(campo, evt){
	var valor = campo.value;
	var e = evt;
	if(document.all){
		tecla=e.keyCode;
	}else{
		tecla=e.which;
	}
	if (tecla==8) return true;
	patron = /^(1|01|2|02|3|03|4|04|5|05|6|06|7|07|8|08|9|09|10|11|12)\:([0-5]0|[0-5][1-9])$/;
	var res = true;
	if(valor.length != 0){
		res = patron.test(valor);
		if(res == false){
			campo.value='';
			campo.focus();
		}
	}
	return res;
}

function validarCharHoras(campo){
	var valor = campo.value;
	patron = /[0-9\:]/;
	var res = true;
	if(valor.length>1){
		res = patron.test(valor);
		alert(res);
		alert(valor);
		if(res == false){
			alert(valor.length);
			if(valor.length>1){
			campo.value = valor.substring(0,valor.length-1);
			}
		}
	}
	return res;
}

function radio(clicked){
    var form = clicked.form;
    var checkboxes = form.elements[clicked.name];
    if (!clicked.checked || !checkboxes.length) {
        clicked.parentNode.parentNode.className="";
        return false;
    }

    for (i=0; i<checkboxes.length; i++) {
        if (checkboxes[i] != clicked) {
            checkboxes[i].checked=false;
            checkboxes[i].parentNode.parentNode.className="";
        }
    }

    // highlight the row    
    clicked.parentNode.parentNode.className="over";
    return true;
}


function esDigito(sChr){
	  var sCod = sChr.charCodeAt(0);
	  return ((sCod > 47) && (sCod < 58));
	  }
	 
	  function valSep(oTxt){
	  var bOk = false;
	  var sep1 = oTxt.value.charAt(2);
	  var sep2 = oTxt.value.charAt(5);
	  bOk = bOk || ((sep1 == "-") && (sep2 == "-"));
	  bOk = bOk || ((sep1 == "/") && (sep2 == "/"));
	  return bOk;
	  }
	 
	  function finMes(oTxt){
	  var nMes = parseInt(oTxt.value.substr(3, 2), 10);
	  var nAno = parseInt(oTxt.value.substr(6), 10);
	  var nRes = 0;
	  switch (nMes){
	   case 1: nRes = 31; break;
	   case 2: nRes = 28; break;
	   case 3: nRes = 31; break;
	   case 4: nRes = 30; break;
	   case 5: nRes = 31; break;
	   case 6: nRes = 30; break;
	   case 7: nRes = 31; break;
	   case 8: nRes = 31; break;
	   case 9: nRes = 30; break;
	   case 10: nRes = 31; break;
	   case 11: nRes = 30; break;
	   case 12: nRes = 31; break;
	  }
	  return nRes + (((nMes == 2) && (nAno % 4) == 0)? 1: 0);
}
	 
function valDia(oTxt){
	  var bOk = false;
	  var nDia = parseInt(oTxt.value.substr(0, 2), 10);
	  bOk = bOk || ((nDia >= 1) && (nDia <= finMes(oTxt)));
	  return bOk;
}
	 
function valMes(oTxt){
	  var bOk = false;
	  var nMes = parseInt(oTxt.value.substr(3, 2), 10);
	  bOk = bOk || ((nMes >= 1) && (nMes <= 12));
	  return bOk;
}
	 
function valAno(oTxt){
	  var bOk = true;
	  var nAno = oTxt.value.substr(6);
	  bOk = bOk && ((nAno.length == 2) || (nAno.length == 4));
	  if (bOk){
	   for (var i = 0; i < nAno.length; i++){
	   bOk = bOk && esDigito(nAno.charAt(i));
	   }
	  }
	  return bOk;
}
	 
function valFecha(oTxt){
	  var bOk = true;
	  if (oTxt.value != ""){
	   bOk = bOk && (valAno(oTxt));
	   bOk = bOk && (valMes(oTxt));
	   bOk = bOk && (valDia(oTxt));
	   bOk = bOk && (valSep(oTxt));
	   if (!bOk){
		   alert('Fecha Incorrecta');
		   oTxt.value = "";
		   oTxt.focus();
	   } 
	  }
}

///////////////////////validar  numero///////
var complet = function (e, obj)
{	
	tecla = e.keyCode? e.keyCode : e.charCode;
	var sKey = String.fromCharCode(tecla);
	if((sKey == '|') || (sKey == '°') || (sKey == '`') || (sKey == '~') ||(sKey == '"') || (sKey == '&') || (sKey == ' ') || (sKey == 'º') || (sKey == '!') || (sKey == '¡') || (sKey == '#') || (sKey == '$') || (sKey == '%') || (sKey == '/') || (sKey == '(') || (sKey == ')') || (sKey == '=') || (sKey == '?') || (sKey == '¿') || (sKey == '@') || (sKey == '*') || (sKey == '+') || (sKey == '[') || (sKey == ']') || (sKey == '{') || (sKey == '}') || (sKey == ';') || (sKey == ',') || (sKey == ':') || (sKey == '.') || (sKey == '_') || (sKey == '-'))
	{	
	 window.event.keyCode=0;
	}
}

function CatchNonNumbers_(e, negative, decimals, decimalSeparator, txt, cantdec, cantent, obj)
{	
	tecla = e.keyCode? e.keyCode : e.charCode;
	var sKey = String.fromCharCode(tecla);
	if((sKey == '|') || (sKey == '°') || (sKey == '`') || (sKey == '~') ||(sKey == '"') || (sKey == '&') || (sKey == ' ') || (sKey == 'º') || (sKey == '!') || (sKey == '¡') || (sKey == '#') || (sKey == '$') || (sKey == '%') || (sKey == '/') || (sKey == '(') || (sKey == ')') || (sKey == '=') || (sKey == '?') || (sKey == '¿') || (sKey == '@') || (sKey == '*') || (sKey == '+') || (sKey == '[') || (sKey == ']') || (sKey == '{') || (sKey == '}') || (sKey == ';') || (sKey == ',') || (sKey == ':') || (sKey == '_') || (sKey == '-'))
	{	
	 return false;
	}
	return CatchNonNumbers(e, negative, decimals, decimalSeparator, txt, cantdec, cantent);
}

function CatchNonNumbers(e, negative, decimals, decimalSeparator, txt, cantdec, cantent)
{
	var uni = e.keyCode? e.keyCode : e.charCode; // get the keyCode
	var decUni = getDecimalSeparatorCode(decimalSeparator); // get the decimal separator keyCode
	var evt = window.event? event : e; // event for crtl, alt and shift key
	if(evt.ctrlKey || evt.altKey || evt.shiftKey) 
		return true;
	else 
	{
	    var ubidec = txt.value.indexOf(decimalSeparator);
	    var ubientxt = getSelectionStart(txt);
	    ubidec +=1;
        if (cantdec <1)
	    {
    	    decimals=false;
	    }
	    if (ubidec > 0 ) //Existe signo Decimal
	    {
	        if (ubientxt >= ubidec) //Ubicado en los decimales
	        {
	            if ((txt.value.length - ubidec) == cantdec) 
	            {	        
        	        return false;
	            }
	        }
	        else //ubicado en los enteros
	        {
	            if ( ubientxt < ( ubidec - 1 ) )
	            {
	                if ((ubidec-1) >= cantent) 
	                {	        
    	                return false;
	                }
	            }
	        }
	    }
	    else
	    {
	        if ( ubidec == 0 )
	        {
	            if ( (txt.value.length >= cantent) && ( uni != decUni ) )
	            {	        
    	            return false;
	            }
	        }
	        else
	        {
	            if ((ubidec-1) >= cantent) 
	            {	        
    	            return false;
	            }
	        }
	    }
	    switch(uni)
	    {
	        case 0:
	            // safari
	            return true;
	        case decUni:
	            if(txt.value.replace("-", "").length > 0 && ((getCursorPosition(txt) > 0 ) || (getCursorPosition(txt) > 1 && txt.value.indexOf("-") > -1)) && txt.value.indexOf(decimalSeparator) == -1 && decimals)
	                return true;
	            else
	                return false;
	            break;
	        case 45: // minus sign
	        	if(negative=='0'){
	        		return false;
	        	}
	            if(getCursorPosition(txt) == 0 && negative)
	                return true;
	            else
	                return false;
	            break;
	        default:
				return IsAllowed(uni);
				break;
	    }
	} 
}


//Archivo JScript
function getCursorPosition(txt)
{
 if(document.selection)
 {
     txt.focus();
     var oSel = document.selection.createRange();
     oSel.moveStart('character', -txt.value.length);
     return oSel.text.length;
 } 
 else(txt.selectionStart) 
     return txt.selectionStart;
}

//(sKey >= 'A' && sKey <= 'Z') || (sKey >= 'a' && sKey <= 'z') || (sKey == 'Ñ') ||
function getDecimalSeparatorCode(separator)
{
 if(separator == ".")
     return 46;
 else
     return 44;
}

function getSelectionStart(o) {
	if (o.createTextRange) {
		var r = document.selection.createRange().duplicate()
		r.moveEnd('character', o.value.length)
		if (r.text == '') return o.value.length
		return o.value.lastIndexOf(r.text)
	} else return o.selectionStart
}

function IsAllowed(s) // Which characters are allowed
{ 
	var arr = new Array(8, 9, 13, 17, 35, 36, 37, 38, 
	    39, 40, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57);
	for(i = 0; i < arr.length; i++)
	{
	    if(arr[i] == s)
	        return true;
	}
	return false;
}

//Verifica Numero Entero Valido
function verificaEntero(){
 valKey=event.keyCode;
 
 if ((valKey=='48') || (valKey=='49') || (valKey=='50') || (valKey=='51') || (valKey=='52') || (valKey=='53') || (valKey=='54') || (valKey=='55') || (valKey=='56') || (valKey=='57') || (valKey=='8'))  
     return true;
 else
     return false;
}	 