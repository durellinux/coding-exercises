#!/usr/bin/env bash

echo "Solving B"
python solver.py input/b_lovely_landscapes.txt $1 > b_out.txt

echo "Solving C"
python solver.py input/c_memorable_moments.txt $1 > c_out.txt

echo "Solving D"
python solver.py input/d_pet_pictures.txt $1 > d_out.txt

echo "Solving E"
python solver.py input/e_shiny_selfies.txt $1 > e_out.txt