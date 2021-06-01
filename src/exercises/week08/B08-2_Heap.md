1.
Heap-Eigenschaft: Ein Heap ist ein Binärbaum, bei dem für jeden Knoten gilt, dass das Element
im Knoten größer ist als das des Elternknotens.
Außerdem sind alle Knoten im Heap linksbündig angeordnet.
2.
- Das größte Element befindet sich auf der letzten Ebene ganz rechts.
- Ja ein Feld in aufsteigend sortierter Reihenfolge ist ein MinHeap, da beim Einfügen größere Elemente
immer als Kinder von kleineren Elementen angeordnet werden.
3.
E, A, S, Y, Q, U, E, S, T, I, O, N
-upHeap:
E, A, S, Y, Q, U, E, S, T, I, O, N
E, A, S, Y, Q, N, E, S, T, I, O, U  | N <-> U
E, A, S, Y, I, N, E, S, T, Q, O, U  | Q <-> I
E, A, S, S, I, N, E, Y, T, Q, O, U  | Y <-> S
E, A, E, S, I, N, S, Y, T, Q, O, U  | S <-> E
A, E, E, S, I, N, S, Y, T, Q, O, U  | A <-> E
-downHeap:
E, A, S, Y, Q, U, E, S, T, I, O, N
A, E, S, Y, Q, U, E, S, T, I, O, N  | A <-> E
A, E, E, Y, Q, U, S, S, T, I, O, N  | S <-> E
A, E, E, S, Q, U, S, Y, T, I, O, N  | S <-> Y
A, E, E, S, I, U, S, Y, T, Q, O, N  | Q <-> I
A, E, E, S, I, N, S, Y, T, Q, O, U  | U <-> N



