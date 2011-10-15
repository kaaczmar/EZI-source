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

void ATSPAlgorithmLSGreedy::optimize() {
	cout << "Local search - GREEDY" << endl;
	bestSequenceValue = calculateObjectiveFunction(instance->getInstanceArray(), instance->getLength());
	cout << "Starting sequence: ";
	instance->show();
	cout << "Starting sequence value: " << bestSequenceValue << endl;

	unsigned int distance = 0;
	unsigned int hops = 0;

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

	cout << "Best sequence after " << hops << " jumps: ";
	bestSequence.show();
	cout << "Best sequence value: " << bestSequenceValue << endl;
}
