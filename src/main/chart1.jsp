<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript" src="resources/js/jquery-3.6.0.js"></script>
<script type="text/javascript">
	/* 들어오자마자 차트가 뜨는 상황(비동기식 - ajax사용) */
	$(function() {
		$.ajax({
			url : "chart5",
			success : function(json5) {
					console.log(json5) // 데이터 분류 상태 찍어보기
					
					count1 = json5.실내
					count2 = json5.야외

				google.charts.load("current", {
					packages : [ "corechart" ]
				});
				google.charts.setOnLoadCallback(drawChart);
				function drawChart() {
					var data = google.visualization.arrayToDataTable([
							[ 'Place', 'Count' ], 
							[ '실내', count1 ],
							[ '야외', count2 ], ]);

					var options = {
						title : '실내 데이트와 야외 데이트 중 어떤 것을 선호하십니까?',
						is3D : true,
					};

					var chart = new google.visualization.PieChart(document
							.getElementById('piechart_3d'));
					chart.draw(data, options);
				} // function
			} // success
		}) // ajax

		 $.ajax({
			url : "chart6",
			success : function(json6) {
					console.log(json6) // 데이터 분류 상태 찍어보기
					
					countM1 = json6.maleOlder
					countM2 = json6.maleYounger
					countM3 = json6.maleSame
					
					countF1 = json6.femaleOlder
					countF2 = json6.femaleYounger
					countF3 = json6.femaleSame

				google.charts.load('current', {'packages':['bar']});
      			google.charts.setOnLoadCallback(drawChart); 

      			function drawChart() {
      				var data = google.visualization.arrayToDataTable([
      			        ['', 'Older', 'Younger', 'Same', { role: 'annotation' } ],
      			        ['Male', countM1, countM2, countM3, ''],
      			        ['Female', countF1, countF2, countF3, ''],
      			      ]);

      			      var options = {
      			        width: 600,
      			        height: 400,
      			        legend: { position: 'top', maxLines: 3 },
      			        bar: { groupWidth: '75%' },
      			        isStacked: true
      			      };

        		var chart = new google.charts.Bar(document.getElementById('barchart_material'));

        		chart.draw(data, google.charts.Bar.convertOptions(options));
     			} //function
			} // success
		 }) // ajax 
	}) // 전체function
</script>
</head>
<!-- view부분!! -->
<body>
	<h3>설문조사</h3>
	<hr color="red">
	<div id="piechart_3d" style="width: 900px; height: 500px;"></div>
	<B>(응답자 남/여 구분) 연상, 연하, 동갑 중 어떤 상대를 원하십니까?</B><br>
	<div id="barchart_material" style="width: 900px; height: 500px;"></div>
</body>
</html>