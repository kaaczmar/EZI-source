/*
 * ATSPAlgorithmGreedy.hpp
 *
 *  Created on: 2011-10-13
 *      Author: jawora
 */

#ifndef ATSPALGORITHMGREEDY_HPP_
#define ATSPALGORITHMGREEDY_HPP_

#include <ATSPAlgorithm.hpp>

class ATSPAlgorithmGreedy : public ATSPAlgorithm {
private:
	unsigned int step;
	unsigned int bestNextStep;
	int bestNextStepValue;

	void calculateNextBestStep();

public:
	ATSPAlgorithmGreedy(ATSPData *data, ATSPInstance *instance, double timeout = 0);
	void optimize(bool showResult = true);
};

#endif /* ATSPALGORITHMGREEDY_HPP_ */
