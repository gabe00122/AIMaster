set xyplane 0;
set dgrid3d 5, 5;
set pm3d;
splot 'output/meta_grid.csv' using 1:2:5 with pm3d;

