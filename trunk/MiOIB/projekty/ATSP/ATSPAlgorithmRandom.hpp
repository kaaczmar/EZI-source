/*
 * ATSPAlgorithmRandom.hpp
 *
 *  Created on: 2011-10-12
 *      Author: jawora
 */

#ifndef ATSPALGORITHMRANDOM_HPP_
#define ATSPALGORITHMRANDOM_HPP_

#include <ATSPAlgorithm.hpp>

class ATSPAlgorithmRandom : public ATSPAlgorithm {
public:
	ATSPAlgorithmRandom(ATSPData *data, ATSPInstance *instance, double timeout = 0);
	void optimize(bool showResult = true);
};

#endif /* ATSPALGORITHMRANDOM_HPP_ */
