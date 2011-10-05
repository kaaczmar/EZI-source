/*
 * Parser.cpp
 *
 *  Created on: 2011-09-26
 *      Author: jawora
 */

#include <Parser.hpp>
#include <iostream>
#include <fstream>
#include <boost/tokenizer.hpp>

#include <boost/foreach.hpp>
#include <boost/algorithm/string.hpp>

#include <boost/algorithm/string/regex.hpp>
#include <boost/lexical_cast.hpp>

#include <list>

using namespace std;
using namespace boost;

Parser::Parser(string filename) :
	filePath(filename.c_str()) {

}

bool Parser::load(ATSPData &data) {
	boost::filesystem::fstream file(filePath);

	if (!file.is_open()) {
		cerr << "Bad file" << endl;
		return false;
	}

	string line;
	vector<string> tokens;

	while (file.good()) {
		getline(file, line);

		replace_all_regex(line, regex("[ \t\n]+"), string(" "));
		replace_all_regex(line, regex("[ \t\n]+$"), string(""));
		replace_all_regex(line, regex("^[ \t\n]+"), string(""));

		split(tokens, line, boost::is_any_of("\t\n "));

		if (tokens[0] == "DIMENSION:") {
			try {
				data.setDimension(lexical_cast<int> (tokens[1]));
			} catch (bad_lexical_cast &) {
				cerr << "Bad dimensions" << endl;
				return false;
			}
		}

		if (tokens[0] == "EDGE_WEIGHT_SECTION") {
			cout << "Reading wheights" << endl;
			break;
		}
	}

	int x = 0, y = 0;

	while (file.good()) {
		getline(file, line);

		replace_all_regex(line, regex("[ \t\n]+"), string(" "));
		replace_all_regex(line, regex("[ \t\n]+$"), string(""));
		replace_all_regex(line, regex("^[ \t\n]+"), string(""));

		split(tokens, line, boost::is_any_of("\t\n "));

		if (tokens[0] == "EOF") {
			cout << "End" << endl;
			break;
		}

		BOOST_FOREACH(string tok, tokens)
		{
			if (tok.length() == 0)
				continue;

			try {
				data.data[x++][y] = lexical_cast<int>(tok);
				if (x >= data.getDimension()){
					x = 0;
					y++;
				}

			} catch(bad_lexical_cast &) {
				cerr << "Bad value: " << tok << "[" << tok.length() << "]" << endl;
			}
		}
	}

//	data.print();
	return true;
}
