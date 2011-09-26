cmake_minimum_required (VERSION 2.6)
project (ATSP)

set(CMAKE_CXX_FLAGS "-Wall -g")

include_directories(.)
include_directories(/usr/include)

file(GLOB FILES *.cpp *.h )
add_executable(atsp ${FILES})
target_link_libraries(atsp boost_filesystem boost_regex)
