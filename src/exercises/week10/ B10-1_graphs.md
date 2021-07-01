1. Ein Graph ist eine Datenstruktur aus Knoten und Kanten, wobei Knoten Daten enthalten und Kanten Knoten miteinander
   verbinden. Es werden gerichtete und ungerichtete Graphen unterschieden. Gerichtete Graphen können entlang ihrer Kante
   nur in eine Richtung durchlaufen werden, ungerichteten Graphen in beide Richtungen. Außerdem gibt es gewichtete
   Graphen, diese sind gerichtete Graphen, deren Kanten eine Gewichtsangabe besitzen.
2.

https://bit.ly/3h1LWoq

3.

Kantenliste:
[5,8, 1,2,1,3,1,4,1,5,2,4,2,5,3,4,4,5]
In Java: int[]
Knotenliste:
[5,8, 4,2,3,4,5, 2,4,5, 1,4, 1,5, 0]
In Java: int[]
Adjazenzmatrix:
1 2 3 4 5 1 [0,1,1,1,1]
2 [0,0,0,1,1]
3 [0,0,0,1,0]
4 [0,0,0,0,1]
5 [0,0,0,0,0]
In Java: int[][]
Adjazenzliste:
1 -> 2,3,4,5 2 -> 4,5 3 -> 4 4 -> 5 5 ->
In Java: Map<Integer, ArrayList<Integer>>

4.

@GraphDataStructureConversion.java edge list -> adjacency matrix: O(n)
edge list -> adjacency matrix: O(n)

