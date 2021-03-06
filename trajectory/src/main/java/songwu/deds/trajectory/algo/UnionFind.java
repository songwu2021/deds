package songwu.deds.trajectory.algo;

import java.util.Arrays;

public class UnionFind
{
    private int[] id; // access to component id (site indexed)
    private int count; // number of components
    public UnionFind(int N)
    { // Initialize component id array.
        count = N;
        id = new int[N];
        for (int i = 0; i < N; i++)
            id[i] = i;
    }
    public int count()    {
        return count;
    }
    public boolean connected(int p, int q)    {
        return find(p) == find(q);
    }
    public int find(int p){
        return id[p];
    }
    public void union(int p, int q){
        int pID = find(p);
        int qID = find(q);
        if (pID == qID) return;
        for (int i = 0; i < id.length; i++)
            if (id[i] == pID) id[i] = qID;
        count--;
    }
    public int[] status(){
        return id;
    }

    public static void main(String[] args) {
        UnionFind unionFind = new UnionFind(6);
        unionFind.union(1,2);
        unionFind.union(4,5);
        System.out.println(Arrays.toString(unionFind.status()));
    }
}
