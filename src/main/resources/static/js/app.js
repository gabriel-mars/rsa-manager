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
		}
	});
}

// Gráfico de atividades executadas por dia
function drawColumn(){
	var result = Object.keys(dados).map(function (key) {       
        return [String(key), dados[key]]; 
    }); 
    
	dados = result;
	
	console.log(result);
	
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

// Gráfico de itens de acordo com a média
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

function drawLines(values){
	var data = new google.visualization.DataTable();
    data.addColumn('string', 'Dias');
    data.addColumn('number', 'Média do dia');
    data.addColumn('number', 'Avaliado pelo colaborador');
    
    console.log(dados);
    console.log(values);

    for(var i = 0; i < dados.length; i++){
		var a = dados[i];
		var b = values[i];
		
  	  	data.addRow([a[0], b[1], a[1]]); 
	}

    var options = {
    		title:'Comparativo de produção com o time',
    		hAxis: {title: 'Data'},
    		vAxis: {title: 'Itens'}
    };

    var chart = new google.visualization.LineChart(document.getElementById("chartMediaDia"));
    chart.draw(data, options);
}