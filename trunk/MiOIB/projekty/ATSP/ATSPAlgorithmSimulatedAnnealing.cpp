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
	//TODO dobrac zaleznie od dlugosci instancji (rozmiaru sasiedztwa)
	const unsigned int markovLength = instance->getLength()*(instance->getLength()-1)/2;
//	const unsigned int markovLength = 5;

	//TODO warunki STOPu
	int steps = 0;
	while (steps < 10) {
		for (unsigned int i = 0; i < markovLength; i++) {
			distance = calculateObjectiveFunction(
					instance->getCurrentNeighbour(), instance->getLength());
			neigh++;

			if (distance < bestSequenceValue) {
				bestSequenceValue = distance;
				instance->initialize(instance->getCurrentNeighbour());
				hopsBetter++;
			}
			else if (exp((double)-1*(distance-bestSequenceValue)/temperature) > (double)rand()/RAND_MAX) {
				instance->initialize(instance->getCurrentNeighbour());
				hopsWorse++;
			}
			if (!instance->nextNeighbour())
				instance->reinitializeNeighbourhood();
		}
		temperature *= coolingRate;
		steps++;
	}
	bestSequence = ATSPInstance(*instance);

	if (showResult) {
		bestSequence.show();
		cout << "\t" << bestSequenceValue << "\t" << baseTemeprature << "\t" << hopsBetter << "\t" << hopsWorse << "\t" << (hopsBetter+hopsWorse) << "\t" << neigh
				<< "\t";
	}
}

