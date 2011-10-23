/*
 * mainfile.cpp
 *
 *  Created on: 2011-09-26
 *      Author: jawora
 */

#include <mainfile.hpp>


using namespace std;
using namespace boost;

int main(int argc, char **argv) {
	string filename;
	string algorithmName("");
	ATSPData data;
	ATSPInstance instance;
	ATSPAlgorithm *algorithm = NULL;
	Parser parser;
	double timeout = -1;

	/***ARGUMENTS***/
	{
		const char *optString = "f:a:t:";
		const struct option longOpts[] = {
			{ "file", 1, NULL, 'f' },
			{ "algorithm", 1, NULL, 'a' },
			{ "timeout", 1, NULL, 't' },
			{ NULL, 0, NULL, 0 }
		};

		int longIndex, opt;

		while((opt = getopt_long( argc, argv, optString, longOpts, &longIndex )) != -1 ) {
			switch( opt ) {
				case 'f':
					filename = optarg;
//					cout << "File: " << filename << endl;
					parser = Parser(filename);
					if (!parser.load(data)){
						cerr << "File load error - aborting execution" << endl;
						return 2;
					}
					instance = ATSPInstance(data.getDimension());
					break;
				case 'a':
					algorithmName = optarg;
					break;
				case 't':
					try {
						timeout = lexical_cast<double> (optarg);
					} catch (bad_lexical_cast &) {
						cerr << "Bad timeout" << endl;
						return false;
					}
					if (timeout < 0){
						cerr << "Timeout must be positive number" << endl;
						return false;
					}
					break;
			}
		}

		if (filename.length() == 0){
			cerr << "Must specify filename with -f" << endl;
			return 1;
		}

		if (algorithmName.length() == 0){
			cerr << "Must specify algorithm with -a" << endl;
			return 1;
		}

//		cout << filename << "\t";

		if (algorithmName.compare("random") == 0) {
			if (timeout == -1){
				cerr << "Timeout must be specified using -t when using random algorithm" << endl;
				return 1;
			}
			algorithm = new ATSPAlgorithmRandom(&data, &instance, timeout);
			instance.randomize();
//			cout << "R\t";
		} else if (algorithmName.compare("heuristic") == 0) {
			algorithm = new ATSPAlgorithmGreedy(&data, &instance);
			instance.randomizeHeuristic();
//			cout << "H\t";
		} else if (algorithmName.compare("greedy") == 0) {
			algorithm = new ATSPAlgorithmLSGreedy(&data, &instance);
			instance.randomize();
//			cout << "G\t";
		} else if (algorithmName.compare("steepest") == 0){
			algorithm = new ATSPAlgorithmLSSteepest(&data, &instance);
			instance.randomize();
//			cout << "S\t";
		} else {
			cerr << "Unknown algorithm: " << algorithmName << endl;
			return 1;
		}
	}
	/***END ARGUMENTS***/

	Executor executor(0.5);
	executor.setAlgorithm(algorithm);

//	for (unsigned int i = 0; i < 200; i++){
//		instance.randomize();
	ATSPInstance baseInstance;
	ATSPAlgorithm *alg1 = new ATSPAlgorithmGreedy(&data,&instance);
	ATSPAlgorithm *alg2 = new ATSPAlgorithmLSSteepest(&data,&instance);
	ATSPAlgorithm *alg3 = new ATSPAlgorithmLSGreedy(&data,&instance);

	for (int i = 0; i < 10; i++){
		instance.randomizeHeuristic();
		instance.reinitializeNeighbourhood();

//		cout << filename << "\tG\t";
//		instance.show();
//		cout << filename << "\tH\t";
//		cout << "\t" << algorithm->calculateObjectiveFunction(instance.getInstanceArray(), instance.getLength()) << "\t";
		alg1->optimize(false);
		double resultQuality = (double) (alg1->getBestSequenceValue()) / (algorithm->getData()->getOptimalSolution()) * 100;
		resultQuality = round(resultQuality*100) / 100.0;
		cout << resultQuality<< " & ";

		baseInstance = ATSPInstance(instance);
		instance.reinitializeNeighbourhood();
//		cout << filename << "\tS\t";
//		instance.show();
//		cout << "\t" << algorithm->calculateObjectiveFunction(instance.getInstanceArray(), instance.getLength()) << "\t";
//		cout << filename << "\tS\t";
//		cout << "\t" << algorithm->calculateObjectiveFunction(instance.getInstanceArray(), instance.getLength()) << "\t";
		alg2->optimize(false);
		resultQuality = (double) (alg2->getBestSequenceValue()) / (algorithm->getData()->getOptimalSolution()) * 100;
		resultQuality = round(resultQuality*100) / 100.0;
		cout << resultQuality<< " & ";


		instance = ATSPInstance(baseInstance);

		instance.reinitializeNeighbourhood();

//		cout << filename << "\tS\t";
//		instance.show();
//		cout << "\t" << algorithm->calculateObjectiveFunction(instance.getInstanceArray(), instance.getLength()) << "\t";
//		cout << filename << "\tG\t";
//		cout << "\t" << algorithm->calculateObjectiveFunction(instance.getInstanceArray(), instance.getLength()) << "\t";
		alg3->optimize(false);
		resultQuality = (double) (alg3->getBestSequenceValue()) / (algorithm->getData()->getOptimalSolution()) * 100;
		resultQuality = round(resultQuality*100) / 100.0;
		cout << resultQuality<< endl;

		sleep(1);
	}

//		cout << "\t\t";
//	}

//	algorithm->optimize(true);

}
