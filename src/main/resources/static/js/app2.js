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
			console.log(result);
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
	
	var result = Object.keys(values).map(function (key) {       
        return [String(key), values[key]]; 
    }); 
	
	dados = values;
	
	values.sort(function(a, b){
		if(a[0] > b[0]){
			return 1;
		}
		
		if(a[0] < b[0]){
			return -1;
		}
		
		return 0;
	});
	
	for(var i = 0; i < dados.length; i++){
		aux = dados[i];
		arrayStr = aux[0].split(':');
		
		aux[0] = arrayStr[0];
		aux[1] = parseInt(arrayStr[1]);
		aux[2] = parseInt(values[i][1]);
		
		//console.log(aux);
		
		valoresAux.push(aux);
	}

	
	var data = google.visualization.arrayToDataTable([
        ['Year', 'Asia', 'Europe'],
        ['2012',  900,      390],
        ['2013',  1000,      400],
        ['2014',  1170,      440],
        ['2015',  1250,       480],
        ['2016',  1530,      540]
     ]);
	
	var options = {
			title: "Consolidado de Itens Aprovados e Rejeitados",
			isStacked: true
	};
    
	var chart = new google.visualization.ColumnChart(document.getElementById("chartAtividadeMes"));
	chart.draw(data, options);
}