/*
 * ATSP.cpp
 *
 *  Created on: 2011-09-26
 *      Author: jawora
 */

#include <iostream>
#include <getopt.h>
#include <string>

#include <ATSP.hpp>
#include <ATSPInstance.hpp>

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

	ATSPInstance instance(10);
	instance.show();
	instance.randomize();
	instance.show();

}
