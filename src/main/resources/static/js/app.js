// Validação do dos colaboradores de cada organização
var colaboradoresInatel = [];
var colaboradoresFitec = [];
var colaboradoresEricsson = [];

$('document').ready(function(){
	$.ajax({
		url: '/colaborador/inatel',
        type: 'GET',
        success: function (dataColabInatel) {
            for (var i = 0; i < dataColabInatel.length; i++) {
            	colaboradoresInatel.push(dataColabInatel[i]);
            }
        }
	});
	
	$.ajax({
		url: '/colaborador/fitec',
        type: 'GET',
        success: function (dataColabFitec) {
            for (var i = 0; i < dataColabFitec.length; i++) {
            	colaboradoresFitec.push(dataColabFitec[i]);
            }
        }
	});
	
	$.ajax({
		url: '/colaborador/ericsson',
        type: 'GET',
        success: function (dataColabEricsson) {
            for (var i = 0; i < dataColabEricsson.length; i++) {
            	colaboradoresEricsson.push(dataColabEricsson[i]);
            }
        }
	});
});

function buscarColaboradores(e){
	
    document.querySelector("#colaborador").innerHTML = '';
    var colaborador_select = document.querySelector("#colaborador");
    var num_colabs_inatel = colaboradoresInatel.length;
    var num_colabs_fitec = colaboradoresFitec.length;
    var num_colabs_ericsson = colaboradoresEricsson.length;
    
    if(e === 'INATEL'){
    	for (var i = 0; i < num_colabs_inatel; i++){
        	var colaborador = colaboradoresInatel[i];
        	$('#colaborador').append($('<option>').val(colaborador).text(colaborador));
        	$('select').formSelect();
        }
    } else if (e === 'FITEC'){
    	for (var i = 0; i < num_colabs_fitec; i++){
        	var colaborador = colaboradoresFitec[i];
        	$('#colaborador').append($('<option>').val(colaborador).text(colaborador));
        	$('select').formSelect();
        }
    } else {
    	for (var i = 0; i < num_colabs_ericsson; i++){
        	var colaborador = colaboradoresEricsson[i];
        	$('#colaborador').append($('<option>').val(colaborador).text(colaborador));
        	$('select').formSelect();
        }
    }
}

// Validação do formulário para o relatório
function getDados() {
	var ary = [];
	let selectOrg = document.getElementById('organizacao');
	let selectColab = document.getElementById('colaborador');
	let date = document.getElementById('data_inicio').value;
  
	let strOrg = selectOrg.options[selectOrg.selectedIndex].value;
	let strColab = selectColab.options[selectColab.selectedIndex].value;
	
	ary.push({ Org: strOrg, Colab: strColab, Data: date });
  
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/relatorio/colaborador",
		data: JSON.stringify(ary),
		success: function(dataReturn){
			console.log(dataReturn);
			drawCharts(dataReturn);
		}
	});
}

function drawCharts(infos){
	var valoresChart = [];
	
	for(var i = 0; i < infos.length; i++){
		valoresChart.push({x: label.val(), y: parseInt(x.val())});
	}
	
	console.log(valoresChart[1]);
	
	var chart = new CanvasJS.Chart("chartContainer", {
		title:{
			text: "Atividades por mês"              
		},
		data: [              
		{
			// Change type to "doughnut", "line", "splineArea", etc.
			type: "column",
			dataPoints: valoresChart
		}
		]
	});
	
	chart.render();
}