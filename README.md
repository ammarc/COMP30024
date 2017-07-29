# Slider Game Agent
This repository contains a game-playing AI for the game of Slider. The agent was implemented
by Ammar and Nam for the Artificial Intelligence subject (COMP30024), at the University of
Melbourne, during Semester 1, 2017.



## Package Structure:

com
 |---namnammar
     |---agents
         |--- Athena                    Main Playing Agent, using Alpha Beta algorithm with pre-defined max depth
         |--- Me                        Supporting Agent, get moves from user input
         |--- Simple                    Supporting Agent, try all moves in order
     |---components
         |--- Board                     Board class, using 2D arrays
         |--- Horizontal                Class for Horizontal Pieces
         |--- Piece                     Parent class representation of a piece
         |--- Player                    Class for Player
         |--- Vertical                  Class for Horizontal Pieces

## Implementation

Athena, our AI playing agent, is implemented using Alpha-Beta Pruning with pre-defined maximum depth.

So at every stage of the game, when move is requested from Athena, all legal moves of the player is tested using
minimax strategy with pruning. Lookahead moves are stopped from being evaluated/expanded if the utility value of Board
at that stage is smaller than a previously examined move by storing the values in alpha and beta. Beta is the minimum
value of last beta and min value at that node. Similarly the same applies for alpha.

Worst case scenario of this algorithm is O(b^d) where b is the branching factor and d is the depth of the tree.
Like defined in the algorithm, for board of dimension 6 or 7, depth d is set at 8 to satisfy time constraints (30s)
of the assignment.
Maximum b is 3(n-1), as there are maximum (n-1) pieces that the player controls, and each piece has maximum 3 moves.
--> worst case scenario is O(n^8)

Best case scenario is O(n^4) when the moves ordering is optimal.

We tried to implement sorting of moves at the initial level, but didn't significantly improve the performance of the
algorithm, so that means that our heuristics in ordering is not good. Although there were more prunings, the cost of
was too taxing on the performance, and therefore not worth pursuing.

One of our earlier implementations had been done using a deep copy of the board at each transition from one state to
the next in our alpha beta, although this worked and won against simple AI's, it proved quite large in memory. So
to get around this, we implemented an undo move method in board, to undo each move made. This meant that there is
in fact only one board, and alpha beta runs on that board. This improved our performance drastically and we were able
to go to depth of up to 10 with ease, as compared to our previous implementation, where we struggled to go past 6.

Our scoring function is made such that each move towards the goal for horizontal and vertical are rewarded. Also if
the score is set such that a board with less pieces is more desirable. Finally, we heavily tax any lateral moves
made by the player, as we found out during playing against others, that making lateral moves generally differentiate
between a win and a loss; blocking the other player is also a good strategy, so we add reward for that as well in
our scoring function.

## Heuristics

Our evaluation function of a board state takes into account the following conditions, and weigh them differently:

* number of pieces left of the player
* the position of each piece (how close they are to the edge to move off)
* whether any opponent piece is blocked

The weight of each factor is derived through experiments and trials of running the algorithm against the Simple Agent.
