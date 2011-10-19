/*
 * Executor.hpp
 *
 *  Created on: 18-10-2011
 *      Author: jawora
 */

#ifndef EXECUTOR_HPP_
#define EXECUTOR_HPP_

#include <ATSPAlgorithm.hpp>
#include <boost/timer.hpp>

class Executor {
private:
	boost::timer timer;
	double minTime;

	ATSPAlgorithm *algorithm;

	ATSPInstance baseInstance;

	double executionTime;
	unsigned int result;

public:
	Executor(double minTime = 0.1);
	void setAlgorithm(ATSPAlgorithm *algorithm);
	void execute();

	void print();

};

#endif /* EXECUTOR_HPP_ */
