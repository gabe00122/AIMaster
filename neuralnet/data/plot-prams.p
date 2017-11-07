set xyplane 0;
set dgrid3d 5, 5;
set pm3d;
splot 'output/meta.csv' using 1:2:4 with pm3d;

