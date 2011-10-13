/*
 * ATSPInstance.hpp
 *
 *  Created on: 2011-10-12
 *      Author: jawora
 */

#ifndef ATSP_INSTANCE_HPP_
#define ATSP_INSTANCE_HPP_

class ATSPInstance{
private:
	unsigned int length;
	unsigned int *instance;

	unsigned int swapX, swapY;
	bool firstNeighbour;

public:
	ATSPInstance(unsigned int length = 0);
	ATSPInstance(ATSPInstance &instance);

	void show();
	unsigned int getLength() { return length; }
	unsigned int getElement(unsigned int position);

	void randomize();
	void swap(unsigned int x, unsigned int y);

	void reinitializeNeighbourhood();
	bool nextNeighbour();
	void resetInstanceToBase();
};

#endif /* INSTANCE_HPP_ */
