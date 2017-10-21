set xyplane 0;
set dgrid3d 25, 25;
set pm3d;
splot 'output/grid.csv' using 1:2:3 with pm3d title columnhead;

