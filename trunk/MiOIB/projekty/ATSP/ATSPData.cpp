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

void ATSPData::setDimension(unsigned int dimension){
	this->dimension = dimension;
	data.resize(extents[dimension][dimension]);
}

void ATSPData::setOptimalSolution(unsigned int optimalSolution){
	this->optimalSolution = optimalSolution;
}

unsigned int ATSPData::getDimension(){
	return dimension;
}

unsigned int ATSPData::getOptimalSolution(){
	return optimalSolution;
}

void ATSPData::print(){
	for (unsigned int y = 0; y < dimension; y++){
		for (unsigned int x = 0; x < dimension; x++){
			cout << data[x][y] << ",";
		}
		cout << endl;
	}
}

int ATSPData::getDistance(int from, int to){
	return data[to][from];
}
