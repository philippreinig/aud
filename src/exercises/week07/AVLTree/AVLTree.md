1)

Ein AVL-Baum ist ein ausbalancierter Binärbaum. Ausbalanciert heißt dabei, dass der maximale Höhenunterschied an jedem
Knoten des Baums zwischen linkem und rechtem Kindbaum maximal 1 betragen darf. Einen Baum auszugleichen sorgt dafür,
dass er schneller durchsucht werden kann. Ein AVL-Suchbaum kann in O(n) = log(n) dursucht werden. Degeneriert er zu
einer Liste gilt O(n) = n.

2)

AVL-Baum aus [14, 15, 17, 7, 5, 10, 16] erstellen:

14 einfügen 14 15 einfügen:
14
\
15 17 einfügen 14
\
15
\
17 -> AVL-Eigenschaft verletzt -> Linksrotation 15 /  \
14 17 7 einfügen 15 /  \
14 17 / 7 5 einfügen 15 /  \
14 17 / 7 / 5 -> AVL-Eigenschaft verletzt -> Rechtsrotation am Knoten 7 15 /  \
7 17 / \
5 14 10 einfügen 15 /  \
7 17 / \
5 14 JFrame jf = new JFrame(); / 10 -> AVL-Eigenschaft am Knoten 15 verletzt -> Trinode-Restructuring der Knoten 7, 14,
15 14 /  \
7 15 / \     \
5 10 17 16 einfügen 14 /    \
7 15 / \       \
5 10 17 / 16 -> AVL-Eigenschaft am Knoten 15 verletzt -> Doppelrotation RL 14 /    \
7 15 / \       \
5 10 16
\
17 14 /    \
7 16 / \ /  \
5 10 15 17




    


    
            