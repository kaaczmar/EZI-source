/*
 * ATSPAlgorithmLSGreedy.cpp
 *
 *  Created on: 2011-10-13
 *      Author: jawora
 */

#include <ATSPAlgorithmLSGreedy.hpp>
#include <iostream>

using namespace std;

ATSPAlgorithmLSGreedy::ATSPAlgorithmLSGreedy(ATSPData *data,
		ATSPInstance *instance, double timeout) :
	ATSPAlgorithm(data, instance, timeout) {

}

void ATSPAlgorithmLSGreedy::optimize(bool showResult) {
	unsigned int distance = 0;
	unsigned int hops = 0;

	bestSequenceValue = calculateObjectiveFunction(instance->getInstanceArray(), instance->getLength());

	while (instance->nextNeighbour())
	{
		distance = calculateObjectiveFunction(instance->getCurrentNeighbour(), instance->getLength());
		if (distance < bestSequenceValue)
		{
			bestSequenceValue = distance;
			instance->initialize(instance->getCurrentNeighbour());
			hops++;
		}
	}
	bestSequence = ATSPInstance(*instance);

	if (showResult){
		bestSequence.show();
		cout << "\t" << bestSequenceValue << "\t" << hops << "\t";
	}
}
