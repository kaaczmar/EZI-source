/*
 * ATSPAlgorithmLSGreedy.hpp
 *
 *  Created on: 2011-10-13
 *      Author: jawora
 */

#ifndef ATSPALGORITHMLSGREEDY_HPP_
#define ATSPALGORITHMLSGREEDY_HPP_

#include <ATSPAlgorithm.hpp>

class ATSPAlgorithmLSGreedy : public ATSPAlgorithm {
public:
	ATSPAlgorithmLSGreedy(ATSPData *data, ATSPInstance *instance, double timeout = 0);
	void optimize(bool showResult = true);
};


#endif /* ATSPALGORUTHMLS_HPP_ */
