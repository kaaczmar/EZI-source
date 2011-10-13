/*
 * mainfile.cpp
 *
 *  Created on: 2011-09-26
 *      Author: jawora
 */

#include <mainfile.hpp>


using namespace std;

int main(int argc, char **argv) {
	string filename;

	/***ARGUMENTS***/
	{
		const char *optString = "f:";
		const struct option longOpts[] = {
			{ "file", 1, NULL, 'f' },
			{ NULL, 0, NULL, 0 }
		};

		int longIndex, opt;

		while((opt = getopt_long( argc, argv, optString, longOpts, &longIndex )) != -1 ) {
			switch( opt ) {
				case 'f':
					filename = optarg;
					cout << "File: " << filename << endl;
					break;
			}
		}

		if (filename.length() == 0){
			cerr << "Must specify filename with -f" << endl;
			return 1;
		}
	}
	/***END ARGUMENTS***/

	ATSPData data;

	Parser parser(filename);
	if (!parser.load(data)){
		cerr << "File load error - aborting execution" << endl;
	}

	ATSPInstance instance(5);
	instance.reinitializeNeighbourhood();
	instance.show();
	while (instance.nextNeighbour()){

	}

//	ATSPInstance instance(data.getDimension());
//
//	ATSPAlgorithm *algorithm = new ATSPAlgorithmRandom(&data, &instance, 10);
//	ATSPAlgorithm *algorithm = new ATSPAlgorithmGreedy(&data, &instance, 10);
//	algorithm->optimize();
}
