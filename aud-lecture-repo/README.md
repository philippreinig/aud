Code examples for lecture Algorithmen&Datenstrukturen (AuD)
===

[![master:p]][master]

[master]: https://vccourses.cs.ovgu.de/roessl/aud21-examples/commits/master
[master:p]: https://vccourses.cs.ovgu.de/roessl/aud21-examples/badges/master/pipeline.svg

This software provides code examples for the lecture [Algorithmen&Datenstrukturen](https://aud.vc.cs.ovgu.de).

Copyright by Christian Roessl (roessl@isg.cs.uni-magdeburg.de).

* I will publish code incrementally during the term. This means the contents are subject to change!

* I'm grateful for any bug reports and improvements!

* Parts of the examples require installation of
  - **[GraphViz]** a set command line programs for [graph drawing] and
  - **[Batik]** a Java library for rendering Scalable Vector Graphics ([SVG])

  * Please refer to the **tutorials** for help with installation.

* You can use the [Makefiles] for building. Probably, importing the source to, e.g., IntelliJ or Eclipse is more convenient.

* Quick installation (Linux, no IDE)

```
cd src
make -C aud all

curl https://mirror.netcologne.de/apache.org/xmlgraphics/batik/binaries/batik-bin-1.14.tar.gz | tar xz

export CLASSPATH=batik-1.14/\*:$CLASSPATH:.

java aud.util.GraphVizDemo
```

[Makefiles]: http://www.gnu.org/software/make/manual/make.html
[GraphViz]: http://www.graphviz.org/
[Batik]: http://xmlgraphics.apache.org/batik/
[SVG]: http://en.wikipedia.org/wiki/Scalable_Vector_Graphics
[graph drawing]: http://en.wikipedia.org/wiki/Graph_drawing
