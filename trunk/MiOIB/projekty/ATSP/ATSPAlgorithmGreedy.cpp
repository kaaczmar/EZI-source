/*
 * ATSPAlgorithmGreedy.cpp
 *
 *  Created on: 2011-10-13
 *      Author: jawora
 */

#include <ATSPAlgorithmGreedy.hpp>
#include <limits.h>
#include <iostream>

using namespace std;

ATSPAlgorithmGreedy::ATSPAlgorithmGreedy(ATSPData *data,
		ATSPInstance *instance, double timeout) :
	ATSPAlgorithm(data, instance, timeout) {

}

void ATSPAlgorithmGreedy::calculateNextBestStep() {
	bestNextStep = -1;
	bestNextStepValue = INT_MAX;

	unsigned int from = instance->getElement(step);
	int distance;

	for (unsigned int i = step + 1; i < instance->getLength(); i++){
		distance = data->getDistance(from,instance->getElement(i));

//		cout << "Distance with " << instance->getElement(i) << ":" << distance;

		if (distance < bestNextStepValue){
			bestNextStepValue = distance;
			bestNextStep = i;
//			cout << " BEST";
		}
//		cout << endl;
	}

}

void ATSPAlgorithmGreedy::optimize() {
	instance->randomize();

	step = 0;

	instance->show();

	while (step < instance->getLength() - 2){
		calculateNextBestStep();
//		cout << "swap with " << instance->getElement(bestNextStep) << endl;
		instance->swap(step+1, bestNextStep);
//		instance->show();
		step++;
	}

	bestSequence = ATSPInstance(*instance);
	bestSequenceValue = calculateObjectiveFunction(instance->getInstanceArray(), instance->getLength());

	cout << "Best sequence: ";
	bestSequence.show();
	cout << "Best sequence value: " << bestSequenceValue << endl;
}
