/*
 * Parser.h
 *
 *  Created on: 2011-09-26
 *      Author: jawora
 */

#ifndef PARSER_H_
#define PARSER_H_

#include <string>
#include <boost/filesystem/fstream.hpp>
#include <boost/filesystem.hpp>
#include <ATSPData.hpp>

class Parser{
private:
	boost::filesystem::path filePath;
public:
	Parser(std::string filename);
	void load();
};

#endif /* PARSER_H_ */
