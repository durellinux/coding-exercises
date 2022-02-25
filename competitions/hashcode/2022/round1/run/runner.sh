#!/bin/bash
mkdir -p $1
cp -r ../solver ./$1

pushd $1/solver

python solver.py ../../../input/b_better_start_small.in.txt > ../b_$1.txt
python solver.py ../../../input/e_exceptional_skills.in.txt > ../e_$1.txt
python solver.py ../../../input/c_collaboration.in.txt > ../c_$1.txt
python solver.py ../../../input/f_find_great_mentors.in.txt > ../f_$1.txt
python solver.py ../../../input/d_dense_schedule.in.txt > ../d_$1.txt

popd
