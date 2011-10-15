/*
 * ATSPAlgorithm.cpp
 *
 *  Created on: 2011-10-12
 *      Author: jawora
 */

#include <ATSPAlgorithm.hpp>
#include <iostream>

using namespace std;

ATSPAlgorithm::ATSPAlgorithm(){

}

ATSPAlgorithm::ATSPAlgorithm(ATSPData *data,
		ATSPInstance *instance, double timeout) :
	data(data), instance(instance), timeout(timeout) {
	assert(data != NULL);
	assert(instance != NULL);
	assert(instance->getLength() == data->getDimension());
	assert(timeout >= 0);
}

void ATSPAlgorithm::showInstance(){
	instance->show();
}

unsigned int ATSPAlgorithm::calculateObjectiveFunction(unsigned int * instanceArray, unsigned int length){
	int value = 0;

	for (unsigned int i = 0; i < length; i++){
		value += data->getDistance(instanceArray[i], instanceArray[((i+1)%(length))]);
	}

	return value;
}
