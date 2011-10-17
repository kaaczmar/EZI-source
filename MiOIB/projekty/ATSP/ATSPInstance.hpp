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
	unsigned int *neighbour;

	unsigned int swapX, swapY;
	bool firstNeighbour;

	void swapNeighbour(unsigned int x, unsigned int y);

public:
	ATSPInstance(unsigned int length = 0);
	ATSPInstance(ATSPInstance &instance);

	void show();
	void showNeighbour();
	unsigned int getLength() { return length; }
	unsigned int getElement(unsigned int position);

	void randomize();
	void randomizeHeuristic();
	void swap(unsigned int x, unsigned int y);

	void reinitializeNeighbourhood();
	bool nextNeighbour();
	unsigned int *getInstanceArray();
	unsigned int *getCurrentNeighbour();

	void initialize(unsigned int *position);
};

#endif /* INSTANCE_HPP_ */
