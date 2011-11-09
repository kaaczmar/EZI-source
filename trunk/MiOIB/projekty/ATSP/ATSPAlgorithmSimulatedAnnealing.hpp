/*
 * ATSPAlgorithmSimulatedAnnealing.hpp
 *
 *  Created on: 07-11-2011
 *      Author: jawora
 */

#ifndef ATSPALGORITHMSIMULATEDANNEALING_HPP_
#define ATSPALGORITHMSIMULATEDANNEALING_HPP_

#include <ATSPAlgorithm.hpp>

class ATSPAlgorithmSimulatedAnnealing : public ATSPAlgorithm {
public:
	ATSPAlgorithmSimulatedAnnealing(ATSPData *data, ATSPInstance *instance, double timeout = 0);
	void optimize(bool showResult = true);
};


#endif /* ATSPALGORITHMSIMULATEDANNEALING_HPP_ */
