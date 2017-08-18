This is an implementation of Hill Climbing and Stimulated Annealing Local Search

Please follow the following instructions to run the project

1. Open the project with Netbean IDE 8.2
2. Run the Project

Modify the Run parameters (Project -> Properties -> Run) to make sure the path of out file is correct
   
    - args[0]: String of output file name, Default is "data\output.txt"

Some information about the configuration of the algorithms
- N: the number of Queens, default is 8.
- no_ramdom_states: the number of random start states, default is 10,000
- alpha = 0.98; //a factor to reduce temperature gradually in the annealing algorithm. should be 0.85->0.99
- epsilon = 1e-6; // the condition to halt doing annealing algorithm
- T: temperature for the the annealing algorithm

For T, we care about 4 things:
+ Start temperature: In this problem, I set up the value of 1+ N*(N-1)/2 as the start temperature for T.
Because, the objective function for the algorithm is the number of queens attacked directly or indirectly, called h
So in the worst case, each queen can attack all of others, we have h = N*(N+1)/2. This means that the maximum value of h is N*(N+1)/2
+ How to decrease T slowly to get a goal state (a big gap of jump can cause missing a goal).
Here I simply set T =T * alpha where alpha < 1 as a linear method. alpha should be in the range 0.85 - 0.99
the bigger alpha is the higher number of start states going to a goal is, but the running time is more
+ Stop temperature: the textbook says when T = 0 we stop the algorithm. Here I set T < epsilon = 1e-6.
It is reasonable for low enough to stop the progress of annealing because of the  decrement of T as mentioned above and outcomes I obtained in the output file.
+ Iterations at each Temperature: Here I follow the algirithm from the textbook. one of each T

With the congiguration: no_ramdom_states = 10,000;N=8; alpha = 0.98; epsilon = 1e-6; T = T * alpha (initially T = 1+ N*(N-1)/2)
I got the outcomes as follows

BUILD SUCCESSFUL (total time: 2 minutes 16 seconds
For Annealing: 65.01%, 178.65, 197.88
+ Total number of moves in success states: 1161408
+ Total number of start states go to a goal: 6501/10000
+ Total number of moves in stuck states: 692376
+ Total number of start states get stuck: 3499/10000
For Hill Climbing:14.03%, 4.09, 3.07
+ Total number of moves in success states: 5744
+ Total number of start states go to a goal: 1403/10000
+ Total number of moves in stuck states: 26373
+ Total number of start states got stuck: 8597/10000


----------------------------------------------------------------------------------------------
I output the progess of the running project on the screen
The outputs look like:
----------------------------------------------------------------------------------------------

eight_queen_problem.Eight_queen_problem.initialize_start_states():
-------------------------------------------------------------------
[1, 7, 0, 2, 0, 0, 1, 0] Value:  10
[3, 5, 0, 6, 5, 6, 5, 7] Value:  8
[7, 0, 5, 6, 0, 0, 4, 7] Value:  6
[5, 5, 0, 7, 3, 1, 5, 2] Value:  6
[2, 1, 1, 1, 0, 0, 5, 1] Value:  10
[1, 1, 1, 2, 6, 6, 5, 1] Value:  12
[5, 6, 4, 0, 3, 3, 0, 2] Value:  5
...
-------------------------------------------------------------------
eight_queen_problem.Eight_queen_problem.do_hill_climbing()
-------------------------------------------------------------------
Get stuck with start state 0 [1, 7, 0, 2, 0, 0, 1, 0]value: 10 Stuck state [3, 7, 0, 2, 0, 5, 1, 4] value = 1: number or moves: 3
Get stuck with start state 1 [3, 5, 0, 6, 5, 6, 5, 7]value: 8 Stuck state [3, 5, 0, 4, 1, 6, 2, 7] value = 1: number or moves: 3
Get stuck with start state 2 [7, 0, 5, 6, 0, 0, 4, 7]value: 6 Stuck state [2, 0, 5, 6, 1, 3, 4, 7] value = 2: number or moves: 3
Get stuck with start state 3 [5, 5, 0, 7, 3, 1, 5, 2]value: 6 Stuck state [5, 4, 0, 7, 3, 1, 6, 2] value = 1: number or moves: 2
Success with start state 4 [2, 1, 1, 1, 0, 0, 5, 1]value: 10 Success state [2, 6, 1, 7, 4, 0, 3, 5]: number or moves: 5
Get stuck with start state 5 [1, 1, 1, 2, 6, 6, 5, 1]value: 12 Stuck state [4, 1, 7, 2, 6, 3, 5, 0] value = 1: number or moves: 4
Get stuck with start state 6 [5, 6, 4, 0, 3, 3, 0, 2]value: 5 Stuck state [5, 1, 4, 0, 7, 3, 7, 2] value = 1: number or moves: 3
Get stuck with start state 7 [5, 4, 0, 0, 7, 2, 7, 0]value: 8 Stuck state [5, 4, 0, 3, 6, 2, 7, 1] value = 1: number or moves: 3
Get stuck with start state 8 [7, 6, 6, 3, 5, 0, 7, 3]value: 5 Stuck state [2, 6, 1, 3, 5, 0, 7, 4] value = 1: number or moves: 3
Get stuck with start state 9 [3, 7, 2, 2, 3, 4, 2, 5]value: 8 Stuck state [3, 7, 2, 4, 6, 4, 0, 5] value = 1: number or moves: 3
...
Total number of moves in success states: 5693

Total number of start states go to a goal: 1395/10000

Total number of moves in stuck states: 26285

Total number of start states got stuck: 8605/10000
13.95%, 4.08, 3.05
-------------------------------------------------------------------
eight_queen_problem.Eight_queen_problem.do_annealing()
-------------------------------------------------------------------
Success with start state 0 [1, 7, 0, 2, 0, 0, 1, 0]value: 10 Success state [1, 5, 0, 6, 3, 7, 2, 4]: number or moves: 160
Get stuck with start state 1 [3, 5, 0, 6, 5, 6, 5, 7]value: 8 Stuck state [3, 5, 2, 5, 1, 6, 4, 0] value = 1: number or moves: 202
Get stuck with start state 2 [7, 0, 5, 6, 0, 0, 4, 7]value: 6 Stuck state [4, 2, 5, 3, 6, 0, 5, 1] value = 1: number or moves: 195
Success with start state 3 [5, 5, 0, 7, 3, 1, 5, 2]value: 6 Success state [2, 5, 1, 6, 0, 3, 7, 4]: number or moves: 211
Success with start state 4 [2, 1, 1, 1, 0, 0, 5, 1]value: 10 Success state [4, 0, 3, 5, 7, 1, 6, 2]: number or moves: 157
Get stuck with start state 5 [1, 1, 1, 2, 6, 6, 5, 1]value: 12 Stuck state [7, 2, 5, 1, 4, 0, 3, 6] value = 1: number or moves: 198
Get stuck with start state 6 [5, 6, 4, 0, 3, 3, 0, 2]value: 5 Stuck state [0, 3, 5, 7, 1, 4, 2, 5] value = 1: number or moves: 189
Get stuck with start state 7 [5, 4, 0, 0, 7, 2, 7, 0]value: 8 Stuck state [6, 0, 5, 1, 4, 0, 7, 3] value = 1: number or moves: 218
Get stuck with start state 8 [7, 6, 6, 3, 5, 0, 7, 3]value: 5 Stuck state [5, 0, 6, 3, 7, 7, 4, 2] value = 1: number or moves: 204
...
Total number of moves in success states: 1161329

Total number of start states go to a goal: 6501/10000

Total number of moves in stuck states: 692828

Total number of start states get stuck: 3499/10000
65.01%, 178.64, 198.01

BUILD SUCCESSFUL (total time: 2 minutes 21 seconds)

