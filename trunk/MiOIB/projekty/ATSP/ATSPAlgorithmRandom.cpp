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

void ATSPAlgorithmRandom::optimize() {
	double tmpValue;

	timer.restart();

	instance->randomize();
	bestSequenceValue = calculateObjectiveFunction();
	bestSequence = ATSPInstance(*instance);

	long tries = 0;

	while (timer.elapsed() < timeout) {
		for (unsigned int _tmp = 0; _tmp < 200; _tmp++){
			instance->randomize();
			tmpValue = calculateObjectiveFunction();
			if (tmpValue < bestSequenceValue) {
				bestSequenceValue = tmpValue;
				bestSequence = ATSPInstance(*instance);
			}
			tries++;
		}
	}

	cout << "Best sequence: ";
	bestSequence.show();
	cout << "Best sequence value: " << bestSequenceValue << endl;
	cout << "Number of tries: " << tries << endl;
}
