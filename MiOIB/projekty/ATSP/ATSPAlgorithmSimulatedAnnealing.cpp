/*
 * ATSPAlgorithmSimulatedAnnealing.cpp
 *
 *  Created on: 07-11-2011
 *      Author: jawora
 */
#include <ATSPAlgorithmSimulatedAnnealing.hpp>
#include <iostream>

using namespace std;

ATSPAlgorithmSimulatedAnnealing::ATSPAlgorithmSimulatedAnnealing(ATSPData *data,
		ATSPInstance *instance, double timeout) :
		ATSPAlgorithm(data, instance, timeout) {

}

void ATSPAlgorithmSimulatedAnnealing::optimize(bool showResult) {
	unsigned int distance = 0;
	unsigned long long hopsBetter = 0;
	unsigned long long hopsWorse = 0;
	unsigned long long neigh = 0;

	bestSequenceValue = calculateObjectiveFunction(instance->getInstanceArray(),
			instance->getLength());
	bestSequence = ATSPInstance(*instance);
	unsigned int currentSequenceValue = bestSequenceValue;

	ATSPInstance tmpInstance(*instance);
	tmpInstance.reinitializeNeighbourhood();

	double sum = 0;
	unsigned int count = 0;

	double myValue = calculateObjectiveFunction(tmpInstance.getInstanceArray(), tmpInstance.getLength());
	while (tmpInstance.nextNeighbour()){
		sum += abs(myValue - calculateObjectiveFunction(tmpInstance.getCurrentNeighbour(), tmpInstance.getLength()));
		count++;
	}

	double temperature = sum/(count*4.0);

	double baseTemeprature = temperature;

	const double coolingRate = 0.8;
	const unsigned int markovLength = instance->getLength()*(instance->getLength()-1)/2;

	unsigned int steps = 0;
	unsigned int lastStepImprove = 0;

	while (steps < 20) {
		for (unsigned int i = 0; i < markovLength; i++) {
			distance = calculateObjectiveFunction(
					instance->getCurrentNeighbour(), instance->getLength());
			neigh++;

			if (distance <= currentSequenceValue) {
				if (distance < bestSequenceValue){
					bestSequenceValue = distance;
					bestSequence = ATSPInstance(*instance);
				}
				instance->initializeAnnealing(instance->getCurrentNeighbour());
				hopsBetter++;
				lastStepImprove = steps;
				currentSequenceValue = calculateObjectiveFunction(instance->getInstanceArray(), instance->getLength());
			} else if (exp((double)((double)currentSequenceValue - distance)/temperature) > (double)rand()/RAND_MAX) {
				instance->initializeAnnealing(instance->getCurrentNeighbour());
				currentSequenceValue = calculateObjectiveFunction(instance->getInstanceArray(), instance->getLength());
				hopsWorse++;
			}
			if (!instance->nextNeighbour()){
				instance->reinitializeNeighbourhood();
			}
		}
		temperature *= coolingRate;
		steps++;
	}

	if (showResult) {
		bestSequence.show();
		cout << "\t" << bestSequenceValue << "\t" << baseTemeprature << "\t" << hopsBetter << "\t" << hopsWorse << "\t" << (hopsBetter+hopsWorse) << "\t" << neigh
				<< "\t";
	}
}

