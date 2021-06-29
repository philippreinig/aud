package aud.example.graph;

import java.io.File;
import java.util.Random;
import java.util.Scanner;

import aud.HashMap;

/** a really simple game, which lets you explore a maze... pardon: a graph */
public class RetroMaze {
  MyGraph g_     = null;
  MyNode  room_  = null;
  MyNode  entry_ = null;
  MyNode  exit_  = null;
  boolean hints_ = false;
  Scanner input = new Scanner(System.in);
  int     count_ = 0;

  public RetroMaze(MyGraph g,MyNode entry,MyNode exit) {
    g_=g;
    entry_=room_=entry;
    exit_=exit;
  }

  void start() {
    System.out.println
      ("You find youself in a maze, and you have to find the exit.\n"+
       "You start in room "+entry_.getLabel()+
       " and the exit is in room "+exit_.getLabel()+".\n"+
       "Good luck!\n");

    while (room_!=exit_) {
      go();
    }
    System.out.println("Congratulations you found the exit!");
  }

  void go() {
    System.out.println
      ("You are in room "+room_.getLabel()+
       ((hints_ && room_.ord>=0) ? " (been there)" : "")+".\n"+
       "There are corridors leading to the following rooms:\n");
    int i=1;
    HashMap<Integer,MyNode> options=new HashMap<Integer,MyNode>();
    for (MyEdge e : g_.getOutEdges(room_)) {
      MyNode node=(MyNode) e.destination();
      if (node==room_)
        node=(MyNode) e.source(); // undirected graph


      options.insert(i,node);
      System.out.println
        ("["+i+"] "+node.getLabel()+
         ((node==exit_ ? " with big sign saying \"EXIT\"" : ""))+
         ((hints_ && node.ord>=0) ? "(been there)" : ""));
      ++i;
    }
    MyNode next = null;

    while (next==null) {
      System.out.print("Which way to go now? ('0' toggles hints)\n> ");
      i=input.nextInt();
      if (i==0) {
        hints_=!hints_;
        go();
        return;
      }
      if (i==-1) {
        System.out.println("Give up?\n");
        System.exit(-1);
      }
      next=options.find(i);
    }

    room_=next;
    room_.ord=++count_; // been there
  }

  static MyNode pickNode(MyGraph g) {
    while (true) { // graph must have more than 2 nodes
      int i=(new Random()).nextInt()%g.getNumNodes();
      for (MyNode node : g) {
        if (i--==0)
          return node;
      }
    }
  }

  public static void main(String[] args) {
    MyGraph g=new MyGraph(false); // easy mode: undirected graph

    if (args.length==0)
      g=new GraphP88();
    else {
      new GraphParser(g.getAbstractGraph()).parse(new File(args[0]));
    }

    MyNode entry=pickNode(g), exit=null;
    while ((exit=pickNode(g))==entry) {}

    RetroMaze maze=new RetroMaze(g,entry,exit);

    maze.start();
  }
}
