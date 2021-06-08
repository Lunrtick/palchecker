cmake_minimum_required(VERSION 3.19)
project(maybethefastest)

if(NOT CMAKE_BUILD_TYPE)
    set(CMAKE_BUILD_TYPE Release)
endif()
set(CMAKE_CXX_FLAGS_DEBUG "-g -O2")
set(CMAKE_CXX_FLAGS_RELEASE "-O3")

set(CMAKE_CXX_FLAGS "-Wall -Wextra")

set(CMAKE_CXX_STANDARD 20)

find_package(OpenMP REQUIRED)
add_executable(maybethefastest main.cpp EpistemicModelChecker.h EpistemicModelChecker.cpp)


include(cmake/CPM.cmake)
CPMAddPackage("gh:fmtlib/fmt#7.1.3")

target_link_libraries(maybethefastest PRIVATE OpenMP::OpenMP_CXX fmt)