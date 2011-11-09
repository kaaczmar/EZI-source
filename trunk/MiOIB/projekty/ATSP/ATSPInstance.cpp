/*
 * ATSPInstance.cpp
 *
 *  Created on: 2011-10-12
 *      Author: jawora
 */

#include <boost/foreach.hpp>
#include <iostream>
#include <assert.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>

#include <ATSPInstance.hpp>

using namespace std;

ATSPInstance::ATSPInstance(unsigned int length) :
	length(length) {
	instance = new unsigned int[length];
	neighbour = new unsigned int[length];

	neighboursCount = length * (length-1)/2;

	for (unsigned int i = 0; i < length; i++) {
		instance[i] = i;
		neighbour[i] = i;
	}
}

ATSPInstance::ATSPInstance(const ATSPInstance &instance){
	this->length = instance.length;

	neighbour = new unsigned int[length];
	this->instance = new unsigned int[this->length];
	for (unsigned int i = 0; i < this->length; i++)
		this->instance[i] = instance.instance[i];

}

void ATSPInstance::swapNeighbour(unsigned int x, unsigned int y) {
	unsigned int temp = neighbour[y];
	neighbour[y] = neighbour[x];
	neighbour[x] = temp;
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
	cout << instance[length - 1];
}

void ATSPInstance::showNeighbour() {
	for (unsigned int i = 0; i < length - 1; i++) {
		cout << neighbour[i] << "-";
	}
	cout << neighbour[length - 1] << endl;
}

void ATSPInstance::randomize() {
	srand(time(NULL));
	for (unsigned int i = 0; i < length - 1; i++) {
		unsigned int position = rand() % (length - i) + i;
		swap(i, position);
	}
}

void ATSPInstance::randomizeHeuristic() {
	srand(time(NULL));
	for (unsigned int i = 0; i < length; i++) {
		instance[i] = i;
	}
	unsigned int position = rand()%length;
	if (position != 0)
		swap(0,position);
}

unsigned int ATSPInstance::getElement(unsigned int position) {
	assert(position < length);
	return instance[position];
}

void ATSPInstance::reinitializeNeighbourhood(){
	swapX = 0;
	swapY = 1;
	firstNeighbour = true;

	for (unsigned int i = 0; i < length; i++)
		neighbour[i] = instance[i];
}

bool ATSPInstance::nextNeighbour(){
	if (!firstNeighbour){
		//Zmiana powrotna
		swapNeighbour(swapX,swapY);
		//Nowe indeksy do zamiany
		if (++swapY == length)
			swapY = ++swapX + 1;
	}

	if (swapX == length - 1)
		return false;

	firstNeighbour = false;
	swapNeighbour(swapX, swapY);

	return true;
}

bool ATSPInstance::nextNeighbourAnnealing(){
	if (!firstNeighbour){
		//Zmiana powrotna
		swapNeighbour(swapX,swapY);
		//Nowe indeksy do zamiany
		if (++swapY == length)
			swapY = ++swapX + 1;
	}

	if (checkedNeighbours == neighboursCount)
		return false;

	if (swapX == length - 1){
		swapX = 0;
		swapY = 1;
	}

	checkedNeighbours++;

	firstNeighbour = false;
	swapNeighbour(swapX, swapY);

	return true;
}

unsigned int *ATSPInstance::getInstanceArray(){
	return instance;
}

unsigned int *ATSPInstance::getCurrentNeighbour(){
	return neighbour;
}

void ATSPInstance::initialize(unsigned int * array){
	instance = array;
	reinitializeNeighbourhood();
}

void ATSPInstance::initializeAnnealing(unsigned int * array){
	for (unsigned int i = 0; i < length; i++)
			neighbour[i] = instance[i];

	instance = array;
	checkedNeighbours = 0;
}

void ATSPInstance::reinitializeWithCopy(unsigned int * array){
	for (unsigned int i = 0; i < length; i++)
		instance[i] = array[i];
	reinitializeNeighbourhood();
}

double ATSPInstance::compareWith(const ATSPInstance &instance){
	assert (this->length == instance.length);

	unsigned int same = 0;
	unsigned int currentBase;
	unsigned int currentSuccessor;

	for (unsigned int i = 0; i < length; i++){
		currentBase = this->instance[i];
		currentSuccessor = this->instance[(i+1)%length];

		unsigned int baseIndex = 0;
		for(;baseIndex < length; baseIndex++){
			if (instance.instance[baseIndex] == currentBase)
				break;
		}

		if (instance.instance[baseIndex] == currentBase && instance.instance[(baseIndex+1)%length] == currentSuccessor)
			same++;
	}

	double similarity = round((double) (same * 100) / length) / 100;

	return similarity;
}
