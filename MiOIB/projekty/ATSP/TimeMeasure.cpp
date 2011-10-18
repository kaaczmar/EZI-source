/*
 * TimeMeasure.cpp
 *
 *  Created on: 18-10-2011
 *      Author: jawora
 */

#include <TimeMeasure.hpp>
#include <assert.h>

TimeMeasure::TimeMeasure(double minTime) : algorithm(NULL) {
	assert(minTime >= 0);
	this->minTime = minTime;
}

void TimeMeasure::setAlgorithm(ATSPAlgorithm *algorithm){
	assert(algorithm != NULL);
	this->algorithm = algorithm;
}

double TimeMeasure::execute(){
	assert(algorithm != NULL);
	long executionCounter = 0;
	double elapsed;

	baseInstance = new unsigned int[algorithm->getInstance()->getLength()];
	for (unsigned int i = 0; i < algorithm->getInstance()->getLength(); i++)
		baseInstance[i] = algorithm->getInstance()->getElement(i);

	timer.restart();
	do{
		algorithm->optimize(executionCounter++ == 0);
		elapsed = timer.elapsed();
		algorithm->getInstance()->reinitializeWithCopy(baseInstance);
	} while (elapsed < minTime);

	return elapsed / executionCounter;
}
