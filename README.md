Flight Plan Project for 3345 Algorithms and Data Structures.

Input flight data file name, requested flights file name, and output file name.

Report: This program is split into 4 parts. The edge/path (City class), vertex/linked list (Cities class), stack 
(TheStack class), the adjacency list (AdjacencyList class), and the flight calculation (FlightCalculation class). 
The adjacency list and flight calculation classes are where the core of the functionality happens. The 
AdjacencyList class utilizes the City and Cities class to create the actual structure of the graph. The 
FlightCalculation class uses the AdjacencyList and TheStack class to figure out all possible paths from the the 
requested flights file. 

Warning: When there are multiple paths with the minimum time/cost, a random one is selected to be a part of the three 
"shortest" paths if the minimum paths is already at the limit of 3.

