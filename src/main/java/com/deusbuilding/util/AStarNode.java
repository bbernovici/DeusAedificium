package com.deusbuilding.util;

import java.util.ArrayList;
import java.util.List;

public class AStarNode {
        public List<AStarNode> neighbors = new ArrayList<AStarNode>();
        public AStarNode parent;
        public int f;
        public int g;
        public int h;
        public int x;
        public int y;
        public int cost;
        public int type;
}
