// Dados time
var ary = [];

// Dados para os gráficos
var dados = [];

//Load the Visualization API and the corechart package.
google.charts.load('current', {'packages':['corechart'], 'language': 'pt'});

function getDadosTime(){
	let selectOrg = document.getElementById('organizacao');
	let date = document.getElementById('data_inicio').value;
  
	let strOrg = selectOrg.options[selectOrg.selectedIndex].value;
	
	ary = [];
	
	ary.push({ Org: strOrg, Data: date });
	
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/relatorio/time",
		data: JSON.stringify(ary),
		success: function(dataReturn){
			var result = Object.keys(dataReturn).map(function (key) {       
		        return [String(key), dataReturn[key]]; 
		    });
			
			dados = dataReturn;
			// Set a callback to run when the Google Visualization API is loaded.
			google.charts.setOnLoadCallback(drawColumn2(result));
//			google.charts.setOnLoadCallback(getMedias);
//			google.charts.setOnLoadCallback(getItensColab);
//			google.charts.setOnLoadCallback(getItensTime);
		}
	});
}

// Gráfico de itens consolidados de aprovados e rejeitados
function drawColumn2(values){
	var aux = [];
	var arrayStr = [];
	var valoresAux = [];
	
	var result = Object.keys(dados).map(function (key) {       
        return [String(key), dados[key]]; 
    }); 
	
	result.sort(function(a, b){
		if(a[0] > b[0]){
			return 1;
		}
		
		if(a[0] < b[0]){
			return -1;
		}
		
		return 0;
	});
	
	for(var i = 0; i < result.length; i++){
		var aux2 = [];
		aux = result[i];
		arrayStr = aux[0].split(':');

		aux2[0] = arrayStr[0];
		aux2[1] = parseInt(arrayStr[1]);
		aux2[2] = parseInt(aux[1]);
		
		valoresAux.push(aux2);
	}
	
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'Data');
    data.addColumn('number', 'Aprovados');
    data.addColumn('number', 'Rejeitados');
	
	for (var i = 0; i < valoresAux.length; i++){
		var a = valoresAux[i];
		data.addRow([a[0], a[2], a[1]]); 
	}
	
	var options = {
			title: "Consolidado de Itens Aprovados e Rejeitados",
			isStacked: true
	};
    
	var chart = new google.visualization.ColumnChart(document.getElementById("chartAtividadeMes"));
	chart.draw(data, options);
}