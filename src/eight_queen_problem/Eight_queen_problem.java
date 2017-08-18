/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eight_queen_problem;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Do Thanh Thuy
 */
public class Eight_queen_problem {

    /**
     * @param args the command line arguments
     */
    private static final int N = 8;// number of queens
    private static final int no_ramdom_states = 10000; //number of random start states
    private static ArrayList<state> random_state_list; //list of random states
    private static final double alpha = 0.98;//a factor to reduce temperature gradually. should be 0.85->0.99
    private static final double epsilon = 1e-6;

    public static void main(String[] args) throws IOException {
        // TODO code application logic here
    
    initialize_start_states();
    String hill_climbing_ret_val = do_hill_climbing();
    String annealing_ret_val = do_annealing();
    String out_fname = args[0];
    writeOutFile(out_fname, hill_climbing_ret_val + "\n" + annealing_ret_val);
    
    }

    private static String do_hill_climbing() {
        System.out.println("-------------------------------------------------------------------");
        System.out.println("eight_queen_problem.Eight_queen_problem.do_hill_climbing()");
        System.out.println("-------------------------------------------------------------------");
        int no_success_moves = 0; int no_success_state = 0;
        int no_stuck_moves = 0; int no_stuck_state = 0;
        for (int i = 0; i<no_ramdom_states;i++){
            state current_state;
            current_state = new state(random_state_list.get(i).get_Q());
            int no_moves = 0;
            while (true){
                state next_state = get_the_best_succecssor(current_state);
                if (next_state.get_value() >= current_state.get_value()) break;
                current_state = next_state;
                no_moves++;
            }
            if (current_state.get_value() == 0){
                no_success_state++;
                no_success_moves += no_moves; //current_state is a solution
                System.out.println("Success with start state " + String.valueOf(i) + " "+ Arrays.toString(random_state_list.get(i).get_Q())+ "value: " + String.valueOf(random_state_list.get(i).get_value()) +  " Success state " + Arrays.toString(current_state.get_Q())+ ": number or moves: " + String.valueOf(no_moves));
            }else{
                no_stuck_state++;
                no_stuck_moves += no_moves;
                System.out.println("Get stuck with start state " + String.valueOf(i) + " " + Arrays.toString(random_state_list.get(i).get_Q())+ "value: " + String.valueOf(random_state_list.get(i).get_value()) + " Stuck state " + Arrays.toString(current_state.get_Q())+ " value = " + String.valueOf(current_state.get_value()) + ": number or moves: " + String.valueOf(no_moves));
            }
        }        
        System.out.println("\nTotal number of moves in success states: " + String.valueOf(no_success_moves)); // + "/" + String.valueOf(no_ramdom_states)
        System.out.println("\nTotal number of start states go to a goal: " + String.valueOf(no_success_state) + "/" + String.valueOf(no_ramdom_states));
        System.out.println("\nTotal number of moves in stuck states: " + String.valueOf(no_stuck_moves));                
        System.out.println("\nTotal number of start states got stuck: " + String.valueOf(no_stuck_state) + "/" + String.valueOf(no_ramdom_states));                
        double percent;
        percent = (double)no_success_state/no_ramdom_states; percent = percent * 100;
        double avg_success_moves = 0;
        if (no_success_state > 0) avg_success_moves = (double)no_success_moves/no_success_state;
        double avg_stuck_moves = 0;
        if (no_stuck_state > 0) avg_stuck_moves = (double)no_stuck_moves/no_stuck_state;
        DecimalFormat double_format = new DecimalFormat(".##"); 
        percent= Double.valueOf(double_format.format(percent));
        String ret_val = String.valueOf(percent) + "%, " + String.format("%.2f", avg_success_moves) + ", " + String.format("%.2f", avg_stuck_moves);
        System.out.println(ret_val);
        return ret_val;
    }

    private static String do_annealing() {
        System.out.println("-------------------------------------------------------------------");
        System.out.println("eight_queen_problem.Eight_queen_problem.do_annealing()");
        System.out.println("-------------------------------------------------------------------");
        int no_success_moves = 0; int no_success_state = 0;
        int no_stuck_moves = 0; int no_stuck_state = 0;
        for (int i = 0; i<no_ramdom_states;i++){
            state current_state;
            current_state = new state(random_state_list.get(i).get_Q());
            int no_moves = 0;
            double T = ((N-1)*N)/2 + 1; //The maximum number of attacked queen directly or indirectly (plus 1)
            while (true){
                T = T * alpha;
                if (T <= epsilon) break;
                state next_state = get_random_succecssor(current_state);
                int delta_E = current_state.get_value() - next_state.get_value();
                if (delta_E > 0) {current_state = next_state; no_moves++;}
                else{
                    double probability = Math.exp(delta_E/T);
                    Random rnd = new Random(); double r = rnd.nextDouble();
                    if (probability > r){
                        current_state = next_state; no_moves++;
                    }                    
                }
            }
            if (current_state.get_value() == 0){
                no_success_state++;
                no_success_moves += no_moves; //current_state is a solution
                System.out.println("Success with start state " + String.valueOf(i) + " "+ Arrays.toString(random_state_list.get(i).get_Q())+ "value: " + String.valueOf(random_state_list.get(i).get_value()) +  " Success state " + Arrays.toString(current_state.get_Q())+ ": number or moves: " + String.valueOf(no_moves));
            }else{
                no_stuck_state++;
                no_stuck_moves += no_moves;
                System.out.println("Get stuck with start state " + String.valueOf(i) + " " + Arrays.toString(random_state_list.get(i).get_Q())+ "value: " + String.valueOf(random_state_list.get(i).get_value()) + " Stuck state " + Arrays.toString(current_state.get_Q())+ " value = " + String.valueOf(current_state.get_value()) + ": number or moves: " + String.valueOf(no_moves));
            }
        }        
        System.out.println("\nTotal number of moves in success states: " + String.valueOf(no_success_moves)); // + "/" + String.valueOf(no_ramdom_states)
        System.out.println("\nTotal number of start states go to a goal: " + String.valueOf(no_success_state) + "/" + String.valueOf(no_ramdom_states));
        System.out.println("\nTotal number of moves in stuck states: " + String.valueOf(no_stuck_moves));                
        System.out.println("\nTotal number of start states get stuck: " + String.valueOf(no_stuck_state) + "/" + String.valueOf(no_ramdom_states));                
    
        double percent;
        percent = (double)no_success_state/no_ramdom_states; percent = percent * 100;
        double avg_success_moves = 0;
        if (no_success_state > 0) avg_success_moves = (double)no_success_moves/no_success_state;
        double avg_stuck_moves = 0;
        if (no_stuck_state > 0) avg_stuck_moves = (double)no_stuck_moves/no_stuck_state;
        DecimalFormat double_format = new DecimalFormat(".##"); 
        percent= Double.valueOf(double_format.format(percent));
        String ret_val = String.valueOf(percent) + "%, " + String.format("%.2f", avg_success_moves) + ", " + String.format("%.2f", avg_stuck_moves);
        System.out.println(ret_val);
        return ret_val;
    }
    private static void initialize_start_states() {
        System.out.println("-------------------------------------------------------------------");
        System.out.println("eight_queen_problem.Eight_queen_problem.initialize_start_states():");
        System.out.println("-------------------------------------------------------------------");
        random_state_list = new ArrayList<>(no_ramdom_states);
        for (int i = 0; i < no_ramdom_states; i++){        
            int a[] = new int[N];
            for (int j = 0; j < N; j++){
               Random rnd = new Random();
               a[j] = rnd.nextInt(N);                
       //        a[j] = 0;
            }
            state s = new state(a);
            random_state_list.add(s);            
            System.out.println(Arrays.toString(random_state_list.get(i).get_Q()) + " Value:  " + String.valueOf(random_state_list.get(i).get_value()));        
        }
        //for (int i = 0; i < no_ramdom_states; i++ )
          //  System.out.println(Arrays.toString(random_state_list.get(i).get_Q()) + " Value:  " + String.valueOf(random_state_list.get(i).get_value()));        
            
    }
// return a random successor state of current_state
    private static state get_random_succecssor(state current_state) {
        ArrayList<state> successor_list = get_all_successors(current_state);
        if (successor_list == null) return null;
        int n = successor_list.size();        
        int k = 0;
        if (n>1){
            Random rnd = new Random();
            k = rnd.nextInt(n);
        }
        return successor_list.get(k);        
    }
// return a successor state of current_state such that the value of the successor in minimum among the values of successors
    private static state get_the_best_succecssor(state current_state) {
        ArrayList<state> successor_list = get_all_successors(current_state);
        if (successor_list == null) return null;
        int min_value = 1 + (N*(N-1))/2; int n = successor_list.size();        
        for (int i = 0; i<n;i++)
            if (min_value > successor_list.get(i).get_value()) min_value = successor_list.get(i).get_value();
        ArrayList<state> best_successor_list = new ArrayList<>((N-1)*N);
        for (int i = 0; i<n;i++)
            if (min_value == successor_list.get(i).get_value()) best_successor_list.add(successor_list.get(i));
        int k = 0;
        if (best_successor_list.size()>1){
            Random rnd = new Random();
            k = rnd.nextInt(best_successor_list.size());                            
        }
        return best_successor_list.get(k);        
    }
//return all successors of a state s;    
    private static ArrayList<state> get_all_successors(state s){
        ArrayList<state> succesor_list = new ArrayList<>(N*(N-1));
        
        for (int i = 0; i<N; i++){
            int k = s.get_Q()[i];
            int a[] =  new int[N];        
            for (int t = 0; t<i; t++) a[t] = s.get_Q()[t];
            for (int t = i + 1; t<N; t++) a[t] = s.get_Q()[t];
            for (int t = 0; t < N; t++)
                if (t != k) {
                    a[i] = t; 
                    state the_successor = new state(a);
                    succesor_list.add(the_successor);
                }            
        }                
        return succesor_list;        
    }    
    private static void writeOutFile(String fname, String str) throws FileNotFoundException, IOException{
        BufferedWriter out = null;
        FileWriter fstream = new FileWriter(fname,true); //true tells to append data at the end of the file
        out = new BufferedWriter(fstream);
        out.write(str);
        if (out != null) out.close();
    }
}
