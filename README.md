# Distributed Tracing

This application finds numbers and latency of traces.

## Features

* Average latency of a trace

* Number of traces originating in a given service and ending in the other service with a maximum number of hops

* Number of traces originating in a given service and ending in the other service with exact number of hops

* Length of the shortest trace originating in a given service and ending in the other service

* Number of traces originating in a given service and ending in the other service with an average latency less than a given number of hops

## Installation

```sh
# compile
javac -cp src -d out src/instana/Tracing.java

# run
java -cp out instana.Tracing
```
## Getting Started

1. Enter graph text file
2. Enter command to run

## Examples
```sh
# find the average latency of a trace
trace-latency A-B-C
```

## Graph Text File
This is comma-separated text file. Only accept alphabet letter representing service and numeric characters representing the latency in the first line. For example: AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7

## Commands
1. Average latency of a trace
```sh
trace-latency  <origin_service>-<intermediate_service>-<destination_service>
# e.g.: trace-latency A-B-C
```

2. Number of traces between given services with a maximum number of hops
```sh
count-trace-with-max-hops  <origin_service>-<destination_service><max_hops>
# e.g.: count-trace-with-max-hops C-C3
```

3. Number of traces between given services with exact number of hops
```sh
count-trace-with-exact-hops  <origin_service>-<destination_service><exact_hops>
# e.g.: count-trace-with-exact-hops A-C4
```

4. Length of the shortest trace between given services
```sh
shortest-length  <origin_service>-<destination_service>
# e.g.: shortest-length A-C
```

5. Number of traces between given services with an average latency less than a given number of hops
```sh
count-trace-with-max-latency  <origin_service>-<destination_service>
# e.g.: count-trace-with-max-latency C-C30
```

6. Run the above commands listed in the input file. The commands are separated by comma.
```sh
batch-job  <file_path>
# e.g.: batch-job commands.txt
```