/*
 * Data.cpp
 *
 *  Created on: 2011-09-26
 *      Author: jawora
 */

#include <ATSPData.hpp>
#include <iostream>

using namespace boost;
using namespace std;

ATSPData::ATSPData() {

}

void ATSPData::setDimension(int dimension){
	this->dimension = dimension;
	data.resize(extents[dimension][dimension]);
}

int ATSPData::getDimension(){
	return dimension;
}

void ATSPData::print(){
	for (int y = 0; y < dimension; y++){
		for (int x = 0; x < dimension; x++){
			cout << data[x][y] << ",";
		}
		cout << endl;
	}
}
