#!/bin/bash

graphToPlot=$1

gnuplot -persist -e "set term png; set output \"${graphToPlot}.png\"; \
set title \"Evolution of error\"; \
set xlabel 'Iteration (index)'; set ylabel 'Error (amount)'; \
set auto x; \
set auto y; \
plot '${graphToPlot}' using 1:2 with lines"