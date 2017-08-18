/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eight_queen_problem;

/**
 *
 * @author Do Thanh Thuy
 */
public class state {
    private int[] Q;
    private int value;    

state(int[] q){
    int n = q.length; Q = new int[n];
    System.arraycopy(q, 0, Q, 0, n);
    int no_attacked_queen;no_attacked_queen = 0;        
    for (int i = 0; i<n-1;i++){
        for (int j = i+1;j<n;j++){
            if (Q[i] == Q[j]) no_attacked_queen++;
                //in the same row
            /*else if (Math.abs(i - Q[i]) == Math.abs(j-Q[j])) {no_attacked_queen +=1;} //in diagonals parallel main diagonal + main diagonal
            else if (Q[i] + i == Q[j] + j){no_attacked_queen +=1;}*///in other diagonals
            else if (Math.abs(Q[i] - Q[j]) == j-i) no_attacked_queen++;
        }
    }
    value = no_attacked_queen;
}
public int get_value(){
    return this.value;
}
public int[] get_Q(){
    return Q;
}
}

