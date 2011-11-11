/*
 * ATSPAlgorithmTabuSearch.hpp
 *
 *  Created on: 11-11-2011
 *      Author: jawora
 */

#ifndef ATSPALGORITHMTABUSEARCH_HPP_
#define ATSPALGORITHMTABUSEARCH_HPP_

#include <ATSPAlgorithm.hpp>
#include <list>

class TabuPair {
public:
	int a;
	int b;
	int objectiveFunctionChange;
	bool isTabu;

	std::list<TabuPair>::iterator tabuPosition;


	TabuPair(int a = 0, int b = 0, int objectiveFunctionChange = 0, bool isTabu = false) :
			a(a),
			b(b),
			objectiveFunctionChange(objectiveFunctionChange),
			isTabu(isTabu) {
	}

	bool operator< (TabuPair const &p) const { return objectiveFunctionChange < p.objectiveFunctionChange; };
	void print();
};

class ATSPAlgorithmTabuSearch: public ATSPAlgorithm {
private:
	unsigned int neighbourhoodSize;

	std::list<TabuPair> tabuList;
	unsigned int tabuListLength;
	unsigned int candidatesNumber;

	bool isOnList(const TabuPair &pair, const std::list<TabuPair> &list);
	bool isOnTabuList(TabuPair &pair);
	std::list<TabuPair> generateCandidates();

public:
	ATSPAlgorithmTabuSearch(ATSPData *data, ATSPInstance *instance,
			double timeout = 0);
	void optimize(bool showResult = true);
};

#endif /* ATSPALGORITHMTABUSEARCH_HPP_ */
