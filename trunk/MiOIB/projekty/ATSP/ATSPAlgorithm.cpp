/*
 * ATSPAlgorithm.cpp
 *
 *  Created on: 2011-10-12
 *      Author: jawora
 */

#include <ATSPAlgorithm.hpp>

ATSPAlgorithm::ATSPAlgorithm(ATSPData *data,
		ATSPInstance *instance) :
	data(data), instance(instance) {
	assert(data != NULL);
	assert(instance != NULL);
	assert(instance->getLength() == data->getDimension());
}

void ATSPAlgorithm::showInstance(){
	instance->show();
}

int ATSPAlgorithm::calculateObjectiveFunction(){
	int value = 0;
	for (unsigned int i = 0; i < instance->getLength(); i++){
		value += data->getDistance(instance->getElement(i),instance->getElement((i+1)%instance->getLength()));
	}
	return value;
}
