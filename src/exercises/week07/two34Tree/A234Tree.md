1)

Ein 234-Baum ist kein Binärbaum, sondern erlaubt bis zu 4 Kindknoten. Außerdem enthält ein Knoten eines 234-Baums nicht
einen einzigartigen Schlüssel, sondern kann zwischen 1 und 3 Schlüsseln enthalten. 1 Schlüssel -> 2 Kindknoten 2
Schlüssel -> 3 Kindknoten 3 Schlüssel -> 4 Kindknoten

2)

3, 7, 5, 15, 17, 9, 13, 21, 11, 19

# Bottom up

3 einfügen 3

7 einfügen 3,7

5 einfügen 3,5,7

15 einfügen 5 / \
3 7,15

17 einfügen 5 / \
3 7,15,17

9 einfügen 5,15 / |   \
3 7,9 17

13 einfügen 5,15 / |   \
3 7,9,13 17

21 einfügen 5,15 / |  \
3 7,9,13 17,21

11 einfügen 5,9,15 / / \    \
3 7 11,13 17,21

19 einfügen 5,9,15 / / \     \
3 7 11,13 17,19,21

# Top down

3 einfügen 3

7 einfügen 3,7

5 einfügen 3,5,7

15 einfügen 5 / \
3 7,15

17 einfügen 5 / \
3 7,15,17

9 einfügen 5,15 / |  \
3 7,9 17

13 einfügen 5,15 / |   \
3 7,9,13 17

21 einfügen 5,15 / |  \
3 7,9,13 17,21

11 einfügen 5,9,15 / / \   \
3 7 11,13 17,21

19 einfügen 9 /   \
5 15 / \ /  \
3 7 11,13 17,19,21

3,5,7,9,11,13,15,17,19,21

# Bottom up

3 einfügen 3 5 einfügen 3,5 7 einfügen 3,5,7 9 einfügen 5 / \
3 7,9 11 einfügen 5 / \
3 7,9,11 13 einfügen 5,9 / | \
3 7 11,13 15 einfügen 5,9 / | \
3 7 11,13,15 17 einfügen 5,9, 13 / / \  \
3 7 11 15,17 19 einfügen 5,9, 13 / / \  \
3 7 11 15,17,19 21 einfügen 9  
/ \  
5 13,17 / \ / | \
3 7 11 15 19,21

# Top down

3 einfügen 3 5 einfügen 3,5 7 einfügen 3,5,7 9 einfügen 5 / \
3 7,9 11 einfügen 5 / \
3 7,9,11 13 einfügen 5,9 / | \
3 7 11,13 15 einfügen 5,9 / | \
3 7 11,13,15 17 einfügen 5,9, 13 / / \  \
3 7 11 15,17 19 einfügen 5,9, 13 / / \  \
3 7 11 15,17,19 21 einfügen 9  
/ \  
5 13,17 / \ / | \
3 7 11 15 19,21

-> Bei sortierter Reihenfolge ist bottom-up und top-down identisch
    


        



