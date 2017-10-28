function Binary(){
};

Binary.prototype.createBinaryMatrix = function(learMatrix, permitsUpper, permitsLower) {
			var binaryMatrix = [];
			for (var row2 = 0; row2<learMatrix.length; row2++) {
				binaryMatrix[row2] = [];
			}
			for(var row = 0; row < learMatrix.length; row++) {
				for(col = 0; col < learMatrix[row].length; col++) {
					if ((learMatrix[row][col] >= permitsLower[col])&&(learMatrix[row][col]<= permitsUpper[col])) {
						binaryMatrix[row][col] = 1;
					}else{
						binaryMatrix[row][col] = 0;
					};
				}
			};
			document.write("<table>");
			for (var row1 = 0; row1<learMatrix.length; row1++) {
				document.write("<tr>");	
				for (var col1 = 0; col1<learMatrix[row1].length; col1++) {
					document.write("<td>");
					binaryMatrix[row1][col1];
					document.write(binaryMatrix[row1][col1]);
					document.write("</td>");
				}
				document.write("</tr>");
			}
			document.write("</table>");
			//the end binary matrix
			return binaryMatrix;
		};

		Binary.prototype.getBinaryMatrix = function(learMatrix, permitsUpper, permitsLower) {
			var binaryMatrix = [];
			for (var row2 = 0; row2<learMatrix.length; row2++) {
				binaryMatrix[row2] = [];
			}
			for(var row = 0; row < learMatrix.length; row++) {
				for(col = 0; col < learMatrix[row].length; col++) {
					if ((learMatrix[row][col] >= permitsLower[col])&&(learMatrix[row][col]<= permitsUpper[col])) {
						binaryMatrix[row][col] = 1;
					}else{
						binaryMatrix[row][col] = 0;
					};
				}
			};
			return binaryMatrix;
		};

		Binary.prototype.imageBinaryMatrix = function(binMatrix,canvas2) {
			var canvasBinary = canvas2;	
			var contextBinary = canvasBinary.getContext('2d');
			canvasBinary.height = binMatrix.length;
			for(var row4 = 0; row4<1; row4++){
				for(var col4 = 0; col4<binMatrix[row4].length; col4++){
				}
				canvasBinary.width = col4;
			}
			var i2 = 0;
			var imgData=contextBinary.createImageData(canvasBinary.width,canvasBinary.height);
			for(var row5 = 0; row5<binMatrix.length; row5++){
				for(var col5 = 0; col5<binMatrix[row5].length; col5++){
					//for (var i=0;i<imgData.data.length;i=i+4) {
						if(binMatrix[row5][col5] == 1){						
							imgData.data[i2+0]=0;
							imgData.data[i2+1]=0;
							imgData.data[i2+2]=0;
							imgData.data[i2+3]=255;					  
						}else{						
							imgData.data[i2+0]=255;
							imgData.data[i2+1]=255;
							imgData.data[i2+2]=255;
							imgData.data[i2+3]=255;					  
						}
						i2=i2+4;
					//}							
				}					
			}
			contextBinary.putImageData(imgData,0,0);
		};

		Binary.prototype.createBinaryVector = function(binaryMatrix) {
			var kol1_0;
			var kol1_1;
			var binaryVector = [];
			for (var i = 0; i < binaryMatrix.length; i++) {
				kol1_1 = 0;
				kol1_0 = 0;
				for (var j = 0; j < binaryMatrix[i].length; j++) {
					if (binaryMatrix[j][i] == 1) {
						kol1_1 = kol1_1 + 1;
					}else{
						kol1_0 = kol1_0 + 1;
					}
				}
				if (kol1_1 >= j/2) {
					binaryVector[i] = 1;					
					document.write(binaryVector[i]);
				}else{
					binaryVector[i] = 0;
					document.write(binaryVector[i]);
				}						
			}
			return binaryVector;
		};

		Binary.prototype.getBinaryVector = function(binaryMatrix) {
			var kol1_0;
			var kol1_1;
			var binaryVector = [];
			for (var i = 0; i < binaryMatrix.length; i++) {
				kol1_1 = 0;
				kol1_0 = 0;
				for (var j = 0; j < binaryMatrix[i].length; j++) {
					if (binaryMatrix[j][i] == 1) {
						kol1_1 = kol1_1 + 1;
					}else{
						kol1_0 = kol1_0 + 1;
					}
				}
				if (kol1_1 >= j/2) {
					binaryVector[i] = 1;
				}else{
					binaryVector[i] = 0;
				}						
			}
			return binaryVector;
		};

		Binary.prototype.imageBinaryVector = function(binaryVector,canvas) {
			var canvasBinVect = canvas;	
			var contextBinVect = canvasBinVect.getContext('2d');
			canvasBinVect.width = binaryVector.length;
			canvasBinVect.height = 10;	
			var i = 0;
			var imgData=contextBinVect.createImageData(canvasBinVect.width,10);
			for(var k = 0; k<10; k++){
				for(var l = 0; l<canvasBinVect.width; l++){
						if(binaryVector[l] == 1){						
							imgData.data[i+0]=0;
							imgData.data[i+1]=0;
							imgData.data[i+2]=0;
							imgData.data[i+3]=255;					  
						}else{						
							imgData.data[i+0]=255;
							imgData.data[i+1]=255;
							imgData.data[i+2]=255;
							imgData.data[i+3]=255;					  
						}
						i=i+4;				
				}					
			}
			contextBinVect.putImageData(imgData,0,0);
		};

		Binary.prototype.getDistance = function(binaryVector1,binaryVector2,countFeatures) {
			var sum = 0;
			for (var i = 0; i < countFeatures; i++) {
				sum += Math.abs(binaryVector1[i] - binaryVector2[i]);
			};
			return sum;
		};

		Binary.prototype.makePair = function(binaryEtalonVectorsArray,countFeatures){
			var pair = [];
			var dpair = [];
			for (var i = 0; i < 3; i++) { //hardcode with 3, not enough time
				pair[i] = 0;
				dpair[i] = countFeatures;
				for (var j = 0; j < 3; j++) { //hardcode with 3, not enough time
					if (i != j) {
						dist = this.getDistance(binaryEtalonVectorsArray[i],binaryEtalonVectorsArray[j],countFeatures);
						document.write(dist+", ");
						if (dist <= dpair[i]) {
							pair[i] = j;
							dpair[i] = dist;							
						};						
					};					
				};
				document.write('<br>');
				document.write('pair['+i+'] = '+pair[i]+'; ');
				document.write('dpair['+i+'] = '+dpair[i]);
				document.write('<br>');
			};
			return pair;
		};

		Binary.prototype.makeDistVectorsToEtalon = function(binaryMatrixArray,countFeatures,pair){
			//in this case features eq. samples
			var dev = [];
			var dePairV = [];
			var distVectors = [];
			distVectors.push(dev);
			distVectors.push(dePairV);
			for (var cl = 0; cl < 3; cl++) { //hardcode with 3, not enough time
				dev[cl] = [];
				for (var t = 0; t < countFeatures; t++) {
					dev[cl][t] = 0;
				};
				//dev[cl].fill(0,0,countFeatures);
				dePairV[cl] = [];
				for (var r = 0; r < countFeatures; r++) {
					dePairV[cl][r] = 0;
				};
				//dePairV[cl].fill(0,0,countFeatures);
				for (var i = 0; i < countFeatures; i++) {
					for (var j = 0; j < countFeatures; j++) {
						if (binaryEtalonVectorsArray[cl][i] != binaryMatrixArray[cl][j][i]) {
							dev[cl][j] += 1;
						};
						if (binaryEtalonVectorsArray[cl][i] != binaryMatrixArray[pair[cl]][j][i]) {
							dePairV[cl][j] += 1;
						};
					};					
				};
				document.write(cl+1+' class and '+((pair[cl]*1)+1)+' class');
				document.write('<br>');
				document.write(cl+1+' class: ');
				document.write('<br>');
				for (var k = 0; k < countFeatures; k++) {					
					document.write(dev[cl][k]+', ');
				};
				document.write('<br>');
				document.write('--------------------------------------------------');
				document.write('<br>');
				document.write(pair[cl]+1+' class: ');
				for (var k = 0; k < countFeatures; k++) {		
					document.write(dePairV[cl][k]+', ');
				};
				document.write('<br>');
				document.write('--------------------------------------------------');
				document.write('<br>');
				document.write('--------------------------------------------------');
				document.write('<br>');
			};
			return distVectors;
		};

Binary.prototype.calculateCriterion = function(k,dist,samples,de,dePair) {
	var params = [];
	var k1 = 0;
	var k2 = 0;
	var k3 = 0;
	var k4 = 0;
	for (var i = 0; i < samples; i++) { //samples - hardcode
		if (de[k][i] <= dist) {
			k1++;
		};		
	};
	for (var j = 0; j < samples; j++) { //samples - hardcode
		if (dePair[k][j] <= dist) {
			k3++;
		};		
	};
	var d1 = k1 / samples;
	var a = (samples - k1) / samples;
	var b = k3 / samples;
	var d2 = (samples - k3) / samples;

	//var kfe = this.calculateShennon(d1,a,b,d2);
	var kfe = this.calculateKulback(d1,d2);
	//if (d1 >= 0.5 && d2 >= 0.5) {
		params.push(d1);
		params.push(a);
		params.push(b);
		params.push(d2);
		params.push(kfe);
	//};	
	return params;
};

Binary.prototype.calculateShennon = function(d1,a,b,d2) {
	var com1 = 0;
	var com2 = 0;
	var com3 = 0;
	var com4 = 0;
	if (d1 == 0) {
		com1 = 0.0
	}else{
		com1 = (d1/(d1+b))*(Math.log(d1/(d1+b))/Math.log(2.0));
	};
	if (a == 0) {
		com2 = 0.0
	}else{
		com2 = (a/(d2+a))*(Math.log(a/(d2+a))/Math.log(2.0));
	};
	if (b == 0) {
		com3 = 0.0
	}else{
		com3 = (b/(d1+b))*(Math.log(b/(d1+b))/Math.log(2.0));
	};
	if (d2 == 0) {
		com4 = 0.0
	}else{
		com4 = (d2/(d2+a))*(Math.log(d2/(d2+a))/Math.log(2.0));
	};
	return 1.0 + (com1 + com2 + com3 + com4)/2.0;
};

Binary.prototype.calculateKulback = function(d1,d2) {
	var result;
	result = (d1+d2+0.1)/(2.0-d1-d2+0.1);
	result = Math.log(result)/Math.log(2.0);
	result *= d1+d2-1.0;
	return result;
}