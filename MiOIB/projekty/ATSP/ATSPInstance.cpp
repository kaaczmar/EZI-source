/*
 * ATSPInstance.cpp
 *
 *  Created on: 2011-10-12
 *      Author: jawora
 */

#include <boost/foreach.hpp>
#include <iostream>
#include <assert.h>

#include <ATSPInstance.hpp>

using namespace std;

ATSPInstance::ATSPInstance(unsigned int length) :
	length(length) {
	instance = new unsigned int[length];
	for (unsigned int i = 0; i < length; i++) {
		instance[i] = i;
	}
}

ATSPInstance::ATSPInstance(ATSPInstance &instance){
	this->length = instance.length;

	this->instance = new unsigned int[this->length];
	for (unsigned int i = 0; i < this->length; i++)
		this->instance[i] = instance.instance[i];

}

void ATSPInstance::swap(unsigned int x, unsigned int y) {
	unsigned int temp = instance[y];
	instance[y] = instance[x];
	instance[x] = temp;
}

void ATSPInstance::show() {
	for (unsigned int i = 0; i < length - 1; i++) {
		cout << instance[i] << "-";
	}
	cout << instance[length - 1] << endl;
}

void ATSPInstance::randomize() {
	srand(time(NULL));
	for (unsigned int i = 0; i < length - 1; i++) {
		unsigned int position = rand() % (length - i) + i;
		swap(i, position);
	}
}

unsigned int ATSPInstance::getElement(unsigned int position) {
	assert(position < length);
	return instance[position];
}

