Red-Black-Tree
---

1)
Ein Rot-Schwarz Baum ist ein binärer Suchbaum, bei dem jedem Knoten entweder das Farbattribut Rot oder
Schwarz zugewiesen wird. Somit ist eine Unterscheidung zwischen ansonsten gleichen (Teil-)Bäumen möglich.
Eigenschaften:
    1. Jeder Knoten ist entweder schwarz oder rot
    2. Die Wurzel ist schwarz
    3. Alle Blätter sind schwarz
    4. Beide Kinder eines roten Knotens sind schwarz
    5. Alle Pfade von einem Knoten zu dessen Blättern enthalten die gleiche Anzahl schwarzer Knoten

2)
6,7,3,4,2,1
6 einfügen:
    6S
7 einfügen:
    6S
      \
        7R
3 einfügen:
    6S
   /  \
 3R    7R
4 einfügen:
    6S
   /  \
 3R    7R
   \
    4R
    ->Rot-Rot-Verletzung -> Onkel ist rot -> Umfärben
    6R
   /  \
 3S    7S
   \
    4R
    -> Umfärben
    6S
   /  \
 3S    7S
   \
    4R
2 einfügen:
      6S
     /  \
   3S    7S
  / \
2R   4R
1 einfügen:
         6S
        /  \
      3S     7S
     /  \
   2R    4R
  /  
1R
    ->Rot-Rot-Verletzung -> Onkel ist rot -> Umfärben
        6S
       /  \
     3R     7S
    /  \
  2S    4S
 /  
1R       

    

    

    