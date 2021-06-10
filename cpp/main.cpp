#include <iostream>
#include <thread>         // std::this_thread::sleep_for
#include <chrono>         // std::chrono::seconds
#include "EpistemicModelChecker.h"
#include "fmt/core.h"
int main(int argc, char* argv[]) {
    if (argc != 3) {
        fmt::print("Expected exactly 2 arguments: the number of children and whether or not to stop after generating the model");
        return 1;
    }
    auto res = EMC::makeMuddyChildren(std::stoi(argv[1]));
    auto m = EMC::generateModel(std::get<0>(res),std::get<1>(res), std::get<2>(res));
    if (std::stoi(argv[2]) != 0) {
        return 0;
    } 
    EMC::solveModel(m, std::get<3>(res));
    std::cout << m.worlds.size() << std::endl;
    return 0;
}
