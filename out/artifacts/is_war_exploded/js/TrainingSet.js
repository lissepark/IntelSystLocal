function TrainingSet() {
};

TrainingSet.prototype.createTrainingSet = function(img, canvas) {
	var j;
	var context = canvas.getContext('2d');
	canvas.width = img.width;
	canvas.height = img.height;
	context.drawImage(img, 0, 0, img.width, img.height);
	var trainingSet = [];
	for (var row = 0; row<img.height; row++) {
		trainingSet[row] = [];
	}
	for(var i=0; i<canvas.height; i++) {
		j=0;
		for (j; j<canvas.width; j++) {	
			var imgd = context.getImageData(j, i, 1, 1);
			var pix = imgd.data;
			var r = pix[0];
			var g = pix[1];
			var b = pix[2];
			var rgbAverage = Math.round((r+g+b)/3);
			trainingSet[i][j] = rgbAverage;
		}
	}
	return trainingSet;
};

TrainingSet.prototype.createTableTrainingSet = function(trainingSet){
	var j;
	document.write('<table>');
	for (var i = 0; i < trainingSet.length; i++) {
		j=0;
		document.write('<tr>');
		for (j; j < trainingSet[i].length; j++) {
			document.write('<td>');			
			document.write(trainingSet[i][j]);
			document.write('</td>');
		};
		document.write('</tr>');
	};
	document.write('</table>');
};

TrainingSet.prototype.sampleMeanVector = function(learMatrix){
	var arrCount = learMatrix.length;
	var arrSampleMeanVector = [];
	for (var i = 0; i < learMatrix[0].length; i++) {
		arrSampleMeanVector[i] = 0;
		for (var j = 0; j < arrCount; j++) {
			arrSampleMeanVector[i] = arrSampleMeanVector[i] + learMatrix[j][i];
		};
		arrSampleMeanVector[i] = Math.round(arrSampleMeanVector[i] / arrCount);
	};
	return arrSampleMeanVector;
};

//permition 50%
TrainingSet.prototype.permitsUpper = function(meanVector) {
	var permitsUpper = [];
	var pu = 0;
	for (var i = 0; i < meanVector.length; i++) {
		if(meanVector[i]*1.4 >= 255) {
			permitsUpper[i] = 255;
		}else{
			permitsUpper[i] = Math.round(meanVector[i]*1.4);
		}
	};
	return permitsUpper;
};

TrainingSet.prototype.tableSampleMeanVector = function(meanVector){
	document.write('<table>');
	document.write('<tr>');
	for (var i = 0; i < meanVector.length; i++) {
		document.write('<td class="meanVectorCell">');
		document.write(meanVector[i]);
		document.write('</td>');
	};
	document.write('</tr>');
	document.write('</table>');
};

//permition 50%
TrainingSet.prototype.permitsLower = function(meanVector) {
	var permitsLower = [];
	for (var i = 0; i < meanVector.length; i++) {
		if(meanVector[i]*0.6 <= 0) {
			permitsLower[i] = 0;
		}else{
			permitsLower[i] = Math.round(meanVector[i]*0.6);
		}
	};
	return permitsLower;
};

TrainingSet.prototype.tablePermitsUpper = function(permitsUpper){
	document.write('<table>');
	document.write('<tr>');
	for (var i = 0; i < permitsUpper.length; i++) {
		document.write('<td class="permitUpperCell">');
		document.write(permitsUpper[i]);
		document.write('</td>');
	};
	document.write('</tr>');
	document.write('</table>');
};

TrainingSet.prototype.tablePermitsLower = function(permitsLower){
	document.write('<table>');
	document.write('<tr>');
	for (var i = 0; i < permitsLower.length; i++) {
		document.write('<td class="permitLowerCell">');
		document.write(permitsLower[i]);
		document.write('</td>');
	};
	document.write('</tr>');
	document.write('</table>');
};


