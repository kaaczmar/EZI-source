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

void ATSPAlgorithmLSSteepest::optimize(bool showResult) {
	bestSequenceValue = calculateObjectiveFunction(instance->getInstanceArray(), instance->getLength());
	bestSequence = ATSPInstance(*instance);

	unsigned long long hops = 0;
	unsigned long long neigh = 0;
	unsigned int distance = 0;
	unsigned int previousBestValue = bestSequenceValue;
	unsigned int *currentBest = new unsigned int[instance->getLength()];

	for (unsigned int i = 0; i < instance->getLength(); i++)
	{
		currentBest[i] = instance->getInstanceArray()[i];
	}

	while (true)
	{
		instance->initialize(currentBest);
		while (instance->nextNeighbour())
		{
			neigh++;
			distance = calculateObjectiveFunction(instance->getCurrentNeighbour(), instance->getLength());
			if (distance < bestSequenceValue)
			{
				bestSequenceValue = distance;
				for (unsigned int i = 0; i < instance->getLength(); i++)
				{
					currentBest[i] = instance->getCurrentNeighbour()[i];
				}
				//if you uncomment this break than this will become Greedy
				//break;
			}
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
	bestSequence = ATSPInstance(*instance);

	if (showResult){
		bestSequence.show();
		cout << "\t" << bestSequenceValue << "\t" << hops << "\t" << neigh << "\t";
	}
}
