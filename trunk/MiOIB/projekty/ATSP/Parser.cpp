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

#include <list>

using namespace std;

Parser::Parser(string filename) : filePath(filename.c_str()) {

}

void Parser::load(){
	boost::filesystem::fstream file(filePath);

	if (!file.is_open()){
		cerr << "Bad file" << endl;
		return;
	}

	string line;
	ATSPData data;
	vector<string> tokens;

	while (file.good()){
		getline(file,line);

		boost::split(tokens, line, boost::is_any_of("\t "));

		BOOST_FOREACH(string token, tokens)
		{
			std::cout << token << '\n';  ;
		}

	}
}
