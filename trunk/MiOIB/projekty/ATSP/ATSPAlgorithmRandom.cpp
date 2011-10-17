/*
 * ATSPAlgorithmRandom.cpp
 *
 *  Created on: 2011-10-12
 *      Author: jawora
 */

#include <ATSPAlgorithmRandom.hpp>
#include <iostream>

using namespace std;

ATSPAlgorithmRandom::ATSPAlgorithmRandom(ATSPData *data,
		ATSPInstance *instance, double timeout) :
	ATSPAlgorithm(data, instance, timeout) {

}

void ATSPAlgorithmRandom::optimize(bool showResult) {
	double tmpValue;

	timer.restart();

	bestSequenceValue = calculateObjectiveFunction(instance->getInstanceArray(), instance->getLength());
	bestSequence = ATSPInstance(*instance);

	unsigned long long tries = 0;

	while (timer.elapsed() < timeout) {
		for (unsigned int _tmp = 0; _tmp < 200; _tmp++){
			instance->randomize();
			tmpValue = calculateObjectiveFunction(instance->getInstanceArray(), instance->getLength());
			if (tmpValue < bestSequenceValue) {
				bestSequenceValue = tmpValue;
				bestSequence = ATSPInstance(*instance);
			}
			tries++;
		}
	}

	if (showResult){
		bestSequence.show();
		cout << "\t" << bestSequenceValue << "\t" << tries << "\t";
	}
}
