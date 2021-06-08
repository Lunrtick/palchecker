#include <iostream>
#include <thread>         // std::this_thread::sleep_for
#include <chrono>         // std::chrono::seconds
#include "EpistemicModelChecker.h"
#include "fmt/core.h"
int main(int argc, char* argv[]) {
    if (argc != 2) {
        fmt::print("Expected exactly 1 argument: the number of children");
        return 1;
    }
    auto res = EMC::makeMuddyChildren(std::stoi(argv[1]));
    auto m = EMC::generateModel(std::get<0>(res),std::get<1>(res), std::get<2>(res));
    EMC::solveModel(m, std::get<3>(res));
    std::cout << m.worlds.size() << std::endl;
    return 0;
}
