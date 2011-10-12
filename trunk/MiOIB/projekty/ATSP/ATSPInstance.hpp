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

	void swap(unsigned int x, unsigned int y);

public:
	ATSPInstance(unsigned int length = 0);
	ATSPInstance(ATSPInstance &instance);
	void show();
	void randomize();
	unsigned int getLength() { return length; }
	unsigned int getElement(unsigned int position);
};

#endif /* INSTANCE_HPP_ */
