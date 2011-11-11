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

		cout << filename << "\t";

		if (algorithmName.compare("random") == 0) {
			if (timeout == -1){
				cerr << "Timeout must be specified using -t when using random algorithm" << endl;
				return 1;
			}
			algorithm = new ATSPAlgorithmRandom(&data, &instance, timeout);
			instance.randomize();
			cout << "R\t";
		} else if (algorithmName.compare("heuristic") == 0) {
			algorithm = new ATSPAlgorithmGreedy(&data, &instance);
			instance.randomizeHeuristic();
			cout << "H\t";
		} else if (algorithmName.compare("greedy") == 0) {
			algorithm = new ATSPAlgorithmLSGreedy(&data, &instance);
			instance.randomize();
			cout << "G\t";
		} else if (algorithmName.compare("steepest") == 0){
			algorithm = new ATSPAlgorithmLSSteepest(&data, &instance);
			instance.randomize();
			cout << "S\t";
		} else if (algorithmName.compare("annealing") == 0){
			algorithm = new ATSPAlgorithmSimulatedAnnealing(&data, &instance);
			instance.randomize();
			cout << "A\t";
		} else if (algorithmName.compare("tabu") == 0){
			algorithm = new ATSPAlgorithmTabuSearch(&data, &instance);
			instance.randomize();
			cout << "T\t";
		} else {
			cerr << "Unknown algorithm: " << algorithmName << endl;
			return 1;
		}
	}
	/***END ARGUMENTS***/

	Executor executor(0.5);
	executor.setAlgorithm(algorithm);

	instance.reinitializeNeighbourhood();
	instance.show();
	cout << "\t" << algorithm->calculateObjectiveFunction(instance.getInstanceArray(), instance.getLength()) << "\t";
	executor.execute();
	executor.print();
	sleep(1);

}
