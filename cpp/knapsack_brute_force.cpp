#include <vector>
#include <string>
#include <iostream>
#include <ostream>



struct Item{
    int value;
    int weight;

    Item() = default;

    Item(int value_, int weight_) : value{value_}, weight{weight_} {}

    friend std::ostream& operator<<(std::ostream& os, const Item& item){
        os << "[" << item.value << ", " << item.weight << "]";
        return os;
    }

    bool operator==(const Item& other){
        return this->value == other.value && this->weight == other.weight;
    }
};

using V_ITEM = std::vector<Item>;

struct OptimalKnapsack{
    int value;
    V_ITEM items;
    OptimalKnapsack(int value_, V_ITEM items_) : value{value_}, items{items_} {}
};

bool knapsack_contains_item(V_ITEM& knapsack, Item& item){
    for(size_t i = 0; i < knapsack.size(); ++i){
        if (knapsack[i] == item) return true;
    }
    return false;
}

void print_knapsack(V_ITEM& knapsack){
    for (V_ITEM::iterator i = knapsack.begin(); i != knapsack.end(); ++i)
    std::cout << *i << ' ';

}

int max_profit(int i, int cap_left, V_ITEM& loot, V_ITEM& knapsack){
    // std::cout << i << ", " << cap_left << ", " << loot_worth << ", ";
    // print_knapsack(knapsack);
    // std::cout << std::endl;
    if (i == 0) return 0;
    if (loot[i].weight > cap_left) return max_profit(i-1, cap_left, loot, knapsack);
    else{
        int max_profit_take_item = loot[i].value + max_profit(i-1, cap_left-loot[i].weight, loot, knapsack);
        int max_profit_leave_item = max_profit(i-1, cap_left, loot, knapsack);
        std::cout << "i:    " << i << ",    cap_left   " << cap_left << "   , take    " << max_profit_take_item << "    , leave   " << max_profit_leave_item << std::endl;
        if (max_profit_take_item > max_profit_leave_item){
            if (!knapsack_contains_item(knapsack, loot[i]))
            knapsack.push_back(loot[i]);
            return max_profit_take_item;
        }else{
            return max_profit_leave_item;
        }
    }
}

OptimalKnapsack optimal_loot(V_ITEM& loot, int cap){
    std::cout << "LOOT: ";
    print_knapsack(loot);
    std::cout << std::endl;
    V_ITEM knapsack;
    int max_value = max_profit(loot.size()-1, cap, loot, knapsack);
    return OptimalKnapsack(max_value, knapsack);
}



int main(){
    int cap = 10;

    V_ITEM v{Item{1,3}, Item{2,7}, Item{5,4}, Item{3,1}, Item{2, 4}, Item{5,8}, Item{2,2}};
    OptimalKnapsack loot = optimal_loot(v, cap);
    std::cout << loot.value << ": ";
    for(size_t i = 0; i < loot.items.size(); ++i){
        std::cout << "|" << loot.items[i];
    }
    return 0;
}