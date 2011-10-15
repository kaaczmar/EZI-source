/*
 * ATSPAlgorithmLSGreedy.cpp
 *
 *  Created on: 2011-10-13
 *      Author: jawora
 */

#include <ATSPAlgorithmLSSteepest.hpp>
#include <iostream>

using namespace std;

ATSPAlgorithmLSSteepest::ATSPAlgorithmLSSteepest(ATSPData *data,
		ATSPInstance *instance, double timeout) :
	ATSPAlgorithm(data, instance, timeout) {

}

void ATSPAlgorithmLSSteepest::optimize() {
	cout << "Local search - STEPPEST" << endl;
	bestSequenceValue = calculateObjectiveFunction(instance->getInstanceArray(), instance->getLength());
	bestSequence = ATSPInstance(*instance);
	cout << "Starting sequence: ";
	bestSequence.show();
	cout << "Starting sequence value: " << bestSequenceValue << endl;

	unsigned int hops = 0;
	unsigned int distance = 0;
	unsigned int previousBestValue = bestSequenceValue;
	unsigned int *currentBest = new unsigned int[instance->getLength()];
	for (int i = 0; i < instance->getLength(); i++)
	{
		currentBest[i] = instance->getInstanceArray()[i];
	}

	while (true)
	{
		instance->initialize(currentBest);
		int neigh = 0;
		while (instance->nextNeighbour())
		{
			distance = calculateObjectiveFunction(instance->getCurrentNeighbour(), instance->getLength());
			if (distance < bestSequenceValue)
			{
				bestSequenceValue = distance;
				for (int i = 0; i < instance->getLength(); i++)
				{
					currentBest[i] = instance->getCurrentNeighbour()[i];
				}
				//if you uncomment this break than this will become Greedy
				//break;
			}
			neigh++;
		}
		//cout << "Neighbours visited: " << neigh << endl;
		if (bestSequenceValue < previousBestValue)
		{
			previousBestValue = bestSequenceValue;
			hops++;
		}
		else
		{
			break;
		}
	}

	cout << "Best sequence after " << hops << " jumps: ";
	bestSequence.show();
	cout << "Best sequence value: " << bestSequenceValue << endl;
}
