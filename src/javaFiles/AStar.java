package javaFiles;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AStar implements PathingStrategy {

    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors){
        List<Node> open = new LinkedList<>();
        open.add(new Node(start, null,
                start, end));
        List<Node> closed = new LinkedList<>();
        List<Point> searched = new LinkedList<>();
        List<Point> path;
        //System.out.println(end+"t");
        //System.out.println(start);
        while(!open.isEmpty()){
            //System.out.println("test");
            Node current = findLowestF(open);
            //System.out.println(current.pos + "c");
            searched.add(current.pos);
            if(withinReach.test(current.pos,end)){
                closed.add(current);
                path = constructPath(closed.get(closed.size()-1));
                //System.out.println("done");
                return path;
            }
            open.remove(current);
            List<Point> next = potentialNeighbors.apply(current.pos).filter(canPassThrough).collect(Collectors.toList());
            for (Point neighbor : next) {
                if (!searched.contains(neighbor)) {
                    //System.out.println(neighbor + "n");
                    Node neighborNode = new Node(neighbor, current, start, end);
                    if(!open.contains(neighborNode)){
                        open.add(neighborNode);
                    }
                }
            }
            closed.add(current);
        }
        path = constructPath(closed.get(closed.size()-1));
        //System.out.println("break loop");
        return path;
    }

    private List<Point> constructPath(Node node){
        List<Point> path = new LinkedList<>();
        if(node.parent == null){
            path.add(node.pos);
        }
        while (node != null){
            path.add(node.pos);
            node = node.parent;
        }
        return path;
    }

    private Node findLowestF(List<Node> nodes){
        Node lowNode = nodes.get(0);
        int lowest = nodes.get(0).g + nodes.get(0).h;
        for(Node node:nodes){
            int f = node.h + node.g;
            if (f < lowest) {
                lowest = f;
                lowNode = node;
            }
        }
        return lowNode;
    }


     class Node {
        private Node parent;
        private Point pos;
        private int h;
        private int g;

        private Node(Point pos, Node parent, Point start, Point goal){
            this.pos = pos;
            this.parent = parent;
            g = pos.manhattan(start);
            h = pos.manhattan(goal);
        }

         @Override
         public boolean equals(Object o) {
             if (this == o) return true;
             if (o == null || getClass() != o.getClass()) return false;

             Node node = (Node) o;

             return pos != null ? pos.equals(node.pos) : node.pos == null;
         }

         @Override
         public String toString() {
             return "Node{" +
                     "parent=" + parent +
                     ", pos=" + pos +
                     '}';
         }
     }

}
