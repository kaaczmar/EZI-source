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



	//TODO dobrac base value zaleznie od sredniej zmiany jakosci
	double temperature = 10;
	const double coolingRate = 0.99;
	//TODO dobrac zaleznie od dlugosci instancji (rozmiaru sasiedztwa)
	const unsigned int markovLength = instance->getLength()*(instance->getLength()-1)/2;
//	const unsigned int markovLength = 5;

	//TODO warunki STOPu
	bool changed = true;
	while (temperature > 0.1) {
		changed = false;
		for (unsigned int i = 0; i < markovLength; i++) {
			distance = calculateObjectiveFunction(
					instance->getCurrentNeighbour(), instance->getLength());
			neigh++;

			if (distance < bestSequenceValue) {
				bestSequenceValue = distance;
				instance->initializeAnnealing(instance->getCurrentNeighbour());
				hopsBetter++;
				changed = true;
				instance->show();
				cout << "\t" << bestSequenceValue << endl;
			}
			else if (exp((double)-1*(distance-bestSequenceValue)/temperature) > (double)rand()/RAND_MAX) {
				instance->initializeAnnealing(instance->getCurrentNeighbour());
				hopsWorse++;
//				cout << exp((double)-1*(distance-bestSequenceValue)/temperature) << "\t" << (distance-bestSequenceValue) << "\t" << temperature << endl;
				//TODO petle -> powinien rozpoczynac przeszukiwanie od nastepnego od ktorego przyszedl
			}
			if (!instance->nextNeighbourAnnealing())
				break;
		}
		temperature *= coolingRate;
	}
	bestSequence = ATSPInstance(*instance);

	if (showResult) {
		bestSequence.show();
		cout << "\t" << bestSequenceValue << "\t" << hopsBetter << "\t" << hopsWorse << "\t" << neigh
				<< "\t";
	}
}

