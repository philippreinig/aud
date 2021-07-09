#include <vector>
#include <string>
#include <iostream>



struct Item{
    int value;
    int weight;

    Item() = default;

    Item(int value_, int weight_) : value{value_}, weight{weight_} {}
};

using V_ITEM = std::vector<Item>;

struct OptimalKnapsack{
    int value;
    V_ITEM items;
    OptimalKnapsack(int value_, V_ITEM items_) : value{value_}, items{items_} {}
};

int calc_knapsack_value(V_ITEM& knapsack){
    int value = 0;
    for(int i = 0; i < knapsack.size(); ++i){
        value += knapsack[i].value;
    }
    return value;
}

int max_profit(int i, int cap_left, V_ITEM& loot, V_ITEM& knapsack){
    if (cap_left == 0) return 0;
    else if (loot[i].weight > cap_left) return calc_knapsack_value(knapsack);
    else{
        int max_profit_take_item = max_profit(i-1, cap_left-loot[i].weight, loot, knapsack);
        int max_profit_leave_item = max_profit(i-1, cap_left, loot, knapsack);
        if (max_profit_take_item > max_profit_leave_item){
            knapsack.push_back(loot[i]);
            return max_profit_take_item;
        }else{
            return max_profit_leave_item;
        }
    }
}

OptimalKnapsack optimal_loot(V_ITEM& loot, int cap){
    V_ITEM knapsack(loot.size());
    int max_value = max_profit(loot.size()-1, cap, loot, knapsack);
    return OptimalKnapsack(max_value, knapsack);
}



int main(){
    int cap = 10;

    V_ITEM v{Item{1,3}, Item{2,7}, Item{5,4}, Item{3,1}, Item{2, 4}, Item{5,8}, Item{2,2}};
    OptimalKnapsack loot = optimal_loot(v, cap);
    std::cout << loot.value << ": " << std::endl;
    return 0;
}