/*
 * ATSPAlgorithmLSSteepest.hpp
 *
 *  Created on: 2011-10-13
 *      Author: jawora
 */

#ifndef ATSPALGORITHMLSSTEEPEST_HPP_
#define ATSPALGORITHMLSSTEEPEST_HPP_

#include <ATSPAlgorithm.hpp>

class ATSPAlgorithmLSSteepest : public ATSPAlgorithm {
public:
	ATSPAlgorithmLSSteepest(ATSPData *data, ATSPInstance *instance, double timeout = 0);
	void optimize();
};

#endif /* ATSPALGORITHMLSSTEEPEST_HPP_ */
