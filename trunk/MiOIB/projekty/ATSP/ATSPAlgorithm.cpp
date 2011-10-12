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
}

void ATSPAlgorithm::showInstance(){
	instance->show();
}
