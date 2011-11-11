/*
 * ATSPAlgorithmTabuSearch.cpp
 *
 *  Created on: 11-11-2011
 *      Author: jawora
 */

#include <ATSPAlgorithmTabuSearch.hpp>
#include <iostream>

using namespace std;

void TabuPair::print(){
	cout << "[" << a << ";" << b << "]\t" << objectiveFunctionChange << endl;
}

ATSPAlgorithmTabuSearch::ATSPAlgorithmTabuSearch(ATSPData *data,
		ATSPInstance *instance, double timeout) :
		ATSPAlgorithm(data, instance, timeout) {
	neighbourhoodSize = instance->getLength() * (instance->getLength() - 1) / 2;
	tabuListLength = neighbourhoodSize / 4;
	candidatesNumber = neighbourhoodSize / 4;

}

bool ATSPAlgorithmTabuSearch::isOnTabuList(const TabuPair &pair, const std::list<TabuPair> &list){
	for (std::list<TabuPair>::const_iterator it = list.begin(); it != list.end(); it++){
		if ((it->a == pair.a && it->b == pair.b) || (it->a == pair.b && it->b == pair.a))
			return true;
	}
	return false;
}

std::list<TabuPair> ATSPAlgorithmTabuSearch::generateCandidates(){
	list<TabuPair> candidates;

	int a, b;
	unsigned int currentDistance = calculateObjectiveFunction(instance->getInstanceArray(), instance->getLength());

	for (unsigned i = 0; i < candidatesNumber; i++){
		ATSPInstance tmpInstance(*instance);
		do{
			a = rand()%instance->getLength();
			b = rand()%instance->getLength();
		} while ((a == b) || (isOnTabuList(TabuPair(a,b),candidates)));

		tmpInstance.swap(a,b);
		unsigned int newDistance = calculateObjectiveFunction(tmpInstance.getInstanceArray(), tmpInstance.getLength());

		TabuPair pair(a,b, (newDistance - currentDistance));
		candidates.push_back(pair);
	}

	candidates.sort();

	for (list<TabuPair>::iterator it = candidates.begin(); it != candidates.end(); it++){
		it->print();
	}

	return candidates;
}

void ATSPAlgorithmTabuSearch::optimize(bool showResult) {
	unsigned int distance = 0;

	bestSequenceValue = calculateObjectiveFunction(instance->getInstanceArray(),
			instance->getLength());

	if (showResult){
		cout << "\n";
		generateCandidates();
	}

//	int steps = 0;
//	while (steps < 10) {
//		//TODO calosc
//		steps++;
//	}
	bestSequence = ATSPInstance(*instance);

	if (showResult) {
		bestSequence.show();
		cout << "\t" << bestSequenceValue << "\t";
	}
}

