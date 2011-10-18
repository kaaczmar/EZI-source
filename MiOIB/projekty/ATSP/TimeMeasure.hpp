/*
 * TimeMeasure.hpp
 *
 *  Created on: 18-10-2011
 *      Author: jawora
 */

#ifndef TIMEMEASURE_HPP_
#define TIMEMEASURE_HPP_

#include <ATSPAlgorithm.hpp>
#include <boost/timer.hpp>

class TimeMeasure {
private:
	boost::timer timer;
	double minTime;
	ATSPAlgorithm *algorithm;
	unsigned int *baseInstance;

public:
	TimeMeasure(double minTime = 0.1);
	void setAlgorithm(ATSPAlgorithm *algorithm);
	double execute();

};

#endif /* TIMEMEASURE_HPP_ */
