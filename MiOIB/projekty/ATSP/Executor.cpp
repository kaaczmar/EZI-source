/*
 * Executor.cpp
 *
 *  Created on: 18-10-2011
 *      Author: jawora
 */

#include <Executor.hpp>
#include <assert.h>
#include <sstream>
#include <iostream>

using namespace std;

Executor::Executor(double minTime) : algorithm(NULL) {
	assert(minTime >= 0);
	this->minTime = minTime;
}

void Executor::setAlgorithm(ATSPAlgorithm *algorithm){
	assert(algorithm != NULL);
	this->algorithm = algorithm;
}

void Executor::execute(){
	assert(algorithm != NULL);
	long executionCounter = 1;

	double elapsed;

	baseInstance = ATSPInstance(*algorithm->getInstance());

	timer.restart();
	algorithm->optimize(true);
	elapsed = timer.elapsed();

	result = algorithm->getBestSequenceValue();

	while (elapsed < minTime) {
		executionCounter++;
		algorithm->getInstance()->reinitializeWithCopy(baseInstance.getInstanceArray());
		algorithm->optimize(false);
		elapsed = timer.elapsed();
	}

	executionTime = round(1000 * elapsed / executionCounter)/1000.0;
}

void Executor::print(){
//	stringstream stream;
//	stream << std::fixed << executionTime;

	double resultQuality = (double) (result) / (algorithm->getData()->getOptimalSolution()) * 100;
	resultQuality = round(resultQuality*100) / 100.0;

	unsigned int baseInstanceValue = algorithm->calculateObjectiveFunction(baseInstance.getInstanceArray(), baseInstance.getLength());

	double baseQuality = (double) (baseInstanceValue) / (algorithm->getData()->getOptimalSolution()) * 100;
	baseQuality = round(baseQuality*100) / 100.0;

	cout << executionTime << "\t" << baseQuality << "\t" << resultQuality << "\t" << (baseQuality - resultQuality) << "\t" << algorithm->getBestSequence().compareWith(baseInstance) << endl;
}
