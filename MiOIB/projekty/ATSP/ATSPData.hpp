/*
 * Data.hpp
 *
 *  Created on: 2011-09-26
 *      Author: jawora
 */

#ifndef DATA_HPP_
#define DATA_HPP_

#include <boost/multi_array.hpp>

class ATSPData{
private:
	unsigned int dimension;
	unsigned int optimalSolution;

public:
	boost::multi_array<int,2> data;

	ATSPData();

	void setDimension(unsigned int dimension);
	void setOptimalSolution(unsigned int oprimalSolution);

	unsigned int getDimension();
	unsigned int getOptimalSolution();

	int getDistance(int from, int to);
	void print();
};

#endif /* DATA_HPP_ */
