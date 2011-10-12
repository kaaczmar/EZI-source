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

class ATSPAlgorithm{
private:
	ATSPData *data;
	ATSPInstance *instance;

public:
	ATSPAlgorithm(ATSPData *data, ATSPInstance *instance);
	void showInstance();
	int calculateObjectiveFunction();
};

#endif /* ATSPALGORITHM_HPP_ */
