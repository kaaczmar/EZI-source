/*
 * ATSPAlgorithm.hpp
 *
 *  Created on: 2011-10-12
 *      Author: jawora
 */

#ifndef ATSPALGORITHM_HPP_
#define ATSPALGORITHM_HPP_

#include <ATSPData.hpp>
#include <ATSPInstance.hpp>
#include <boost/timer.hpp>

class ATSPAlgorithm{
protected:
	ATSPData *data;
	ATSPInstance *instance;
	boost::timer timer;
	double timeout;

	ATSPInstance bestSequence;
	unsigned int bestSequenceValue;

public:
	ATSPAlgorithm();
	ATSPAlgorithm(ATSPData *data, ATSPInstance *instance, double timeout = 0);
	void showInstance();
	unsigned int calculateObjectiveFunction(unsigned int * instanceArray, unsigned int length);
	virtual void optimize() = 0;
};

#endif /* ATSPALGORITHM_HPP_ */
