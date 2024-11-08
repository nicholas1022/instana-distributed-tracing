# Introduction

This CLI application finds the data about the traces of a microservice application.
* Average latency of the trace

# Assumption
1. It allows a trace that its intermediate nodes and the destination node are same.  
e.g.: C -> C  
C,D,C  
C,D,C,D,C  
C,D,C,E,B,C
2. Nodes are represented by A - E only

3. No negative latency

4. The supplied parameters for problem #6, 7, and 10 are not too big to cause application out of memory.  
e.g.:  
#6, #7 -> 100  
#10 -> 100

# Methodology
This application uses graph to represent and manipulate a trace.

# Implementation
## Graph
* Use map of map to implement the graph with latency data
* Developed two data models TraceHopState and TraceLatencyState for manipulation of the trace

## Trace
* Find the number of trace with max or exact hop using BFS
* Find the shortest trace using Dijkstra's algorithm

## Data Input
* Depended on the abstract to decouple the code and allow easy modification 
