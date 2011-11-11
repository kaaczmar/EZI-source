/*
 * ATSPAlgorithmTabuSearch.cpp
 *
 *  Created on: 11-11-2011
 *      Author: jawora
 */

#include <ATSPAlgorithmTabuSearch.hpp>
#include <iostream>

using namespace std;

void TabuPair::print() {
	cout << "[" << a << ";" << b << "]\t" << objectiveFunctionChange << endl;
}

ATSPAlgorithmTabuSearch::ATSPAlgorithmTabuSearch(ATSPData *data,
		ATSPInstance *instance, double timeout) :
		ATSPAlgorithm(data, instance, timeout) {
	neighbourhoodSize = instance->getLength() * (instance->getLength() - 1) / 2;
	tabuListLength = neighbourhoodSize / 4;
	candidatesNumber = neighbourhoodSize / 4;

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

bool ATSPAlgorithmTabuSearch::isOnTabuList(TabuPair &pair) {
	for (std::list<TabuPair>::iterator it = tabuList.begin();
			it != tabuList.end(); it++) {
		if ((it->a == pair.a && it->b == pair.b)
				|| (it->a == pair.b && it->b == pair.a)) {
			pair.tabuPosition = it;
			return true;
		}
	}
	return false;
}

std::list<TabuPair> ATSPAlgorithmTabuSearch::generateCandidates() {
	list<TabuPair> candidates;

	int a, b;
	unsigned int currentDistance = calculateObjectiveFunction(
			instance->getInstanceArray(), instance->getLength());

	for (unsigned i = 0; i < candidatesNumber; i++) {
		ATSPInstance tmpInstance(*instance);
		do {
			a = rand() % instance->getLength();
			b = rand() % instance->getLength();
		} while (a == b || isOnList(TabuPair(a, b), candidates));

		tmpInstance.swap(a, b);
		unsigned int newDistance = calculateObjectiveFunction(
				tmpInstance.getInstanceArray(), tmpInstance.getLength());

		TabuPair pair(a, b, (newDistance - currentDistance));
		pair.isTabu = isOnTabuList(pair);

		candidates.push_back(pair);
	}

	candidates.sort();

	return candidates;
}

void ATSPAlgorithmTabuSearch::optimize(bool showResult) {
	list<TabuPair> candidates;

	bestSequenceValue = calculateObjectiveFunction(instance->getInstanceArray(),
			instance->getLength());
	unsigned int currentSequenceValue = bestSequenceValue;

	unsigned int steps = 0;
	while (steps < tabuListLength/2) {
		candidates = generateCandidates();

		TabuPair pairToChange;

		list<TabuPair>::iterator it = candidates.begin();
		for (; it != candidates.end(); it++) {
			//Nie ważne czy tabu czy nie -> jak jest najlepsze to bierzemy
			if (currentSequenceValue + it->objectiveFunctionChange
					< bestSequenceValue) {
				pairToChange = *it;
				if (it->isTabu) {
					tabuList.erase(it->tabuPosition);
				}
				break;
			}
			//Jezeli nie jest tabu to jest to najlepszy ruch nie-tabu -> bierzemy
			else if (!it->isTabu) {
				pairToChange = *it;
				break;
			}
		}
		//Nie znalazl zadnego ruchu -> bierzemy ruch najmniej tabu
		if (it == candidates.end()) {
			pairToChange = tabuList.back();
			tabuList.pop_back();
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
//				cout << "Change" << endl;
		}

		//Dodaj ruch do tabu
		if (tabuList.size() == tabuListLength) {
			tabuList.pop_back();
		}
		pairToChange.isTabu = true;
		tabuList.push_front(pairToChange);

//		pairToChange.print();
//		cout << currentSequenceValue << endl;

		steps++;
	}

	if (showResult) {
		bestSequence.show();
		cout << "\t" << bestSequenceValue << "\t";
	}
}

