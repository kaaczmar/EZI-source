/*
 * ATSPAlgorithmTabuSearch.cpp
 *
 *  Created on: 11-11-2011
 *      Author: jawora
 */

#include <ATSPAlgorithmTabuSearch.hpp>
#include <iostream>

using namespace std;
using namespace boost;

void TabuPair::print() {
	cout << "[" << a << ";" << b << "]\t" << objectiveFunctionChange << endl;
}

ATSPAlgorithmTabuSearch::ATSPAlgorithmTabuSearch(ATSPData *data,
		ATSPInstance *instance, double timeout) :
		ATSPAlgorithm(data, instance, timeout) {
	neighbourhoodSize = instance->getLength() * (instance->getLength() - 1) / 2;
	tabuListLength = instance->getLength() * 3;
	candidatesNumber = instance->getLength() * 3;

	tabuArray.resize(extents[instance->getLength()][instance->getLength()]);

}

bool ATSPAlgorithmTabuSearch::isOnList(const TabuPair &pair,
		const list<TabuPair> &list) {
	for (std::list<TabuPair>::const_iterator it = list.begin();
			it != list.end(); it++) {
		if ((it->a == pair.a && it->b == pair.b)
				|| (it->a == pair.b && it->b == pair.a))
			return true;
	}
	return false;
}

unsigned int ATSPAlgorithmTabuSearch::getTabu(const TabuPair &pair){
	if (pair.a < pair.b)
		return tabuArray[pair.a][pair.b];
	else
		return tabuArray[pair.b][pair.a];
}

void ATSPAlgorithmTabuSearch::addToTabu(const TabuPair &pair){
	if (pair.a < pair.b)
		tabuArray[pair.a][pair.b] = steps;
	else
		tabuArray[pair.b][pair.a] = steps;
}

bool ATSPAlgorithmTabuSearch::isOnTabuList(const TabuPair &pair) {
	if (getTabu(pair) != 0 && getTabu(pair) < steps - tabuListLength)
		return true;
	else
		return false;
}

void ATSPAlgorithmTabuSearch::generateCandidates(std::list<TabuPair> &candidates) {
	candidates.clear();

	int a, b;
	unsigned int currentDistance = calculateObjectiveFunction(
			instance->getInstanceArray(), instance->getLength());

	for (unsigned i = 0; i < candidatesNumber; i++) {
		do {
			a = rand() % instance->getLength();
			b = rand() % instance->getLength();
		} while (a == b || isOnList(TabuPair(a, b), candidates));

		instance->swap(a, b);
		unsigned int newDistance = calculateObjectiveFunction(
				instance->getInstanceArray(), instance->getLength());

		TabuPair pair(a, b, (newDistance - currentDistance));
		pair.isTabu = isOnTabuList(pair);

		candidates.push_back(pair);

		instance->swap(a,b);
	}

	candidates.sort();

}

void ATSPAlgorithmTabuSearch::optimize(bool showResult) {
	list<TabuPair> candidates;

	bestSequenceValue = calculateObjectiveFunction(instance->getInstanceArray(),
			instance->getLength());
	bestSequence = ATSPInstance(*instance);

	unsigned int currentSequenceValue = bestSequenceValue;

	TabuPair pairToChange;
	TabuPair leastTabuCandidate;
	unsigned int leastTabuValue = steps;

	steps = 1;
	unsigned long lastStepImprove = 1;
	while (steps - lastStepImprove < 2 * instance->getLength()) {
		generateCandidates(candidates);

		leastTabuValue = steps;

		list<TabuPair>::iterator it = candidates.begin();
		for (; it != candidates.end(); it++) {
			//Nie ważne czy tabu czy nie -> jak jest najlepsze to bierzemy
			if (currentSequenceValue + it->objectiveFunctionChange
					< bestSequenceValue) {
				pairToChange = *it;
				break;
			}
			//Jezeli nie jest tabu to jest to najlepszy ruch nie-tabu -> bierzemy
			else if (!it->isTabu) {
				pairToChange = *it;
				break;
			}

			if (getTabu(*it) < leastTabuValue)
				leastTabuCandidate = *it;
		}
		//Nie znalazl zadnego ruchu -> bierzemy ruch najmniej tabu
		if (it == candidates.end()) {
			pairToChange = leastTabuCandidate;
		}
		//Wykonaj zamiane
		instance->swap(pairToChange.a, pairToChange.b);

		//Przelicz aktualna funkcje celu
		currentSequenceValue = calculateObjectiveFunction(
				instance->getInstanceArray(), instance->getLength());

		//Jesli jest najlepsza wartosc podmien najlepsza instancje
		if (currentSequenceValue < bestSequenceValue) {
			bestSequence = ATSPInstance(*instance);
			bestSequenceValue = currentSequenceValue;
			lastStepImprove = steps;
		}

		//Dodaj ruch do tabu
		addToTabu(pairToChange);

//		pairToChange.print();
//		cout << currentSequenceValue << endl;

		steps++;
	}

	if (showResult) {
		bestSequence.show();
		cout << "\t" << bestSequenceValue << "\t" << steps << "\t";
	}
}

