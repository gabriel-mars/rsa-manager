// Dados colaborador
var ary = [];

//Load the Visualization API and the corechart package.
google.charts.load('current', {'packages':['corechart'], 'language': 'pt'});

// Arrays com os dados para os gráficos
var dados = [];
var dadosMedia = [];

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

	let selectOrg = document.getElementById('organizacao');
	let selectColab = document.getElementById('colaborador');
	let date = document.getElementById('data_inicio').value;
  
	let strOrg = selectOrg.options[selectOrg.selectedIndex].value;
	let strColab = selectColab.options[selectColab.selectedIndex].value;
	
	ary = [];
	
	ary.push({ Org: strOrg, Colab: strColab, Data: date });
  
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/relatorio/colaborador",
		data: JSON.stringify(ary),
		success: function(dataReturn){
			dados = dataReturn;
			// Set a callback to run when the Google Visualization API is loaded.
			google.charts.setOnLoadCallback(drawColumn);
			google.charts.setOnLoadCallback(getMedias);
			google.charts.setOnLoadCallback(getItensColab);
			google.charts.setOnLoadCallback(getItensTime);
		}
	});
}

// Gráfico de atividades executadas por dia
function drawColumn(){
	var result = Object.keys(dados).map(function (key) {       
        return [String(key), dados[key]]; 
    }); 
    
	dados = result;
	
	dados.sort(function(a, b){
		if(a[0] > b[0]){
			return 1;
		}
		
		if(a[0] < b[0]){
			return -1;
		}
		
		return 0;
	});
	
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'Topping');
	data.addColumn('number', 'Itens avalidados');
  
	for(var i = 0; i < result.length; i++){
		var a = result[i];
		
  	  	data.addRow([a[0], a[1]]); 
	}

	var options = {
		  title:'Itens avaliados por dia',
		  hAxis: {
	          title: 'Data',
	          viewWindow: {
	            min: [7, 30, 0],
	            max: [17, 30, 0]
	          }
	        },
	        vAxis: {
	            title: 'Itens'
	          }
	 };

	// Instantiate and draw our chart, passing in some options.
	var chart = new google.visualization.ColumnChart(document.getElementById('chartAtividadeDia'));
	chart.draw(data, options); 
}

// Chamada dos dados para o gráfico de acordo com a média
function getMedias(){	
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/relatorio/media/dia",
		data: JSON.stringify(ary),
		success: function(dataReturn){
			var result = Object.keys(dataReturn).map(function (key) {       
		        return [String(key), dataReturn[key]]; 
		    });
			
			drawLines(result);
		}
	});
}

//Gráfico de itens de acordo com a média
function drawLines(values){
	var auxDp = [];
	var soma = 0;
	var media = 0;
	var raiz = 0;
	
	var data = new google.visualization.DataTable();
    data.addColumn('string', 'Dias');
    data.addColumn('number', 'Média');
    data.addColumn('number', 'Colaborador');
    data.addColumn('number', 'DP inferior');
    data.addColumn('number', 'DP superior');
    
    // Calculo do desvio padrão
    for(var i = 0; i < dados.length; i++){
		var a = dados[i];
		var b = values[i];
		
		var sub = Math.pow(a[1] - b[1], 2);
		auxDp.push(sub);
	}
    
    for(var i = 0; i < auxDp.length; i++){
    	soma += auxDp[i];
    }
    
    media = (soma / (auxDp.length - 1));
    raiz = Math.sqrt(media);
    
    for(var i = 0; i < dados.length; i++){
		var a = dados[i];
		var b = values[i];
		
  	  	data.addRow([a[0], b[1], a[1], 100, raiz*2]); 
	}

    var options = {
    		title:'Comparativo de produção com o time',
    		hAxis: {title: 'Data'},
    		vAxis: {title: 'Itens'}
    };

    var chart = new google.visualization.LineChart(document.getElementById("chartMediaDia"));
    chart.draw(data, options);
}

// Chamada dos dados para o gráfico pizza Itens
function getItensColab(){	
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/relatorio/itens/colaborador",
		data: JSON.stringify(ary),
		success: function(dataReturn){
			var result = Object.keys(dataReturn).map(function (key) {       
		        return [String(key), dataReturn[key]]; 
		    });
			
			drawPieColab(result);
		}
	});
}

// Gráfico pizza Itens
function drawPieColab(values){
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Topping');
    data.addColumn('number', 'Slices');
    
    var abonados, rejeitados, aprovados;
    var aux = [];
	var array = [];
	
	for(var i = 0; i < values.length; i++){
		var aux = values[i];
		var array = aux[0].split(':');
	}
	
	rejeitado = array[0];
	abonados = aux[1];
	aprovados = array[1];
    
	data.addRows([
      ['Aprovados', parseInt(aprovados)],
      ['Reprovados', parseInt(rejeitado)],
      ['Abonados', parseInt(abonados)]
    ]);

    var options = {title:'Itens trabalhados no mês - Colaborador'};

    var chart = new google.visualization.PieChart(document.getElementById('colab_chart_div'));
    chart.draw(data, options);
}

//Chamada dos dados para o gráfico pizza Itens
function getItensTime(){	
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		url: "/relatorio/itens/time",
		data: JSON.stringify(ary),
		success: function(dataReturn){
			var result = Object.keys(dataReturn).map(function (key) {       
		        return [String(key), dataReturn[key]]; 
		    });
			
			drawPieTime(result);
		}
	});
}

function drawPieTime(values){
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Topping');
    data.addColumn('number', 'Slices');
    
    var abonados, rejeitados, aprovados;
    var aux = [];
	var array = [];
	
	for(var i = 0; i < values.length; i++){
		var aux = values[i];
		var array = aux[0].split(':');
	}
	
	rejeitado = array[0];
	abonados = aux[1];
	aprovados = array[1];
	
    data.addRows([
      ['Aprovados', parseInt(aprovados)],
      ['Reprovados', parseInt(rejeitado)],
      ['Abonados', parseInt(abonados)]
    ]);

    var options = {title:'Itens trabalhados no mês - TIME'};

    var chart = new google.visualization.PieChart(document.getElementById('time_chart_div'));
    chart.draw(data, options);
}