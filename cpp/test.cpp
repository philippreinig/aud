#include <iostream>
#include <vector>
#include <algorithm>

struct Item{
    int value;
    int weight;

    Item() = default;

    Item(int value_, int weight_) : value{value_}, weight{weight_} {}

    friend std::ostream& operator<<(std::ostream& os, const Item& item){
        os << "[" << item.value << ", " << item.weight << "]";
        return os;
    }
};

using ITEMS = std::vector<Item>;

struct PerfectLoot{
    int value;
    ITEMS items;
    PerfectLoot(int value_, ITEMS items_) : value{value_}, items{items_} {};
};

int counter = 0;

PerfectLoot max_profit(int i, int cap, ITEMS& loot){
    ++counter;
    if (i == 0){
        return PerfectLoot{0, ITEMS{}};
        }
    else{
        if (loot[i].weight > cap) return max_profit(i-1, cap, loot);
        else{
            PerfectLoot leave = max_profit(i-1, cap, loot);
            PerfectLoot take =  max_profit(i-1, cap-loot[i].weight, loot);
            take.value = take.value + loot[i].value;
            if (take.value > leave.value){
                take.items.push_back(loot[i]);
                return take;
            }
            else return leave;
        }
    }
}

int main(){
    ITEMS loot = {Item{1,6}, Item{5,4}, Item{4,3}, Item{3,2}, Item{6,4}, Item{2,3}, Item{1,5}, Item{7, 3}};

    int capacity = 10;

    PerfectLoot optimal_loot = max_profit(loot.size(), capacity, loot);

    std::cout << optimal_loot.value << std::endl;

    std::cout << "looted following items: " << std::endl;
    for(size_t i = 0; i < optimal_loot.items.size(); ++i){
        std::cout << optimal_loot.items[i] << std::endl;
    }

    std::cout << "decisions to determine perfect loot: " << counter << std::endl;
}
